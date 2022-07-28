package com.classes.utility;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.classes.objects.Listeners;
import com.classes.objects.Utente;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DB {
    FirebaseDatabase db;
    String url;
    String username;
    String password;

    public DB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect() {
        // Create connection
        db = FirebaseDatabase.getInstance("https://mycloset-5fce8-default-rtdb.europe-west1.firebasedatabase.app");
        db.setPersistenceEnabled(true);
        // vedere https://firebase.google.com/docs/database/android/offline-capabilities?authuser=0
        // per più dettagli sulla persistenza
    }

    public void SELECT(String tableName, HashMap<String, String> filters, final Listeners l, Context context) {
        l.onStart();

        DatabaseReference table = db.getReference(tableName);

        if(filters.containsKey("id")) {
            table.orderByKey().equalTo(filters.get("id")).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        ArrayList<Object> list = new ArrayList<>();

                        try {
                            for(DataSnapshot ds : snapshot.getChildren()) {
                                list.add(ds.getValue(Class.forName("com.classes.objects." + tableName)));
                            }
                        } catch(Exception e) {
                            Log.e("ERROR", "Class not found", e);
                        }

                        l.onSuccess(list, context);
                    } else {
                        // negative alert
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // negative alert
                }
            });
        } else {
            table.orderByKey().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    if (dataSnapshot.exists()) {
                        ArrayList<Object> list = new ArrayList<>();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Object obj = null;

                            try {
                                obj = ds.getValue(Class.forName("com.classes.objects." + tableName));
                            } catch (Exception e) {
                                Log.e("ERROR", "Class not found");
                            }

                            Boolean insert = true;

                            if (filters != null) {
                                for (String key : filters.keySet()) {
                                    try {
                                        Class<?> c = obj.getClass();
                                        Field field = c.getField(key);
                                        Object fieldValue = field.get(obj).getClass().cast(field.get(obj));
                                        String fieldString = fieldValue.toString();

                                        if (!fieldString.contains(filters.get(key))) {
                                            insert = false;
                                        }
                                    } catch (Exception e) {
                                        insert = false;
                                        Log.e("ERROR", "Null value has been read");
                                    }
                                }
                            }

                            if (insert) {
                                list.add(obj);
                            }
                            Log.d("DEBUG", "Sending " + list.toString());
                        }

                        l.onSuccess(list, context);
                    } else {
                        // negative alert
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("DEBUG", "Failed to read value.", error.toException());
                    l.onFailed(error);
                }
            });
        }
    }

    public void INSERT(String tableName, Object json) {
        DatabaseReference table = db.getReference(tableName);

        String id = table.push().getKey();
        table.child(id).setValue(json).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // positive alert
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // negative alert
            }
        });
    }

    public void DELETE(String tableName, String id) {
        // maybe also table name and other parameters are needed
        // delete the item by id
        DatabaseReference item = db.getReference(tableName).child(id);
        item.removeValue();
    }

    public void MODIFY(String id, String tableName, HashMap<String, Object> json) {
        // maybe also table name, new value, fields to be modified and other parameters are needed
        DatabaseReference table = db.getReference(tableName);

        DatabaseReference item = table.child(id);

        item.setValue(json).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // positive alert
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // negative alert
            }
        });
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

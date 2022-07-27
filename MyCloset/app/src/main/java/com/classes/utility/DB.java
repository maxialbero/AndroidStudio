package com.classes.utility;

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

import java.util.ArrayList;
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

    public void SELECT(String tableName, HashMap<String, String> filters, final Listeners l) {
        l.onStart();

        String id = "";
        DatabaseReference table = db.getReference(tableName);

        table.orderByKey().equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                l.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEBUG", "Failed to read value.", error.toException());
                l.onFailed(error);
            }
        });

        /*final Map<String, Object> res = new HashMap<String, Object>();

        // should be a prepared statement to avoid SQL injections
        // fill the ArrayList with the query result
        if(filters != null && !filters.isEmpty()) {
            Log.d("DEBUG", filters.toString());
        } else {
            db.collection(table).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot querySnapshot) {
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        Map<String, Object> tmp = doc.getData();
                        for (String key : tmp.keySet()) {
                            res.put(key, tmp.get(key));
                            Log.d("DEBUG", res.toString());
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("DEBUG", "Document not found");
                    // alert error
                }
            });
        }

        Log.d("DEBUG", res.toString());

        return res;*/
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

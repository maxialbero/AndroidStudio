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
    }

    public Map<String, Object> SELECT(String tableName, HashMap<String, String> filters, final Listeners l) {
        Map<String, Object> res = new HashMap<String, Object>();

        l.onStart();    // manage the async reading

        String id = "";
        DatabaseReference table = db.getReference(tableName).child(id);

        table.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                // Al callback dell'evento viene passato uno snapshot contenente tutti i dati in
                // quella posizione, inclusi i dati figlio. Se non ci sono dati, lo snapshot
                // restituirà false quando chiami exists() e null quando chiami getValue() su di esso.
                if(dataSnapshot.getValue() instanceof ArrayList) {
                    ArrayList<HashMap<Object, Object>> list = (ArrayList<HashMap<Object, Object>>) dataSnapshot.getValue();

                    for(HashMap<Object, Object> item : list) {
                        if(item != null) {
                            for(Object key : item.keySet()) {
                                Log.d("DEBUG", list.indexOf(item) + " - " + key + ": " + item.get(key));
                            }
                        }
                    }
                } else {
                    HashMap<Object, Object> item = (HashMap<Object, Object>) dataSnapshot.getValue();
                    if(item != null) {
                        for(Object key : item.keySet()) {
                            Log.d("DEBUG", key + ": " + item.get(key));
                        }
                    } else {
                        // alert item not found
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEBUG", "Failed to read value.", error.toException());
            }
        });

        return res;

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

    public void INSERT(String tableName, HashMap<String, Object> json) {
        String id = "3";
        DatabaseReference table = db.getReference(tableName);
        //Utente u = new Utente("Lorenzo", "Rossi", "Lollo", "password", "lollo@gmail.com");

        // table.push();
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
        /*if(json != null) {
            CollectionReference doc = db.collection(table);
            doc.add(json).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d("DEBUG", "INSERT correctly executed on table " + table + " for the ID " + documentReference.getId());
                    Log.d("DEBUG", "Success");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("DEBUG", "Failure");
                }
            });
        }*/
    }

    public void DELETE(int id) {
        // maybe also table name and other parameters are needed
        // delete the item by id
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

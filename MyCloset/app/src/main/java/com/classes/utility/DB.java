package com.classes.utility;

import androidx.annotation.NonNull;

import com.classes.objects.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DB {
    FirebaseFirestore db;
    String url;
    String username;
    String password;

    // Connection connection; // the DB connection

    public DB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect() {
        // Create connection
        db = FirebaseFirestore.getInstance();
    }

    public Map<String, Object> SELECT(String table, ArrayList<String> params) {
        Map<String, Object> res = new HashMap<String, Object>();

        // should be a prepared statement to avoid SQL injections
        // fill the ArrayList with the query result
        db.document(table).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    if (params != null) {
                        for (String p : params) {
                            Object item = documentSnapshot.getString(p);
                            //res.put(p, item);
                        }
                    } else {
                        //res = documentSnapshot.getData();
                    }
                    //res.put("", "");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // alert error
            }
        });

        return res;
    }

    public void INSERT(String table, HashMap<String, Object> json) {
        // maybe also table name and other parameters are needed
        // execute the insert query

        // check if empty before calling the insert
        DocumentReference doc = db.document(table);

        doc.set(json).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void DELETE(int id) {
        // maybe also table name and other parameters are needed
        // delete the item by id
    }

    public void MODIFY(int id) {
        // maybe also table name, new value, fields to be modified and other parameters are needed
        // delete the item by id
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

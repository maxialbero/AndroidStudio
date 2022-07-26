package com.example.mycloset;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.classes.objects.Listeners;
import com.classes.objects.Utente;
import com.classes.utility.DB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.social_view);

        db = new DB("", "", "");

        db.connect();

        Listeners l = new Listeners() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                /*
                da sistemare coi filtri
                Log.e("DEBUG " ,"Count: " + data.getChildrenCount());

                if(data.getChildrenCount() > 1) {
                    for(DataSnapshot snapshot : data.getChildren()) {
                        // crea un'istanza dell'oggetto, anche se ha campi a null
                        Utente utente = snapshot.getValue(Utente.class);
                        // forse conviene controllare se l'oggetto è null, prima di usarlo
                        if(utente != null) {
                            Log.d("DEBUG", utente.toString());
                        } else {
                            // error
                        }
                    }
                } else {
                    Utente utente = data.getValue(Utente.class);
                    if(utente != null) {
                        Log.d("DEBUG", utente.toString());
                    } else {
                        // errro
                    }
                }
                */
                // Al callback dell'evento viene passato uno snapshot contenente tutti i dati in
                // quella posizione, inclusi i dati figlio. Se non ci sono dati, lo snapshot
                // restituirà false quando chiami exists() e null quando chiami getValue() su di esso.
                if(data.getValue() instanceof ArrayList) {
                    ArrayList<HashMap<Object, Object>> list = new ArrayList<>();
                    list = (ArrayList<HashMap<Object, Object>>) data.getValue();

                    for(HashMap item : list) {
                        if(item != null) {
                            for(Object key : item.keySet()) {
                                Log.d("DEBUG", list.indexOf(item) + " - " + key + ": " + item.get(key));
                            }
                        }
                    }
                } else {
                    HashMap<Object, Object> item = (HashMap<Object, Object>) data.getValue();
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
            public void onFailed(DatabaseError databaseError) {

            }
        };
        db.SELECT("Utente", null, l);

        //Log.d("DEBUG", users.toString());
        /*for(String key : users.keySet()) {
            TextView tw = new TextView(this);
            tw.setText(key);
            cl.addView(tw);
        }*/

        /*recyclerView = findViewById(R.id.recView);

        ArrayList<Item> list = db.SELECT("");
        ChatAdapter chatAdapter = new ChatAdapter(this, list);

        Button button = (Button) findViewById(R.id.Social);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
            }
        });*/

    }

    public void save(View view) {
        TextView username = (TextView) findViewById(R.id.txtUsername);
        EditText t = (EditText) findViewById(R.id.editUsername);
        String text = t.getText().toString();

        String debug;
        if(!text.isEmpty()) {
            Utente utente = new Utente();
            utente.setNome(text);

            db.INSERT("Utente", utente);
            // positive alert
            debug = "Inserito";
        } else {
            // negative alert
            debug = "Non inserito";
        }
        Log.d("DEBUG", debug);
    }
}
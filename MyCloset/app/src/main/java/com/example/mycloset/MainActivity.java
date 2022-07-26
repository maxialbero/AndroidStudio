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
import com.classes.utility.DB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

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

        HashMap<String, Object> json = new HashMap<>();

        json.put("Nome", text);

        String debug;
        if(!text.isEmpty()) {
            db.INSERT("Utente", json);
            debug = "Inserito";
        } else {
            debug = "Non inserito";
        }
        Log.d("DEBUG", debug);
    }
}
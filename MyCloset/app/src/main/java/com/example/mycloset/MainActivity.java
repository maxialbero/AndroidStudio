package com.example.mycloset;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.classes.utility.DB;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.social_view);

        db = new DB("", "", "");

        db.connect();

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
package com.unitn.mycloset;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.classes.utility.DB;
import com.example.mycloset.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB("", "", "");

        /*recyclerView = findViewById(R.id.recView);

        DB db = new DB("", "", "");

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
        EditText t = (EditText) findViewById(R.id.testText);
        String text = t.getText().toString();

        HashMap<String, Object> json = new HashMap<>();

        json.put("Nome", text);
        if(!text.isEmpty()) {
            db.INSERT("Utente", json);
        } else {
            t.setText("DIOCANEEEEEE");
        }
    }
}
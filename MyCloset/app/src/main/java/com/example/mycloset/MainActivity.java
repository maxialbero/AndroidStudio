package com.example.mycloset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.classes.objects.Item;
import com.classes.utility.DB;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recView);

        DB db = new DB("", "", "");

        ArrayList<Item> list = db.SELECT("");

        ChatAdapter chatAdapter = new ChatAdapter(this, list);
    }

//    Button button = (Button) findViewById(R.id.Social);
//    button.setOnClickListener(new View.OnClickListener() {
//        public void onClick(View v) {
//            // Do something in response to button click
//        }
//    });
}
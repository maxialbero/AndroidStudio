package com.example.mycloset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
}
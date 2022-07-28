package com.example.mycloset;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.classes.objects.Utente;
import com.classes.utility.DB;

public class SigninActivity extends AppCompatActivity {
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }

    public void save(View view) {
        // TODO: vai a riprendere l'istanza del DB
        db = new DB("", "", "");
        db.connect();

        EditText txtNome = (EditText) findViewById(R.id.editNome);
        EditText txtCognome = (EditText) findViewById(R.id.editCognome);
        EditText txtUsername = (EditText) findViewById(R.id.editUsername);
        EditText txtEmail = (EditText) findViewById(R.id.editEmail);
        EditText txtPassword = (EditText) findViewById(R.id.editPassword);

        String nome = txtNome.getText().toString();
        String cognome = txtCognome.getText().toString();
        String username = txtUsername.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        String debug;
        if(!(nome.isEmpty() || cognome.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty())) {
            Utente utente = new Utente(nome, cognome, username, email, password);

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
package com.example.mycloset;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.classes.objects.Utente;
import com.classes.utility.DB;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.nio.channels.GatheringByteChannel;
import java.util.concurrent.TimeUnit;

public class SigninActivity extends AppCompatActivity {
    DB db;  // deve essere condiviso con tutta l'applicazione
    GoogleSignInClient client;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        client = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.googleSignIn);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthButton(v);
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthButton(v);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void revokeAccess() {
        client.revokeAccess().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // alert to notify the user
                Log.d("DEBUG", "Accessi revocati");
            }
        });
    }

    public void AuthButton(View v) {
        switch(v.getId()) {
            case R.id.googleSignIn:
                signIn();
                break;
            case R.id.logout:
                signOut();
                break;
        }
    }

    public void signIn() {
        Intent signInIntent = client.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut() {
        client.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // alert to notify the user
                Log.d("DEBUG", "Utente disconnesso");
            }
        });
    }

    public void updateUI(GoogleSignInAccount account) {
        // valuta se mostrare la schermata di login o meno
        // se account è null non è loggato, altrimenti si
        if(account == null) {
            Log.d("DEBUG", "Utente non loggato");
        } else {
            Log.d("DEBUG", "Utente " + account.getDisplayName() + " già loggato");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("DEBUG", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
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
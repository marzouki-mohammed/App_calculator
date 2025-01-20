package com.example.calculator;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button registerButton , logInButton ;
    private FirebaseAuth firebaseAuth;

    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.EmailSignUp);
        passwordEditText = findViewById(R.id.passwordSignUp);
        registerButton = findViewById(R.id.registerbutton);
        logInButton  = findViewById(R.id.loginButton);
        firebaseAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emailEditText.setText("");
                            passwordEditText.setText("");
                            Toast.makeText(MainActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        logInButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Create a User object
                            User user = new User(email, password);

                            // Start the new activity and pass the user object as an extra
                            Intent intent = new Intent(MainActivity.this, Calculator_Contents.class);
                            intent.putExtra("user", user);
                            startActivity(intent);

                            Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.LoginGooglebutton).setOnClickListener(v -> signInWithGoogle());





//        logInButton.setOnClickListener(v -> {
//            String email = emailEditText.getText().toString().trim();
//            String password = passwordEditText.getText().toString().trim();
//
//            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
//                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            firebaseAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(task ->{
//                        if (task.isSuccessful()) {
//                            Intent intent = new Intent(MainActivity.this, Calculator_Contents.class);
//                            startActivity(intent);
//
//                        } else {
//                            Toast.makeText(MainActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//
//        });


//        Button btnLaunch = findViewById(R.id.loginButton);
//        btnLaunch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Calculator_Contents.class);
//                startActivity(intent);
//            }
//        });
//        Button btncreateAccount= findViewById(R.id.SignUpbutton);
//        btncreateAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, signUp.class);
//                startActivity(intent);
//            }
//        });

    }
    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(Exception.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (Exception e) {
                Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Google Sign-In Successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }



}

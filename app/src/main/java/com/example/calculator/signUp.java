package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signUp extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Get references to UI elements
        EditText usernameField = findViewById(R.id.usernameSignUp);
        EditText emailField = findViewById(R.id.EmailSignUp);
        EditText passwordField = findViewById(R.id.passwordSignUp);
        EditText phoneField = findViewById(R.id.phoneSignUp);
        EditText addressField = findViewById(R.id.adressSignUp);
        Button registerButton = findViewById(R.id.registerbutton);

        // Set click listener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect input values
                String username = usernameField.getText().toString().trim();
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();
                String phone = phoneField.getText().toString().trim();
                String address = addressField.getText().toString().trim();

                // Validate inputs
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) ||
                        TextUtils.isEmpty(password) || TextUtils.isEmpty(phone) ||
                        TextUtils.isEmpty(address)) {
                    Toast.makeText(signUp.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a user map
                Map<String, Object> user = new HashMap<>();
                user.put("username", username);
                user.put("email", email);
                user.put("password", password);
                user.put("phone", phone);
                user.put("address", address);

                // Add user to Firestore
                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(signUp.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(signUp.this, Calculator_Contents.class);
                            startActivity(intent);
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(signUp.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}

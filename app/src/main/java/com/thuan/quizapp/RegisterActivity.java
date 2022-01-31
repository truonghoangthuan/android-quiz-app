package com.thuan.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thuan.quizapp.model.User;

public class RegisterActivity extends AppCompatActivity {
    // For register.
    EditText inputNewUsername, inputNewEmail, inputNewPassword;

    Button buttonRegister;

    TextView loginLink;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Firebase
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        inputNewEmail = findViewById(R.id.inputNewEmail);
        inputNewUsername = findViewById(R.id.inputNewUsername);
        inputNewPassword = findViewById(R.id.inputNewPassword);

        buttonRegister = findViewById(R.id.buttonRegister);

        loginLink = findViewById(R.id.loginLink);
    }

    public void loginLinkOnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void buttonRegisterOnClick(View view) {
        User user = new User(inputNewUsername.getText().toString(),
                inputNewPassword.getText().toString(),
                inputNewEmail.getText().toString());
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(user.getUsername()).exists()) {
                    Toast.makeText(RegisterActivity.this, "Username already exist!", Toast.LENGTH_SHORT).show();
                } else {
                    users.child(user.getUsername()).setValue(user);
                    Toast.makeText(RegisterActivity.this, "Create account successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
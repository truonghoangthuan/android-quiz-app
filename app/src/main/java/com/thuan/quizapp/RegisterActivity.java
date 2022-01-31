package com.thuan.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    // For register.
    EditText inputNewUsername, inputNewEmail, inputNewPassword;

    Button buttonRegister;

    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputNewEmail = findViewById(R.id.inputNewEmail);
        inputNewUsername = findViewById(R.id.inputNewUsername);
        inputNewPassword = findViewById(R.id.inputNewPassword);

        buttonRegister = findViewById(R.id.buttonRegister);

        loginLink = findViewById(R.id.loginLink);
    }
}
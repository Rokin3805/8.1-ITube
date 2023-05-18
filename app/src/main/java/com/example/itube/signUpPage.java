package com.example.itube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.itube.Data.UserDatabaseHelper;

public class signUpPage extends AppCompatActivity {

    EditText usernameIn;
    EditText fullName;
    EditText passwordIn;
    EditText passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        usernameIn = findViewById(R.id.editTextUsername);
        fullName = findViewById(R.id.editTextFullName);
        passwordIn = findViewById(R.id.editTextPassword);
        passwordConfirm = findViewById(R.id.editTextConfirmPassword);
    }

    public void createAcc(View view) {
        String username = usernameIn.getText().toString();
        String password = passwordIn.getText().toString();
        String confirmPassword = passwordConfirm.getText().toString();
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(signUpPage.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
        }
        //passwords must match
        else if (!password.equals(confirmPassword)) {
            Toast.makeText(signUpPage.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
        }
        //update database with new user if the username isnt taken
        else {
            UserDatabaseHelper dbHelper = new UserDatabaseHelper(signUpPage.this);
            boolean userExists = dbHelper.checkUser(username);
            if (userExists) {
                Toast.makeText(signUpPage.this, "Username already taken!", Toast.LENGTH_SHORT).show();
            } else {
                long result = dbHelper.insertUser(username, password);
                if (result != -1) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(signUpPage.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }
}
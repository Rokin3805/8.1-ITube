package com.example.itube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.itube.Data.UserDatabaseHelper;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);

    }

    public void logIn (View view)
    {
        String user = username.getText().toString();
        String pass = password.getText().toString();
        UserDatabaseHelper db = new UserDatabaseHelper(this);
        if (db.checkCredentials(user,pass))
        {
            Intent goToEdit = new Intent(this, editPlaylist.class);
            goToEdit.putExtra("username", user);
            startActivity(goToEdit);
        }
        else
        {
            Toast.makeText(MainActivity.this, "INVALID ACCOUNT", Toast.LENGTH_SHORT).show();
        }
    }
    public void signUp(View view)
    {
        Intent signUp = new Intent(this, signUpPage.class);
        startActivity(signUp);
    }
}
package com.example.covidtracer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
    }

    public void forgotPassword(View view) {
    }

    public void login(View view) {
        if(true){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this, "Incorrect email or password. Please try again.", Toast.LENGTH_SHORT).show();
    }

    public void createAccount(View view) {
        Intent intent = new Intent(getApplicationContext(), CreatAccActivity.class);
        startActivity(intent);
    }
}

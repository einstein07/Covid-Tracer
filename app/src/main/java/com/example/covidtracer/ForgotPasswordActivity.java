package com.example.covidtracer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgot_password);
    }

    public void sendmail(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}

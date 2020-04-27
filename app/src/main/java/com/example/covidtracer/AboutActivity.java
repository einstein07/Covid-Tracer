package com.example.covidtracer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);

    }

    public void goHome(View view) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
}

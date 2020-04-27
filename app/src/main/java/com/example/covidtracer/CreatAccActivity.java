package com.example.covidtracer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreatAccActivity extends AppCompatActivity {
    private EditText txt_name;
    private EditText txt_username;
    private EditText txt_email;
    private EditText txt_password1;
    private EditText txt_password2;
    private TextView txv_name;
    private TextView txv_username;
    private TextView txv_email;
    private TextView txv_password1;
    private TextView txv_password2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        txt_name =      findViewById(R.id.full_name);
        txt_username =  findViewById(R.id.username);
        txt_email =     findViewById(R.id.email_create_acc);
        txt_password1 = findViewById(R.id.password1_create_acc);
        txt_password2 = findViewById(R.id.password2_create_acc);

        txv_name =      findViewById(R.id.warning_name);
        txv_username =  findViewById(R.id.warning_username);
        txv_email =     findViewById(R.id.warning_email);
        txv_password1 = findViewById(R.id.warning_password1);
        txv_password2 = findViewById(R.id.warning_password2);

        txt_name.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String text = txt_name.getText().toString();
                    if(!text.isEmpty())
                        txv_name.setVisibility(View.INVISIBLE);
                }
            }
        });

        txt_username.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String text = txt_username.getText().toString();
                    if(!text.isEmpty())
                        txv_username.setVisibility(View.INVISIBLE);
                }
            }
        });
        txt_email.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String text = txt_email.getText().toString();
                    if(!text.isEmpty())
                        txv_email.setVisibility(View.INVISIBLE);
                }
            }
        });
        txv_password1.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String text = txt_password1.getText().toString();
                    if(!text.isEmpty()) {
                        txv_password1.setVisibility(View.INVISIBLE);
                        txv_password2.setText(R.string.passwords_don_t_match);
                    }
                }
            }
        });
        txv_password2.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String text = txt_password2.getText().toString();
                    if(!text.isEmpty() && text.equals(txt_password1.getText().toString()))
                        txv_password2.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void loginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void register(View view) {

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}

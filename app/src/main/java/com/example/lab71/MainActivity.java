package com.example.lab71;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button login;
    CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();

        SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        if(preferences.getBoolean("remember", false)) {
            username.setText(preferences.getString("username", ""));
            password.setText(preferences.getString("password", ""));
            remember.setChecked(preferences.getBoolean("remember", false));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("remember", remember.isChecked());
                editor.putString("username", username.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.commit();

                if(remember.isChecked()) {
                    Toast.makeText(MainActivity.this, "Ghi nhớ thông tin đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(MainActivity.this, FileInputOutput.class);
                startActivity(intent);

            }
        });

    }

    private void mapping() {
        username = findViewById(R.id.edtUsername);
        password = findViewById(R.id.edtPassword);
        login = findViewById(R.id.btnLogin);
        remember = findViewById(R.id.chkRemember);
    }
}
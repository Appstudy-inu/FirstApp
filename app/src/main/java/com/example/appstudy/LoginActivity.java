package com.example.appstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.appstudy.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText Id = findViewById(R.id.Id);
        EditText Pw = findViewById(R.id.Pw);
        Button LoginBtn = findViewById(R.id.LoginBtn);
        TextView FindIdText= findViewById(R.id.FindIdText);
        TextView FindPwText= findViewById(R.id.FindPwText);
        TextView SignupText= findViewById(R.id.SignupText);
        TextView LoginKakao= findViewById(R.id.LoginKakao);
        TextView LoginGoogle= findViewById(R.id.LoginGoogle);
        TextView LoginNaver= findViewById(R.id.LoginNaver);

        SignupText.setOnClickListener(view->{
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
        });
    }
}
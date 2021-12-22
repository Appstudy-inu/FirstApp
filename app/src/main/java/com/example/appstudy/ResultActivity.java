package com.example.appstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ResultActivity extends AppCompatActivity {

    private ImageView imageview;
    private TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String UserName = intent.getStringExtra("UserName");
        String PhotoUrl = intent.getStringExtra("PhotoUrl");

        imageview = findViewById(R.id.PhotoProfile);
        Glide.with(this).load(PhotoUrl).into(imageview);
        textview = findViewById(R.id.UserName);
        textview.setText(UserName);
    }
}
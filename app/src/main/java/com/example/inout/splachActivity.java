package com.example.inout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.inout.aut.LOGIN_Activity;
import com.example.inout.aut.create_accountActivity;

public class splachActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);
        new Handler() .postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splachActivity.this, LOGIN_Activity.class));

            }
        },2000);
    }
}
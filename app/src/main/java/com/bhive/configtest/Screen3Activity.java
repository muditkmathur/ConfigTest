package com.bhive.configtest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class Screen3Activity extends AppCompatActivity {
    private static final String IMG_COLOR_KEY = "bg_color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        getSupportActionBar().setTitle("Screen 3" );

        ImageView imgView = (ImageView) findViewById(R.id.imageView);
        imgView.setBackgroundColor(Color.parseColor(ConfigTestApplication.getConfigTestApplicationContext().getFirebaseRemoteConfig().getString(IMG_COLOR_KEY)));

    }
}

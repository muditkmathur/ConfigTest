package com.bhive.configtest;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Screen2Activity extends AppCompatActivity {

    private static final String IMG_PATH_KEY = "img_path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        getSupportActionBar().setTitle("Screen 2" );

        ImageView imgView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this).load(ConfigTestApplication.getConfigTestApplicationContext().getFirebaseRemoteConfig().getString(IMG_PATH_KEY)).into(imgView);

        Button mNextButton = (Button) findViewById(R.id.btn_next);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Screen2Activity.this, Screen3Activity.class));
            }
        });
    }
}

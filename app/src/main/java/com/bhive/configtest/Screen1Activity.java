package com.bhive.configtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Screen1Activity extends AppCompatActivity {

    private static final String LOADING_PHRASE_CONFIG_KEY = "loading_phrase";
    private static final String TEXT1_TITLE_KEY = "text1_title";
    private static final String TEXT2_TITLE_KEY = "text2_title";
    private static final String TEXT3_TITLE_KEY = "text3_title";

    private TextView mText1TextView;
    private TextView mText2TextView;
    private TextView mText3TextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);
        getSupportActionBar().setTitle("Screen 1 " );

        mText1TextView = (TextView) findViewById(R.id.textView) ;
        mText2TextView = (TextView) findViewById(R.id.textView2) ;
        mText3TextView = (TextView) findViewById(R.id.textView3) ;

        FloatingActionButton actionBtnDownload = (FloatingActionButton) findViewById(R.id.action_btn_download);
        actionBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchDetails();
            }
        });


        // Fetch discount config.
        fetchDetails();

        Button mNextButton = (Button) findViewById(R.id.btn_next);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Screen1Activity.this, Screen2Activity.class));
            }
        });
    }

    /**
     * Fetch discount from server.
     */
    private void fetchDetails() {
        final ConfigTestApplication configTestApplication = ConfigTestApplication.getConfigTestApplicationContext();
        mText1TextView.setText(configTestApplication.getFirebaseRemoteConfig().getString(LOADING_PHRASE_CONFIG_KEY));
        mText2TextView.setText(configTestApplication.getFirebaseRemoteConfig().getString(LOADING_PHRASE_CONFIG_KEY));
        mText3TextView.setText(configTestApplication.getFirebaseRemoteConfig().getString(LOADING_PHRASE_CONFIG_KEY));

        long cacheExpiration = 3600; // 1 hour in seconds.
        // If in developer mode cacheExpiration is set to 0 so each fetch will retrieve values from
        // the server.
        if (configTestApplication.getFirebaseRemoteConfig().getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        // [START fetch_config_with_callback]
        // cacheExpirationSeconds is set to cacheExpiration here, indicating that any previously
        // fetched and cached config would be considered expired because it would have been fetched
        // more than cacheExpiration seconds ago. Thus the next fetch would go to the server unless
        // throttling is in progress. The default expiration duration is 43200 (12 hours).
        configTestApplication.getFirebaseRemoteConfig().fetch(cacheExpiration)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Screen1Activity.this, "Fetch Succeeded",
                                    Toast.LENGTH_SHORT).show();

                            // Once the config is successfully fetched it must be activated before newly fetched
                            // values are returned.
                            configTestApplication.getFirebaseRemoteConfig().activateFetched();
                        } else {
                            Toast.makeText(Screen1Activity.this, "Fetch Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        displayDetails();
                    }
                });
        // [END fetch_config_with_callback]
    }

    /**
     * Display price with discount applied if promotion is on. Otherwise display original price.
     */
    // [START display_price]
    private void displayDetails() {
        ConfigTestApplication configTestApplication = ConfigTestApplication.getConfigTestApplicationContext();

        mText1TextView.setText(configTestApplication.getFirebaseRemoteConfig().getString(TEXT1_TITLE_KEY));
        mText2TextView.setText(configTestApplication.getFirebaseRemoteConfig().getString(TEXT2_TITLE_KEY));
        mText3TextView.setText(configTestApplication.getFirebaseRemoteConfig().getString(TEXT3_TITLE_KEY));
    }
    // [END display_price]
}

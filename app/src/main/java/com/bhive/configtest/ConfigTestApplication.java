package com.bhive.configtest;

import android.app.Application;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

/**
 * Created by rashmimathur on 28/09/16.
 */

public class ConfigTestApplication extends Application {

    private static ConfigTestApplication application = null;
    private FirebaseRemoteConfig firebaseRemoteConfig;


    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        // Get Remote Config instance.
        // [START get_remote_config_instance]
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // [END get_remote_config_instance]

        // Create Remote Config Setting to enable developer mode.
        // Fetching configs from the server is normally limited to 5 requests per hour.
        // Enabling developer mode allows many more requests to be made per hour, so developers
        // can test different config values during development.
        // [START enable_dev_mode]
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        firebaseRemoteConfig.setConfigSettings(configSettings);
        // [END enable_dev_mode]

        // Set default Remote Config values. In general you should have in app defaults for all
        // values that you may configure using Remote Config later on. The idea is that you
        // use the in app defaults and when you need to adjust those defaults, you set an updated
        // value in the App Manager console. Then the next time you application fetches from the
        // server, the updated value will be used. You can set defaults via an xml file like done
        // here or you can set defaults inline by using one of the other setDefaults methods.S
        // [START set_default_values]
        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        // [END set_default_values]

    }

    public FirebaseRemoteConfig getFirebaseRemoteConfig(){
        return firebaseRemoteConfig;
    }

    public static ConfigTestApplication getConfigTestApplicationContext() {
        return application;
    }
}

package com.aqoong.lib.gplayversionmanagersample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aqoong.lib.gplayversionmanager.VersionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] test = VersionManager.getGooglePlayVersion(this);

        Log.d("TEST", "version");
        for(int s : test){
            Log.d("TEST", ""+s);
        }

    }
}

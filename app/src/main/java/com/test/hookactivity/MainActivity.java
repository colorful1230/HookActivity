package com.test.hookactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }

    public void onHookClick(View view) {
        Intent intent = new Intent();
        intent.setPackage("com.test.libdemo");
        intent.setClassName(this, "com.test.libdemo.LibActivity");
        startActivity(intent);
    }

    public void onStartTargetActivity(View view) {
        Intent intent = new Intent();
        intent.setPackage("com.test.hookactivity.TargetActivity");
        startActivity(intent);
    }
}

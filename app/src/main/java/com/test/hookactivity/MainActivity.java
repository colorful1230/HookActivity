package com.test.hookactivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HookUtils.hookActivityManagerService(getClassLoader());
        HookUtils.hookActivityThreadHandler();
        setContentView(R.layout.activity_main);
    }

    public void onHookClick(View view) {
        Intent intent = new Intent(this, TargetActivity.class);
        startActivity(intent);
    }
}

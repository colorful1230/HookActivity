package com.test.hookactivity;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by zhaolin on 2017/8/21.
 */

public class HookApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LoadApkUtils.loadApk(base, Environment.getExternalStorageDirectory().getPath()
                + File.separator + "lib.apk");
        HookUtils.hookActivityManagerService(getClassLoader());
        HookUtils.hookActivityThreadHandler();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

package com.test.hookactivity;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

/**
 * Created by zhaolin on 2017/8/21.
 */

public class LoadApkUtils {

    public static void loadApk(Context context, String apkPath) {
        File dexFile = context.getDir("dex", Context.MODE_PRIVATE);

        File apkFile = new File(apkPath);

        ClassLoader classLoader = context.getClassLoader();
        DexClassLoader dexClassLoader = new DexClassLoader(apkFile.getAbsolutePath(),
                dexFile.getAbsolutePath(), null, classLoader.getParent());

        try {
            Field fieldClassLoader = ClassLoader.class.getDeclaredField("parent");
            if (fieldClassLoader != null) {
                fieldClassLoader.setAccessible(true);
                fieldClassLoader.set(classLoader, dexClassLoader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


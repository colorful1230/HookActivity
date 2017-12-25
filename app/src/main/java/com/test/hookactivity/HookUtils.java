package com.test.hookactivity;

import android.os.Handler;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Created by zhaolin on 17-8-13.
 */

public class HookUtils {

    public static final void hookActivityManagerService(ClassLoader classLoader) {
        try {
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
            if (activityManagerNativeClass == null) {
                return;
            }
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            if (gDefaultField == null) {
                return;
            }
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);

            Class<?> singleton = Class.forName("android.util.Singleton");
            if (singleton == null) {
                return;
            }

            Field mInstanceField = singleton.getDeclaredField("mInstance");
            if (mInstanceField == null) {
                return;
            }
            mInstanceField.setAccessible(true);

            Object activityManager = mInstanceField.get(gDefault);
            Class<?> activityManagerInterface = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(classLoader,
                    new Class[] {activityManagerInterface}, new HookActivityHandler(activityManager));
            mInstanceField.set(gDefault, proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void hookActivityThreadHandler() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            if (currentActivityThreadField == null) {
                return;
            }
            currentActivityThreadField.setAccessible(true);
            Object currentActivityThread = currentActivityThreadField.get(null);

            Field mHField = activityThreadClass.getDeclaredField("mH");
            if (mHField == null) {
                return;
            }
            mHField.setAccessible(true);
            Handler mH = (Handler) mHField.get(currentActivityThread);
            Field mCallbackField = Handler.class.getDeclaredField("mCallback");
            if (mCallbackField == null) {
                return;
            }

            mCallbackField.setAccessible(true);
            mCallbackField.set(mH, new ActivityThreadHandlerCallback(mH));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

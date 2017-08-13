package com.test.hookactivity;

import android.content.ComponentName;
import android.content.Intent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by zhaolin on 17-8-13.
 */

public class HookActivityHandler implements InvocationHandler {

    private static final String TAG = "HookActivityHandler";

    private Object mBase;

    public HookActivityHandler(Object base) {
        this.mBase = base;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if ("startActivity".equalsIgnoreCase(method.getName())) {
            Intent rawIntent = null;
            int index = 0;

            for (int i = 0; i < objects.length; i++) {
                if (objects[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }

            rawIntent = (Intent) objects[index];

            Intent newIntent = new Intent();
            String targetPackageName = "com.test.hookactivity";

            ComponentName componentName = new ComponentName(targetPackageName,
                    PlaceHolderActivity.class.getCanonicalName());
            newIntent.setComponent(componentName);
            newIntent.putExtra("extra_target_intent", rawIntent);

            objects[index] = newIntent;
            return method.invoke(mBase, objects);

        }
        return method.invoke(mBase, objects);
    }
}

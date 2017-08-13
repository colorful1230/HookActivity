package com.test.hookactivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Field;

/**
 * Created by zhaolin on 17-8-13.
 */

public class ActivityThreadHandlerCallback implements Handler.Callback {

    private Handler mHandler;

    public ActivityThreadHandlerCallback(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    public boolean handleMessage(Message message) {
        int what = message.what;
        switch (what) {
            case 100:
                handleStartActivity(message);
                break;
        }
        mHandler.handleMessage(message);
        return true;
    }

    private void handleStartActivity(Message message) {
        Object object = message.obj;

        try {
            Field intent = object.getClass().getDeclaredField("intent");
            if (intent == null) {
                return;
            }
            intent.setAccessible(true);
            Intent rawIntent = (Intent) intent.get(object);

            Intent targetIntent = rawIntent.getParcelableExtra("extra_target_intent");
            if (targetIntent == null) {
                return;
            }
            rawIntent.setComponent(targetIntent.getComponent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

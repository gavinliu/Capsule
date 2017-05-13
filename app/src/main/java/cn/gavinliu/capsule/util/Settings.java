package cn.gavinliu.capsule.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

/**
 * Created by gavin on 2017/5/13.
 */

public class Settings {

    private static Settings sInstance;

    public static void createInstance(Context ctx) {
        if (sInstance == null) sInstance = new Settings(ctx);
    }

    public static Settings getInstance() {
        return sInstance;
    }

    private Settings(Context ctx) {
        mPreference = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    private SharedPreferences mPreference;


    public String getTriggerWay() {
        return mPreference.getString("trigger_way", "1");
    }
}

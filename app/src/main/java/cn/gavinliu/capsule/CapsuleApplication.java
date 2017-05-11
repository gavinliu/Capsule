package cn.gavinliu.capsule;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;

/**
 * Created by Gavin on 17-5-11.
 */

public class CapsuleApplication extends Application {

    @Override
    public void onCreate() {
        SpeechUtility.createUtility(this, "appid=5912d3d6");
        super.onCreate();
    }
}

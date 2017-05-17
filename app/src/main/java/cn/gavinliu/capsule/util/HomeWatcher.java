package cn.gavinliu.capsule.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.ViewConfiguration;

/**
 * Created by Gavin on 17-5-10.
 */

public class HomeWatcher {

    private static final String TAG = HomeWatcher.class.getSimpleName();

    private static int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();

    private Context mContext;
    private IntentFilter mFilter;
    private InnerReceiver mReceiver;
    private Listener mListener;

    private long mTapTime;

    public HomeWatcher(Context context, Listener listener) {
        mContext = context;
        mListener = listener;
        mFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        mReceiver = new InnerReceiver();
    }

    public void startWatch() {
        if (mReceiver != null) {
            mContext.registerReceiver(mReceiver, mFilter);
        }
    }

    public void stopWatch() {
        if (mReceiver != null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }

    class InnerReceiver extends BroadcastReceiver {

        /**
         * base reason copy from PhoneWindowManager
         */
        static public final String SYSTEM_DIALOG_REASON_KEY = "reason";
        static public final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions"; // 长按电源键
        static public final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps"; // 最近任务
        static public final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey"; // home 键
        static public final String SYSTEM_DIALOG_REASON_ASSIST = "assist";

        /**
         * special reason
         */
        static public final String SYSTEM_DIALOG_REASON_VOICE = "voiceinteraction"; // 原生 Android 长按 home 键，出现 Google now

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    Log.e(TAG, "==> action:" + action + ", reason:" + reason);

                    switch (reason) {

                        case SYSTEM_DIALOG_REASON_HOME_KEY: {
                            long time = System.currentTimeMillis();
                            boolean isDoubleTap = time - mTapTime <= DOUBLE_TAP_TIMEOUT;
                            mTapTime = time;

                            if (isDoubleTap) {
                                Log.e(TAG, "<== onHomeDoubleTap");
                                if (mListener != null) mListener.onDoubleTap();
                            } else {
                                Log.e(TAG, "<== onHomeTap");
                                if (mListener != null) mListener.onTap();
                            }
                            break;
                        }

                        case SYSTEM_DIALOG_REASON_VOICE: {
                            Log.e(TAG, "<== onHomeLongPressed");
                            if (mListener != null) mListener.onLongPressed();
                            break;
                        }
                    }

                }
            }
        }
    }

    public interface Listener {
        void onTap();

        void onDoubleTap();

        void onLongPressed();
    }
}

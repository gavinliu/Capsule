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
    private OnHomePressedListener mListener;

    private long mTapTime;

    public HomeWatcher(Context context) {
        mContext = context;
        mFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
    }

    public void setOnHomePressedListener(OnHomePressedListener listener) {
        mListener = listener;
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
         * copy from PhoneWindowManager
         */
        static public final String SYSTEM_DIALOG_REASON_KEY = "reason";
        static public final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
        static public final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        static public final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        static public final String SYSTEM_DIALOG_REASON_ASSIST = "assist";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    Log.e(TAG, "==> action:" + action + ", reason:" + reason);

                    if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                        long time = System.currentTimeMillis();
                        if (time - mTapTime <= DOUBLE_TAP_TIMEOUT) {
                            Log.e(TAG, "<== onHomeDoubleTap");
                            if (mListener != null) mListener.onHomeDoubleTap();
                        }
                        mTapTime = time;
                    }
                }
            }
        }
    }

    public interface OnHomePressedListener {
        void onHomeDoubleTap();
    }
}

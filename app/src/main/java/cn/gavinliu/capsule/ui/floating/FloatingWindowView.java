package cn.gavinliu.capsule.ui.floating;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.gavinliu.capsule.R;

/**
 * Created by Gavin on 17-5-11.
 */

public class FloatingWindowView extends FrameLayout implements FloatingWindowContract.View {

    public static FloatingWindowView newInstance(Context context) {
        FloatingWindowView view = new FloatingWindowView(context);
        view.setBackgroundColor(0xCC000000);
        view.setVisibility(GONE);
        return view;
    }

    private FloatingWindowView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void setPresenter(FloatingWindowContract.Presenter presenter) {

    }

    private TextView mTextView;

    @Override
    public boolean shownToggle() {
        if (getVisibility() == GONE) {
            setVisibility(VISIBLE);
            return true;
        } else {
            setVisibility(GONE);
            return false;
        }
    }

    @Override
    public void showSpeechRecognizerView() {
        if (mTextView == null) {
            mTextView = new TextView(getContext());
            mTextView.setText("说出你的想法...");
            mTextView.setTextSize(14);
            mTextView.setTextColor(0xFFFFFFFF);
            mTextView.setBackgroundResource(R.drawable.item_capsule_red);

            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 100;
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            addView(mTextView, params);
        }

        mTextView.setVisibility(VISIBLE);
    }

}

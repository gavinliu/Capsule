package cn.gavinliu.capsule.ui.floating;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

/**
 * Created by Gavin on 17-5-11.
 */

public class FloatingWindowView extends FrameLayout {

    public static FloatingWindowView newInstance(Context context) {
        FloatingWindowView view = new FloatingWindowView(context);
        view.setBackgroundColor(0x80000000);
        view.setVisibility(GONE);
        return view;
    }

    private FloatingWindowView(@NonNull Context context) {
        super(context);
    }


    public void shownToggle() {
        if (getVisibility() == GONE) {
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }
}

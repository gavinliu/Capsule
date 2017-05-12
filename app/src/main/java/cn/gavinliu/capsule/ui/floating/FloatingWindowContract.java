package cn.gavinliu.capsule.ui.floating;


import cn.gavinliu.capsule.ui.BasePresenter;
import cn.gavinliu.capsule.ui.BaseView;

/**
 * Created by Gavin on 17-5-11.
 */

public interface FloatingWindowContract {

    interface View extends BaseView<Presenter> {
        void shownToggle();

        void showSpeechRecognizerView();
    }

    interface Presenter extends BasePresenter {
        void startSpeechRecognizer();
    }
}

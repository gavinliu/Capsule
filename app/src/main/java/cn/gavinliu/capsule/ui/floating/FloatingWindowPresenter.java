package cn.gavinliu.capsule.ui.floating;

/**
 * Created by Gavin on 17-5-11.
 */

public class FloatingWindowPresenter implements FloatingWindowContract.Presenter {

    private FloatingWindowContract.View mView;

    public FloatingWindowPresenter(FloatingWindowContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void startSpeechRecognizer() {
        mView.showSpeechRecognizerView();
    }
}

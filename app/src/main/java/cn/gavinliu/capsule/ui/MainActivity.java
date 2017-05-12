package cn.gavinliu.capsule.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.gavinliu.capsule.R;
import cn.gavinliu.capsule.service.HomeService;
import cn.gavinliu.capsule.ui.setting.SettingFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, HomeService.class));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, SettingFragment.newInstance())
                    .commitAllowingStateLoss();
        }
    }
}

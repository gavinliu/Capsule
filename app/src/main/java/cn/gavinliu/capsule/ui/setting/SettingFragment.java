package cn.gavinliu.capsule.ui.setting;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.gavinliu.capsule.R;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Gavin on 17-5-12.
 */

public class SettingFragment extends PreferenceFragmentCompat {

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting);
    }

    private RxPermissions mRxPermissions;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Preference trigger = findPreference("trigger_way");
        trigger.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                updateTriggerWaySummary(preference, newValue);
                return true;
            }
        });
        updateTriggerWaySummary(trigger, cn.gavinliu.capsule.util.Settings.getInstance().getTriggerWay());

        findPreference("permission").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                checkPermissions();
                return false;
            }
        });

        findPreference("open_source").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse("https://github.com/gavinliu/Capsule"));
                startActivity(intent);
                return true;
            }
        });

        checkPermissions();
    }

    private void updateTriggerWaySummary(Preference preference, Object value) {
        if ("1".equals(value)) {
            preference.setSummary("当前选择是双击Home键");
        } else {
            preference.setSummary("当前选择是长按Home键");
        }
    }

    private void updatePermissionSummary(Preference preference, Boolean value) {
        if (value) {
            preference.setSummary("已获得所有权限");
        } else {
            preference.setSummary("权限不足，请点击");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && Build.VERSION.SDK_INT >= 23 && Settings.canDrawOverlays(getActivity())) {
            updatePermissionSummary(findPreference("permission"), true);
        }
    }

    private void checkPermissions() {
        if (mRxPermissions == null) mRxPermissions = new RxPermissions(getActivity());
        mRxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            if (Build.VERSION.SDK_INT >= 23) {
                                if (!Settings.canDrawOverlays(getActivity())) {
                                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:cn.gavinliu.capsule"));
                                    startActivityForResult(intent, 0);
                                } else {
                                    updatePermissionSummary(findPreference("permission"), true);
                                }
                            }
                        } else {
                            updatePermissionSummary(findPreference("permission"), false);
                        }
                    }
                });
    }
}

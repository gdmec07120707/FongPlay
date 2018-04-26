package com.fong.play.ui.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.fong.play.R;
import com.fong.play.common.Constant;
import com.fong.play.common.rx.RxSchedulers;
import com.fong.play.common.utils.ACache;
import com.fong.play.common.utils.DataCleanManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        initData();
    }

    private void initData() {
        initClearCachePref();

        initDownloadDirPref();

    }

    private void initDownloadDirPref() {
        final Preference pref = getPreferenceManager().findPreference(getString(R.string.key_apk_download_dir));
        final String dir = ACache.get(getActivity()).getAsString(Constant.APK_DOWNLOAD_DIR);
        pref.setSummary(dir);

        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                dirChoose(pref,dir);
                return false;
            }
        });

    }

    //文件选择器
    private void dirChoose(Preference pref, String dir) {

    }

    private void initClearCachePref() {
        final Preference pref = getPreferenceManager().findPreference("key_clear_cache");

        try {
            pref.setSummary(DataCleanManager.getCacheSize(getActivity().getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                clearCache().subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(SettingFragment.this.getActivity(), "清理成功", Toast.LENGTH_LONG).show();
                        pref.setSummary(DataCleanManager.getCacheSize(getActivity().getCacheDir()));
                    }
                });
                return true;
            }
        });
    }

    private Observable clearCache() {

        Observable<Object> observable =
                Observable.create(new ObservableOnSubscribe() {
                    @Override
                    public void subscribe(ObservableEmitter e) throws Exception {
                        DataCleanManager.cleanFiles(getActivity());
                        DataCleanManager.cleanInternalCache(getActivity());
                        DataCleanManager.deleteFolderFile(ACache.get(getActivity()).getAsString(Constant.APK_DOWNLOAD_DIR), false);
                        e.onNext(1);
                        e.onComplete();
                    }
                }).compose(RxSchedulers.io_main());
        return observable;

    }
}

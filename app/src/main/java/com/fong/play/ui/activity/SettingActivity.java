package com.fong.play.ui.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import com.fong.play.R;
import com.fong.play.di.component.AppComponent;
import com.fong.play.ui.fragment.SettingFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;

    @Override
    protected int setLayout() {
        return R.layout.template_toolbar_framelayout;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {
        mToolBar.setNavigationIcon(new IconicsDrawable(this)
                .sizeDp(16)
                .color(ContextCompat.getColor(this,R.color.white))
                .icon(Ionicons.Icon.ion_ios_arrow_back)
        );
        mToolBar.setTitle("系统设置");

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getFragmentManager().beginTransaction().replace(R.id.content,new SettingFragment()).commit();

    }
}

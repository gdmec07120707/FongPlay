package com.fong.play.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.eftimoff.androipathview.PathView;
import com.fong.play.R;
import com.fong.play.common.Constant;
import com.fong.play.common.utils.ACache;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

/**
 * Created by FANGDINGJIE
 * 2018/3/21.
 */

public class WelcomeActivity extends AppCompatActivity {
    @BindView(R.id.pathView)
    PathView pathView;
    @BindView(R.id.activity_welcome)
    LinearLayout activityWelcome;
    public static String value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        pathView.getPathAnimator()
                .delay(100)
                .duration(3000)
                .interpolator(new AccelerateDecelerateInterpolator())
                .listenerEnd(new PathView.AnimatorBuilder.ListenerEnd() {
                    @Override
                    public void onAnimationEnd() {
                        jump();
                    }
                })
                .start();
    }

    private void jump(){
        Logger.d("jump");
        String isShowGuide =  ACache.get(this).getAsString(Constant.IS_SHOW_GUIDE);
        // 第一次启动进入引导页面
        if(null == isShowGuide){
            startActivity(new Intent(this,GuideActivity.class));
        }
        else{
            startActivity(new Intent(this,MainActivity.class));
        }
        this.finish();
    }
}

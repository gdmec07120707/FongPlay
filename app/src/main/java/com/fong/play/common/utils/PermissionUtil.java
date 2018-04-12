package com.fong.play.common.utils;

import android.content.Context;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;

/**
 * Created by FANGDINGJIE
 * 2018/4/9.
 */

public class PermissionUtil {
    public static Observable<Boolean> requestPermisson(Context activity, String permission) {
        RxPermissions rxPermissions = RxPermissions.getInstance(activity);
        return rxPermissions.request(permission);
    }
}

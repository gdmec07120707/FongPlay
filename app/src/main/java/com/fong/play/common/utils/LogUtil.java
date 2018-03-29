package com.fong.play.common.utils;


import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by FANGDINGJIE
 * 2018/3/29.
 */

public class LogUtil {
    /**
     * 初始化log工具，在app入口处调用
     *
     * @param isLogEnable 是否打印log
     */
    public static void init(boolean isLogEnable) {
        Logger.init("LogHttpInfo")
                .hideThreadInfo()
                .logLevel(isLogEnable ? LogLevel.FULL : LogLevel.NONE)
                .methodOffset(2);
    }

    public static void d(String message) {
        Logger.d(message);
    }

    public static void i(String message) {
        Logger.i(message);
    }

    public static void w(String message, Throwable e) {
        String info = e != null ? e.toString() : "null";
        Logger.w(message + "：" + info);
    }

    public static void e(String message, Throwable e) {
        Logger.e(e, message);
    }

    public static void json(String json) {
        Logger.json(json);
    }
}


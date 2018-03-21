package com.fong.play.common.exception;

/**
 * Created by FANGDINGJIE
 * 2018/3/21.
 */

public class ApiException extends BaseException {

    public ApiException(int code, String displayMessage) {
        super(code, displayMessage);
    }
}

package com.fong.play.common.rx;

import android.content.Context;
import android.widget.Toast;

import com.fong.play.common.exception.ApiException;
import com.fong.play.common.exception.BaseException;
import com.fong.play.common.exception.ErrorMessageFactory;
import com.google.gson.JsonParseException;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by FANGDINGJIE
 * 2018/3/23.
 */

public class RxErrorHnadler {
    private Context context;

    public RxErrorHnadler(Context context){
        this.context = context;
    }

    /**
     * 判断错误类型
     * @param e
     * @return
     */
    public BaseException handlerError(Throwable e) {
        BaseException exception = new BaseException();
        if(e instanceof ApiException){
            exception.setCode(((ApiException)e).getCode());
        }else if(e instanceof JsonParseException){
            exception.setCode(BaseException.JSON_ERROR);
        }else if(e instanceof HttpException){
            exception.setCode(BaseException.HTTP_ERROR);
        }else if(e instanceof SocketTimeoutException){
            exception.setCode(BaseException.SOCKET_TIMEOUT_ERROR);
        }else if(e instanceof SocketException){
            exception.setCode(BaseException.SOCKET_ERROR);
        }else{
            exception.setCode(BaseException.UNKNOWN_ERROR);
        }
        exception.setDisplayMessage(ErrorMessageFactory.create(context,exception.getCode()));
        return exception;
    }

    /**
     * 显示错误信息
     * @param e
     */
    public void  showErrorMessage(BaseException e){
        Toast.makeText(context,e.getDisplayMessage(),Toast.LENGTH_LONG).show();
    }

}

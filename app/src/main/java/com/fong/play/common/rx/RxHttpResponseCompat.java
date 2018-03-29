package com.fong.play.common.rx;

import com.fong.play.common.exception.ApiException;
import com.fong.play.common.exception.BaseException;
import com.fong.play.data.bean.BaseBean;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by FANGDINGJIE
 * 2018/3/21.
 */

public class RxHttpResponseCompat {

    public static <T> Observable.Transformer<BaseBean<T>, T> compatResult() {
        return new Observable.Transformer<BaseBean<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseBean<T>> baseBeanObservable) {

                return baseBeanObservable.flatMap(new Func1<BaseBean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(final BaseBean<T> tBaseBean) {

                        if (tBaseBean.success()) {
                            return Observable.create(new Observable.OnSubscribe<T>() {
                                @Override
                                public void call(Subscriber<? super T> subscriber) {
                                    try {
                                        subscriber.onNext(tBaseBean.getData());
                                        subscriber.onCompleted();
                                        //subscriber.onError(new ApiException(BaseException.HTTP_ERROR,"Http错误"));
                                    } catch (Exception e) {
                                        subscriber.onError(e);
                                    }
                                }
                            });
                        } else {
                            return Observable.error(new ApiException(tBaseBean.getStatus(), tBaseBean.getMessage()));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };


    }


}

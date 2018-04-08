package com.fong.play.data;

import android.widget.Toast;

import com.fong.play.data.bean.BaseBean;
import com.fong.play.data.bean.Category;
import com.fong.play.data.http.ApiService;
import com.fong.play.presenter.constract.CategoryContract;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by FANGDINGJIE
 * 2018/4/2.
 */

public class CategoryModel implements CategoryContract.ICategoryModel {

    private ApiService mApiService;

    public CategoryModel(ApiService mApiService){
        this.mApiService = mApiService;
    }

    @Override
    public Observable<BaseBean<List<Category>>> requestData() {

        return mApiService.getCategory();
    }
}

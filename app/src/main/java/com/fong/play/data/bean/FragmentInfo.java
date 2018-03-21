package com.fong.play.data.bean;

/**
 * Created by FANGDINGJIE
 * 2018/3/16.
 */

public class FragmentInfo {

    public FragmentInfo(String title, Class fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    private String title;

    private Class fragment;

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}

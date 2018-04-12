package com.fong.play.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fong.play.data.bean.FragmentInfo;
import com.fong.play.ui.fragment.CategoryFragment;
import com.fong.play.ui.fragment.GamesFragment;
import com.fong.play.ui.fragment.TopListFragment;
import com.fong.play.ui.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FANGDINGJIE
 * 2018/3/16.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<FragmentInfo> mFragments ;


    public ViewPagerAdapter(FragmentManager fm,List<FragmentInfo> fragments) {
        super(fm);
       this.mFragments = fragments;
    }



    @Override
    public Fragment getItem(int position) {
        try {
            return (Fragment) mFragments.get(position).getFragment().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }
}

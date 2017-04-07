package com.nanchen.aiyagirl.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager通用
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  16:29
 */

public class CommonViewPagerAdapter extends FragmentPagerAdapter {
    private String[] title;
    private List<Fragment> mFragments = new ArrayList<>();

    public CommonViewPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        title = titles;
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}

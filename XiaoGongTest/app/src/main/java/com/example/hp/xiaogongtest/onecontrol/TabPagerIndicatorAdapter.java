package com.example.hp.xiaogongtest.onecontrol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @类名:TabPagerIndicator
 * @创建人:赵祖元
 * @创建时间：2018/7/30 20:50
 * @简述:
 */
public class TabPagerIndicatorAdapter extends FragmentPagerAdapter {
    private static final String[] TITLE = new String[]{"页面1","页面2","页面3"};

    public TabPagerIndicatorAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("arg",TITLE[position]);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return TITLE.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position % TITLE.length];
    }
}

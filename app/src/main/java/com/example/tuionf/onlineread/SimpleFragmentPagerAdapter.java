package com.example.tuionf.onlineread;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tuionf.onlineread.ui.PageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tuionf
 * @date 2017/10/16
 * @email 596019286@qq.com
 * @explain
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<String> tabs = new ArrayList<>();

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context, List<String> tabs) {
        super(fm);
        this.context = context;
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(context,position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }
}

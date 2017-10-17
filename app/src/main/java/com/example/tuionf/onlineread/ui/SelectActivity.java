package com.example.tuionf.onlineread.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.tuionf.onlineread.R;
import com.example.tuionf.onlineread.SimpleFragmentPagerAdapter;

import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager view_pager;
    private ArrayList<String> tabs = new ArrayList<>();
    private SimpleFragmentPagerAdapter pagerAdapter;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        view_pager = (ViewPager) findViewById(R.id.view_pager);

        initData();

        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this,tabs);

        view_pager.setAdapter(pagerAdapter);
        view_pager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(view_pager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.getTabAt(index).select();


    }

    private void initData() {
        index = getIntent().getIntExtra("index",0);
        tabs.add("DOC");
        tabs.add("EXCEl");
        tabs.add("PPT");
        tabs.add("PDF");
        tabs.add("TXT");
//		mList.add("最近");
    }
}

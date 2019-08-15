package com.example.hp.xiaogongtest.onecontrol;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.hp.xiaogongtest.R;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener{


    @BindView(R.id.indicator)
    TabPageIndicator indicator;
    @BindView(R.id.pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);

        FragmentPagerAdapter adapter = new TabPagerIndicatorAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        indicator.setViewPager(pager);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}

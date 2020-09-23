package com.canada.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import android.os.Bundle;

import com.canada.weather.UI.SubPage;
import com.canada.weather.UI.MainPage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private VerticalViewPager verticalViewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> list = new ArrayList<>();
        list.add(new MainPage());
        list.add(new SubPage());
        verticalViewPager = findViewById(R.id.pager);
        pagerAdapter = new VPagerAdapter(getSupportFragmentManager(), list);
        verticalViewPager.setAdapter(pagerAdapter);
    }
}
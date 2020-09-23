package com.canada.weather.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.canada.weather.R;
import com.canada.weather.UI.Adapter.SubPageAdapter;


public class SubPage extends Fragment {

    private ImageView img_dot_one, img_dot_two, img_dot_three, img_dot_four;
    private ViewPager viewPager;
    private SubPageAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_sub_page, container, false);

        viewPager = root.findViewById(R.id.sub_page);
        img_dot_one = root.findViewById(R.id.img_dot_one);
        img_dot_two = root.findViewById(R.id.img_dot_two);
        img_dot_three = root.findViewById(R.id.img_dot_three);
        img_dot_four = root.findViewById(R.id.img_dot_four);

        adapter = new SubPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    img_dot_one.setImageResource(R.drawable.viewpager_dot);
                    img_dot_two.setImageResource(R.drawable.viewpager_nondot);
                    img_dot_three.setImageResource(R.drawable.viewpager_nondot);
                    img_dot_four.setImageResource(R.drawable.viewpager_nondot);
                }else if(position == 1) {
                    img_dot_one.setImageResource(R.drawable.viewpager_nondot);
                    img_dot_two.setImageResource(R.drawable.viewpager_dot);
                    img_dot_three.setImageResource(R.drawable.viewpager_nondot);
                    img_dot_four.setImageResource(R.drawable.viewpager_nondot);
                }else if(position == 2) {
                    img_dot_one.setImageResource(R.drawable.viewpager_nondot);
                    img_dot_two.setImageResource(R.drawable.viewpager_nondot);
                    img_dot_three.setImageResource(R.drawable.viewpager_dot);
                    img_dot_four.setImageResource(R.drawable.viewpager_nondot);
                }else {
                    img_dot_one.setImageResource(R.drawable.viewpager_nondot);
                    img_dot_two.setImageResource(R.drawable.viewpager_nondot);
                    img_dot_three.setImageResource(R.drawable.viewpager_nondot);
                    img_dot_four.setImageResource(R.drawable.viewpager_dot);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return root;
    }
}
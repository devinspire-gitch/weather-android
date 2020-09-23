package com.canada.weather.UI.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.canada.weather.UI.SubPages.CurrentStatus;
import com.canada.weather.UI.SubPages.MapPage;
import com.canada.weather.UI.SubPages.MoreStates;
import com.canada.weather.UI.SubPages.WeatherForecast;

public class SubPageAdapter extends FragmentStatePagerAdapter {
        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        int mNoOfTabs;
        public SubPageAdapter(FragmentManager fm){
            super(fm);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    CurrentStatus tab1 =new CurrentStatus();
                    return tab1;
                case 1:
                    WeatherForecast tab2 = new WeatherForecast();
                    return tab2;
                case 2:
                    MoreStates tab3 = new MoreStates();
                    return tab3;
                case 3:
                    MapPage tab4 = new MapPage();
                    return tab4;
                default:
                    return  null;
            }
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return 4;
        }

}

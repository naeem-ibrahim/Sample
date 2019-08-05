package com.algorepublic.brandmaker.ui.home;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.FragmentHomeScreenBinding;
import com.algorepublic.brandmaker.ui.activity.ActivityFragment;
import com.algorepublic.brandmaker.ui.dashboard.MainActivity;
import com.algorepublic.brandmaker.ui.notification.NotificationFragment;
import com.algorepublic.brandmaker.ui.stores.StoreFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class HomeFragment extends Fragment {
    private static HomeFragment fragment;
     FragmentHomeScreenBinding b;

    int[] tabIcons = new int[]{R.drawable.ic_list, R.drawable.ic_marker,R.drawable.ic_action,R.drawable.ic_notifications};

    public static HomeFragment getInstance() {
        if (fragment == null) {
            fragment = new HomeFragment();
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false);
        setupViewPager();
        return b.getRoot();
    }

    private void setupTabIcons() {
        b.tabs.getTabAt(0).setIcon(tabIcons[0]);
        b.tabs.getTabAt(1).setIcon(tabIcons[1]);
        b.tabs.getTabAt(2).setIcon(tabIcons[3]);
//        b.tabs.getTabAt(3).setIcon(tabIcons[3]);

        b.tabs.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getContext(), R.color.red);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getContext(), R.color.text_color_g);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(ActivityFragment.getInstance(),"Activity");
        adapter.addFragment(StoreFragment.getInstance(),"Check In");
//        adapter.addFragment(StoreFragment.getInstance(),"Action");
        adapter.addFragment(NotificationFragment.getInstance(),"Notification");
        b.vp.setAdapter(adapter);


        b.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                String selectedTabName="Store Near Me";
                if(position==0){
                    selectedTabName="Activity";
                }else  if(position==2){
                    selectedTabName="Notifications";
                }
                ((MainActivity) getContext()).setToolBar(selectedTabName,"",false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        b.tabs.setupWithViewPager(b.vp);
        setupTabIcons();

//        b.tabs.setScrollPosition(1,0f,true);
//        b.vp.setCurrentItem(1);
        b.tabs.getTabAt(1).select();
    }


}

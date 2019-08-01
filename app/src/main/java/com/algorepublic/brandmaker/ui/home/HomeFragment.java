package com.algorepublic.brandmaker.ui.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.FragmentHomeScreenBinding;
import com.algorepublic.brandmaker.ui.stores.StoreFragment;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class HomeFragment extends Fragment {
    private static HomeFragment fragment;
     FragmentHomeScreenBinding b;

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

    public void setupViewPager() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(StoreFragment.getInstance(),"StoreFragment");
        b.vp.setAdapter(adapter);

        b.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


}

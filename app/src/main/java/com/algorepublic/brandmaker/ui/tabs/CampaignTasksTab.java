package com.algorepublic.brandmaker.ui.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.FragmentTabsBinding;
import com.algorepublic.brandmaker.ui.statement.StatementFragment;
import com.algorepublic.brandmaker.ui.dashboard.MainActivity;
import com.algorepublic.brandmaker.ui.home.ViewPagerAdapter;

/**
 * Created By apple on 2019-08-05
 */
public class CampaignTasksTab extends Fragment {
    private static CampaignTasksTab fragment;
    private FragmentTabsBinding b;

    private int brandID=0;
    private String brandName;

    public static CampaignTasksTab getInstance(int brandID,String brandName) {
        if (fragment == null) {
            fragment = new CampaignTasksTab();
        }
        Bundle args = new Bundle();
        args.putInt("BrandID",brandID);
        args.putString("BrandName",brandName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_tabs, container, false);

        brandID=getArguments().getInt("BrandID");
        brandName=getArguments().getString("BrandName");

        b.tvTab1.setText("Campaign");
        b.tvTab2.setText("Tasks");

        b.tvTab1.setVisibility(View.GONE);
        b.view.setVisibility(View.GONE);
        b.tvTab2.setVisibility(View.GONE);
        b.view2.setVisibility(View.GONE);

        b.tvTab3.setVisibility(View.VISIBLE);
        b.view3.setVisibility(View.VISIBLE);

        b.tvTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.tvTab1.setTextColor(getContext().getColor(R.color.red));
                b.view.setBackgroundColor(getContext().getColor(R.color.red));
                b.tvTab2.setTextColor(getContext().getColor(R.color.text_color_g));
                b.view2.setBackgroundColor(getContext().getColor(R.color.text_color_g));

                b.viewPager.setCurrentItem(0);
            }
        });

        b.tvTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b.tvTab2.setTextColor(getContext().getColor(R.color.red));
                b.view2.setBackgroundColor(getContext().getColor(R.color.red));
                b.tvTab1.setTextColor(getContext().getColor(R.color.text_color_g));
                b.view.setBackgroundColor(getContext().getColor(R.color.text_color_g));

                b.viewPager.setCurrentItem(1);
            }
        });

        setupViewPager();


        return b.getRoot();
    }

    private void setupViewPager() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        ((MainActivity) getContext()).setToolBar(brandName,"Check In 02-08-2019 - 9:00 PM",true);
        adapter.addFragment(StatementFragment.getInstance(brandID), "Campaign");
//        adapter.addFragment(StatementFragment.getInstance(brandID,1), "Task");

        b.viewPager.setAdapter(adapter);

        b.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if(position==0){
                    b.tvTab1.setTextColor(getContext().getColor(R.color.red));
                    b.view.setBackgroundColor(getContext().getColor(R.color.red));
                    b.tvTab2.setTextColor(getContext().getColor(R.color.text_color_g));
                    b.view2.setBackgroundColor(getContext().getColor(R.color.text_color_g));
                }else {
                    b.tvTab2.setTextColor(getContext().getColor(R.color.red));
                    b.view2.setBackgroundColor(getContext().getColor(R.color.red));
                    b.tvTab1.setTextColor(getContext().getColor(R.color.text_color_g));
                    b.view.setBackgroundColor(getContext().getColor(R.color.text_color_g));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}

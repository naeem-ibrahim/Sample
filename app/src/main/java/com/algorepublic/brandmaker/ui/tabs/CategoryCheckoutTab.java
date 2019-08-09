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
import com.algorepublic.brandmaker.ui.category.CategoryFragment;
import com.algorepublic.brandmaker.ui.checkout.CheckOutFragment;
import com.algorepublic.brandmaker.ui.dashboard.MainActivity;
import com.algorepublic.brandmaker.ui.home.ViewPagerAdapter;

/**
 * Created By apple on 2019-08-02
 */
public class CategoryCheckoutTab extends Fragment {
    private static CategoryCheckoutTab fragment;
    private FragmentTabsBinding b;
    private int storeID;
    private String storeName;

    public static CategoryCheckoutTab getInstance(int storeID,String storeName) {
        if (fragment == null) {
            fragment = new CategoryCheckoutTab();
        }
        Bundle args = new Bundle();
        args.putInt("StoreID",storeID);
        args.putString("StoreName",storeName);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_tabs, container, false);

        storeID= getArguments().getInt("StoreID");
        storeName= getArguments().getString("StoreName");

        b.tvTab1.setText("Category");
        b.tvTab2.setText("Checkout");

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
        ((MainActivity) getContext()).setToolBar(storeName, "Check In 02-08-2019 - 9:00 PM", true);
        adapter.addFragment(CategoryFragment.getInstance(storeID), "Category");
        adapter.addFragment(CheckOutFragment.getInstance(storeID), "Checkout");
        b.viewPager.setAdapter(adapter);

        b.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    b.tvTab1.setTextColor(getContext().getColor(R.color.red));
                    b.view.setBackgroundColor(getContext().getColor(R.color.red));
                    b.tvTab2.setTextColor(getContext().getColor(R.color.text_color_g));
                    b.view2.setBackgroundColor(getContext().getColor(R.color.text_color_g));
                } else {
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

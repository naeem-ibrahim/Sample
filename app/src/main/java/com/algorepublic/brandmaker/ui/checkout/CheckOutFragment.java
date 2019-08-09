package com.algorepublic.brandmaker.ui.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.algorepublic.brandmaker.BMApp;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.CheckoutDataBinding;
import com.algorepublic.brandmaker.databinding.NotificationDataBinding;
import com.algorepublic.brandmaker.ui.dashboard.MainActivity;
import com.algorepublic.brandmaker.ui.notification.NotificationFragment;
import com.algorepublic.brandmaker.utils.Constants;

/**
 * Created By apple on 2019-08-02
 */
public class CheckOutFragment extends Fragment {
    private CheckoutDataBinding binding;
    private CheckOutViewModel viewModel;
    private int storeID;

    public static CheckOutFragment getInstance(int storeID) {
        CheckOutFragment fragment = new CheckOutFragment();
        Bundle args = new Bundle();
        args.putInt("StoreID",storeID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_check_out, container, false);
        viewModel = ViewModelProviders.of(this).get(CheckOutViewModel.class);
        storeID= getArguments().getInt("StoreID");

        binding.checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BMApp.db.putBoolean(Constants.CHECK_IN, false);
                ((MainActivity) getContext()).reload();
            }
        });

        return binding.getRoot();
    }

}

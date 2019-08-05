package com.algorepublic.brandmaker.ui.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.CheckoutDataBinding;
import com.algorepublic.brandmaker.databinding.NotificationDataBinding;
import com.algorepublic.brandmaker.ui.notification.NotificationFragment;

/**
 * Created By apple on 2019-08-02
 */
public class CheckOutFragment extends Fragment {
    private CheckoutDataBinding b;
    private CheckOutViewModel viewModel;

    public static CheckOutFragment getInstance() {
        CheckOutFragment fragment = new CheckOutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_check_out, container, false);
        viewModel = ViewModelProviders.of(this).get(CheckOutViewModel.class);

        return b.getRoot();
    }

}

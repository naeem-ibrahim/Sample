package com.algorepublic.brandmaker.ui.storedetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.FragmentInfoBinding;
import com.algorepublic.brandmaker.ui.checkout.CheckOutFragment;
import com.algorepublic.brandmaker.ui.dashboard.MainActivity;

/**
 * Created By apple on 2019-08-05
 */
public class StoreDetailsFragment extends Fragment {

    private FragmentInfoBinding binding;
    private StoreDetailsViewModel viewModel;

    public static StoreDetailsFragment getInstance() {
        StoreDetailsFragment fragment = new StoreDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false);
        viewModel = ViewModelProviders.of(this).get(StoreDetailsViewModel.class);
        ((MainActivity) getContext()).setToolBar("Store Name", "Check In 02-08-2019 - 9:00 PM", false);

        return binding.getRoot();
    }

}

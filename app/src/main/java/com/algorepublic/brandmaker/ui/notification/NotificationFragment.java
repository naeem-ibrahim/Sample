package com.algorepublic.brandmaker.ui.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.NotificationDataBinding;
/**
 * Created By apple on 2019-08-02
 */
public class NotificationFragment extends Fragment {
    private NotificationDataBinding b;
    private NotificationViewModel viewModel;

    public static NotificationFragment getInstance() {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false);
        viewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);

        return b.getRoot();
    }




}

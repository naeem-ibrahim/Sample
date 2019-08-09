package com.algorepublic.brandmaker.ui.storedetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.FragmentInfoBinding;
import com.algorepublic.brandmaker.model.StoresModel;
import com.algorepublic.brandmaker.ui.dashboard.MainActivity;

/**
 * Created By apple on 2019-08-05
 */
public class StoreDetailsFragment extends Fragment {

    private FragmentInfoBinding binding;
    private StoreDetailsViewModel viewModel;
    private StoresModel store;

    public static StoreDetailsFragment getInstance(StoresModel store) {
        StoreDetailsFragment fragment = new StoreDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("Store",store);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false);
        viewModel = ViewModelProviders.of(this).get(StoreDetailsViewModel.class);

        store=(StoresModel)getArguments().getSerializable("Store");

        ((MainActivity) getContext()).setToolBar(store.getStoreName(), "Check In 02-08-2019 - 9:00 PM", false);

        binding.mapNavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                Uri.parse("http://maps.google.com/maps?daddr=20.5666,45.345"));
//                startActivity(intent);
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("www.google.com")
                        .appendPath("maps")
                        .appendPath("dir")
                        .appendPath("")
                        .appendQueryParameter("api", "1")
                        .appendQueryParameter("destination", store.getLatitude() + "," + store.getLongitude());
                String url = builder.build().toString();
//                Log.d("Directions", url);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        return binding.getRoot();
    }

}

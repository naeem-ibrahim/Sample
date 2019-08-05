package com.algorepublic.brandmaker.ui.answers.picture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.FragmentAttemptAnswersBinding;
import com.algorepublic.brandmaker.databinding.FragmentTakePhotoBinding;
import com.algorepublic.brandmaker.ui.answers.options.OptionViewModel;
import com.algorepublic.brandmaker.ui.answers.options.OptionsFragment;

/**
 * Created By apple on 2019-08-02
 */
public class PictureFragment extends Fragment {

    private FragmentTakePhotoBinding binding;
    private PictureViewModel viewModel;

    public static PictureFragment getInstance() {
        PictureFragment fragment = new PictureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_take_photo, container, false);
        viewModel = ViewModelProviders.of(this).get(PictureViewModel.class);

        return binding.getRoot();
    }

}

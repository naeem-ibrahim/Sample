package com.algorepublic.brandmaker.ui.answers.options;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.databinding.FragmentAttemptAnswersBinding;

/**
 * Created By apple on 2019-08-02
 */
public class OptionsFragment extends Fragment {

    private FragmentAttemptAnswersBinding b;
    private OptionViewModel viewModel;

    public static OptionsFragment getInstance() {
        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_attempt_answers, container, false);
        viewModel = ViewModelProviders.of(this).get(OptionViewModel.class);

        return b.getRoot();
    }

}

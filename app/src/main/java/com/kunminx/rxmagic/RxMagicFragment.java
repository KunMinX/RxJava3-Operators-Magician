package com.kunminx.rxmagic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kunminx.rxmagic.databinding.FragmentRxmagicBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Create by KunMinX at 19/4/20
 */
public class RxMagicFragment extends Fragment {

    private FragmentRxmagicBinding mBinding;

    public static RxMagicFragment newInstance() {
        Bundle args = new Bundle();
        RxMagicFragment fragment = new RxMagicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxmagic, container, false);
        mBinding = FragmentRxmagicBinding.bind(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}

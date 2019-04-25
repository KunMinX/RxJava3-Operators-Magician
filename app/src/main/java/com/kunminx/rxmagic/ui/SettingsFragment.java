package com.kunminx.rxmagic.ui;
/*
 * Copyright (c) 2018-2019. KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.databinding.FragmentSettingsBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Create by KunMinX at 19/4/23
 */
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mBinding = FragmentSettingsBinding.bind(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.toolbar.setTitle(R.string.guide);
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_drawer_menu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbar);


    }


    private void showTipOfDeveloping(View v) {
        showTip(v, getString(R.string.tip_developing));
    }

    private void showTip(View v, String tip) {
        Snackbar.make(v, tip, Snackbar.LENGTH_SHORT)
                .setAnchorView(v)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                .show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.rxmagic_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((MainActivity) getActivity()).openDrawer();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

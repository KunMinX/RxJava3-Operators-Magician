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

import com.google.android.material.tabs.TabLayoutMediator;
import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.databinding.FragmentOperatorsBinding;
import com.kunminx.rxmagic.ui.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Create by KunMinX at 19/4/23
 */
public class OperatorsFragment extends BaseFragment {

    private FragmentOperatorsBinding mBinding;
    private String[] mFragmentTitles;
    private Fragment[] mFragments;

    public static OperatorsFragment newInstance() {
        Bundle args = new Bundle();
        OperatorsFragment fragment = new OperatorsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operators, container, false);
        mBinding = FragmentOperatorsBinding.bind(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.toolbar.setTitle(R.string.operators);
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_drawer_menu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbar);

        mFragmentTitles = getResources().getStringArray(R.array.fragments);
        mFragments = new Fragment[mFragmentTitles.length];

        mBinding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return OperatorsFragment.this.createFragment(position);
            }

            @Override
            public int getItemCount() {
                return mFragmentTitles.length;
            }
        });

        new TabLayoutMediator(mBinding.tabs, mBinding.viewPager, (tab, position) -> {
            tab.setText(mFragmentTitles[position].replace("ExampleFragment", ""));
        }).attach();
    }

    private Fragment createFragment(Integer tag) {
        if (mFragments[tag] != null) {
            return mFragments[tag];
        }
        String name = "com.kunminx.samples.ui.operators." + mFragmentTitles[tag];
        Fragment fragment = null;
        try {
            fragment = (Fragment) Class.forName(name).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        mFragments[tag] = fragment;
        return mFragments[tag];
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

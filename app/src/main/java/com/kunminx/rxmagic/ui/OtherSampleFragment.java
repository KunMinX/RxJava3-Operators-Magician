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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.databinding.FragmentOtherSampleBinding;
import com.kunminx.rxmagic.ui.base.BaseFragment;
import com.kunminx.samples.ui.cache.CacheExampleFragment;
import com.kunminx.samples.ui.networking.NetworkingFragment;
import com.kunminx.samples.ui.operators.CompletableObserverExampleFragment;
import com.kunminx.samples.ui.pagination.PaginationFragment;
import com.kunminx.samples.ui.rxbus.RxBusFragment;
import com.kunminx.samples.ui.search.SearchFragment;

/**
 * Create by KunMinX at 19/4/23
 */
public class OtherSampleFragment extends BaseFragment {

  private static final String TAG = "";
  private FragmentOtherSampleBinding mBinding;
  private NetworkingFragment mNetworkingFragment;
  private CacheExampleFragment mCacheExampleFragment;
  private RxBusFragment mRxBusFragment;
  private PaginationFragment mPaginationFragment;
  private CompletableObserverExampleFragment mCompletableObserverExampleFragment;
  private SearchFragment mSearchFragment;
  private Fragment mLastFragment;
  private String mLastFragmentTag = "";

  public static OtherSampleFragment newInstance(String fragmentTag) {
    Bundle args = new Bundle();
    args.putString(TAG, fragmentTag);
    OtherSampleFragment fragment = new OtherSampleFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_other_sample, container, false);
    mBinding = FragmentOtherSampleBinding.bind(view);
    setHasOptionsMenu(true);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    String tag = getArguments().getString(TAG);

    if (TextUtils.isEmpty(tag)) {
      tag = "Other Samples";
    }

    mBinding.toolbar.setTitle(tag.replace("Fragment", ""));
    mBinding.toolbar.setNavigationIcon(R.drawable.ic_drawer_menu);
    ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbar);

    replaceSubFragment(tag);
  }

  public void replaceSubFragment(String fragmentTag) {
    if (TextUtils.isEmpty(fragmentTag) || mLastFragmentTag.equals(fragmentTag)) {
      return;
    }
    mLastFragmentTag = fragmentTag;
    mBinding.toolbar.setTitle(fragmentTag.replace("Fragment", ""));
    switch (fragmentTag) {
      case "NetworkingFragment":
        loadNetworkingFragment();
        break;
      case "RxBusFragment":
        loadRxBusFragment();
        break;
      case "PaginationFragment":
        loadPaginationFragment();
        break;
      case "CompletableObserverExampleFragment":
        loadCompletableObserverExampleFragment();
        break;
      case "CacheExampleFragment":
        loadCacheExampleFragment();
        break;
      case "SearchFragment":
        loadSearchFragment();
        break;
      default:
    }
  }

  private void loadFragment(Fragment fragment) {
    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

    if (!fragment.isAdded()) {
      transaction.add(R.id.sub_fragment_container, fragment, fragment.getClass().getSimpleName());
    } else {
      transaction.show(fragment);
    }
    if (mLastFragment != null) {
      transaction.hide(mLastFragment);
    }
    transaction.commit();
    mLastFragment = fragment;
  }

  private void loadNetworkingFragment() {
    if (mNetworkingFragment == null) {
      mNetworkingFragment = new NetworkingFragment();
    }
    loadFragment(mNetworkingFragment);
  }

  private void loadRxBusFragment() {
    if (mRxBusFragment == null) {
      mRxBusFragment = new RxBusFragment();
    }
    loadFragment(mRxBusFragment);
  }

  private void loadPaginationFragment() {
    if (mPaginationFragment == null) {
      mPaginationFragment = new PaginationFragment();
    }
    loadFragment(mPaginationFragment);
  }

  private void loadCompletableObserverExampleFragment() {
    if (mCompletableObserverExampleFragment == null) {
      mCompletableObserverExampleFragment = new CompletableObserverExampleFragment();
    }
    loadFragment(mCompletableObserverExampleFragment);
  }

  private void loadCacheExampleFragment() {
    if (mCacheExampleFragment == null) {
      mCacheExampleFragment = new CacheExampleFragment();
    }
    loadFragment(mCacheExampleFragment);
  }

  private void loadSearchFragment() {
    if (mSearchFragment == null) {
      mSearchFragment = new SearchFragment();
    }
    loadFragment(mSearchFragment);
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

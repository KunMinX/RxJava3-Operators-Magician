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
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.databinding.FragmentAboutBinding;
import com.kunminx.rxmagic.ui.base.BaseFragment;

/**
 * Create by KunMinX at 19/4/23
 */
public class AboutFragment extends BaseFragment {

  private FragmentAboutBinding mBinding;

  public static AboutFragment newInstance() {
    Bundle args = new Bundle();
    AboutFragment fragment = new AboutFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_about, container, false);
    mBinding = FragmentAboutBinding.bind(view);
    setHasOptionsMenu(true);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mBinding.toolbar.setTitle(R.string.about);
    mBinding.toolbar.setNavigationIcon(R.drawable.ic_drawer_menu);
    ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbar);

    mBinding.webView.getSettings().setUseWideViewPort(true);
    mBinding.webView.getSettings().setJavaScriptEnabled(true);
    mBinding.webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
    mBinding.webView.loadUrl(getString(R.string.link_about));
    mBinding.webView.setWebChromeClient(new WebChromeClientProgress());
  }

  private class WebChromeClientProgress extends WebChromeClient {
    @Override
    public void onProgressChanged(WebView view, int progress) {
      mBinding.progress.setProgress(progress);
      if (progress == 100) {
        mBinding.progress.setVisibility(View.GONE);
      }
      super.onProgressChanged(view, progress);
    }
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

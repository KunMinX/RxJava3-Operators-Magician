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
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.databinding.ActivityMainBinding;
import com.kunminx.rxmagic.ui.base.BaseActivity;
import com.kunminx.samples.ui.cache.CacheExampleFragment;
import com.kunminx.samples.ui.networking.NetworkingFragment;
import com.kunminx.samples.ui.operators.CompletableObserverExampleFragment;
import com.kunminx.samples.ui.pagination.PaginationFragment;
import com.kunminx.samples.ui.rxbus.RxBusFragment;
import com.kunminx.samples.ui.search.SearchFragment;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;



/**
 * Create by KunMinX at 19/4/17
 */
public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBinding;
    private RxGuideFragment mRxGuideFragment;
    private RxMagicFragment mRxMagicFragment;
    private OperatorsFragment mOperatorsFragment;
    private OtherSampleFragment mOtherSampleFragment;
    private SettingsFragment mSettingsFragment;
    private AboutFragment mAboutFragment;
    private Fragment mLastFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        KeyboardVisibilityEvent.setEventListener(this, isOpen -> {
            mRxMagicFragment.setCardViewVisible(isOpen);
        });

        hideNavigationViewScrollbars(mBinding.navView);
        mBinding.navView.setNavigationItemSelectedListener(item -> {
            if (item.isChecked()) {
                closeDrawer();
                return true;
            }
            switch (item.getItemId()) {
                case R.id.nav_guide:
                    loadGuideFragment();
                    break;
                case R.id.nav_declare:
                    loadMagicFragment();
                    break;
                case R.id.nav_operators:
                    loadOperatorsFragment();
                    break;
                case R.id.nav_networking:
                    loadOtherSampleFragment(NetworkingFragment.class.getSimpleName());
                    break;
                case R.id.nav_cache:
                    loadOtherSampleFragment(CacheExampleFragment.class.getSimpleName());
                    break;
                case R.id.nav_rxbus:
                    loadOtherSampleFragment(RxBusFragment.class.getSimpleName());
                    break;
                case R.id.nav_pagination:
                    loadOtherSampleFragment(PaginationFragment.class.getSimpleName());
                    break;
                case R.id.nav_compose:
                    loadOtherSampleFragment(CompletableObserverExampleFragment.class.getSimpleName());
                    break;
                case R.id.nav_search:
                    loadOtherSampleFragment(SearchFragment.class.getSimpleName());
                    break;
                case R.id.nav_settings:
                    loadSettingFragment();
                    break;
                case R.id.nav_about:
                    loadAboutFragment();
                    break;
                default:
            }
            closeDrawer();
            return true;
        });

        loadMagicFragment();

    }

    private void hideNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            for (int i = 0; i < navigationView.getChildCount(); i++) {
                View view = navigationView.getChildAt(i);
                if (view instanceof NavigationMenuView) {
                    NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(i);
                    if (navigationMenuView != null) {
                        navigationMenuView.setVerticalScrollBarEnabled(false);
                        navigationMenuView.setOverScrollMode(navigationMenuView.OVER_SCROLL_NEVER);
                    }
                }
            }
        }
    }

    public void loadMagicFragment() {
        if (mRxMagicFragment == null) {
            mRxMagicFragment = RxMagicFragment.newInstance();
        }
        loadFragment(mRxMagicFragment);
    }

    private void loadGuideFragment() {
        if (mRxGuideFragment == null) {
            mRxGuideFragment = RxGuideFragment.newInstance();
        }
        loadFragment(mRxGuideFragment);
    }

    private void loadOperatorsFragment() {
        if (mOperatorsFragment == null) {
            mOperatorsFragment = OperatorsFragment.newInstance();
        }
        loadFragment(mOperatorsFragment);
    }

    private void loadOtherSampleFragment(String fragmentTag) {
        if (mOtherSampleFragment == null) {
            mOtherSampleFragment = OtherSampleFragment.newInstance(fragmentTag);
            loadFragment(mOtherSampleFragment);
        } else {
            mOtherSampleFragment.replaceSubFragment(fragmentTag);
            loadFragment(mOtherSampleFragment);
        }
    }

    private void loadSettingFragment() {
        if (mSettingsFragment == null) {
            mSettingsFragment = SettingsFragment.newInstance();
        }
        loadFragment(mSettingsFragment);
    }

    private void loadAboutFragment() {
        if (mAboutFragment == null) {
            mAboutFragment = AboutFragment.newInstance();
        }
        loadFragment(mAboutFragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (!fragment.isAdded()) {
            transaction.add(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        } else if (!fragment.isVisible()) {
            transaction.show(fragment);
        }
        if (mLastFragment != null && !mLastFragment.equals(fragment)) {
            transaction.hide(mLastFragment);
        }
        transaction.commit();
        mLastFragment = fragment;
    }

    public void openDrawer() {
        mBinding.drawer.openDrawer(GravityCompat.START);
    }

    public boolean closeDrawer() {
        if (mBinding.drawer.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (closeDrawer() || isSureToExitAfterDoubleClick()) {
                    return true;
                }
                break;
            default:
        }
        return super.onKeyDown(keyCode, event);
    }

}

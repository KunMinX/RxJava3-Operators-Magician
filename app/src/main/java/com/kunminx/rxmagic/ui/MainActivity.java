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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.databinding.ActivityMainBinding;
import com.kunminx.samples.ui.OperatorsActivity;
import com.kunminx.samples.ui.cache.CacheExampleActivity;
import com.kunminx.samples.ui.networking.NetworkingActivity;
import com.kunminx.samples.ui.pagination.PaginationActivity;
import com.kunminx.samples.ui.search.SearchActivity;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Create by KunMinX at 19/4/17
 */
public class MainActivity extends AppCompatActivity {

    private int mScreenHeight;
    private ActivityMainBinding mBinding;
    private RxMagicFragment mRxMagicFragment;
    private RxGuideFragment mRxGuideFragment;
    private AboutFragment mAboutFragment;
    private SettingsFragment mSettingsFragment;
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
            switch (item.getItemId()) {
                case R.id.nav_guide:
                    if (!item.isChecked()) {
                        loadGuideFragment();
                    }
                    closeDrawer();
                    break;
                case R.id.nav_declare:
                    if (!item.isChecked()) {
                        loadMagicFragment();
                    }
                    closeDrawer();
                    break;
                case R.id.nav_operators:
                    startActivity(new Intent(MainActivity.this, OperatorsActivity.class));
                    break;
                case R.id.nav_networking:
                    startActivity(new Intent(MainActivity.this, NetworkingActivity.class));
                    break;
                case R.id.nav_cache:
                    startActivity(new Intent(MainActivity.this, CacheExampleActivity.class));
                    break;
                case R.id.nav_rxbus:
                    //TODO
                    Snackbar.make(mBinding.navView, getString(R.string.tip_developing), Snackbar.LENGTH_SHORT).show();
//                    ((MyApplication) MainActivity.this.getApplication()).sendAutoEvent();
//                    startActivity(new Intent(MainActivity.this, RxBusActivity.class));
                    break;
                case R.id.nav_pagination:
                    startActivity(new Intent(MainActivity.this, PaginationActivity.class));
                    break;
                case R.id.nav_compose:
                    //TODO
                    Snackbar.make(mBinding.navView, getString(R.string.tip_developing), Snackbar.LENGTH_SHORT).show();
//                    startActivity(new Intent(MainActivity.this, ComposeOperatorExampleActivity.class));
                    break;
                case R.id.nav_search:
                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                    break;
                case R.id.nav_settings:
                    //TODO
                    Snackbar.make(mBinding.navView, getString(R.string.tip_developing), Snackbar.LENGTH_SHORT).show();
                    break;
                case R.id.nav_about:
                    if (!item.isChecked()) {
                        loadAboutFragment();
                    }
                    closeDrawer();
                    break;
                default:
            }
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

    private void loadAboutFragment() {
        if (mAboutFragment == null) {
            mAboutFragment = AboutFragment.newInstance();
        }
        loadFragment(mAboutFragment);
    }

    private void loadSettingFragment() {
        if (mSettingsFragment == null) {
            mSettingsFragment = SettingsFragment.newInstance();
        }
        loadFragment(mSettingsFragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (!fragment.isAdded()) {
            transaction.add(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        } else {
            transaction.show(fragment);
        }
        if (mLastFragment != null) {
            transaction.hide(mLastFragment);
        }
        transaction.commit();
        mLastFragment = fragment;
    }

    public void openDrawer() {
        mBinding.drawer.openDrawer(GravityCompat.START);
    }

    public void closeDrawer() {
        mBinding.drawer.closeDrawer(GravityCompat.START);
    }

}

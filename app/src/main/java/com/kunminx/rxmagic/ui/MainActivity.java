package com.kunminx.rxmagic.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.databinding.ActivityMainBinding;
import com.kunminx.rxmagic.MyApplication;
import com.kunminx.samples.ui.OperatorsActivity;
import com.kunminx.samples.ui.cache.CacheExampleActivity;
import com.kunminx.samples.ui.compose.ComposeOperatorExampleActivity;
import com.kunminx.samples.ui.networking.NetworkingActivity;
import com.kunminx.samples.ui.pagination.PaginationActivity;
import com.kunminx.samples.ui.rxbus.RxBusActivity;
import com.kunminx.samples.ui.search.SearchActivity;

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

    private ActivityMainBinding mBinding;
    private RxMagicFragment mRxMagicFragment;
    private RxGuideFragment mRxGuideFragment;
    private Fragment mLastFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

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
//                    ((MyApplication) MainActivity.this.getApplication()).sendAutoEvent();
//                    startActivity(new Intent(MainActivity.this, RxBusActivity.class));
                    break;
                case R.id.nav_pagination:
                    startActivity(new Intent(MainActivity.this, PaginationActivity.class));
                    break;
                case R.id.nav_compose:
                    startActivity(new Intent(MainActivity.this, ComposeOperatorExampleActivity.class));
                    break;
                case R.id.nav_search:
                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                    break;
                case R.id.nav_about:
//                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                    break;
                default:
            }
            return true;
        });

        loadMagicFragment();


    }

    //TODO
    private ColorStateList getDefaultTint() {
        int[] colors = new int[]{
                getResources().getColor(R.color.colorWhite),
                getResources().getColor(R.color.colorWhite),
                getResources().getColor(R.color.colorWhite),
                getResources().getColor(R.color.colorWhite),
                getResources().getColor(R.color.colorWhite),
                getResources().getColor(R.color.colorWhite)
        };
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_checked, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        return new ColorStateList(states, colors);
    }


    //TODO 此处遍历可用操作符简写
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

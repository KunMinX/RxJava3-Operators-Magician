package com.kunminx.rxmagic;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.kunminx.rxmagic.databinding.FragmentRxmagicBinding;
import com.kunminx.samples.MyApplication;
import com.kunminx.samples.ui.OperatorsActivity;
import com.kunminx.samples.ui.cache.CacheExampleActivity;
import com.kunminx.samples.ui.compose.ComposeOperatorExampleActivity;
import com.kunminx.samples.ui.networking.NetworkingActivity;
import com.kunminx.samples.ui.pagination.PaginationActivity;
import com.kunminx.samples.ui.rxbus.RxBusActivity;
import com.kunminx.samples.ui.search.SearchActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
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

        mBinding.toolbar.setTitle(R.string.app_name);
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_drawer_menu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbar);

//        mBinding.navView.setBackgroundTintList(getDefaultTint());
        hideNavigationViewScrollbars(mBinding.navView);
        mBinding.navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_declare:
                    mBinding.drawer.closeDrawer(GravityCompat.START);
                    break;
                case R.id.nav_operators:
                    startActivity(new Intent(getActivity(), OperatorsActivity.class));
                    break;
                case R.id.nav_networking:
                    startActivity(new Intent(getActivity(), NetworkingActivity.class));
                    break;
                case R.id.nav_cache:
                    startActivity(new Intent(getActivity(), CacheExampleActivity.class));
                    break;
                case R.id.nav_rxbus:
                    //TODO
                    ((MyApplication) getActivity().getApplication()).sendAutoEvent();
                    startActivity(new Intent(getActivity(), RxBusActivity.class));
                    break;
                case R.id.nav_pagination:
                    startActivity(new Intent(getActivity(), PaginationActivity.class));
                    break;
                case R.id.nav_compose:
                    startActivity(new Intent(getActivity(), ComposeOperatorExampleActivity.class));
                    break;
                case R.id.nav_search:
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                    break;
                case R.id.nav_about:
//                    startActivity(new Intent(getActivity(), SearchActivity.class));
                    break;
                default:
            }
            return true;
        });

        mBinding.tvCode.setText(getString(R.string.test_code));
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


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.rxmagic_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mBinding.drawer.openDrawer(GravityCompat.START);

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

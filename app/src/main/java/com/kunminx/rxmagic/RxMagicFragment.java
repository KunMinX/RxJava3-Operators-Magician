package com.kunminx.rxmagic;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
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
import androidx.annotation.RequiresApi;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ImageView ivHead = (ImageView) mBinding.navView.inflateHeaderView(R.layout.layout_drawer_header);
//        ImageView imageView = (ImageView) ivHead.findViewById(R.id.iv_drawer);
//        Glide.with(this).load(getResources().getDrawable(R.drawable.bg_head)).into(ivHead);
        mBinding.navView.setBackgroundTintList(getDefaultTint());
        mBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                    default:
                }
                return true;
            }
        });
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.rxmagic_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case android.
        }

        return super.onOptionsItemSelected(item);
    }
}

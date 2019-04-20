package com.kunminx.rxmagic;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.kunminx.rxmagic.databinding.ActivityMainBinding;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

/**
 * Create by KunMinX at 19/4/17
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private RxMagicFragment mRxMagicFragment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        loadMagicFragment();


        ImageView ivHead = (ImageView) mBinding.navView.getHeaderView(0);
//        Glide.with(this).load("").centerCrop().into(ivHead);
        mBinding.navView.setBackgroundTintList(getDefaultTint());
        mBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_declare:

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
                        ((MyApplication) getApplication()).sendAutoEvent();
                        startActivity(new Intent(MainActivity.this, RxBusActivity.class));
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
                    default:
                }
                return true;
            }
        });
    }

    //TODO
    private ColorStateList getDefaultTint() {
        int[] colors = new int[]{
                getResources().getColor(R.color.colorGray),
                getResources().getColor(R.color.colorGray),
                getResources().getColor(R.color.colorGray),
                getResources().getColor(R.color.colorGray),
                getResources().getColor(R.color.colorGray),
                getResources().getColor(R.color.colorGray)
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

    private void loadMagicFragment() {
        mRxMagicFragment = RxMagicFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(mRxMagicFragment, mRxMagicFragment.getClass().getSimpleName())
                .addToBackStack(null).commit();
    }

}

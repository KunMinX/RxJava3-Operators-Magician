package com.kunminx.rxmagic;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.kunminx.rxmagic.databinding.ActivityMainBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

/**
 * Create by KunMinX at 19/4/17
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        ImageView ivHead = (ImageView) mBinding.navView.getHeaderView(0);
//        Glide.with(this).load("").centerCrop().into(ivHead);

        mBinding.navView.setItemIconTintList(null);
        mBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_operators:

                        break;
                    case R.id.nav_networking:

                        break;
                    case R.id.nav_cache:

                        break;
                    case R.id.nav_rxbus:

                        break;
                    case R.id.nav_pagination:

                        break;
                    case R.id.nav_compose:

                        break;
                    case R.id.nav_search:

                        break;
                    default:
                }
                return true;
            }
        });

    }


}

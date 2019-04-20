package com.kunminx.rxmagic;

import android.os.Bundle;

import com.kunminx.rxmagic.databinding.ActivityMainBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

/**
 * Create by KunMinX at 19/4/17
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private RxMagicFragment mRxMagicFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        loadMagicFragment();


    }

    private void loadMagicFragment() {
        mRxMagicFragment = RxMagicFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mRxMagicFragment)
                .addToBackStack(null).commit();
    }

}

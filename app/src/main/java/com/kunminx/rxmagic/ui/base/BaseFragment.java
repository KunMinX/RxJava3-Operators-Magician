package com.kunminx.rxmagic.ui.base;
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


import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.kunminx.rxmagic.R;

/**
 * Create by KunMinX at 19/5/6
 */
public class BaseFragment extends Fragment {


  protected void showTipOfDeveloping(View v) {
    showTip(v, getString(R.string.tip_developing));
  }

  protected void showTip(View v, String tip) {
    Snackbar.make(v, tip, Snackbar.LENGTH_SHORT)
            .setAnchorView(v)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
            .show();
  }
}

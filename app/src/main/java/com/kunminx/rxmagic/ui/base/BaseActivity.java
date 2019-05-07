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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.kunminx.rxmagic.R;

/**
 * Create by KunMinX at 19/5/6
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * avoid multi times click in a time.
     */
    private long enableTime = 0;

    /**
     * exit time, to judge if is sure to exit
     */
    private long exitTime = 0;

    private static final int DEFAULT_EXIT_DURATION = 2000;


    /**
     * allow multi click during a time
     *
     * @param duration
     * @return isEnableToClick
     */
    protected boolean isEnableToClick(long duration) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - enableTime > duration) {
            enableTime = currentTime;
            return true;
        } else {
            return false;
        }
    }

    /**
     * is sure to exit after double click
     *
     * @return isSureToExit
     */

    protected boolean isSureToExitAfterDoubleClick(View view, String tip, int duration) {
        if ((System.currentTimeMillis() - exitTime) > duration) {
            Snackbar.make(view, tip, Snackbar.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }

    protected boolean isSureToExitAfterDoubleClick(View view, String tip) {
        return isSureToExitAfterDoubleClick(view, tip, DEFAULT_EXIT_DURATION);
    }

    protected boolean isSureToExitAfterDoubleClick(String tip) {
        return isSureToExitAfterDoubleClick(getWindow().getDecorView(), tip, DEFAULT_EXIT_DURATION);
    }

    protected boolean isSureToExitAfterDoubleClick() {
        return isSureToExitAfterDoubleClick(getWindow().getDecorView(), getString(R.string.exit_tip), DEFAULT_EXIT_DURATION);
    }
}

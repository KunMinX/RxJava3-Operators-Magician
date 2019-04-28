package com.kunminx.rxmagic.ui.widget;
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


import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Create by KunMinX at 19/4/28
 */
public class ScrollListenWebView extends WebView {

    private OnScrollChangeListener mListener;
    private int mCalCount;

    public ScrollListenWebView(Context context) {
        super(context);
    }

    public ScrollListenWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollListenWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float contentHeight = getContentHeight() * getScale();
        float currentHeight = getHeight() + getScrollY();
        if (Math.abs(contentHeight - currentHeight) < 1) {
            mListener.onPageEnd(l, t, oldl, oldt);
        } else if (getScrollY() == 0) {
            mListener.onPageTop(l, t, oldl, oldt);
        } else {
            mListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public void setListener(OnScrollChangeListener listener) {
        mListener = listener;
    }

    public interface OnScrollChangeListener {
        void onPageEnd(int l, int t, int oldl, int oldt);

        void onPageTop(int l, int t, int oldl, int oldt);

        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}

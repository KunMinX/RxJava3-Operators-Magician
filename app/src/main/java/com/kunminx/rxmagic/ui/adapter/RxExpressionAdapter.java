package com.kunminx.rxmagic.ui.adapter;

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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;

import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.bean.RxExpression;
import com.kunminx.rxmagic.databinding.AdapterRxExpressionBinding;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by KunMinX at 19/4/21
 */
public class RxExpressionAdapter extends BaseBindingAdapter<RxExpression, AdapterRxExpressionBinding> {

    private SparseArray<String> mTextCache = new SparseArray<>();
    private OnItemClickListener mListener;

    public RxExpressionAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_rx_expression;
    }

    @Override
    protected void onBindItem(AdapterRxExpressionBinding binding, RxExpression item, RecyclerView.ViewHolder holder) {

        binding.btnOp.setText(item.getRxOperator().getName());
        binding.et.setText(mTextCache.get(holder.getAdapterPosition()));

        if ((binding.et.getTag() != null && (boolean) binding.et.getTag())) {
            return;
        }

        binding.et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.equals(mTextCache.get(holder.getAdapterPosition()), s.toString())) {
                    return;
                }
                mTextCache.put(holder.getAdapterPosition(), s.toString());
                if (binding.et.getText() != null) {
                    item.setExpression(binding.et.getText().toString());
                }
            }
        });

        binding.btnOp.setOnClickListener(v -> {
            mListener.onItemClick(v, item, holder.getAdapterPosition());
        });
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RxExpression item, int position);
    }
}

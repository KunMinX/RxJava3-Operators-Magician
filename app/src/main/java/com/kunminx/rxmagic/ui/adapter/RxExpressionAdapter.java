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
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.bean.RxExpression;
import com.kunminx.rxmagic.databinding.AdapterRxExpressionBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by KunMinX at 19/4/21
 */
public class RxExpressionAdapter extends BaseBindingAdapter<RxExpression, AdapterRxExpressionBinding> {

    private SparseArray<String> mTextCache = new SparseArray<>();
    private OnItemClickListener mListener;
    private int mEtFocusPosition = -1;
    private InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    private boolean mIsDeleteMode;

    public void setDeleteMode(boolean deleteMode) {
        mIsDeleteMode = deleteMode;
        notifyDataSetChanged();
    }

    public boolean isDeleteMode() {
        return mIsDeleteMode;
    }

    public void removeCacheByPosition(int position) {
        mTextCache.remove(position);
    }

    public void clearCache() {
        mTextCache.clear();
    }

    public RxExpressionAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_rx_expression;
    }

    @Override
    protected void onBindItem(AdapterRxExpressionBinding binding, RxExpression item, RecyclerView.ViewHolder holder) {

        binding.btnOp.setVisibility(mIsDeleteMode ? View.GONE : View.VISIBLE);
        binding.btnDelete.setVisibility(mIsDeleteMode ? View.VISIBLE : View.GONE);

        binding.btnOp.setText(item.getRxOperator().getName());
        binding.et.setText(mTextCache.get(holder.getAdapterPosition()));
        binding.et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEtFocusPosition = holder.getAdapterPosition();
                }
            }
        });

        binding.btnOp.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onOperatorButtonClick(v, item, holder.getAdapterPosition());
            }
        });

        binding.btnDelete.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onDeleteButtonClick(v, item, holder.getAdapterPosition());
            }
        });
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mTextCache.put(mEtFocusPosition, s.toString());
            mList.get(mEtFocusPosition).setExpression(s.toString());
        }
    };

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        AdapterRxExpressionBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.et.addTextChangedListener(mTextWatcher);

        if (mEtFocusPosition == holder.getAdapterPosition()) {
            binding.et.requestFocus();
            binding.et.setSelection(binding.et.getText().length());
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        AdapterRxExpressionBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.et.removeTextChangedListener(mTextWatcher);

        binding.et.clearFocus();
        if (mEtFocusPosition == holder.getAdapterPosition()) {
            inputMethodManager.hideSoftInputFromWindow(binding.et.getWindowToken(), 0);
        }
    }

    public void setOnButtonClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
//        void onItemClick(View view, RxExpression item, int position);

        void onOperatorButtonClick(View view, RxExpression item, int position);

        void onDeleteButtonClick(View view, RxExpression item, int position);
    }
}

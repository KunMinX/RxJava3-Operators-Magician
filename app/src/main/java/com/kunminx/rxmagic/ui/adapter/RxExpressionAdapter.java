package com.kunminx.rxmagic.ui.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;

import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.bean.RxExpression;
import com.kunminx.rxmagic.databinding.AdapterRxExpressionBinding;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by KunMinX at 19/4/21
 */
public class RxExpressionAdapter extends BaseBindingAdapter<RxExpression, AdapterRxExpressionBinding> {

    private SparseArray<String> mTextCache = new SparseArray<>();

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
    }
}

package com.kunminx.rxmagic.ui;

import android.content.Context;

import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.bean.RxExpression;
import com.kunminx.rxmagic.databinding.AdapterRxExpressionBinding;
import com.kunminx.rxmagic.ui.adapter.BaseBindingAdapter;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by KunMinX at 19/4/21
 */
public class RxExpressionAdapter extends BaseBindingAdapter<RxExpression, AdapterRxExpressionBinding> {

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
        binding.et.setText(item.getExpression());

    }
}

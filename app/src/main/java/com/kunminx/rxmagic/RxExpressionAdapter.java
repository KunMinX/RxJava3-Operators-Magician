package com.kunminx.rxmagic;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by KunMinX at 19/4/21
 */
public class RxExpressionAdapter extends RecyclerView.Adapter<RxExpressionAdapter.RxExpressionViewHolder> {


    @NonNull
    @Override
    public RxExpressionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RxExpressionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class RxExpressionViewHolder extends RecyclerView.ViewHolder {

        public RxExpressionViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}

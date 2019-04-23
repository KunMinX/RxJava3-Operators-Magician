package com.kunminx.rxmagic.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author KunMinX
 * Create at 2018/6/30
 */
public abstract class BaseBindingAdapter<M, B extends ViewDataBinding> extends RecyclerView.Adapter {

    protected Context mContext;
    protected List<M> mList;
    /**
     * 是否开启了筛选模式
     */
    protected boolean mFilterMode;

    public void setFilterMode(boolean filterMode) {
        mFilterMode = filterMode;
    }

    public boolean isFilterMode() {
        return mFilterMode;
    }

    public List<M> getList() {
        return mList;
    }

    /**
     * 完整的列表。相对于可能被筛选的list来说。
     */
    private List<M> mOriginalList = new ArrayList<>();

    public List<M> getOriginalList() {
        return mOriginalList;
    }

    public BaseBindingAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
    }

    public void setList(List<M> list) {
        if (list != null) {
            mList.clear();
            mList.addAll(list);
            if (!mFilterMode) {
                mOriginalList.clear();
                mOriginalList.addAll(mList);
            }
        }
    }

    @Override
    public int getItemCount() {
        return null == this.mList ? 0 : this.mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(this.mContext), this.getLayoutResId(viewType), parent, false);
        return new BaseBindingViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        this.onBindItem(binding, this.mList.get(position), holder);
    }

    protected abstract @LayoutRes
    int getLayoutResId(int viewType);

    /**
     * RecyclerView 中的数据有位置改变（比如删除）时一般不会重新调用 onBindViewHolder() 方法，除非这个元素不可用。
     * 为了实时获取元素的位置，我们通过 ViewHolder.getAdapterPosition() 方法。
     *
     * @param binding
     * @param item
     * @param holder
     */
    protected abstract void onBindItem(B binding, M item, RecyclerView.ViewHolder holder);

    public class BaseBindingViewHolder extends RecyclerView.ViewHolder {
        BaseBindingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
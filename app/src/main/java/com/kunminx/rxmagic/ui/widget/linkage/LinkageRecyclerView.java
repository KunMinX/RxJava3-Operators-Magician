package com.kunminx.rxmagic.ui.widget.linkage;
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
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kunminx.rxmagic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by KunMinX at 19/4/27
 */
public class LinkageRecyclerView extends ConstraintLayout {

    private Context mContext;
    private RecyclerView mRvLevel1;
    private RecyclerView mRvLevel2;
    private LinkageLevel1Adapter mLevel1Adapter;
    private LinkageLevel2Adapter mLevel2Adapter;
    private TextView mTvLevel2Title;
    private List<String> mGroupNames;
    private List<LinkageItem> mItems;


    //右侧title在数据中所对应的position集合
    private List<Integer> mHeaderPositions = new ArrayList<>();

    //title的高度
    private int mTitleHeight;
    //记录右侧当前可见的第一个item的position
    private int mFirstPosition = 0;
    private GridLayoutManager mGridLayoutManager;

    public List<Integer> getHeaderPositions() {
        return mHeaderPositions;
    }

    public LinkageRecyclerView(Context context) {
        super(context);
    }

    public LinkageRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public LinkageRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context, @Nullable AttributeSet attrs) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_linkage_view, this);
        mRvLevel1 = (RecyclerView) view.findViewById(R.id.rv_level_1);
        mRvLevel2 = (RecyclerView) view.findViewById(R.id.rv_level_2);
        mTvLevel2Title = (TextView) view.findViewById(R.id.level_2_header);

        mGridLayoutManager = new GridLayoutManager(mContext, 3);
        if (mLevel2Adapter == null) {
            mLevel2Adapter = new LinkageLevel2Adapter(R.layout.adapter_linkage_level_2,
                    R.layout.layout_linkage_level_2_title, null);
            mRvLevel2.setLayoutManager(mGridLayoutManager);
            mRvLevel2.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(dpToPx(mContext, getDimens(mContext, R.dimen.dp_4))
                            , 0
                            , dpToPx(mContext, getDimens(mContext, R.dimen.dp_4))
                            , dpToPx(mContext, getDimens(mContext, R.dimen.dp_4)));
                }
            });
            mRvLevel2.setAdapter(mLevel2Adapter);
        }

        if (mLevel1Adapter == null) {
            mLevel1Adapter = new LinkageLevel1Adapter(R.layout.adapter_linkage_level_1, null);
            mRvLevel1.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            mRvLevel1.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
            mRvLevel1.setAdapter(mLevel1Adapter);
        }
    }

    public void init(List<String> groupNames, List<LinkageItem> linkageItems) {
        this.mItems = linkageItems;
        this.mGroupNames = groupNames;
        initLinkageLevel2();
        initLinkageLevel1();
    }

    private void initLinkageLevel2() {
        mLevel2Adapter.setNewData(mItems);

        //设置右侧初始title
        if (mItems.get(mFirstPosition).isHeader) {
            mTvLevel2Title.setText(mItems.get(mFirstPosition).header);
        }

        mRvLevel2.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取右侧title的高度
                mTitleHeight = mTvLevel2Title.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //判断如果是header
                if (mItems.get(mFirstPosition).isHeader) {
                    //获取此组名item的view
                    View view = mGridLayoutManager.findViewByPosition(mFirstPosition);
                    if (view != null) {
                        //如果此组名item顶部和父容器顶部距离大于等于title的高度,则设置偏移量
                        if (view.getTop() >= mTitleHeight) {
                            mTvLevel2Title.setY(view.getTop() - mTitleHeight);
                        } else {
                            //否则不设置
                            mTvLevel2Title.setY(0);
                        }
                    }
                }

                //因为每次滑动之后,右侧列表中可见的第一个item的position肯定会改变,并且右侧列表中可见的第一个item的position变换了之后,
                //才有可能改变右侧title的值,所以这个方法内的逻辑在右侧可见的第一个item的position改变之后一定会执行
                int firstPosition = mGridLayoutManager.findFirstVisibleItemPosition();
                if (mFirstPosition != firstPosition && firstPosition >= 0) {
                    //给first赋值
                    mFirstPosition = firstPosition;
                    //不设置Y轴的偏移量
                    mTvLevel2Title.setY(0);

                    //判断如果右侧可见的第一个item是否是header,设置相应的值
                    if (mItems.get(mFirstPosition).isHeader) {
                        mTvLevel2Title.setText(mItems.get(mFirstPosition).header);
                    } else {
                        mTvLevel2Title.setText(mItems.get(mFirstPosition).t.getGroup());
                    }
                }

                //遍历左边列表,列表对应的内容等于右边的title,则设置左侧对应item高亮
                for (int i = 0; i < mGroupNames.size(); i++) {
                    if (mGroupNames.get(i).equals(mTvLevel2Title.getText().toString())) {
                        mLevel1Adapter.selectItem(i);
                    }
                }

                //如果右边最后一个完全显示的item的position,等于bean中最后一条数据的position(也就是右侧列表拉到底了),
                //则设置左侧列表最后一条item高亮
                if (mGridLayoutManager.findLastCompletelyVisibleItemPosition() == mItems.size() - 1) {
                    mLevel1Adapter.selectItem(mGroupNames.size() - 1);
                }
            }
        });
    }

    private void initLinkageLevel1() {
        mLevel1Adapter.setNewData(mGroupNames);

        mLevel1Adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //点击左侧列表的相应item,右侧列表相应的title置顶显示
                    //(最后一组内容若不能填充右侧整个可见页面,则显示到右侧列表的最底端)
                    case R.id.layout_group:
                        mLevel1Adapter.selectItem(position);
                        mGridLayoutManager.scrollToPositionWithOffset(mHeaderPositions.get(position), 0);
                        break;
                    default:
                }
            }
        });
    }


    /**
     * 获得资源 dimens (dp)
     *
     * @param context
     * @param id      资源id
     * @return
     */
    public float getDimens(Context context, int id) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float px = context.getResources().getDimension(id);
        return px / dm.density;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5f);
    }
}

package com.kunminx.rxmagic.ui;

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

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kunminx.linkage.LinkageLevelOneAdapter;
import com.kunminx.linkage.LinkageLevelTwoAdapter;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.bean.LinkageItem;
import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.bean.RxExpression;
import com.kunminx.rxmagic.bean.RxOperator;
import com.kunminx.rxmagic.databinding.FragmentRxmagicBinding;
import com.kunminx.rxmagic.ui.adapter.RxExpressionAdapter;
import com.kunminx.rxmagic.ui.base.BaseFragment;

import java.util.List;

import thereisnospon.codeview.CodeViewTheme;

/**
 * Create by KunMinX at 19/4/20
 */
public class RxMagicFragment extends BaseFragment {

    private FragmentRxmagicBinding mBinding;
    private RxExpressionAdapter mAdapter;
    private static float DIALOG_HEIGHT = 480;

    public static RxMagicFragment newInstance() {
        Bundle args = new Bundle();
        RxMagicFragment fragment = new RxMagicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxmagic, container, false);
        mBinding = FragmentRxmagicBinding.bind(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.toolbar.setTitle(R.string.app_name);
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_drawer_menu);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbar);

        mAdapter = new RxExpressionAdapter(getContext());
        mAdapter.setOnButtonClickListener(new RxExpressionAdapter.OnItemClickListener() {
            @Override
            public void onOperatorButtonClick(View view, RxExpression item, int position) {
                View view2 = View.inflate(getContext(), R.layout.layout_linkage, null);
                LinkageRecyclerView linkage = view2.findViewById(R.id.linkage);
                initLinkageDatas(linkage);
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
                AlertDialog dialog = builder.setView(linkage).show();
                linkage.setLayoutHeight(DIALOG_HEIGHT);
                linkage.setClickListener(new LinkageRecyclerView.OnLinkageItemClickListener() {
                    @Override
                    public void onLinkageLevel1Click(LinkageLevelOneAdapter.LevelOneViewHolder holder,
                                                     String group, int position) {

                    }

                    @Override
                    public void onLinkageLevel2Click(LinkageLevelTwoAdapter.LevelTwoViewHolder holder,
                                                     LinkageItem linkageItem, int position) {
                        item.getRxOperator().setName(linkageItem.t.getTitle());
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onDeleteButtonClick(View view, RxExpression item, int position) {
                //to avoid click delete button too quickly to over animation and get index -1.
                if (position == -1) {
                    return;
                }
                mAdapter.removeCacheByPosition(position);
                mAdapter.getList().remove(position);
                mAdapter.notifyItemRemoved(position);
                afterRemoveItems();
            }
        });

        mBinding.rv.setAdapter(mAdapter);

        mBinding.code.setTheme(CodeViewTheme.ARDUINO_LIGHT).fillColor();
        mBinding.code.showCode(getString(R.string.code_tip));

        mBinding.btnAdd.setOnClickListener(v -> {
            //TODO testData
            RxOperator rxOperator = new RxOperator();
            rxOperator.setName("Just");
            rxOperator.setGroup("Creator");
            RxExpression expression = new RxExpression();
            expression.setRxOperator(rxOperator);

            mAdapter.getList().add(expression);
            mAdapter.notifyItemInserted(mAdapter.getList().size() - 1);

            if (!mBinding.btnRemove.isEnabled() && mAdapter.getList().size() > 0) {
                mBinding.btnRemove.setEnabled(true);
                mBinding.btnClear.setEnabled(true);
                mBinding.ivEmpty.setVisibility(View.GONE);
            }
        });

        mBinding.btnRemove.setOnClickListener(v -> {
            // if isDeleteMode when click btnRemove, then everything is going to disDeleteMode. Visa versa.
            boolean isDeleteMode = mAdapter.isDeleteMode();
            mAdapter.setDeleteMode(!isDeleteMode);
            mBinding.btnRemove.setText(isDeleteMode ? getString(R.string.btn_name_remove) : getString(R.string.btn_name_close));
            mBinding.btnAdd.setEnabled(isDeleteMode);
        });

        mBinding.btnClear.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(getContext(), R.style.AlertDialogTheme)
                    .setTitle(getString(R.string.dialog_title_warning))
                    .setMessage(getString(R.string.dialog_msg_clear_op_list))
                    .setPositiveButton(getString(R.string.sure), (dialog, which) -> {
                        mAdapter.clearCache();
                        mAdapter.getList().clear();
                        mAdapter.notifyDataSetChanged();
                        afterRemoveItems();
                    })
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show();
        });

        mBinding.btnPreview.setOnClickListener(v -> {
            String code = getCodeOfExpressions();
            if (TextUtils.isEmpty(code)) {
                showTip(v, getString(R.string.tip_of_developing));
            } else {
                mBinding.code.showCode(code);
            }
        });
    }

    private void afterRemoveItems() {
        mBinding.btnClear.setEnabled(mAdapter.isDeleteMode() && mAdapter.getList().size() > 0);
        if (mAdapter.getList().size() == 0) {
            mBinding.ivEmpty.setVisibility(View.VISIBLE);
            mBinding.code.showCode(getString(R.string.code_tip));
            mBinding.btnRemove.setEnabled(false);
            mAdapter.setDeleteMode(false);
            mBinding.btnRemove.setText(getString(R.string.btn_name_remove));
            mBinding.btnAdd.setEnabled(true);
        }
    }

    private void initLinkageDatas(LinkageRecyclerView linkage) {
        Gson gson = new Gson();
        List<LinkageItem> items = gson.fromJson(getString(R.string.operators_json),
                new TypeToken<List<LinkageItem>>() {
                }.getType());

        linkage.init(items);
    }

    private String getCodeOfExpressions() {
        if (mAdapter.getList().size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Output:\n\n").append("Observable");
        for (RxExpression rxExpression : mAdapter.getList()) {
            sb.append(".").append(rxExpression.getRxOperator().getName()).append("(");
            String expression = rxExpression.getExpression();

            //TODO if expression is i + 1, then make it i -> i + 1
            sb.append(expression).append(")\n");
        }
        sb.append(".subscribe(getObserve());\n");
        return sb.toString();
    }

    void setCardViewVisible(boolean isKeyboardShow) {
        mBinding.cdvCode.setVisibility(isKeyboardShow ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.rxmagic_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((MainActivity) getActivity()).openDrawer();

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

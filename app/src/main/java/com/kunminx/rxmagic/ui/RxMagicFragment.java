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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.bean.RxExpression;
import com.kunminx.rxmagic.bean.RxOperator;
import com.kunminx.rxmagic.databinding.FragmentRxmagicBinding;
import com.kunminx.rxmagic.ui.adapter.RxExpressionAdapter;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import thereisnospon.codeview.CodeViewTheme;

/**
 * Create by KunMinX at 19/4/20
 */
public class RxMagicFragment extends Fragment {

    private FragmentRxmagicBinding mBinding;
    private RxExpressionAdapter mAdapter;

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
        mAdapter.setListener((view1, item, position) -> {
            //TODO should be replaced by linkageListView
            showMenu(view1, R.menu.operator_menu, item);
        });
        mBinding.rv.setAdapter(mAdapter);

        try {
            mBinding.code.setTheme(CodeViewTheme.ARDUINO_LIGHT).fillColor();
            mBinding.code.showCode(getString(R.string.code_tip));
        } catch (Exception ex) {
            System.out.println(ex.toString() + "--------------------------------------");
        }

        mBinding.btnAdd.setOnClickListener(v -> {
            //TODO testData
            RxOperator rxOperator = new RxOperator();
            rxOperator.setName("Just");
            rxOperator.setGroup("Creator");
            RxExpression expression = new RxExpression();
            expression.setRxOperator(rxOperator);

            mAdapter.getList().add(expression);
            mAdapter.notifyItemInserted(mAdapter.getList().size() - 1);

            if (!mBinding.btnDelete.isEnabled() && mAdapter.getList().size() > 0) {
                mBinding.btnDelete.setEnabled(true);
                mBinding.btnClear.setEnabled(true);
                mBinding.ivEmpty.setVisibility(View.GONE);
            }
        });

        mBinding.btnDelete.setOnClickListener(this::showTipOfDeveloping);
        mBinding.btnClear.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(getContext(), R.style.AlertDialogTheme)
                    .setTitle(getString(R.string.dialog_title_warning))
                    .setMessage(getString(R.string.dialog_msg_clear_op_list))
                    .setPositiveButton(getString(R.string.sure), (dialog, which) -> {
                        mAdapter.getList().clear();
                        mAdapter.notifyDataSetChanged();
                        mBinding.ivEmpty.setVisibility(View.VISIBLE);
                        mBinding.btnDelete.setEnabled(false);
                        mBinding.btnClear.setEnabled(false);
                        mBinding.code.showCode(getString(R.string.code_tip));
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

    private void showTipOfDeveloping(View v) {
        showTip(v, getString(R.string.tip_developing));
    }

    private void showTip(View v, String tip) {
        Snackbar.make(v, tip, Snackbar.LENGTH_SHORT)
                .setAnchorView(v)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                .show();
    }

    private void showMenu(View v, @MenuRes int menuRes, RxExpression rxExpression) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
        if (popup.getMenu() instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) popup.getMenu();
            menuBuilder.setOptionalIconsVisible(true);
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                rxExpression.getRxOperator().setName(menuItem.getTitle().toString());
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });
        popup.show();
    }

    private String getCodeOfExpressions() {
        if (mAdapter.getList().size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Output:\n\n").append("Observable");
        for (RxExpression rxExpression : mAdapter.getList()) {
            sb.append(".").append(rxExpression.getRxOperator().getName().toLowerCase())
                    .append("(").append(rxExpression.getExpression()).append(")\n");
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

package com.kunminx.rxmagic.ui.widget.dialog;
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


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kunminx.rxmagic.R;
import com.kunminx.rxmagic.databinding.LayoutLinkageBinding;
import com.kunminx.rxmagic.ui.widget.linkage.LinkageItem;
import com.kunminx.rxmagic.ui.widget.linkage.LinkageRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by KunMinX at 19/4/27
 */
public class LinkageDialogFragment extends DialogFragment {

    private LayoutLinkageBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*View view = inflater.inflate(R.layout.layout_linkage, container, false);
        mBinding = LayoutLinkageBinding.bind(view);
        return view;*/
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.layout_linkage, null);
        LinkageRecyclerView linkage = view1.findViewById(R.id.linkage);

        initLinkageDatas(linkage);
        builder.setView(view1);
        return builder.create();
    }

    private void initLinkageDatas(LinkageRecyclerView linkage) {
        Gson gson = new Gson();
        List<LinkageItem> items = (List<LinkageItem>) gson.fromJson(getString(R.string.operators_json), new TypeToken<List<LinkageItem>>() {
        }.getType());
        List<String> groupNames = new ArrayList<>();

        try {
            if (items != null && items.size() > 0) {
                for (LinkageItem item1 : items) {
                    if (item1.isHeader) {
                        groupNames.add(item1.header);
                    }
                }
            }
            toString();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).isHeader) {
                    linkage.getHeaderPositions().add(i);
                }
            }
        } catch (Exception e) {
            e.toString();
        }
        linkage.init(groupNames, items);
    }
}

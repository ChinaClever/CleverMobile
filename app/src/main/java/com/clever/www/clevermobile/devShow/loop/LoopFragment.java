package com.clever.www.clevermobile.devShow.loop;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clever.www.clevermobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lzy. Created on: 17-2-10.
 */

public class LoopFragment extends Fragment{
    private List<LoopItem> mLoopItemList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loop_fragment,container, false);

        initLoop();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        LoopAdapter adapter = new LoopAdapter(mLoopItemList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    /**
     * 初始化回路界面
     */
    private void initLoop() {
        for(int i=0; i<6; ++i) {
            mLoopItemList.add(new LoopItem(i+1));
        }
    }


}

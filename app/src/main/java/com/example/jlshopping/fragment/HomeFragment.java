package com.example.jlshopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.jlshopping.R;
import com.example.jlshopping.adapter.GoodsClassAdapter;
import com.example.jlshopping.adapter.GoodsListAdapter;
import com.example.jlshopping.bean.GoodClassBean;
import com.example.jlshopping.bean.GoodListBean;
import com.example.jlshopping.util.JsonUtil;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 作者：@sy
 * 创建时间：2020/9/17 0017$ 10:36$
 * 描述：首页Fragment
 * 备注：
 */
public class HomeFragment extends Fragment {

    private GridView gridView1, gridView2;
    private GoodsClassAdapter mGoodsClassAdapter;
    private GoodsListAdapter mGoodsListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        gridView1 = view.findViewById(R.id.gridView1);
        gridView2 = view.findViewById(R.id.gridView2);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //上面的商品分类GradView
        ArrayList<GoodClassBean> goodsClass = JsonUtil.getGoodsClass(getContext());
        mGoodsClassAdapter = new GoodsClassAdapter(getContext(), goodsClass);
        gridView1.setAdapter(mGoodsClassAdapter);
        //下面的商品列表GradView
        ArrayList<GoodListBean> goodList = JsonUtil.getGoodList(getContext());
        mGoodsListAdapter = new GoodsListAdapter(getContext(), goodList);
        gridView2.setAdapter(mGoodsListAdapter);
    }
}

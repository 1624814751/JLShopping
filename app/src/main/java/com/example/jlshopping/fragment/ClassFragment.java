package com.example.jlshopping.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.jlshopping.R;
import com.example.jlshopping.adapter.GoodsClass2Adapter;
import com.example.jlshopping.adapter.GoodsClassAdapter;
import com.example.jlshopping.bean.GoodClassBean;
import com.example.jlshopping.util.JsonUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * 作者：@sy
 * 创建时间：2020/9/17 0017$ 10:40$
 * 描述：分类Fragment
 * 备注：
 */
public class ClassFragment extends Fragment {
    GridView gridView1;
    GoodsClass2Adapter mGoodsClassAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class, container, false);
        gridView1 = view.findViewById(R.id.gridView1);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //上面的商品分类GradView
        ArrayList<GoodClassBean> goodsClass = JsonUtil.getGoodsClass(getContext());
        mGoodsClassAdapter = new GoodsClass2Adapter(getContext(), goodsClass);
        gridView1.setAdapter(mGoodsClassAdapter);
    }
}

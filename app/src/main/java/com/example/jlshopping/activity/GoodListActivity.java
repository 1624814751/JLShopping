package com.example.jlshopping.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jlshopping.R;
import com.example.jlshopping.adapter.GoodsListAdapter;
import com.example.jlshopping.bean.GoodListBean;
import com.example.jlshopping.util.JsonUtil;

import java.util.ArrayList;

public class GoodListActivity extends AppCompatActivity {

    ImageView iv_back;
    TextView tv_title;
    GridView gridView;
    private GoodsListAdapter mGoodsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_list);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        gridView = findViewById(R.id.gridView2);

        //通过Intent传递过来的数据，显示页面的标题
        String title = getIntent().getStringExtra("title");
        tv_title.setText(title);
    }

    private void initData() {
        //下面的商品列表GradView
        ArrayList<GoodListBean> goodList = JsonUtil.getGoodList(this);
        mGoodsListAdapter = new GoodsListAdapter(this, goodList);
        gridView.setAdapter(mGoodsListAdapter);
    }

    private void initListener() {
        //退出页面
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
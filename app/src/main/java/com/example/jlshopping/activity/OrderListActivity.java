package com.example.jlshopping.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.jlshopping.R;
import com.example.jlshopping.adapter.OrderListAdapter;
import com.example.jlshopping.bean.OrderListBean;
import com.example.jlshopping.util.JsonUtil;

import java.util.ArrayList;

/**
 * 订单列表页面
 */
public class OrderListActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView1;
    OrderListAdapter orderListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased);

        initView();
        initData();
    }

    private void initView() {
        listView1 = findViewById(R.id.listView1);
        findViewById(R.id.imageView4).setOnClickListener(this);
    }

    private void initData() {
        ArrayList<OrderListBean> orderList = JsonUtil.getOrderList(this);
        orderListAdapter = new OrderListAdapter(this, orderList);
        listView1.setAdapter(orderListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView4://退出
                finish();
                break;
        }
    }
}
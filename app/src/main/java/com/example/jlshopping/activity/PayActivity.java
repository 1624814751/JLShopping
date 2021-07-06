package com.example.jlshopping.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jlshopping.R;
import com.example.jlshopping.bean.OrderListBean;
import com.example.jlshopping.util.JsonUtil;

import java.util.ArrayList;

/**
 * 支付页面
 */
public class PayActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_price;
    private ArrayList<String> good_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        initView();
    }

    private void initView() {
        String good_total_price = getIntent().getStringExtra("good_total_price");
        good_ids = getIntent().getStringArrayListExtra("good_ids");
        tv_price = findViewById(R.id.tv_price);
        tv_price.setText("￥ " + good_total_price);
        findViewById(R.id.iv_pay).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.iv_pay://支付
                Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
                editOrderListPayStutas();
                finish();
                break;
        }
    }

    /**
     * 修改订单信息列表中的支付后的产品的支付状态
     */
    private void editOrderListPayStutas() {
        ArrayList<OrderListBean> orderList = JsonUtil.getOrderList(this);//获取存储在本地的订单信息
        for (int i = 0; i < orderList.size(); i++) {//循环订单列表信息
            for (int j = 0; j < good_ids.size(); j++) {//循环支付过的商品id列表
                if (good_ids.get(j).equals(orderList.get(i).getGood_id())) {//判断已支付的商品在订单信息中的位置
                    orderList.get(i).setPay(true);//将已经支付的产品的支付状态改为已支付
                }
            }
        }
        JsonUtil.setOrderList(this, orderList);//将修改后的订单信息，重新存入sp中
    }
}
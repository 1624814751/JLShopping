package com.example.jlshopping.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jlshopping.AppConfigs;
import com.example.jlshopping.R;
import com.example.jlshopping.bean.GoodListBean;
import com.example.jlshopping.bean.OrderListBean;
import com.example.jlshopping.util.ImageUtils;
import com.example.jlshopping.util.JsonUtil;

import java.io.Serializable;
import java.util.ArrayList;

public class GoodDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView tpic;
    TextView price, detaila;
    private GoodListBean goodListBean;
    /**
     * 用来判断是从什么地方进来的
     * 如果是“goodList”：代表从产品列表进来的，可以显示加入购物车等按钮
     * 如果是“orderList”：代表从订单列表进来的，是查看详情的，则“加入购物车”、“立即支付”等按钮不显示
     */
    private String whereComming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //获取上个页面传递过来的商品信息(想要将一个Bean通过Intent传递到下一个页面，该Bean需要实现Serializable接口)
        goodListBean = (GoodListBean) getIntent().getSerializableExtra("goodListBean");
        whereComming = getIntent().getStringExtra("whereComming");
        initView();
        initData();
    }

    private void initView() {
        tpic = findViewById(R.id.tpic);
        price = findViewById(R.id.price);
        detaila = findViewById(R.id.detaila);
        LinearLayout ll_go_pay = findViewById(R.id.ll_go_pay);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);

        if ("goodList".equals(whereComming)) {//从产品列表进入的
            ll_go_pay.setVisibility(View.VISIBLE);
        } else {
            ll_go_pay.setVisibility(View.GONE);
        }
    }

    private void initData() {
        tpic.setImageResource(ImageUtils.getGoodIcon(goodListBean.getGood_icon()));
        price.setText("￥ " + goodListBean.getGood_price());
        detaila.setText(goodListBean.getGood_name());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2://加入购物车
                ArrayList<OrderListBean> orderList = JsonUtil.getOrderList(this);//获取存储在本地的订单信息
                int has = isHas(orderList);
                if (has >= 0) {//存在
                    int pay_number = orderList.get(has).getPay_number();//获取购买数量
                    orderList.get(has).setPay_number(++pay_number);//把购买数量加1
                } else {//不存在，则将该商品加入到订单列表中
                    OrderListBean orderListBean = new OrderListBean();
                    orderListBean.setGood_id(goodListBean.getGood_id());
                    orderListBean.setGood_name(goodListBean.getGood_name());
                    orderListBean.setGood_price(goodListBean.getGood_price());
                    orderListBean.setGood_icon(goodListBean.getGood_icon());
                    orderListBean.setPay(false);//将产品设置为未支付状态
                    orderListBean.setPay_number(1);
                    orderList.add(orderListBean);//将该产品信息添加到列表中
                }
                JsonUtil.setOrderList(this, orderList);//将产品添加到SharedPreferences中

                Toast.makeText(this, "已加入购物车", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3://支付
                Intent intent = new Intent(this, OrderActivity.class);
                //把商品的id、名称、价格、图片传递到下一个页面
                intent.putExtra("goodListBean", goodListBean);
                startActivity(intent);
                break;
            case R.id.iv_back://返回
                finish();
                break;
        }
    }

    /**
     * 用来判断当前的订单信息中是否有该订单
     *
     * @param orderList
     * @return 如果存在，返回该商品在订单列表中的下标值
     * 如果不存在，则返回-1
     */
    private int isHas(ArrayList<OrderListBean> orderList) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getGood_id().equals(goodListBean.getGood_id())) {//判断本地订单信息中是否有该订单
                return i;
            }
        }
        return -1;
    }
}
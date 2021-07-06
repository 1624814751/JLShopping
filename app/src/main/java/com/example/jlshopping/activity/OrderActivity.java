package com.example.jlshopping.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jlshopping.R;
import com.example.jlshopping.bean.GoodListBean;
import com.example.jlshopping.bean.OrderListBean;
import com.example.jlshopping.util.ImageUtils;
import com.example.jlshopping.util.JsonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单页面--结算页面
 */
public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView JSPic;
    EditText et_num, et_content;
    TextView JSprice, JSprice2, tv_title;
    private GoodListBean goodListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jie_suan);

        initView();
        initData();
    }

    private void initData() {
        //获取上个页面传递过来的商品信息
        goodListBean = (GoodListBean) getIntent().getSerializableExtra("goodListBean");

        JSPic.setImageResource(ImageUtils.getGoodIcon(goodListBean.getGood_icon()));
        JSprice.setText("￥ " + goodListBean.getGood_price());
        JSprice2.setText("￥ " + goodListBean.getGood_price());
        tv_title.setText(goodListBean.getGood_name());
    }

    private void initView() {
        JSPic = findViewById(R.id.JSPic);
        et_num = findViewById(R.id.et_num);
        et_content = findViewById(R.id.et_content);
        JSprice = findViewById(R.id.JSprice);
        JSprice2 = findViewById(R.id.JSprice2);
        tv_title = findViewById(R.id.tv_title);

        findViewById(R.id.btn_pay).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay://去支付
                //获取存储在本地的订单信息
                ArrayList<OrderListBean> orderList = JsonUtil.getOrderList(this);
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

                ArrayList<String> good_ids = new ArrayList<>();
                good_ids.add(goodListBean.getGood_id());
                Intent intent = new Intent(this, PayActivity.class);
                intent.putExtra("good_total_price", goodListBean.getGood_price());
                intent.putExtra("good_ids", good_ids);//将需要支付的商品的id，以列表的形式传递过去，方便修改订单列表中，该产品的支付状态
                startActivity(intent);
                finish();//进入支付页面，关闭订单页面
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
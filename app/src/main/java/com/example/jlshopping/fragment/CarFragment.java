package com.example.jlshopping.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jlshopping.AppConfigs;
import com.example.jlshopping.R;
import com.example.jlshopping.activity.PayActivity;
import com.example.jlshopping.adapter.CarAdapter;
import com.example.jlshopping.bean.OrderListBean;
import com.example.jlshopping.util.JsonUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 作者：@sy
 * 创建时间：2020/9/17 0017$ 10:40$
 * 描述：购物车Fragment
 * 备注：
 */
public class CarFragment extends Fragment implements View.OnClickListener, CarAdapter.OnCheckedListener {
    Button button8;
    ListView listView1;
    CarAdapter carAdapter;
    TextView textView8;
    TextView textView9;
    LinearLayout line1;
    ImageView imageView8;

    private boolean isAllSelect;//是否全选
    Float price = 0f;//合计的价格

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_car, container, false);
        button8 = view.findViewById(R.id.button8);
        listView1 = view.findViewById(R.id.listView1);
        textView9 = view.findViewById(R.id.textView9);
        textView8 = view.findViewById(R.id.textView8);
        imageView8 = view.findViewById(R.id.imageView8);
        line1 = view.findViewById(R.id.line1);
        button8.setOnClickListener(this);
        line1.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<OrderListBean> orderList = JsonUtil.getOrderList(getContext());
        //用于存放显示在购物车中的数据（订单列表中有两种状态：1、已支付  2、未支付）
        ArrayList<OrderListBean> orderListBeans = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            if (!orderList.get(i).isPay()) {//如果订单为“未支付”状态，则显示
                orderListBeans.add(orderList.get(i));
            }
        }
        carAdapter = new CarAdapter(getContext(), orderListBeans, this);
        carAdapter.initSelected(carAdapter.getCount());
        listView1.setAdapter(carAdapter);

        resetState();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button8://结算
                if (price > 0) {
                    ArrayList<String> good_ids = new ArrayList<>();
                    for (int i = 0; i < carAdapter.isSelected.size(); i++) {
                        if (carAdapter.isSelected.get(i)) {//判断当前的商品有没有被选择
                            good_ids.add(carAdapter.getItem(i).getGood_id());
                        }
                    }
                    Intent intent = new Intent(getContext(), PayActivity.class);
                    intent.putExtra("good_total_price", price + "");
                    intent.putExtra("good_ids", good_ids);//将需要支付的商品的id，以列表的形式传递过去，方便修改订单列表中，该产品的支付状态
                    startActivity(intent);
                }
                break;
            case R.id.line1://全选
                if (!isAllSelect) {//全选
                    isAllSelect = true;
                    imageView8.setImageResource(R.mipmap.rb_select);
                    selectAll();
                } else {//取消全选
                    isAllSelect = false;
                    imageView8.setImageResource(R.mipmap.rb_not_select);
                    cancelAll();

                }
                break;
        }
    }

    /**
     * 重置CheckBox的状态
     */
    private void resetState() {
        for (int i = 0; i < carAdapter.getCount(); i++) {
            carAdapter.isSelected.put(i, false);
            carAdapter.hasSelected.clear();
        }
        isAllSelect = false;
        price = 0f;
        textView8.setText("合计： ￥0.0");
        carAdapter.notifyDataSetChanged();
    }

    /**
     * 全选
     */
    public void selectAll() {
        for (int i = 0; i < carAdapter.getCount(); i++) {
            carAdapter.isSelected.put(i, true);
            if (!carAdapter.hasSelected.contains("select_" + i))
                carAdapter.hasSelected.add("select_" + i);
            Float good_price = Float.parseFloat(carAdapter.getItem(i).getGood_price());
            int pay_number = carAdapter.getItem(i).getPay_number();
            price += good_price * pay_number;
        }
        Log.i("check_car", "isSelect == " + carAdapter.getItem(0).toString());
        carAdapter.notifyDataSetChanged();
        textView8.setText("合计： ￥" + price);
    }

    /**
     * 取消全选
     */
    public void cancelAll() {
        for (int i = 0; i < carAdapter.getCount(); i++) {
            carAdapter.isSelected.put(i, false);
            carAdapter.hasSelected.clear();
        }
        carAdapter.notifyDataSetChanged();

        price = 0f;
        textView8.setText("合计： ￥0.0");
    }

    /**
     * 点击列表中的CheckBox时的回调
     */
    @Override
    public void checkedListener() {
        price = 0f;
        if (carAdapter.hasSelected.size() == 0) {
            //没有被选中的商品
            isAllSelect = false;
            imageView8.setImageResource(R.mipmap.rb_not_select);//将全选按钮取消
            textView8.setText("合计： ￥0.0");
        } else if (carAdapter.hasSelected.size() == carAdapter.getCount()) {
            //如果选中的商品数==总列表数,表示全选
            for (int i = 0; i < carAdapter.getCount(); i++) {
                Float good_price = Float.parseFloat(carAdapter.getItem(i).getGood_price());
                int pay_number = carAdapter.getItem(i).getPay_number();
                price += good_price * pay_number;
            }
            isAllSelect = true;
            imageView8.setImageResource(R.mipmap.rb_select);//设置全选
            textView8.setText("合计： ￥" + price);
        } else {
            for (int i = 0; i < carAdapter.getCount(); i++) {
                if (carAdapter.isSelected.get(i)) {//只有被选中的商品，计算价格
                    Float good_price = Float.parseFloat(carAdapter.getItem(i).getGood_price());
                    int pay_number = carAdapter.getItem(i).getPay_number();
                    price += good_price * pay_number;
                }
            }
            isAllSelect = false;
            imageView8.setImageResource(R.mipmap.rb_not_select);//将全选按钮取消
            textView8.setText("合计： ￥" + price);
        }
    }
}

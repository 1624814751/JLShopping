package com.example.jlshopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jlshopping.R;
import com.example.jlshopping.activity.GoodDetailActivity;
import com.example.jlshopping.bean.GoodListBean;
import com.example.jlshopping.bean.OrderListBean;
import com.example.jlshopping.util.ImageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：@sy
 * 创建时间：2020/9/17 0017$ 15:01$
 * 描述：购物车的adapter
 * 备注：
 */
public class CarAdapter extends BaseAdapter {
    /**
     * 用来保存选中状态和对应的位置，用于解决item的复用问题
     */
    public static Map<Integer, Boolean> isSelected;
    /**
     * 用来保存之前选中状态的位置，用于加载更多数据时恢复已选中的位置
     */
    public static List<String> hasSelected = new ArrayList<>();
    ArrayList<OrderListBean> orderListBeans;
    Context context;
    OnCheckedListener onCheckedListener;

    public CarAdapter(Context context, ArrayList<OrderListBean> orderListBeans, OnCheckedListener onCheckedListener) {
        this.context = context;
        this.orderListBeans = orderListBeans;
        this.onCheckedListener = onCheckedListener;
    }

    @Override
    public int getCount() {
        return orderListBeans == null ? 0 : orderListBeans.size();
    }

    @Override
    public OrderListBean getItem(int position) {
        return orderListBeans != null && orderListBeans.size() > 0 ? orderListBeans.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * @param position    指现在是第几个条目
     * @param convertView 旧视图，就是绘制好了的视图
     * @param parent      父级视图，也就是ListView之类的
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //LayoutInflater是用来加载布局的，用LayoutInflater的inflate方法就可以将item布局绘制出来。
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.car_1, null);
            holder = new ViewHolder();
            holder.checkBox3 = convertView.findViewById(R.id.checkBox3);
            holder.picCar = convertView.findViewById(R.id.picCar);
            holder.detailC = convertView.findViewById(R.id.detailC);
            holder.priceC = convertView.findViewById(R.id.priceC);
            holder.numberC = convertView.findViewById(R.id.numberC);
            holder.ll_go_pay = convertView.findViewById(R.id.ll_go_pay);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.picCar.setImageResource(ImageUtils.getGoodIcon(orderListBeans.get(position).getGood_icon()));
        holder.detailC.setText(orderListBeans.get(position).getGood_name());
        holder.numberC.setText("× " + orderListBeans.get(position).getPay_number());
        holder.priceC.setText("￥" + orderListBeans.get(position).getGood_price());
        holder.checkBox3.setChecked(isSelected.get(position));

        holder.ll_go_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到详情页
                Intent intent = new Intent(context, GoodDetailActivity.class);
                //(想要将一个Bean通过Intent传递到下一个页面，该Bean需要实现Serializable接口)
                GoodListBean goodListBean = new GoodListBean();
                goodListBean.setGood_id(orderListBeans.get(position).getGood_id());
                goodListBean.setGood_icon(orderListBeans.get(position).getGood_icon());
                goodListBean.setGood_name(orderListBeans.get(position).getGood_name());
                goodListBean.setGood_price(orderListBeans.get(position).getGood_price());
                intent.putExtra("goodListBean", goodListBean);
                intent.putExtra("whereComming", "orderList");
                context.startActivity(intent);
            }
        });
        holder.checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //同时将CheckBox的状态保存到HashMap中，其中key为点击的位置，value为状态
                isSelected.put(position, isChecked);

                //判断是否已经存在，如果已经，则移除，否则添加
                if (!isChecked) {
                    hasSelected.remove("select_" + position);
                } else {
                    if (!hasSelected.contains("select_" + position))
                        hasSelected.add("select_" + position);
                }

                onCheckedListener.checkedListener();
            }
        });
        //用inflate方法绘制好后的view最后return返回给getView方法就可以了。
        return convertView;
    }

    /**
     * 初始选中状态
     *
     * @param size
     */
    public void initSelected(int size) {
        //判断isSelected是否已经存在
        if (isSelected == null) {
            isSelected = new HashMap<>();
            for (int i = 0; i < size; i++) {
                isSelected.put(i, false);
            }
        } else {//此部分适用于具有上拉加载功能的ListView
            for (int i = 0; i < size; i++) {
                isSelected.put(i, false);
                //遍历加载之前所保存的选中的位置
                for (int j = 0; j < hasSelected.size(); j++) {
                    if (hasSelected.get(j).equals("select_" + i)) {
                        isSelected.put(i, true);
                    }
                }
            }
        }
    }

    static class ViewHolder {
        CheckBox checkBox3;
        ImageView picCar;
        TextView detailC, priceC, numberC;
        LinearLayout ll_go_pay;
    }

    /**
     * 当点击checkBox时，会相应这个接口中的方法
     */
    public interface OnCheckedListener {
        void checkedListener();
    }
}

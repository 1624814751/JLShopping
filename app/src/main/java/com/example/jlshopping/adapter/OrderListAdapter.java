package com.example.jlshopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jlshopping.R;
import com.example.jlshopping.activity.GoodDetailActivity;
import com.example.jlshopping.bean.GoodListBean;
import com.example.jlshopping.bean.OrderListBean;
import com.example.jlshopping.util.ImageUtils;
import com.example.jlshopping.util.JsonUtil;

import java.util.ArrayList;

/**
 * 作者：@sy
 * 创建时间：2020/9/17 0017$ 15:01$
 * 描述：订单列表的adapter
 * 备注：
 */
public class OrderListAdapter extends BaseAdapter {
    ArrayList<OrderListBean> orderListBeans;
    Context context;

    public OrderListAdapter(Context context, ArrayList<OrderListBean> orderListBeans) {
        this.context = context;
        this.orderListBeans = orderListBeans;
    }

    @Override
    public int getCount() {
        return orderListBeans == null ? 0 : orderListBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            convertView = inflater.inflate(R.layout.adapter_order_list, null);
            holder = new ViewHolder();
            holder.iv = convertView.findViewById(R.id.iv);
            holder.status = convertView.findViewById(R.id.status);
            holder.tv_good_name = convertView.findViewById(R.id.tv_good_name);
            holder.tv_price = convertView.findViewById(R.id.tv_price);
            holder.tv_num = convertView.findViewById(R.id.tv_num);
            holder.tv_total_price = convertView.findViewById(R.id.tv_total_price);
            holder.btn_delete = convertView.findViewById(R.id.btn_delete);
            holder.btn_detail = convertView.findViewById(R.id.btn_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv.setImageResource(ImageUtils.getGoodIcon(orderListBeans.get(position).getGood_icon()));
        holder.tv_good_name.setText(orderListBeans.get(position).getGood_name());
        int pay_number = orderListBeans.get(position).getPay_number();//购买的数量
        String good_price = orderListBeans.get(position).getGood_price();//购买的单价
        holder.tv_num.setText("× " + pay_number);
        holder.tv_price.setText("￥" + good_price);
        Float v = Float.parseFloat(good_price);//将单价从String类型，转化成float类型
        holder.tv_total_price.setText("￥" + (v * pay_number));
        if (orderListBeans.get(position).isPay()) {//判断该订单是否已经支付过
            holder.status.setText("支付成功");
        } else {
            holder.status.setText("待支付");
        }

        //删除订单
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListBeans.remove(position);//将该订单从列表中删除
                JsonUtil.setOrderList(context, orderListBeans);//并修改SP中存入的订单信息
                notifyDataSetChanged();//刷新订单
            }
        });
        //查看订单
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        //用inflate方法绘制好后的view最后return返回给getView方法就可以了。
        return convertView;
    }

    static class ViewHolder {
        ImageView iv;
        TextView status, tv_good_name, tv_price, tv_num, tv_total_price;
        Button btn_delete, btn_detail;
    }
}

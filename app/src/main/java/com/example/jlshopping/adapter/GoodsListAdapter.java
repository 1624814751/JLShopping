package com.example.jlshopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jlshopping.R;
import com.example.jlshopping.activity.GoodDetailActivity;
import com.example.jlshopping.activity.GoodListActivity;
import com.example.jlshopping.bean.GoodClassBean;
import com.example.jlshopping.bean.GoodListBean;
import com.example.jlshopping.util.ImageUtils;

import java.util.ArrayList;

/**
 * 作者：@sy
 * 创建时间：2020/9/17 0017$ 15:01$
 * 描述：商品分类的adapter
 * 备注：
 */
public class GoodsListAdapter extends BaseAdapter {
    ArrayList<GoodListBean> goodListBeans;
    Context context;

    public GoodsListAdapter(Context context, ArrayList<GoodListBean> goodListBeans) {
        this.context = context;
        this.goodListBeans = goodListBeans;
    }

    @Override
    public int getCount() {
        return goodListBeans == null ? 0 : goodListBeans.size();
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
            convertView = inflater.inflate(R.layout.first_a2, null);
            holder = new ViewHolder();
            holder.rl_item = convertView.findViewById(R.id.rl_item);
            holder.textView = convertView.findViewById(R.id.content2);
            holder.price = convertView.findViewById(R.id.price);
            holder.imageView = convertView.findViewById(R.id.pic2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setImageResource(ImageUtils.getGoodIcon(goodListBeans.get(position).getGood_icon()));
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodDetailActivity.class);
                //(想要将一个Bean通过Intent传递到下一个页面，该Bean需要实现Serializable接口)
                intent.putExtra("goodListBean", goodListBeans.get(position));
                intent.putExtra("whereComming","goodList");
                context.startActivity(intent);
            }
        });
        holder.textView.setText(goodListBeans.get(position).getGood_name());
        holder.price.setText("￥" + goodListBeans.get(position).getGood_price());

        //用inflate方法绘制好后的view最后return返回给getView方法就可以了。
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView, price;
        RelativeLayout rl_item;
    }
}

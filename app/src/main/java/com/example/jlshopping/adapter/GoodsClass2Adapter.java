package com.example.jlshopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jlshopping.R;
import com.example.jlshopping.activity.GoodListActivity;
import com.example.jlshopping.bean.GoodClassBean;
import com.example.jlshopping.util.ImageUtils;

import java.util.ArrayList;

/**
 * 作者：@sy
 * 创建时间：2020/9/17 0017$ 15:01$
 * 描述：商品分类的adapter
 * 备注：
 */
public class GoodsClass2Adapter extends BaseAdapter {
    ArrayList<GoodClassBean> goodClassBeans;
    Context context;

    public GoodsClass2Adapter(Context context, ArrayList<GoodClassBean> goodClassBeans) {
        this.context = context;
        this.goodClassBeans = goodClassBeans;
    }

    @Override
    public int getCount() {
        return goodClassBeans == null ? 0 : goodClassBeans.size();
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
            convertView = inflater.inflate(R.layout.first_a3, null);
            holder = new ViewHolder();
            holder.rl_item = convertView.findViewById(R.id.rl_item);
            holder.textView = convertView.findViewById(R.id.content);
            holder.imageView = convertView.findViewById(R.id.pic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setImageResource(ImageUtils.getGoodsClassIcon(goodClassBeans.get(position).getGoods_class_icon()));
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 6) {//点击的是评价好物，需要做跳转
                    Intent intent = new Intent(context, GoodListActivity.class);
                    intent.putExtra("title", goodClassBeans.get(position).getGoods_class_name());
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, goodClassBeans.get(position).getGoods_class_name(), Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.textView.setText(goodClassBeans.get(position).getGoods_class_name());

        //用inflate方法绘制好后的view最后return返回给getView方法就可以了。
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout rl_item;
    }
}

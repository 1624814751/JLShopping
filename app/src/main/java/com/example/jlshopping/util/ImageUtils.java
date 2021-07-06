package com.example.jlshopping.util;

import com.example.jlshopping.R;

import java.util.ArrayList;

/**
 * 作者：@sy
 * 创建时间：2020/9/18 0018$ 15:39$
 * 描述：
 * 备注：
 */
public class ImageUtils {
    /**
     * 获取产品分类的图标
     *
     * @param position
     * @return
     */
    public static int getGoodsClassIcon(int position) {
        ArrayList<Integer> img = new ArrayList<>();
        img.add(R.drawable.tb1_1);
        img.add(R.drawable.tb1_2);
        img.add(R.drawable.tb1_3);
        img.add(R.drawable.tb1_4);
        img.add(R.drawable.tb1_5);
        img.add(R.drawable.tb1_6);
        img.add(R.drawable.tb1_7);
        img.add(R.drawable.tb1_8);
        img.add(R.drawable.tb1_9);
        img.add(R.drawable.tb1_10);
        return img.get(position);
    }

    /**
     * 获取产品的图标
     *
     * @param position
     * @return
     */
    public static int getGoodIcon(int position) {
        ArrayList<Integer> img = new ArrayList<>();
        img.add(R.drawable.tb2_1);
        img.add(R.drawable.tb2_2);
        img.add(R.drawable.tb2_3);
        img.add(R.drawable.tb2_4);
        img.add(R.drawable.tb2_5);
        img.add(R.drawable.tb2_6);
        img.add(R.drawable.tb2_7);
        img.add(R.drawable.tb2_8);
        return img.get(position);
    }
}

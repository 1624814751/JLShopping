package com.example.jlshopping.bean;

import java.io.Serializable;

/**
 * 作者：@sy
 * 创建时间：2020/9/17 0017$ 16:09$
 * 描述：产品列表的数据结构
 * 备注：
 */
public class GoodListBean implements Serializable {
    private String good_id;//产品的id
    private String good_name;//产品的名称
    private int good_icon;//产品的图片
    private String good_price;//产品的价格

    public String getGood_id() {
        return good_id;
    }

    public void setGood_id(String good_id) {
        this.good_id = good_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public int getGood_icon() {
        return good_icon;
    }

    public void setGood_icon(int good_icon) {
        this.good_icon = good_icon;
    }

    public String getGood_price() {
        return good_price;
    }

    public void setGood_price(String good_price) {
        this.good_price = good_price;
    }
}

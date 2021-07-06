package com.example.jlshopping.bean;

/**
 * 作者：@sy
 * 创建时间：2020/9/17 0017$ 11:18$
 * 描述：产品分类的数据结构
 * 备注：
 */
public class GoodClassBean {
    private String goods_class_id;//产品分类的id
    private String goods_class_name;//产品分类的名称
    private int goods_class_icon;//产品分类的图片

    public String getGoods_class_id() {
        return goods_class_id;
    }

    public void setGoods_class_id(String goods_class_id) {
        this.goods_class_id = goods_class_id;
    }

    public String getGoods_class_name() {
        return goods_class_name;
    }

    public void setGoods_class_name(String goods_class_name) {
        this.goods_class_name = goods_class_name;
    }

    public int getGoods_class_icon() {
        return goods_class_icon;
    }

    public void setGoods_class_icon(int goods_class_icon) {
        this.goods_class_icon = goods_class_icon;
    }

    @Override
    public String toString() {
        return "GoodClassBean{" +
                "goods_class_id='" + goods_class_id + '\'' +
                ", goods_class_name='" + goods_class_name + '\'' +
                ", goods_class_icon='" + goods_class_icon + '\'' +
                '}';
    }
}

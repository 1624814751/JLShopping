package com.example.jlshopping.bean;

/**
 * 订单列表的数据结构
 */
public class OrderListBean {
    private String good_id;
    private String good_name;
    private String good_price;
    private int good_icon;
    private boolean pay;//用于判断该订单是否已经购买
    private int pay_number;//下单的数量，默认为1

    public int getPay_number() {
        return pay_number;
    }

    public void setPay_number(int pay_number) {
        this.pay_number = pay_number;
    }

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

    public String getGood_price() {
        return good_price;
    }

    public void setGood_price(String good_price) {
        this.good_price = good_price;
    }

    public int getGood_icon() {
        return good_icon;
    }

    public void setGood_icon(int good_icon) {
        this.good_icon = good_icon;
    }

    public boolean isPay() {
        return pay;
    }

    public void setPay(boolean pay) {
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "OrderListBean{" +
                "good_id='" + good_id + '\'' +
                ", good_name='" + good_name + '\'' +
                ", good_price='" + good_price + '\'' +
                ", good_icon=" + good_icon +
                ", pay=" + pay +
                ", pay_number=" + pay_number +
                '}';
    }
}

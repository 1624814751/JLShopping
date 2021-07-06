package com.example.jlshopping.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.jlshopping.AppConfigs;
import com.example.jlshopping.R;
import com.example.jlshopping.bean.GoodClassBean;
import com.example.jlshopping.bean.GoodListBean;
import com.example.jlshopping.bean.OrderListBean;
import com.example.jlshopping.bean.UserBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class JsonUtil {

    /**
     * 获取注册的用户信息列表
     *
     * @return
     */
    public static ArrayList<UserBean> getUserInfo(String userInfo) {
        Log.i("register_user_info", "json == " + userInfo);
        ArrayList<UserBean> list = new ArrayList<>();
        if (!"".equals(userInfo)) {
            try {
                JSONArray jsonArray = new JSONArray(userInfo);
                for (int i = 0; i < jsonArray.length(); i++) {
                    UserBean userBean = new UserBean();
                    JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                    String school_name = jsonObj.getString("school_name");
                    String user_number = jsonObj.getString("user_number");
                    String user_pass = jsonObj.getString("user_pass");
                    userBean.setSchool_name(school_name);
                    userBean.setUser_number(user_number);
                    userBean.setUser_pass(user_pass);
                    list.add(userBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.i("register_user_info", "获取userInfo == " + list.toString());
        return list;
    }

    /**
     * 该方法是，将存放用户信息的List转换成Json串，存放在SharedPreferences里面
     *
     * @param userBeans ArrayList<UserBean>中存放的是所有的注册的用户信息
     * @return 返回的是转化成的json串
     */
    public static String setUserJson(ArrayList<UserBean> userBeans) {
        JSONArray jsonArray = new JSONArray();
        try {
            int count = userBeans.size();
            for (int i = 0; i < count; i++) {
                JSONObject tmpObj = new JSONObject();
                tmpObj.put("school_name", userBeans.get(i).getSchool_name());
                tmpObj.put("user_number", userBeans.get(i).getUser_number());
                tmpObj.put("user_pass", userBeans.get(i).getUser_pass());
                jsonArray.put(tmpObj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String userInfo = jsonArray.toString(); //将JSONArray转换得到String
        Log.i("register_user_info", "存放userInfo == " + userInfo);
        return userInfo;
    }

    /**
     * 从raw中的json_goods_class文件中获取商品分类的json串，并转化成ArrayList
     *
     * @return 返回的是产品信息列表
     */
    public static ArrayList<GoodClassBean> getGoodsClass(Context context) {
        ArrayList<GoodClassBean> list = new ArrayList<>();
        String json_goods = "";//用于存储产品的json串

        //1、从raw中获取产品的json串
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.json_goods_class);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            json_goods = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("get_goods_json", "获取商品json == " + json_goods);

        //2、将获取的json串转化为List
        if (!"".equals(json_goods)) {//判断获取的json串是否为空
            try {
                JSONArray jsonArray = new JSONArray(json_goods);
                for (int i = 0; i < jsonArray.length(); i++) {
                    GoodClassBean goodClassBean = new GoodClassBean();
                    JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                    goodClassBean.setGoods_class_name(jsonObj.getString("goods_class_name"));//产品分类的名称
                    goodClassBean.setGoods_class_icon(jsonObj.getInt("goods_class_icon"));//产品分类的图片
                    list.add(goodClassBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.i("get_goods_json", "获取商品list == " + list.toString());
        return list;
    }

    /**
     * 从raw中的json_goods_list文件中获取商品的json串，并转化成ArrayList
     *
     * @return 返回的是产品信息列表
     */
    public static ArrayList<GoodListBean> getGoodList(Context context) {
        ArrayList<GoodListBean> list = new ArrayList<>();
        String json_goods = "";//用于存储产品的json串

        //1、从raw中获取产品的json串
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.json_goods_list);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            json_goods = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("get_goods_json", "获取商品json == " + json_goods);

        //2、将获取的json串转化为List
        if (!"".equals(json_goods)) {//判断获取的json串是否为空
            try {
                JSONArray jsonArray = new JSONArray(json_goods);
                for (int i = 0; i < jsonArray.length(); i++) {
                    GoodListBean goodListBean = new GoodListBean();
                    JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                    goodListBean.setGood_id(jsonObj.getString("good_id"));//产品的id
                    goodListBean.setGood_name(jsonObj.getString("good_name"));//产品的名称
                    goodListBean.setGood_icon(jsonObj.getInt("good_icon"));//产品的图片
                    goodListBean.setGood_price(jsonObj.getString("good_price"));//产品的价格
                    list.add(goodListBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.i("get_goods_json", "获取商品list == " + list.toString());
        return list;
    }

    /**
     * 获取用户订单列表
     *
     * @return
     */
    public static ArrayList<OrderListBean> getOrderList(Context context) {
        //获取存在本地的登录账号
        String login_number = context.getSharedPreferences(AppConfigs.SP_NAME, MODE_PRIVATE).getString(AppConfigs.SP_LOGIN_NUMBER, "");
        //将当前用户的账号拼接在"user_order_list_"后面，创建一个新的SharedPreferences文件，用于存储当前的用户的订单列表
        SharedPreferences pref = context.getSharedPreferences(AppConfigs.SP_USER_ORDER_LIST + login_number, MODE_PRIVATE);
        String orderInfo = pref.getString(AppConfigs.SP_USER_ORDER_INFO, "");

        Log.i("order_list_info", "json == " + orderInfo);
        ArrayList<OrderListBean> list = new ArrayList<>();
        if (!"".equals(orderInfo)) {
            try {
                JSONArray jsonArray = new JSONArray(orderInfo);
                for (int i = 0; i < jsonArray.length(); i++) {
                    OrderListBean orderListBean = new OrderListBean();
                    JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                    String good_id = jsonObj.getString("good_id");
                    String good_name = jsonObj.getString("good_name");
                    String good_price = jsonObj.getString("good_price");
                    int good_icon = jsonObj.getInt("good_icon");
                    Boolean pay = jsonObj.getBoolean("pay");
                    int pay_number = jsonObj.getInt("pay_number");
                    orderListBean.setGood_icon(good_icon);
                    orderListBean.setGood_name(good_name);
                    orderListBean.setGood_id(good_id);
                    orderListBean.setGood_price(good_price);
                    orderListBean.setPay(pay);
                    orderListBean.setPay_number(pay_number);
                    list.add(orderListBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.i("order_list_info", "获取orderInfo == " + list.toString());
        return list;
    }

    /**
     * 该方法是，将存放用户订单的List转换成Json串，存放在SharedPreferences里面
     *
     * @param orderListBeans ArrayList<OrderListBean>中存放的是当前登录的用户的订单列表
     * @return 返回的是转化成的json串
     */
    public static void setOrderList(Context context, ArrayList<OrderListBean> orderListBeans) {
        //获取存在本地的登录账号
        String login_number = context.getSharedPreferences(AppConfigs.SP_NAME, MODE_PRIVATE).getString(AppConfigs.SP_LOGIN_NUMBER, "");
        //将当前用户的账号拼接在"user_order_list_"后面，创建一个新的SharedPreferences文件，用于存储当前的用户的订单列表
        SharedPreferences pref = context.getSharedPreferences(AppConfigs.SP_USER_ORDER_LIST + login_number, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        JSONArray jsonArray = new JSONArray();
        try {
            int count = orderListBeans.size();
            for (int i = 0; i < count; i++) {
                JSONObject tmpObj = new JSONObject();
                tmpObj.put("good_id", orderListBeans.get(i).getGood_id());
                tmpObj.put("good_name", orderListBeans.get(i).getGood_name());
                tmpObj.put("good_price", orderListBeans.get(i).getGood_price());
                tmpObj.put("good_icon", orderListBeans.get(i).getGood_icon());
                tmpObj.put("pay", orderListBeans.get(i).isPay());
                tmpObj.put("pay_number", orderListBeans.get(i).getPay_number());
                jsonArray.put(tmpObj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String orderInfo = jsonArray.toString(); //将JSONArray转换得到String
        Log.i("order_list_info", "存放orderInfo == " + orderInfo);
        editor.putString(AppConfigs.SP_USER_ORDER_INFO, orderInfo);
        editor.commit();
    }
}

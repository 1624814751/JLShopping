package com.example.jlshopping;

/**
 * 作者：@sy
 * 创建时间：2020/9/16 0016$ 16:46$
 * 描述：用于记录全局的字段
 * 备注：
 */
public class AppConfigs {
    public static String SP_NAME = "user_info";  //用于存储用户信息的文件名
    public static String SP_LOGIN_SCHOOL = "school_name";//学校名称
    public static String SP_LOGIN_NUMBER = "user_number";//登录帐号
    public static String SP_LOGIN_PWD = "user_pwd";//登录密码

    public static String SP_REGISTER_USER_INFOS = "register_user_infos";//存放的注册的用户信息（json串，存放的是将ArrayList<UserBean>转化成的json）

    /**
     * 用于存储不同用户的订单列表
     * 用法：使用時，將用戶账号拼接在该字段后面，例如“user_order_list_123456”
     * 用处：该字段是SharedPreferences存储的一个文件名，该文件中存储的是用户的订单列表转换的json字符串
     */
    public static String SP_USER_ORDER_LIST = "user_order_list_";
    public static String SP_USER_ORDER_INFO = "user_order_infos";//存放的是当前用户的订单信息（json串，存放的是将ArrayList<OrderListBean>转化成的json）
}

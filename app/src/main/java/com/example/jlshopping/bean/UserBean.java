package com.example.jlshopping.bean;

/**
 * 用来记录注册的用户信息的
 */
public class UserBean {
    private String school_name;//学校名称
    private String user_number;//帐号
    private String user_pass;//密码

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getUser_number() {
        return user_number;
    }

    public void setUser_number(String user_number) {
        this.user_number = user_number;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "school_name='" + school_name + '\'' +
                ", user_number='" + user_number + '\'' +
                ", user_pass='" + user_pass + '\'' +
                '}';
    }
}

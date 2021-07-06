package com.example.jlshopping.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jlshopping.AppConfigs;
import com.example.jlshopping.R;
import com.example.jlshopping.bean.UserBean;
import com.example.jlshopping.util.JsonUtil;

import java.util.ArrayList;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText SchoolName, SchoolNumber, login_password;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /**
         * 参数一：存储数据的文件名
         * 参数二：存储的Mode
         */
        pref = getSharedPreferences(AppConfigs.SP_NAME, MODE_PRIVATE);
        //创建Editor的对象（相当于笔，将数据写到共享文件中）
        editor = pref.edit();

        initView();
    }

    private void initView() {
        SchoolName = findViewById(R.id.SchoolName);
        SchoolNumber = findViewById(R.id.SchoolNumber);
        login_password = findViewById(R.id.login_password);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.RstNumber).setOnClickListener(this);

        String dd_school = pref.getString(AppConfigs.SP_LOGIN_SCHOOL, "");
        String dd_name = pref.getString(AppConfigs.SP_LOGIN_NUMBER, "");
        String dd_pwd = pref.getString(AppConfigs.SP_LOGIN_PWD, "");
        SchoolName.setText(dd_school);
        SchoolNumber.setText(dd_name);
        login_password.setText(dd_pwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button://登录
                String name = SchoolNumber.getText().toString().trim();
                String pwds = login_password.getText().toString().trim();

                if ("".equals(name)) {//判断帐号是否为空
                    Toast.makeText(this, "帐号不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(pwds)) {//判断密码是否为空
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                //获取存在本地的注册的用户信息
                String string = pref.getString(AppConfigs.SP_REGISTER_USER_INFOS, "");
                //将用户信息的json转化成ArrayList
                ArrayList<UserBean> userInfo = JsonUtil.getUserInfo(string);
                //判断本地是否有注册信息
                if (userInfo != null && userInfo.size() > 0) {
                    int position = getUser(userInfo, name);//判断本地数据中是否包含该用户信息
                    if (position >= 0) {//包含,position为该用户信息在ArrayList<UserBean>中的下标位置
                        //判断输入的账号密码是否正确
                        if (name.equals(userInfo.get(position).getUser_number()) && pwds.equals(userInfo.get(position).getUser_pass())) {
                            Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
                            //将最近登录的账号密码存入本地，用于再次进入app时登录页面直接显示登录的信息
                            editor.putString(AppConfigs.SP_LOGIN_SCHOOL, userInfo.get(position).getSchool_name());
                            editor.putString(AppConfigs.SP_LOGIN_NUMBER, name);
                            editor.putString(AppConfigs.SP_LOGIN_PWD, pwds);
                            editor.commit();
                            startActivity(new Intent(this, FirstPageActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "登录失败", Toast.LENGTH_LONG).show();
                            Toast.makeText(this, "请重新输入", Toast.LENGTH_LONG).show();
                        }
                    } else {//不包含
                        Toast.makeText(this, "该账号不存在", Toast.LENGTH_LONG).show();
                    }
                } else {//本地数据中暂时没有用户信息
                    Toast.makeText(this, "该账号不存在", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.RstNumber://注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            default:
        }
    }

    /**
     * 判断已经注册过的用户信息中，是否含有该用户
     *
     * @param userInfo
     * @param number
     * @return 包含，则返回相应的下标 position      不包含，则返回-1
     */
    private int getUser(ArrayList<UserBean> userInfo, String number) {
        for (int i = 0; i < userInfo.size(); i++) {
            //判断用户名是否已经被注册
            if (number.equals(userInfo.get(i).getUser_number())) {
                return i;
            }
        }
        return -1;
    }
}

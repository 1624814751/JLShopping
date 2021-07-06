package com.example.jlshopping.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jlshopping.AppConfigs;
import com.example.jlshopping.R;
import com.example.jlshopping.bean.UserBean;
import com.example.jlshopping.util.JsonUtil;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText SchoolName, SchoolNumber, login_password, login_password2;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        login_password2 = findViewById(R.id.login_password2);

        findViewById(R.id.button1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1://注册
                String number = SchoolNumber.getText().toString().trim();
                String pass = login_password.getText().toString().trim();
                String pass_re = login_password2.getText().toString().trim();
                if ("".equals(number)) {//判断帐号是否为空
                    Toast.makeText(this, "帐号不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(pass)) {//判断密码是否为空
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(pass_re)) {//判断确认密码是否为空
                    Toast.makeText(this, "请再次输入密码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!pass.equals(pass_re)) {//判断两次密码输入的是否相同
                    Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
                    return;
                }
                //获取存在本地的注册的用户信息
                String string = pref.getString(AppConfigs.SP_REGISTER_USER_INFOS, "");
                //将用户信息的json转化成ArrayList
                ArrayList<UserBean> userInfo = JsonUtil.getUserInfo(string);
                if (userInfo != null && userInfo.size() > 0) {//判断本地是否有用户信息
                    for (int i = 0; i < userInfo.size(); i++) {
                        if (number.equals(userInfo.get(i).getUser_number())) {//判断本地是否已经注册过该账号
                            Toast.makeText(this, "账号已存在", Toast.LENGTH_LONG).show();
                            return;
                        } else {//本地没有注册过该账号
                            setUserInfo(number, pass, userInfo);
                        }
                    }
                } else {//没有用户信息
                    setUserInfo(number, pass, userInfo);
                }
                break;
        }
    }

    /**
     * 将新注册的用户信息存入本地
     *
     * @param number
     * @param pass
     * @param userInfo
     */
    private void setUserInfo(String number, String pass, ArrayList<UserBean> userInfo) {
        //将注册的用户信息放入UserBean中
        UserBean userBean = new UserBean();
        userBean.setSchool_name(SchoolName.getText().toString().trim());
        userBean.setUser_number(number);
        userBean.setUser_pass(pass);

        //将新注册的用户信息添加到ArrayList中去
        userInfo.add(userBean);
        //将ArrayList转化成json串
        String s = JsonUtil.setUserJson(userInfo);
        //将含有用户信息的json串存到本地
        editor.putString(AppConfigs.SP_REGISTER_USER_INFOS, s);
        //提交
        editor.commit();

        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        finish();
    }
}
package com.example.jlshopping.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.jlshopping.R;
import com.example.jlshopping.fragment.CarFragment;
import com.example.jlshopping.fragment.ClassFragment;
import com.example.jlshopping.fragment.HomeFragment;
import com.example.jlshopping.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class FirstPageActivity extends AppCompatActivity {

    ViewPager vp_tab;
    RadioGroup rg_tab;
    MainAdapter mMainAdapter;
    ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        rg_tab = findViewById(R.id.rg_tab);
        vp_tab = findViewById(R.id.vp_tab);
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ClassFragment());
        fragments.add(new CarFragment());
        fragments.add(new MineFragment());

        mMainAdapter = new MainAdapter(getSupportFragmentManager(), fragments);
        vp_tab.setAdapter(mMainAdapter);
        vp_tab.setCurrentItem(0);
        //设置默认页
        rg_tab.check(R.id.rb_homepage);
    }

    private void initListener() {
        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_homepage:
                        vp_tab.setCurrentItem(0, false);
                        break;
                    case R.id.rb_category:
                        vp_tab.setCurrentItem(1, false);
                        break;
                    case R.id.rb_shop_cart:
                        vp_tab.setCurrentItem(2, false);
                        break;
                    case R.id.rb_user:
                        vp_tab.setCurrentItem(3, false);
                        break;
                }
            }
        });
    }

    /**
     * 用于将Fragment和ViewPager做关联
     */
    public class MainAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;

        public MainAdapter(@NonNull FragmentManager fm, List<Fragment> mFragments) {
            super(fm);
            this.mFragments = mFragments;
        }

        @Override
        public Fragment getItem(int position) {//必须实现
            return mFragments.get(position);
        }

        @Override
        public int getCount() {//必须实现
            return mFragments.size();
        }
    }
}
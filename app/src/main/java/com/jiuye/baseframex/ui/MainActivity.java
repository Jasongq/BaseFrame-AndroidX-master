package com.jiuye.baseframex.ui;
import android.view.View;
import android.widget.RadioGroup;


import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.jiuye.baseframex.R;
import com.jiuye.baseframex.base.BaseActivity;
import com.jiuye.baseframex.ui.adapter.MyPagerAdapter;
import com.jiuye.baseframex.ui.fragment.MineFragment;
import com.jiuye.baseframex.ui.fragment.SimpleFragment;
import com.jiuye.baseframex.util.CommonTools;
import com.jiuye.baseframex.util.MessageEvent;
import com.jiuye.baseframex.util.StatusBarUtil;
import com.jiuye.baseframex.util.ToastUtil;
import com.jiuye.baseframex.widget.MyViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.mViewPager)
    MyViewPager mViewPager;
    @BindView(R.id.radioBtn1)
    AppCompatRadioButton radioBtn1;
    @BindView(R.id.radioBtn2)
    AppCompatRadioButton radioBtn2;
    @BindView(R.id.radioBtn3)
    AppCompatRadioButton radioBtn3;
    @BindView(R.id.radioBtn4)
    AppCompatRadioButton radioBtn4;
    @BindView(R.id.mGroup)
    RadioGroup mGroup;
    @BindView(R.id.centerTv)
    AppCompatTextView centerTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setStatusBarColor() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0,null);
        //字体颜色
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initData() {
        initFragment();
    }

    private void initFragment() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(SimpleFragment.newInstance("微信"));
        fragmentList.add(SimpleFragment.newInstance("通讯录"));
        fragmentList.add(SimpleFragment.newInstance("发现"));
        fragmentList.add(MineFragment.newInstance("我的"));
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.addOnPageChangeListener(onPageChangeListener);
        mGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        radioBtn1.setChecked(true);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    radioBtn1.setChecked(true);
                    break;
                case 1:
                    radioBtn2.setChecked(true);
                    break;
                case 2:
                    radioBtn3.setChecked(true);
                    break;
                case 3:
                    radioBtn4.setChecked(true);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.radioBtn1:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.radioBtn2:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.radioBtn3:
                    mViewPager.setCurrentItem(2);
                    break;
                case R.id.radioBtn4:
                    mViewPager.setCurrentItem(3);
                    break;
                default:
                    break;
            }
        }
    };

    @OnClick({R.id.centerTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.centerTv:
                ToastUtil.show("上传");
                break;
            default:
                break;
        }
    }

    /**
     * 注册EventBus
     * @return
     */
    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(MessageEvent event) {
        switch (event.getCode()) {
            case CommonTools.EVENT_CODE_BACK_GROUND:
                ToastUtil.show("AndroidX已经切换到后台");
                break;
            case CommonTools.EVENT_CODE_FORE_GROUND:
                //后台回到app 如果用户已经登录，需要更新用户信息

                break;
            default:
                break;
        }
    }
}

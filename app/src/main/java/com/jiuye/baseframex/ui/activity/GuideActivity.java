package com.jiuye.baseframex.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.jiuye.baseframex.R;
import com.jiuye.baseframex.base.BaseActivity;
import com.jiuye.baseframex.ui.MainActivity;
import com.jiuye.baseframex.util.CommonTools;
import com.jiuye.baseframex.util.GlideUtil;
import com.jiuye.baseframex.util.SPUtils;
import com.jiuye.baseframex.util.StatusBarUtil;
import com.jiuye.baseframex.widget.indicator.CircleIndicator;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2019/04/04  14:28
 * desc   : 引导页
 * version: 1.0
 */
public class GuideActivity extends BaseActivity {
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.mIndicator)
    CircleIndicator mIndicator;
    @BindView(R.id.startBtn)
    MaterialButton startBtn;

    private int[] gridIcon = {R.drawable.welcome, R.drawable.welcome, R.drawable.welcome};
    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void setStatusBarColor() {
        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 0);
    }
    @Override
    protected void initData() {
        GuideAdapter adapter = new GuideAdapter(GuideActivity.this, gridIcon);
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
        adapter.registerDataSetObserver(mIndicator.getDataSetObserver());
        mViewPager.addOnPageChangeListener(onPageChangeListener);
        mViewPager.setOffscreenPageLimit(3);
    }
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
        @Override
        public void onPageSelected(int position) {
            startBtn.setVisibility(position==2?View.VISIBLE:View.GONE);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    @OnClick({R.id.startBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.startBtn:
                SPUtils.putBoolean(CommonTools.IS_FIRST_INTO,false);
                if (SPUtils.getBooleanUser(CommonTools.USER_ISLOGIN, false)) {
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                }
                finish();
                break;
            default:
                break;
        }
    }
    class GuideAdapter extends PagerAdapter {
        private Context mContext;
        private int[] iconList;

        public GuideAdapter(Context mContext, int[] iconList) {
            this.mContext = mContext;
            this.iconList = iconList;
        }

        @Override
        public int getCount() {
            return null == iconList ? 0 : iconList.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(iconList[position]);
            //GlideUtil.load(mContext,iconList[position], imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onBackPressed() {}
}

package com.jiuye.baseframex.widget.indicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * CircleIndicator work with ViewPager
 * 使用方法1(圆形选中状态大圆):  直接使用高度固定大小 ,建议20dp
 * 使用方法2(自定义动画和指示器样式):
 *              app:ci_animator="@animator/indicator_animator"
 *              app:ci_animator_reverse="@animator/indicator_animator_reverse"
 *              app:ci_drawable="@drawable/black_radius_square"
 *              app:ci_height="4dp"
 *              app:ci_margin="6dp"
 *              app:ci_width="10dp"
 * 使用方法3(圆形无动画):
 *         app:ci_animator="@animator/indicator_no_animator"
 *         app:ci_drawable="@drawable/white_radius"
 *         app:ci_drawable_unselected="@drawable/black_radius"
 * 使用方法4: DynamicAdapter
 *           mAdapter.registerDataSetObserver(indicator.getDataSetObserver());
 *
 */
public class CircleIndicator extends BaseCircleIndicator {

    private ViewPager mViewpager;

    public CircleIndicator(Context context) {
        super(context);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setViewPager(@Nullable ViewPager viewPager) {
        mViewpager = viewPager;
        if (mViewpager != null && mViewpager.getAdapter() != null) {
            mLastPosition = -1;
            createIndicators();
            mViewpager.removeOnPageChangeListener(mInternalPageChangeListener);
            mViewpager.addOnPageChangeListener(mInternalPageChangeListener);
            mInternalPageChangeListener.onPageSelected(mViewpager.getCurrentItem());
        }
    }

    private void createIndicators() {
        removeAllViews();
        PagerAdapter adapter = mViewpager.getAdapter();
        int count;
        if (adapter == null || (count = adapter.getCount()) <= 0) {
            return;
        }
        createIndicators(count, mViewpager.getCurrentItem());
    }

    private final ViewPager.OnPageChangeListener mInternalPageChangeListener =
            new ViewPager.OnPageChangeListener() {

                @Override public void onPageScrolled(int position, float positionOffset,
                        int positionOffsetPixels) {
                }

                @Override public void onPageSelected(int position) {

                    if (mViewpager.getAdapter() == null
                            || mViewpager.getAdapter().getCount() <= 0) {
                        return;
                    }
                    internalPageSelected(position);
                    mLastPosition = position;
                }

                @Override public void onPageScrollStateChanged(int state) {
                }
            };

    public DataSetObserver getDataSetObserver() {
        return mInternalDataSetObserver;
    }

    private final DataSetObserver mInternalDataSetObserver = new DataSetObserver() {
        @Override public void onChanged() {
            super.onChanged();
            if (mViewpager == null) {
                return;
            }
            PagerAdapter adapter = mViewpager.getAdapter();
            int newCount = adapter != null ? adapter.getCount() : 0;
            int currentCount = getChildCount();
            if (newCount == currentCount) {
                // No change
                return;
            } else if (mLastPosition < newCount) {
                mLastPosition = mViewpager.getCurrentItem();
            } else {
                mLastPosition = -1;
            }
            createIndicators();
        }
    };

    /**
     * @deprecated User ViewPager addOnPageChangeListener
     */
    @Deprecated public void setOnPageChangeListener(
            ViewPager.OnPageChangeListener onPageChangeListener) {
        if (mViewpager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        }
        mViewpager.removeOnPageChangeListener(onPageChangeListener);
        mViewpager.addOnPageChangeListener(onPageChangeListener);
    }
}

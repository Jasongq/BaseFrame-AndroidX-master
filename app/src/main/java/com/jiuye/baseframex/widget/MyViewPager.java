package com.jiuye.baseframex.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.jiuye.baseframex.R;


/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2019/03/28  14:35
 * desc   :
 * version: 1.0
 */
public class MyViewPager extends ViewPager {
    public boolean isScroll;
    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.MyViewPager);
        isScroll=array.getBoolean(R.styleable.MyViewPager_isScroll,false);
        array.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScroll){
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScroll){
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }
}

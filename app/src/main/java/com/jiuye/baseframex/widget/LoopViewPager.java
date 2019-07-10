/*
 * Copyright (C) 2013 Leszek Mzyk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jiuye.baseframex.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jiuye.baseframex.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 需要实现循环滚动的时候，不需要改动代码，只需要把xml的节点换成LoopViewPager就可以了
 * 如果滑动到边界的时候，出现了闪现的情况，调用setBoundaryCaching( true ), 或者 DEFAULT_BOUNDARY_CASHING 变成 true
 * 如果使用 FragmentPagerAdapter 或者 FragmentStatePagerAdapter, 必须改变Adapter，使其多创建两个条目，比如
 * original adapter position    [0,1,2,3]
 * modified adapter position  [0,1,2,3,4,5]
 * modified     realPosition  [3,0,1,2,3,0]
 * modified     InnerddPosition [4,1,2,3,4,1]
 */
public class LoopViewPager extends ViewPager {
    private static final boolean DEFAULT_BOUNDARY_CASHING = false;
    private static final boolean DEFAULT_BOUNDARY_LOOPING = true;

    private LoopPagerAdapterWrapper mAdapter;//实现了循环滚动的Adapter
    private boolean mBoundaryCaching = DEFAULT_BOUNDARY_CASHING;
    private boolean mBoundaryLooping = DEFAULT_BOUNDARY_LOOPING;
    private List<OnPageChangeListener> mOnPageChangeListeners;

    private Handler mHandler;  //处理轮播的Handler
    private boolean mIsAutoLoop = true;  //是否自动轮播
    private int mDelayTime = 5000; //轮播的延时时间
    private boolean isDetached;    //是否被回收过
    private int currentPosition;   //当前的条目位置

    public LoopViewPager(Context context) {
        super(context);
        init(context);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray array=getResources().obtainAttributes(attrs, R.styleable.LoopViewPager);
        mIsAutoLoop = array.getBoolean(R.styleable.LoopViewPager_lvp_isAutoLoop, mIsAutoLoop);
        mDelayTime = array.getInteger(R.styleable.LoopViewPager_lvp_delayTime, mDelayTime);
        array.recycle();
        setAutoLoop(mIsAutoLoop, mDelayTime);
    }

    private void init(Context context) {
        super.removeOnPageChangeListener(onPageChangeListener);
        super.addOnPageChangeListener(onPageChangeListener);
    }
    /**
     * helper function which may be used when implementing FragmentPagerAdapter
     *
     * @return (position - 1)%count
     */
    public static int toRealPosition(int position, int count) {
        position = position - 1;
        if (position < 0) {
            position += count;
        } else {
            position = position % count;
        }
        return position;
    }

    /**
     * If set to true, the boundary views (i.e. first and last) will never be
     * destroyed This may help to prevent "blinking" of some views
     * 如果设置为真,则边界的观点(即第一个和最后一个)永远不会摧毁这可能有助于防止“闪烁”的一些看法
     */
    public void setBoundaryCaching(boolean flag) {
        mBoundaryCaching = flag;
        if (mAdapter != null) {
            mAdapter.setBoundaryCaching(flag);
        }
    }

    public void setBoundaryLooping(boolean flag) {
        mBoundaryLooping = flag;
        if (mAdapter != null) {
            mAdapter.setBoundaryLooping(flag);
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        mAdapter = new LoopPagerAdapterWrapper(adapter);
        mAdapter.setBoundaryCaching(mBoundaryCaching);
        mAdapter.setBoundaryLooping(mBoundaryLooping);
        super.setAdapter(mAdapter);
        setCurrentItem(0, false);
    }
    /**
     * 默认返回的是传进来的Adapter
     */
    @Override
    public PagerAdapter getAdapter() {
        return mAdapter != null ? mAdapter.getRealAdapter() : null;
    }

    @Override
    public int getCurrentItem() {
        return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
//        int realItem = mAdapter.toInnerPosition(item);
//        super.setCurrentItem(realItem, smoothScroll);
        try {
            int realItem = mAdapter == null ? 0 : mAdapter.toInnerPosition(item);
            super.setCurrentItem(realItem, smoothScroll);
        }catch (Exception e){}
    }

    @Override
    public void setCurrentItem(int item) {
        if (getCurrentItem() != item) {
            setCurrentItem(item, true);
        }
    }

    @Override
    public void addOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = new ArrayList<>();
        }
        mOnPageChangeListeners.add(listener);
    }

    @Override
    public void removeOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.remove(listener);
        }
    }

    @Override
    public void clearOnPageChangeListeners() {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.clear();
        }
    }



    private final OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousOffset = -1;
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {

            int realPosition = mAdapter.toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;

                if (mOnPageChangeListeners != null) {
                    for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
                        OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                        if (listener != null) {
                            listener.onPageSelected(realPosition);
                        }
                    }
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int realPosition = position;
            if (mAdapter != null) {
                realPosition = mAdapter.toRealPosition(position);

                if (positionOffset == 0 && mPreviousOffset == 0 && (position == 0
                        || position == mAdapter.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }

            mPreviousOffset = positionOffset;

            if (mOnPageChangeListeners != null) {
                for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
                    OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                    if (listener != null) {
                        if (realPosition != mAdapter.getRealCount() - 1) {
                            listener.onPageScrolled(realPosition, positionOffset,
                                    positionOffsetPixels);
                        } else {
                            if (positionOffset > .5) {
                                listener.onPageScrolled(0, 0, 0);
                            } else {
                                listener.onPageScrolled(realPosition, 0, 0);
                            }
                        }
                    }
                }
            }
        }
        /**
         * state == ViewPager.SCROLL_STATE_DRAGGING  //正在滑动   pager处于正在拖拽中
         * state == ViewPager.SCROLL_STATE_SETTLING   //pager正在自动沉降，相当于松手后，pager恢复到一个完整pager的过程
         * state == ViewPager.SCROLL_STATE_IDLE  //空闲状态  pager处于空闲状态
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            //当滑动到了第一页 或者 最后一页的时候，跳转到指定的对应页
            //不能在onPageSelected中写这段逻辑，因为onPageSelected当松手的时候，就调用了
            //不是在滑动结束后再调用
            if (mAdapter != null) {
                int position = LoopViewPager.super.getCurrentItem();
                int realPosition = mAdapter.toRealPosition(position);
                if (state == ViewPager.SCROLL_STATE_IDLE && (position == 0
                        || position == mAdapter.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }
            //分发事件给外部传进来的监听
            if (mOnPageChangeListeners != null) {
                for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
                    OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                    if (listener != null) {
                        listener.onPageScrollStateChanged(state);
                    }
                }
            }
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isDetached) {
            if (onPageChangeListener != null) {
                super.addOnPageChangeListener(onPageChangeListener);
            }
            if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.sendEmptyMessageDelayed(0, mDelayTime);
            }
            isDetached = false;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (onPageChangeListener != null) {
            super.removeOnPageChangeListener(onPageChangeListener);
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        isDetached = true;
    }
    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putInt("currentPosition", currentPosition);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable("superState"));
            currentPosition = bundle.getInt("currentPosition");
        } else {
            super.onRestoreInstanceState(state);
        }
        setCurrentItem(currentPosition);
    }



    /**
     * 设置是否自动轮播  delayTime延时的毫秒
     */
    public void setAutoLoop(boolean isAutoLoop, int delayTime) {
        mIsAutoLoop = isAutoLoop;
        mDelayTime = delayTime;
        if (mIsAutoLoop) {
            if (mHandler == null) {
                mHandler = new InnerHandler();
                mHandler.sendEmptyMessageDelayed(0, mDelayTime);
            } else {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.sendEmptyMessageDelayed(0, mDelayTime);
            }
        } else {
            if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler = null;
            }
        }
    }

    /**
     * 自动轮播的Handler
     */
    @SuppressLint("HandlerLeak")
    private class InnerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            setCurrentItem(getCurrentItem()+1);
            sendEmptyMessageDelayed(0, mDelayTime);
        }
    }
    /**
     * 手指按下时 不自动轮播
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction()== MotionEvent.ACTION_DOWN||ev.getAction()== MotionEvent.ACTION_POINTER_DOWN){
            setAutoLoop(false,mDelayTime);
        }else if (ev.getAction()== MotionEvent.ACTION_UP){
            if (mIsAutoLoop) {
                if (mHandler == null) {
                    mHandler = new InnerHandler();
                    mHandler.sendEmptyMessageDelayed(0, mDelayTime);
                } else {
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.sendEmptyMessageDelayed(0, mDelayTime);
                }
            }else {
                if (mHandler != null) {
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler = null;
                }
            }
        }else {
            setAutoLoop(true,mDelayTime);
        }
        return super.onTouchEvent(ev);
    }


}

package com.jiuye.baseframex.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <pre>
 *     author : GuoQiang
 *     e-mail : 849199845@qq.com
 *     time   : 2018/11/20
 *     desc   :   ①如把初始化内容放到initData实现,就是采用Lazy方式加载的Fragment
 *                ②如不需要Lazy加载则initData方法内留空,初始化内容放到initViews即可
 *      -
 *      -注1: 如果是与ViewPager一起使用，调用的是setUserVisibleHint。
 *      ------可以调用mViewPager.setOffscreenPageLimit(size),若设置了该属性 则viewpager会缓存指定数量的Fragment
 *      -注2: 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
 *      -注3: 针对初始就show的Fragment 为了触发onHiddenChanged事件 达到lazy效果 需要先hide再show
 *     version: 1.0
 * </pre>
 */
public abstract class BaseFragment extends Fragment {
    /**fragment标题*/
    protected String fragmentTitle;
    /**是否可见状态*/
    private boolean isVisible;
    /**标志位，View已经初始化完成。*/
    private boolean isPrepared;
    /**是否第一次加载*/
    private boolean isFirstLoad = true;
    protected LayoutInflater inflater;

    protected Activity activity;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        isFirstLoad = true;
        View view = initView(inflater, container, savedInstanceState);
        unbinder= ButterKnife.bind(this, view);
        isPrepared = true;
        lazyLoad();
        return view;
    }


    /**
     * 若是与ViewPager一起使用，调用的是setUserVisibleHint
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 注1:是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 注2:是初始就show的Fragment 为了触发该事件 需要先hide再show
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }


    protected void lazyLoad() {
        if (isPrepared&&isVisible){
            updateInfo();
        }
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        initData();
    }
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    protected abstract void initData();
    /**每次切换Fragment 都会执行*/
    protected void updateInfo(){};
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        activity=null;
    }

    public String getTitle() {
        return TextUtils.isEmpty(fragmentTitle) ? "" : fragmentTitle;
    }
    public void setTitle(String title) {
        fragmentTitle = title;
    }

}

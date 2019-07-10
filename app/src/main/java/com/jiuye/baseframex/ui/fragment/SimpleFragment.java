package com.jiuye.baseframex.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jiuye.baseframex.R;
import com.jiuye.baseframex.base.BaseFragment;

import butterknife.BindView;

/**
 * @author : GuoQiang
 * e-mail : 849199845@qq.com
 * time   : 2019/03/07  16:08
 * desc   : A placeholder fragment containing a simple view.
 * version: 1.0
 */
public class SimpleFragment extends BaseFragment {
    @BindView(R.id.title_tv)
    TextView titleTv;
    private String mTitle;

    public static SimpleFragment newInstance(String params) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle args = new Bundle();
        args.putString("params", params);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle=getArguments().getString("params");
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple, container, false);
    }

    @Override
    protected void initData() {
        titleTv.setText(TextUtils.isEmpty(mTitle) ? "" : mTitle);
    }

}

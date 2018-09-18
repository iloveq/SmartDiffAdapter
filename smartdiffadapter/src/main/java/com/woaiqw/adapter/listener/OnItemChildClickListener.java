package com.woaiqw.adapter.listener;

import android.view.View;

import com.woaiqw.adapter.base.BaseSmartAdapter;

/**
 * Created by haoran on 2018/9/18.
 */
public interface OnItemChildClickListener {

    void onItemChildClick(BaseSmartAdapter adapter, View view, int position);
}

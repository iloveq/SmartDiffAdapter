package com.woaiqw.adapter.listener;

import android.view.View;

import com.woaiqw.adapter.base.BaseSmartAdapter;

/**
 * Created by haoran on 2018/9/18.
 */
public interface OnItemLongClickListener {

    boolean onItemLongClick(BaseSmartAdapter adapter, View view, int position);

}

package com.woaiqw.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by haoran on 2018/5/3.
 */

public interface SmartDiffCallBack<T> {
    boolean areItemsTheSame(T oldItem, T newItem);

    boolean areContentsTheSame(T oldItem, T newItem);

    @Nullable
    Object getChangePayload(Bundle bundle, T oldItem, T newItem);
}

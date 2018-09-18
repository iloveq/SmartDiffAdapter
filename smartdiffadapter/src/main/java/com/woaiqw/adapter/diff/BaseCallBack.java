package com.woaiqw.adapter.diff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by haoran on 2018/5/3.
 */

public class BaseCallBack<T> extends DiffUtil.Callback {

    private final List<T> oldItems;
    private final List<T> newItems;
    private SmartDiffCallBack<T> callBack;


    BaseCallBack(List<T> oldItems, List<T> newItems, SmartDiffCallBack<T> smartDiffCallBack) {
        this.oldItems = oldItems;
        this.newItems = newItems;
        this.callBack = smartDiffCallBack;
    }


    @Override
    public final int getOldListSize() {
        return oldItems.size();
    }

    @Override
    public final int getNewListSize() {
        return newItems.size();
    }

    /**
     * 用来判断 两个对象是否是相同的Item。
     * For example, if your items have unique ids, this method should check their id equality.
     *
     * @param oldItemPosition
     * @param newItemPosition
     * @return True if the two items represent the same object or false if they are different.
     */
    @Override
    public final boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return callBack.areItemsTheSame(oldItems.get(oldItemPosition), newItems.get(newItemPosition));
    }

    /**
     * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
     *
     * @param oldItemPosition
     * @param newItemPosition
     * @return True if the contents of the items are the same or false if they are different.Item的视觉表现是否相同。
     */
    @Override
    public final boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return callBack.areContentsTheSame(oldItems.get(oldItemPosition), newItems.get(newItemPosition));
    }

    /**
     * 当{@link # areItemsTheSame(int, int)} 返回true，且{@link # areContentsTheSame(int, int)} 返回false时，DiffUtils会回调此方法，
     * 去得到这个Item（有哪些）改变的payload。
     *
     * @param oldItemPosition
     * @param newItemPosition
     * @return 默认的实现是返回null
     */
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return callBack.getChangePayload(new Bundle(), oldItems.get(oldItemPosition), newItems.get(newItemPosition));
    }
}
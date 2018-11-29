package com.woaiqw.adapter.diff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;

import com.woaiqw.adapter.base.BaseSmartAdapter;
import com.woaiqw.adapter.holder.BaseViewHolder;

import java.util.List;

/**
 * Created by haoran on 2018/5/3.
 */

public abstract class BaseSmartDiffAdapter<T, K extends BaseViewHolder> extends BaseSmartAdapter<T, K> {


    public BaseSmartDiffAdapter(int layoutResId, List<T> mData) {
        super(layoutResId, mData);
    }


    public BaseSmartDiffAdapter(int mLayoutResId) {
        super(mLayoutResId);
    }

    public BaseSmartDiffAdapter(List<T> mData) {
        super(mData);
    }

    /**
     * for local refresh
     *
     * @param holder
     * @param position
     * @param payloads
     */
    @Override
    public void onBindViewHolder(K holder, int position, List<Object> payloads) {
        if (payloads == null || payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            Bundle payload = (Bundle) payloads.get(0);
            convert(holder, payload);
        }
    }


    protected abstract void convert(K helper, Bundle payload);

    private SmartDiffCallBack<T> smartDiffCallBack;

    public void setSmartDiffCallBack(SmartDiffCallBack<T> smartDiffCallBack) {
        this.smartDiffCallBack = smartDiffCallBack;
    }


    /**
     * DiffUtils 刷新数据
     *
     * @param newData 新数据
     */
    public void refreshData(final List<T> newData) {
        if (smartDiffCallBack == null) {
            throw new RuntimeException("callback must be created before refresh data");
        }
        MyTask<T, K> task = new MyTask(this, smartDiffCallBack);
        task.execute(mData, newData);

    }


    static class MyTask<T, K extends BaseViewHolder> extends AsyncTask<List<T>, Void, DiffUtil.DiffResult> {

        private SmartDiffCallBack<T> smartDiffCallBack;
        private BaseSmartDiffAdapter<T, K> adapter;
        private List<T> newData;

        MyTask(BaseSmartDiffAdapter<T, K> adapter, SmartDiffCallBack<T> smartDiffCallBack) {
            this.adapter = adapter;
            this.smartDiffCallBack = smartDiffCallBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SafeVarargs
        @Override
        protected final DiffUtil.DiffResult doInBackground(List<T>... lists) {
            List<T> mData = lists[0];
            newData = lists[1];
            DiffUtil.DiffResult result;
            BaseCallBack callBack = new BaseCallBack(mData, newData, smartDiffCallBack);
            result = DiffUtil.calculateDiff(callBack, true);
            return result;
        }

        @Override
        protected void onPostExecute(DiffUtil.DiffResult result) {
            super.onPostExecute(result);
            result.dispatchUpdatesTo(adapter);
            adapter.replaceData(newData);
        }
    }
}




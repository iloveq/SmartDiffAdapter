package com.woaiqw.adapter.diff;

import android.os.Bundle;
import android.support.v7.util.DiffUtil;

import com.woaiqw.adapter.base.BaseSmartAdapter;
import com.woaiqw.adapter.holder.BaseViewHolder;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by haoran on 2018/5/3.
 */

public abstract class BaseSmartDiffAdapter<T, K extends BaseViewHolder> extends BaseSmartAdapter<T, K> {


    private Disposable disposable;
    private CompositeDisposable manager;

    private void initManager() {
        manager = new CompositeDisposable();
    }

    public BaseSmartDiffAdapter(int layoutResId, List<T> mData) {
        super(layoutResId, mData);
        initManager();
    }


    public BaseSmartDiffAdapter(int mLayoutResId) {
        super(mLayoutResId);
        initManager();
    }

    public BaseSmartDiffAdapter(List<T> mData) {
        super(mData);
        initManager();
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
        disposable = Observable.create(new ObservableOnSubscribe<DiffUtil.DiffResult>() {
            @Override
            public void subscribe(ObservableEmitter<DiffUtil.DiffResult> e) {
                BaseCallBack callBack = new BaseCallBack(mData, newData, smartDiffCallBack);
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callBack, true);
                e.onNext(diffResult);
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DiffUtil.DiffResult>() {
            @Override
            public void accept(DiffUtil.DiffResult diffResult) {
                diffResult.dispatchUpdatesTo(BaseSmartDiffAdapter.this);
                mData = newData;

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                replaceData(newData);
            }
        });
        if (manager != null) {
            manager.add(disposable);
        }
    }

    public void release() {
        if (manager == null) {
            return;
        }
        if (manager.isDisposed()) {
            manager.dispose();
        }
    }


}

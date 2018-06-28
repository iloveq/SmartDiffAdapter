package com.woaiqw.adapter;

import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by haoran on 2018/5/3.
 */

public abstract class BaseSmartAdapter<T, K extends BaseViewHolder> extends RecyclerView.Adapter<K> {

    private RecyclerView mRecyclerView;
    protected int mLayoutResId;
    protected LayoutInflater mLayoutInflater;
    protected List<T> mData;

    private void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    protected RecyclerView getRecyclerView() {
        checkNotNull();
        return mRecyclerView;
    }

    private void checkNotNull() {
        if (getRecyclerView() == null) {
            throw new RuntimeException("recyclerView not be instance");
        }
    }

    /**
     * 保存 recyclerView 的引用并 setAdapter
     * setAdapter 和 bindToRecyclerView 只能选择一个
     */
    public void bindToRecyclerView(RecyclerView recyclerView) {
        if (getRecyclerView() != null) {
            throw new RuntimeException("bind twice");
        }
        setRecyclerView(recyclerView);
        getRecyclerView().setAdapter(this);
    }

    /**
     * initialize some
     *
     * @param layoutResId 布局
     * @param mData       数据
     */
    public BaseSmartAdapter(int layoutResId, List<T> mData) {
        if (layoutResId != 0)
            this.mLayoutResId = layoutResId;
        this.mData = mData == null ? new ArrayList<T>() : mData;
    }

    @Nullable
    public T getItem(@IntRange(from = 0) int position) {
        if (position < mData.size())
            return mData.get(position);
        else
            return null;
    }

    public BaseSmartAdapter(int mLayoutResId) {
        this(mLayoutResId, null);
    }

    public BaseSmartAdapter(List<T> mData) {
        this(0, mData);
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        K baseViewHolder = null;
        this.mLayoutInflater = LayoutInflater.from(parent.getContext());
        baseViewHolder = onCreateDefViewHolder(parent, viewType);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        convert(holder, getItem(position));
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    protected abstract void convert(K helper, T item);


    protected View getItemView(@LayoutRes int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    @Override
    public int getItemViewType(int position) {
        return getDefItemViewType(position);
    }

    private MultiTypeDelegate<T> mMultiTypeDelegate;

    public void setMultiTypeDelegate(MultiTypeDelegate<T> multiTypeDelegate) {
        mMultiTypeDelegate = multiTypeDelegate;
    }

    public MultiTypeDelegate<T> getMultiTypeDelegate() {
        return mMultiTypeDelegate;
    }

    protected int getDefItemViewType(int position) {
        if (mMultiTypeDelegate != null) {
            return mMultiTypeDelegate.getDefItemViewType(mData, position);
        }
        return super.getItemViewType(position);
    }


    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mLayoutResId;
        if (mMultiTypeDelegate != null) {
            layoutId = mMultiTypeDelegate.getLayoutId(viewType);
        }
        return createBaseViewHolder(parent, layoutId);
    }


    protected K createBaseViewHolder(ViewGroup parent, int layoutResId) {
        return createBaseViewHolder(getItemView(layoutResId, parent));
    }

    /**
     * if you want to use subclass of BaseViewHolder in the adapter,
     * you must override the method to create new ViewHolder.
     *
     * @param view view
     * @return new ViewHolder
     */
    @SuppressWarnings("unchecked")
    protected K createBaseViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        K k;
        // 泛型擦除会导致z为null
        if (z == null) {
            k = (K) new BaseViewHolder(view);
        } else {
            k = createGenericKInstance(z, view);
        }
        return k != null ? k : (K) new BaseViewHolder(view);
    }

    /**
     * try to create Generic K instance
     *
     * @param z
     * @param view
     * @return
     */
    @SuppressWarnings("unchecked")
    private K createGenericKInstance(Class z, View view) {
        try {
            Constructor constructor;
            // inner and unstatic class
            if (z.isMemberClass() && !Modifier.isStatic(z.getModifiers())) {
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                constructor.setAccessible(true);
                return (K) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (K) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * get generic parameter K
     *
     * @param z
     * @return
     */
    private Class getInstancedGenericKClass(Class z) {
        Type type = z.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (BaseViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                }
            }
        }
        return null;
    }


    /**
     * change data      *****************************************************************************************************************************************************************
     */

    public void setData(@IntRange(from = 0) int index, @NonNull T data) {
        mData.set(index, data);
        notifyItemChanged(index);
    }

    /**
     * add one new data
     */

    public void setNewData(@Nullable List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        notifyDataSetChanged();
    }

    public void addData(@NonNull T data) {
        mData.add(data);
        notifyItemInserted(mData.size());
    }

    public void addData(@IntRange(from = 0) int position, @NonNull Collection<? extends T> newData) {
        mData.addAll(position, newData);
        notifyItemRangeInserted(position, newData.size());
    }

    // 不是同一个引用才清空列表
    public void replaceData(@NonNull Collection<? extends T> data) {
        if (data != mData) {
            mData.clear();
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }


}

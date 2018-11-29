package com.woaiqw.adapter.holder;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.AdapterView;

import com.woaiqw.adapter.base.BaseSmartAdapter;

/**
 * Created by haoran on 2018/5/3.
 */

public interface IHolder {

    IHolder get(View itemView);

    View getItemView();

    <T extends View> T getView(@IdRes int viewId);

    IHolder setTag(@IdRes int viewId, Object tag);

    IHolder setTag(@IdRes int viewId, int key, Object tag);

    @Deprecated
    IHolder setOnTouchListener(@IdRes int viewId, View.OnTouchListener listener);

    @Deprecated
    IHolder setOnLongClickListener(@IdRes int viewId, View.OnLongClickListener listener);

    @Deprecated
    IHolder setOnItemClickListener(@IdRes int viewId, AdapterView.OnItemClickListener listener);

    IHolder setOnItemLongClickListener(@IdRes int viewId, AdapterView.OnItemLongClickListener listener);

    @Deprecated
    IHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener);

    IHolder setText(@IdRes int viewId, CharSequence value);

    IHolder setText(@IdRes int viewId, @StringRes int strId);

    IHolder setImageResource(@IdRes int viewId, @DrawableRes int imageResId);

    IHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color);

    IHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes);

    IHolder setTextColor(@IdRes int viewId, @ColorInt int textColor);

    IHolder setImageDrawable(@IdRes int viewId, Drawable drawable);

    IHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap);

    IHolder setAlpha(@IdRes int viewId, float value);

    IHolder setGone(@IdRes int viewId, boolean visible);

    IHolder setVisible(@IdRes int viewId, boolean visible);

    IHolder linkify(@IdRes int viewId);

    IHolder setTypeface(@IdRes int viewId, Typeface typeface);

    IHolder setTypeface(Typeface typeface, int... viewIds);

    IHolder setProgress(@IdRes int viewId, int progress);

    IHolder setProgress(@IdRes int viewId, int progress, int max);

    IHolder setMax(@IdRes int viewId, int max);

    IHolder setRating(@IdRes int viewId, float rating);

    IHolder setRating(@IdRes int viewId, float rating, int max);

    IHolder setAdapter(BaseSmartAdapter adapter);

    IHolder addOnLongClickListener(@IdRes final int viewId);

    IHolder addOnClickListener(@IdRes final int viewId);

}

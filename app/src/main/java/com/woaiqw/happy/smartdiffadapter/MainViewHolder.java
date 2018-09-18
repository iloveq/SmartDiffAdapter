package com.woaiqw.happy.smartdiffadapter;

import android.view.View;
import android.widget.TextView;

import com.woaiqw.adapter.holder.BaseViewHolder;

/**
 * Created by haoran on 2018/5/3.
 */

public class MainViewHolder extends BaseViewHolder {

    private TextView tv_name,tv_sex,tv_age,tv_weight,tv_score;

    public MainViewHolder(View itemView) {
        super(itemView);
        tv_name = getView(R.id.student_name);
        tv_sex = getView(R.id.student_sex);
        tv_age = getView(R.id.student_age);
        tv_weight = getView(R.id.student_weight);
        tv_score = getView(R.id.student_score);
    }

}

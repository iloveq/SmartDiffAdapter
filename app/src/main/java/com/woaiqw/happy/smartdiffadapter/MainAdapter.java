package com.woaiqw.happy.smartdiffadapter;

import android.os.Bundle;

import com.woaiqw.adapter.BaseSmartAdapter;

/**
 * Created by haoran on 2018/5/3.
 */

public class MainAdapter extends BaseSmartAdapter<Student, MainViewHolder> {


    public MainAdapter(int mLayoutResId) {
        super(mLayoutResId);
    }

    @Override
    protected void convert(MainViewHolder helper, Student item) {
        helper.setText(R.id.student_name, item.getName())
                .setText(R.id.student_sex, item.getSex())
                .setText(R.id.student_age, item.getAge())
                .setText(R.id.student_weight, item.getWeight())
                .setText(R.id.student_score, item.getScore());
    }

    @Override
    protected void convert(MainViewHolder helper, Bundle payload) {
        for (String key : payload.keySet()) {
            switch (key) {
                case "name":
                    helper.setText(R.id.student_name, payload.getString("name"));
                    break;
                case "sex":
                    helper.setText(R.id.student_sex, payload.getString("sex"));
                    break;
                case "age":
                    helper.setText(R.id.student_age, payload.getString("age"));
                    break;
                case "weight":
                    helper.setText(R.id.student_weight, payload.getString("weight"));
                    break;
                case "score":
                    helper.setText(R.id.student_score, payload.getString("score"));
                    break;

            }
        }
    }
}

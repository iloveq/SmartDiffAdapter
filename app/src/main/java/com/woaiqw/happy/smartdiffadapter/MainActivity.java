package com.woaiqw.happy.smartdiffadapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.woaiqw.adapter.diff.SmartDiffCallBack;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv;
    private List<Student> students = new ArrayList<>();
    private List<Student> studentsNew = new ArrayList<>();
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new MainAdapter(R.layout.item);
        adapter.setSmartDiffCallBack(new SmartDiffCallBack<Student>() {
            @Override
            public boolean areItemsTheSame(Student oldItem, Student newItem) {
                return oldItem.getName().equals(newItem.getName());
            }

            @Override
            public boolean areContentsTheSame(Student oldItem, Student newItem) {
                if (!oldItem.getName().equals(newItem.getName())) {
                    return false;
                }
                if (!oldItem.getSex().equals(newItem.getSex())) {
                    return false;
                }
                if (oldItem.getAge().equals(newItem.getAge())) {
                    return false;
                }
                if (oldItem.getWeight().equals(newItem.getWeight())) {
                    return false;
                }
                if (oldItem.getScore().equals(newItem.getScore())) {
                    return false;
                }
                return true;
            }

            @Nullable
            @Override
            public Bundle getChangePayload(Bundle bundle, Student oldItem, Student newItem) {
                if (!oldItem.getName().equals(newItem.getName())) {
                    bundle.putString("name", newItem.getName());
                }
                if (!oldItem.getAge().equals(newItem.getAge())) {
                    bundle.putString("age", newItem.getAge());
                }
                if (!oldItem.getSex().equals(newItem.getSex())) {
                    bundle.putString("sex", newItem.getSex());
                }
                if (!oldItem.getWeight().equals(newItem.getWeight())) {
                    bundle.putString("weight", newItem.getWeight());
                }
                if (!oldItem.getScore().equals(newItem.getScore())) {
                    bundle.putString("score", newItem.getScore());
                }
                if (bundle.size() == 0) {
                    return null;
                }
                return bundle;
            }
        });
        rv.setAdapter(adapter);
        // 点击刷新
        findViewById(R.id.refresh).setOnClickListener(this);
        initData();
        adapter.setNewData(students);

    }

    private void initData() {
        for (int i = 0; i < 7; i++) {
            Student s = new Student();
            s.setName("s" + i);
            s.setAge(i + "岁");
            s.setSex(i % 2 == 0 ? "男" : "女");
            s.setWeight(i + "斤");
            s.setScore(i + "分");
            students.add(s);
        }
    }

    @Override
    public void onClick(View v) {
        try {
            for (Student student : students) {
                studentsNew.add(student.clone());
            }
            studentsNew.add(new Student("哈哈", "lllll", "100", "100", "100"));//模拟新增数据
            studentsNew.get(0).setName("Android");
            studentsNew.get(0).setSex("修改了性别");//模拟修改数据
            Student student = studentsNew.get(1);//模拟数据位移
            studentsNew.remove(student);
            studentsNew.add(student);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        adapter.refreshData(studentsNew);
    }


}

# SmartDiffAdapter
use DiffUtils in android.support.v7.util 
    rxjava2 
    support multitype items
    to 
    smart refresh data
    and so on 
this work will be update to build project simplily
just like this 👇
your RecyclerView adapter:
```
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

```
use:
```
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
        ....
        //the main
        adapter.refreshData(studentsNew);
```

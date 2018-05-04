package com.woaiqw.happy.smartdiffadapter;

/**
 * Created by haoran on 2018/5/3.
 */

public class Student implements Cloneable {
    private String name;
    private String sex;
    private String age;
    private String weight;
    private String score;

    public Student() {
    }

    public Student(String name, String sex, String age, String weight, String score) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.weight = weight;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        if (sex != null ? !sex.equals(student.sex) : student.sex != null) return false;
        if (age != null ? !age.equals(student.age) : student.age != null) return false;
        if (weight != null ? !weight.equals(student.weight) : student.weight != null) return false;
        return score != null ? score.equals(student.score) : student.score == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        return result;
    }

    @Override
    protected Student clone() throws CloneNotSupportedException {
        Student s = null;
        try {
            s = (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return s;
    }
}

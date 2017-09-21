package com.ming.blueprint.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ming on 2017/9/11 14:14
 * description：
 */
@Entity
public class Student {

    @Id
    private Long _id;

    @NotNull
    private int id;//学生id
    @NotNull
    private String name;//学生姓名

    private String sex;//学生性别
    private String age;//学生年龄
    private String grade;//学生年级
    private String hobby;//学生爱好
    @Generated(hash = 1628746216)
    public Student(Long _id, int id, @NotNull String name, String sex, String age,
            String grade, String hobby) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.grade = grade;
        this.hobby = hobby;
    }
    @Generated(hash = 1556870573)
    public Student() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getGrade() {
        return this.grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getHobby() {
        return this.hobby;
    }
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}

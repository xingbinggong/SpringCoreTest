package com.xingbg.spring.mybatis1.pojo;

public class MySchool {
    private Integer id;
    private String s_name;
    private Integer s_grade;

    @Override
    public String toString() {
        return "MySchool{" +
                "id=" + id +
                ", s_name='" + s_name + '\'' +
                ", s_grade=" + s_grade +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public Integer getS_grade() {
        return s_grade;
    }

    public void setS_grade(Integer s_grade) {
        this.s_grade = s_grade;
    }
}

package com.example.qlmuctieuhocphan;

public class Course {
    private int id;
    private String name;
    private double time1;
    private double time2;
    private double target;

    public Course(int id, String name, double time1, double time2, double target) {
        this.id = id;
        this.name = name;
        this.time1 = time1;
        this.time2 = time2;
        this.target = target;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTime1() {
        return time1;
    }

    public void setTime1(double time1) {
        this.time1 = time1;
    }

    public double getTime2() {
        return time2;
    }

    public void setTime2(double time2) {
        this.time2 = time2;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }
}

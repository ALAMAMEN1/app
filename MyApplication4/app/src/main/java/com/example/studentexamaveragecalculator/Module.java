package com.example.studentexamaveragecalculator;

public class Module {
    public String name;
    public int coefficient;
    public double td = 0, tp = 0, exam = 0;

    public Module(String name, int coefficient) {
        this.name = name;
        this.coefficient = coefficient;
    }

    public Module() {}

    public double getModuleAverage() {
        double sum = (td > 0 ? td : 0) + (tp > 0 ? tp : 0);
        int count = (td > 0 ? 1 : 0) + (tp > 0 ? 1 : 0);
        double average = (count == 0) ? 0 : sum / count;
        return (average + exam) / 2;
    }
}
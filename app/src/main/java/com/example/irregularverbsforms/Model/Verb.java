package com.example.irregularverbsforms.Model;

public class Verb {
    private int id;
    private String basicForm;
    private String pastSimple;
    private String pastPrinciple;

    public Verb(int id, String basicForm, String pastSimple, String pastPrinciple) {
        this.id = id;
        this.basicForm = basicForm;
        this.pastSimple = pastSimple;
        this.pastPrinciple = pastPrinciple;
    }

    public int getId() {
        return id;
    }

    public String getBasicForm() {
        return basicForm;
    }

    public String getPastSimple() {
        return pastSimple;
    }

    public String getPastPrinciple() {
        return pastPrinciple;
    }
}

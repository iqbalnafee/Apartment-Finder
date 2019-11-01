package com.example.apartment_finder2;

public class SubmissionMockData {

    private String name,age;

    SubmissionMockData()
    {

    }
    SubmissionMockData(String n,String a)
    {
        name=n;
        age=a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

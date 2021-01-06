package org.example.blog.pojo;

public class User {
    private String userName;
    private int age;
    private House house;

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public String getUserName() {
        return userName;
    }

    public User(String userName, int age, String gender) {
        this.userName = userName;
        this.age = age;
        this.gender = gender;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String gender;
}

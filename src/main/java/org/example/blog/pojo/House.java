package org.example.blog.pojo;

public class House {
    private String houseName;
    private String address;

    public House(String houseName, String address) {
        this.houseName = houseName;
        this.address = address;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

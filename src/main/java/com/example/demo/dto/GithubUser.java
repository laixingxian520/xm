package com.example.demo.dto;

/**
 * GITHUb获取的用户
 */
public class GithubUser {
    //名字
    private  String name;

    //id唯一
    private  Long id;

    private String bio;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GithubUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}

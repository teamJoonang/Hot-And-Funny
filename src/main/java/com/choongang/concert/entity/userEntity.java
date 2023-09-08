package com.choongang.concert.entity;

import lombok.Data;

@Data
public class userEntity {
    private int id;
    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private String address;
    private int age;
    private String gender;
    private String tel;
}

package com.choongang.concert.dto.user;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AdminDto {

    private String id;
    private String adminLoginId;
    private String adminPassword;
    private String adminNickname;
    private String adminName;
}

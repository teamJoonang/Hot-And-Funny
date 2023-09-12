package com.choongang.concert.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ErrorResponse {

    private String errorLoginId;
    private String errorPassword;
    private String errorRepeatPassword;
    private String errorName;
    private String errorNickname;
    private String errorAddr;
    private String errorAge;
    private String errorTel;

}

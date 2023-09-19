package com.choongang.concert.dto.mypage;

import lombok.Getter;

@Getter
public enum Status {

    ACTIVE("유효"), CANCEL("취소"), WAITING("대기"), EXPIRED("만료");

    private final String value;

    Status(String value) {
        this.value = value;
    }

}

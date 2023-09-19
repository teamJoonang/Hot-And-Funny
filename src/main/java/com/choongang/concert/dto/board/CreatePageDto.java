package com.choongang.concert.dto.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Getter @Setter
@ToString
public class CreatePageDto {

    private int pageNum;
    private int amount;
    private int offset;
    private String type;
    private String keyword;

    public CreatePageDto(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
        this.offset = (pageNum - 1) * amount;
    }

    public CreatePageDto() {
        this(1, 10);
    }

    public String[] getTypeArr() {
        return type == null ? new String[]{} : type.split("");
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
        this.offset = (pageNum - 1) * this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.offset = (this.pageNum - 1) * amount;
    }

    public String makeQueryString(int pageNum) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("pageNum", pageNum)
//                .queryParam("amount", amount)
                .queryParam("type", type)
                .queryParam("keyword", keyword)
                .build()
                .encode();
        return uriComponents.toString();
    }
}

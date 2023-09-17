package com.choongang.concert.dto.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class PageDto {

    private int pageStart;
    private int pageEnd;
    private boolean next;
    private boolean prev;
    private int total;
    private CreatePageDto cp;

    public PageDto(CreatePageDto cp, int total) {
        this.cp = cp;
        this.total = total;
        this.pageEnd = (int) (Math.ceil(cp.getPageNum() / 10.0)) * 10;
        this.pageStart = this.pageEnd - 9;
        int realEnd = (int) (Math.ceil(total * 1.0) / cp.getAmount());

        if (realEnd < pageEnd) {
            this.pageEnd = realEnd;
        }

        this.prev = this.pageStart > 1;

        this.next = this.pageEnd < realEnd;
    }
}

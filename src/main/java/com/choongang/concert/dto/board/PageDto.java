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
        this.pageEnd = (int) ((Math.ceil(cp.getPageNum() / 5.0)) * 5);
        this.pageStart = this.pageEnd - 4;
        int realEnd = (int) (Math.ceil(total * 1.0 / cp.getAmount()));

        if (realEnd < pageEnd) {
            this.pageEnd = realEnd;
            if (pageEnd == 0) {
                pageEnd = 1;
            }
        }

        this.prev = this.pageStart > 1;

        this.next = this.pageEnd < realEnd;
    }
}

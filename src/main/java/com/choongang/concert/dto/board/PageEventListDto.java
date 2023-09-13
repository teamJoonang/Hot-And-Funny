package com.choongang.concert.dto.board;

import lombok.Data;

@Data
public class PageEventListDto {
	private int rowCount = 5; 
	private int pageCount = 5;
	private int totalCount;
	private int page;
	private int startPage = 1; 
	private int endPage;
	private int totalPageCount; 
	private boolean isPrev = false; // 다음 페이지 
	private boolean isNext = false; // 이전 페이지 
	private int offset; 
	
	public PageEventListDto(final int totalCount, final int page) {
		
		setTotalPageCount(totalCount, this.rowCount);
		setStartPage(this.startPage, page, this.pageCount);
		setEndpage(this.startPage, this.pageCount, this.totalPageCount);
		isPrev(page, this.pageCount);
		isNext(this.endPage, this.totalPageCount);
		setOffset(page, this.rowCount);
	}

	private void setTotalPageCount(final int totalCount, final int rowCount) {
		this.totalPageCount = (int) Math.ceil(totalCount * 1.0 / rowCount); 
	}
	
	private void setStartPage(final int startPage, final int page, final int pageCount) {
		this.startPage = startPage + (((page - startPage) / pageCount) * pageCount);
	}
	
	private void setEndpage(final int startPage, final int pageCount, final int totalPageCount) {
		this.endPage = ((startPage - 1) + pageCount) < totalPageCount ? (startPage - 1) + pageCount : totalPageCount;
	}
	
	private void isPrev(final int page, final int pageCount) {
		this.isPrev = 1 < ((page * 1.0) / pageCount);
	}
	
	private void isNext(final int endPage, final int totalPageCount) {
		this.isNext = endPage < totalPageCount;
	}
	private void setOffset(final int page, final int rowCount) {
		this.offset = (page - 1 ) * rowCount;
	}
}

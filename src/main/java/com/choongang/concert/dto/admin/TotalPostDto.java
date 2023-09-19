package com.choongang.concert.dto.admin;

import lombok.Data;

@Data
public class TotalPostDto {
	
	private Long id;
	private String title;
	private String content;
	private String writer;
	private String boardType;
	private String boardId;
	private String date;
}

package com.choongang.concert.dto.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EventDto {
	
	 	private Long id;
	    private String category;
	    private String title;
	    private String content;
	    private String createdAt;
	    private String writer;
	    private Integer hit;
}

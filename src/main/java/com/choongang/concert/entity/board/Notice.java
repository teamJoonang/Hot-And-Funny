package com.choongang.concert.entity.board;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice {
	
	
	private Long id;
	private String category;
	private String title;
	private LocalDateTime cratedAt;
	private Long viewCnt;
	
}

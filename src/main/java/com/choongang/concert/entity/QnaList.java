package com.choongang.concert.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnaList {
	
	private Long id;
	private String category;
	private String title;
	private LocalDateTime cratedAt;
	private Long userId;
	private Long responseYn;
}

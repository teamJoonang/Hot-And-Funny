package com.choongang.concert.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventList {

	
	private Long id;
	private String category;
	private String title;
	private Long writer_id;
	private LocalDateTime created_at;
	private Long view_cnt;
}

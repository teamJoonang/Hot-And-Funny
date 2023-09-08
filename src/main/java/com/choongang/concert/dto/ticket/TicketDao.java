package com.choongang.concert.domain.ticket;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TicketDao {

	public List<ChoiceDateData> remainingSeat (@Param("concertDate") Date concertDate);
}

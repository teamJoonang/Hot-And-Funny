package com.choongang.concert.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgeGroupDto {

	//mapper.xml 에서 티켓을 구매한 Member 중 연령대별 로 가져올수 있는 방법을 생각해볼것
	// 크게 2가지 방법이 있음
	// 쿼리문에서 나이대별로 가져와서 나이대별로 List에 넣을것인지,
	// 전체를 가져와서 코드에서 나이대별로 분류할지 2가지중 하나를 선택해야될 듯

	// 내가 보여주는 예시는 전체를 가져왔다는 가정하에 작성해볼께
	private String name;
	private int age;

	//위의 age,name은 예제임 dto에 추가할것이 있으면 더 추가할 것


	public AgeGroupDto(String name, int age) {
		this.name = name;
		this.age = age;
	}
}

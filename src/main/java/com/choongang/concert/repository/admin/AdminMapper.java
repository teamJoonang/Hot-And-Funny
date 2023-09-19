package com.choongang.concert.repository.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.choongang.concert.dto.admin.Bar2Dto;
import com.choongang.concert.dto.admin.PageDto;
import com.choongang.concert.dto.admin.QnaPostDto;
import com.choongang.concert.dto.admin.RefundDto;
import com.choongang.concert.dto.admin.ResponseTotalPostDto;
import com.choongang.concert.dto.admin.TotalPostDto;
import com.choongang.concert.dto.admin.UserInfoDTO;
import com.choongang.concert.dto.admin.UserInfoSearchDto;

@Mapper
public interface AdminMapper {

	// 회원 전체 목록 조회
	public List<UserInfoDTO> getUserList(PageDto params);
	

    /**
     * 게시글 수 카운팅
     * @return 게시글 수
     */
    int count(PageDto params);
    
    
    
    
    ///// statics.html에 필요한 연령별 인원수 카운트
//    public List<UserInfoDTO> getAgeList();
   
    //	SecondDonut 차트에서 쓸 내용 (연령대별 통계)
    public List<Integer> ageGroup();

    //	Bar 차트에서 쓸 내용 (예매율 통계)
    public List<Integer> reservationGroup();
    
	//	Bar2차트에서 쓸 내용(실시간 좌석 확인)
    public List<Bar2Dto> seatGroup();
    
    //	Donut 차트에서 쓸 내용(성비별 통계)
    public List<Integer> genderGroup();
    
    //	Area 차트에서 쓸 내용 (매출 현황)
    public List<Integer> areaGroup();
    
    
    
    ////////	게시판 관리		//////////////
    
    ///	전체 게시판 관리
    public List<TotalPostDto> getTotalList();
    
    
    //	전체 게시판 삭제(서버단에서 동작)
    public void deleteTotal(List<Long> totalDataForm);
    //	전체 게시판 삭제(서버단에서 동작한 값을 디비에 저장)
    public void resTotalDelete(List<ResponseTotalPostDto> totalDataForm);
    public void deleteQnaBoard(List<ResponseTotalPostDto> totalDataForm);
    public void deleteEventBoard(List<ResponseTotalPostDto> totalDataForm);
    public void deleteNoticeBoard(List<ResponseTotalPostDto> totalDataForm);
    
    
    
    ///	qna 게시판 관리
    public List<QnaPostDto> getQnaList();
    
    //	qna 게시판 수정
    public void updateQna(QnaPostDto qnaPostDto);
    //	qna 게시판 삭제(서버단에서 동작)
    public void deleteQna(List<Long> dataForm);
    //	qna 게시판 삭제(서버단에서 동작한 값을 디비에 저장)
    public void resDelete(List<Long> dataForm);
    
    
    
    //////	refund
    public List<RefundDto> getRefundList();
    
    //	refund 응답
    public List<RefundDto> getRefundData(RefundDto refundDto);






}

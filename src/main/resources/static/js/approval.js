

// JOSN 데이터 받아왔을때 이런식으로 사용해줌
const ticketArray = JSON.parse(sessionStorage.getItem("ticketArray"));
const concertInfo = JSON.parse(sessionStorage.getItem("concertInfo"));

const abc = sessionStorage.getItem("concertInfo");
const totalPrice = document.getElementById("total-price");
const discountPrice = document.getElementById("discount-price");
const commissionPrice = document.getElementById("commission-price");
const finalPrice = document.getElementById("final-price");

const concertDate = document.getElementById("concert-date");
const concertTime = document.getElementById("concert-time");

const seatNumber = document.querySelectorAll(".seat-number");

const btnConfirm = document.getElementById("btn-confirm");
const btnTicket = document.getElementById("btn-ticket");

const userId = 1;


selectedDate();
selectedSeat();

resultPrice();


// 선택된 날짜 및 시간 출력
function selectedDate(){
	concertDate.innerText = concertInfo.concertDate;
	concertTime.innerText = concertInfo.concertTime;
}

// 선택된 좌석 출력
function selectedSeat(){
	for(let i=0; i<ticketArray.length; i++){
		seatNumber[i].textContent = ticketArray[i].seatNum;
	}	
}

//	최종 가격 표시
function resultPrice(){
	finalPrice.innerText = totalPrice - discountPrice + commissionPrice;		
}

// 확인 버튼을 눌렀을 때 index 화면으로 전환
btnConfirm.addEventListener("click",()=>{
	window.opener.location.href="/";
	self.close();
})

// 티켓 버튼을 눌렀을 때 ticket_check 화면으로 전환
btnTicket.addEventListener("click",()=>{
	dateSend();
	window.location.href="/ticket/ticket/check"
})



function dateSend(){
	
	/*
	const params={};
	
	params.concertDate = $('#concert-date').val();
	params.seatNum=[];
	$('.seat-number').each(function(){
		params.seatNum.push($(this).val());
	})
	*/
	const params={};
	
	params.concertDate = concertInfo.concertDate;
	params.seatNum=[];
	ticketArray.forEach(function(model){
		params.seatNum.push(model.seatNum);
	});	

    
	$.ajax({
		url: '/ticket/check'
		,method : 'POST'
	//	,data : JSON.stringify(params)
		,data: {
        concertDate: "2023-09-09",
        userId: userId
    	}
		,contentType: 'application/json'
		,dataType: 'json'
		,async: false
		,success:function(data){
			alert("성공");
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
			console.log(jqXHR);
			console.log(textStatus);
			alert("실패");
		}
	});	
}

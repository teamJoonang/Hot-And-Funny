

// JOSN 데이터 받아왔을때 이런식으로 사용해줌
const ticketArray = JSON.parse(sessionStorage.getItem("ticketArray"));
const concertInfo = JSON.parse(sessionStorage.getItem("concertInfo"));

const noDiscountPrice = JSON.parse(sessionStorage.getItem("noDiscountPrice"));
const discountPrice = JSON.parse(sessionStorage.getItem("discountPrice"));
const totalCharge = JSON.parse(sessionStorage.getItem("totalCharge"));

const totalPrice = document.getElementById("total-price");
const finalDiscountPrice = document.getElementById("discount-price");
const commissionPrice = document.getElementById("commission-price");
const finalPrice = document.getElementById("final-price");

const concertDate = document.getElementById("concert-date");
const concertTime = document.getElementById("concert-time");

const seatNumber = document.querySelectorAll(".seat-number");

const btnConfirm = document.getElementById("btn-confirm");
const btnTicket = document.getElementById("btn-ticket");

console.log(discountPrice);
console.log(noDiscountPrice);
console.log(totalCharge);
// 나중에 추가해야함
const userId = "1";
const concertDateEx = "2023-09-09"

selectedDate();
selectedSeat();

resultPrice();


// 선택된 날짜 및 시간 출력
function selectedDate(){
	 concertDate.innerText = concertInfo.concertDate;
	 concertTime.innerText = concertInfo.concertTime;
	
	//concertDate.innerText = "2023-09-09";
	//concertTime.innerText = "19:00";
}

// 선택된 좌석 출력
function selectedSeat(){
	
	seatNumber[0].textContent = "B-08";
	seatNumber[1].textContent = "A-09";
	seatNumber[2].textContent = "A-10";
	seatNumber[3].textContent = "A-11";
	// for(let i=0; i<ticketArray.length; i++){
	// 	seatNumber[i].textContent = ticketArray[i].seatNum;
	// }	
}

//	최종 가격 표시
function resultPrice(){
	const totalPrice = noDiscountPrice;
	const finalDiscountPrice = discountPrice;
	const commissionPrice = totalCharge;
	finalPrice.innerText = Number(totalPrice) - Number(finalDiscountPrice) + Number(commissionPrice);		
}

// 확인 버튼을 눌렀을 때 index 화면으로 전환
btnConfirm.addEventListener("click",()=>{
	window.opener.location.href="/";
	self.close();
})

// 티켓 버튼을 눌렀을 때 ticket_check 화면으로 전환
btnTicket.addEventListener("click",()=>{
	dateSend();
	

})



//	티켓 보기 버튼을 클릭했을때 ajax 

function dateSend(){
window.location.href="/ticket/ticket/check?concertDate="+concertDateEx;
//window.location.href="/ticket/ticket/check?concertDate="+concertDateEx+"&userId="+userId; 

/*
const data = [{concertDate : "2023-09-09", userId : userId}];

var infoToPass = data; 
var form = $('<form action="/next-page" method="post"></form>');
form.append('<input type="hidden" name="info" value="' + infoToPass + '">');
form.appendTo($('body')).submit();
*/


/*
	$.ajax({
		url: '/ticket/ticket/check'
		,method: 'GET'
		,data: {
			concertDate : concertDateEx,
			userId : userId
		}
		,contentType: 'application/json'
		,dataType: 'json'
		//,async: false
		,success:function(data){
			alert("성공");
			console.log(data);
			//location.href="/ticket/ticket/check"+concertDateEx + "/" + userId
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(errorThrown);
			console.log(jqXHR);
			console.log(textStatus);
			alert("실패");
		}
	});
*/
}


/*
//윈도우 사이즈
var popupWidth = 1200;
var popupHeight = 850;

//윈도우 오픈 가운데 정렬
var left = (screen.availWidth - popupWidth) / 2;


if( window.screenLeft < 0){                            //듀얼모니터 일때
	left += window.screen.width*-1;
} else if ( window.screenLeft > window.screen.width ){ //단일모니터일때
	left += window.screen.width;
}

var top = (screen.availHeight - popupHeight) / 2 -10;

//윈도우 오픈 옵션
var options = 'left=' + left + ',top=' + top +', width=' + popupWidth+ ',height=' + popupHeight ;	
//var options = 'left=' + left + ',top=' + top ;    //팝업 오픈 위치만 지정

//윈도우 오픈
window.open("", "popup_window", options);

//현재 열려있는 url
//개발 : https://deve.com/
//운영 : https://real.com/
var windowUrl = window.location.href;
var action = "http://localhost:8080/ticket/ticket/check";
var action = "https://dev.com/exJspFile.do";

if(windowUrl.indexOf("real") == -1){
 	action = "https://real.com/exJspFile.do";
}

post방식 JSON data
var parma1 = "123";    //param1
var parma2 = "QWER";   //parma2

var params = {
	
	concertDate  : "2023-09-09",      //param1
	userId  : userId       //parma2
	
};

var form = document.createElement('form');        //form엘리먼트 생성

form.setAttribute('method', 'post');              //POST 메서드 적용
form.setAttribute('action', action);	      //데이터를 전송할 url
form.setAttribute('target', 'popup_window');      //window.open targetID
document.charset = "utf-8";                       //인코딩

for ( var key in params) {	// key, value로 이루어진 객체 params
    var hiddenField = document.createElement('input');
    hiddenField.setAttribute('type', 'hidden'); //값 입력
    hiddenField.setAttribute('name', key);
    hiddenField.setAttribute('value', params[key]);
    form.appendChild(hiddenField);
    console.log(key);
    console.log(params[key]);
}

document.body.appendChild(form);

form.submit();	// 전송
*/
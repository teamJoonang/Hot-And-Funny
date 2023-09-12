

// JOSN 데이터 받아왔을때 이런식으로 사용해줌
const storedData = sessionStorage.getItem("ticketArray");
const ticketArray = JSON.parse(storedData);

const totalPrice = document.getElementById("total-price");
const discountPrice = document.getElementById("discount-price");
const commissionPrice = document.getElementById("commission-price");
const finalPrice = document.getElementById("final-price");

const concertDate = document.getElementById("concert-date");

const seatNumber = document.querySelectorAll(".seat-number");

const btnConfrim = document.getElementById("btn-confirm");

selectedSeat();



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
btnConfrim.addEventListener("click",()=>{
	window.opener.location.href="/";
	self.close();
})

console.log(ticketArray[0].seatNum);
console.log(ticketArray.length);

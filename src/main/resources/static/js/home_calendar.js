
let date = new Date();
const concertSite = document.getElementById('concert-site');
const concertRuntime = document.getElementById('concert-runtime');
const concertDate = document.getElementById('concert-date');
const concertGrade = document.getElementById('concert-grade');
const sendeDate = document.querySelectorAll('.selected');
let selectedDate = null;

const btnReservaiton = document.querySelector('.btn-reservation');

const concertDates = [
	{ number: 1, date: 9, month: 9, year: 2023, time: "20:30:00" },
	{ number: 2, date: 10, month: 9, year: 2023, time: "18:30:00" },
	{ number: 3, date: 11, month: 9, year: 2023, time: "21:30:00" },
	{ number: 4, date: 23, month: 9, year: 2023, time: "21:30:00" },
	{ number: 5, date: 24, month: 9, year: 2023, time: "21:30:00" }
];

const viewYear = date.getFullYear();
const viewMonth = date.getMonth();


const clickableDates = generateClickableDates(concertDates);

function renderCalendar() {


	//  year-month 채우기
	document.querySelector('.year-month').textContent = `${viewYear}년   ${viewMonth + 1}월`

	//  지난 달 마지막 Date, 이번 달 마지막 Date
	const prevLast = new Date(viewYear, viewMonth, 0); // 지난 달 마지막 날짜
	const thisLast = new Date(viewYear, viewMonth + 1, 0); // 이번 달 마지막 날짜

	const PLDate = prevLast.getDate(); // 지난 달 마지막 날짜
	const PLDay = prevLast.getDay(); // 지난 달 마지막 요일 일요일(0)~ 토요일(6)

	const TLDate = thisLast.getDate();
	const TLDay = thisLast.getDay();

	//  Dates 기본 배열들
	const prevDates = [];
	const thisDates = [...Array(TLDate + 1).keys()].slice(1); // 0~ (마지막날 -1) + 1 을 저장 후 0 삭제
	const nextDates = [];

	//  prevDates 계산
	if (PLDay !== 6) {
		for (let i = 0; i < PLDay + 1; i++) {
			prevDates.unshift(PLDate - i);
		}
	}

	//  nextDates 계산
	for (let i = 1; i < 7 - TLDay; i++) {
		nextDates.push(i);
	}

	//  Dates 합치기
	const dates = prevDates.concat(thisDates, nextDates);

	//  Dates 정리
	const firstDateIndex = dates.indexOf(1); // 이번달의 첫 번째 날의 인덱스
	const lastDateIndex = dates.lastIndexOf(TLDate);    //  이번달의 마지막 날의 인덱스

	dates.forEach((date, i) => {
		const condition = i >= firstDateIndex && i < lastDateIndex + 1
			? 'this'
			: 'other';
		dates[i] = `<div class="date"><span class=${condition}>${date}</span></div>`;
	});

	document.querySelector('.dates').innerHTML = dates.join('');

	todayCheck();
	concertCheck();
	concertDateColor();
};


//  달력
renderCalendar();

//  날짜 클릭시 타이틀 화면에 표시
document.querySelector('.dates').addEventListener('click', (event) => {
	const clickedDate = event.target.innerText;
	//console.log(clickedDate);
	const [clickedDay] = clickedDate.split('\n');
	
	selectedDate = concertDates.find(date => date.date === Number(clickedDay));
	//console.log([clickedDay]);
	//console.log("날짜 고른 값 : " + selectedDate);
	const today = new Date();
	const day = today.getDate()-1;
	
	//	선택 날짜 정보 title에 띄우기
	if (selectedDate !== null && selectedDate.date > day) {
		const formattedDay = String(clickedDay).padStart(2, '0'); // 두 자리로 포맷팅
		const formattedTime = selectedDate.time.substring(0,5);
		concertSite.innerText = `• 서울시 강서구 양천로 125 서울문화예관 B1F`;
		concertDate.innerHTML = `• ${selectedDate.year}
								-${String(selectedDate.month).padStart(2, '0')}
								-${formattedDay} 
								/ time. ${formattedTime}`;
		concertGrade.innerText = `• 18등급`;
		concertRuntime.innerText = `• 180분`;
	}
	else {

	}

	const concertId = selectedDate.number;
	
	//console.log("concertId: " + concertId);
	//console.log(userId);

	// 서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
	if (selectedDate !== null && selectedDate.date > day){
		$.ajax({
			url: 'calendar/' + concertId
			, method: 'GET'
			, dataType: 'json'
			, success: function(data) {
				showSeatCount(data);
	
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(errorThrown);
				console.log(jqXHR);
				console.log(textStatus);
	
				//alert("실패");
			}
		});
	}

	reservationSeat();

});

// 예매버튼 눌렀을때 팝업창 띄우기!

function reservationSeat (){
	const today = new Date();
	const day = today.getDate()-1;
	 
	if (selectedDate !== null && selectedDate.date > day){
				
		const formattedDay = String(selectedDate.date).padStart(2, '0'); // 두 자리로 포맷팅
		const concertDate = selectedDate.year+"-"+String(selectedDate.month).padStart(2, '0')+"-"+formattedDay;
		const seatChoiceUrl = 'http://localhost:8080/ticket/seat/choice/' + concertDate;
		btnReservaiton.addEventListener('click',function(){
			window.open(
				seatChoiceUrl,
				'seatChoice',
				'width=1230, height=820, location=true, status=no, scrollbars=no');
			//const popupWindw = window.open(
			//seatChoiceUrl,
			//'seatChoice',
			//'width=1230, height=820, location=true, status=no, scrollbars=no');
			//console.log("예매 날짜 : " + concertDate);
			//console.log(seatChoiceUrl);
			//console.log(typeof(seatChoiceUrl));
			
			// 크기 조절
			//popupWindw.resizeTo(1250,900);
			//popupWindw.onresize = (_=>{
				//popupWindw.resizeTo(1250,900);
			//})
		})
	}	
	else {
		alert("날짜를 확인 해주세요. 공연 이틀 전까지 예매 가능합니다.");
	}
}



// AJAX 내 남은 seat 수를 표현해주는 <tag>
function showSeatCount(data) {

	$("#remainSeat").empty();
	$.each(data, function(index, aa) {
		const tr = $("<tr></tr>");
		const td1 = $("<td class='col-lg-5'></td>").text(aa.remainingSeat);
		const td2 = $("<td class='col-lg-5'></td>").text(aa.remainingSeatCount);
		tr.append(td1, td2);
		$("#remainSeat").append(tr);
	});
}

/*
fetch('/ticket/home/caledar')
			.then(response => response.json())
			.then(data => {
			// JSON 데이터를 사용하여 원하는 작업 수행
			console.log(data);
		})
		.catch(error => {
			console.log("데이터 가져오기 실패", error);
		}
*/

////////////다음날, 이전날, 오늘날 구현 삭제///////////
////////////////////////////////////////////////////
// const prevMonth = () => {
//     date.setDate(1);
//     date.setMonth(date.getMonth() - 1);
//     renderCalendar();
// };
// const nextMonth = () => { 
//     date.setDate(1);
//     date.setMonth(date.getMonth() + 1);
//     renderCalendar();
// };
// const goToday = () => {
//     date = new Date();
//     renderCalendar();
// };
/////////////////////////////////////////////////////

//  오늘 날짜 표시하기
function todayCheck() {
	const today = new Date();
	if (viewMonth === today.getMonth() && viewYear === today.getFullYear()) {
		for (let date of document.querySelectorAll('.this')) {
			if (+date.innerText === today.getDate()) {
				date.setAttribute('id', 'today');
				// break
			}
		}
	}
}



//  날짜 이벤트 표시하기 
function concertCheck() {
	for (const concertDate of concertDates) {
		if (concertDate.month === viewMonth + 1 && concertDate.year === viewYear) {
			const concertElements = document.querySelectorAll(`.this`);
			const concertArray = Array.from(concertElements);


			const dateIndex = concertDate.date - 1;
			const dateElement = concertArray[dateIndex];

			if (dateElement) {
				dateElement.classList.add(`concert`);
				dateElement.closest('.date').classList.add('selected');
			}
		}
	}
}

//  날짜 클릭 데이터 배열 저장 (지정날짜 외 클릭 불가)
function generateClickableDates(concertDates) {
	const clickableDates = {};
	for (const concertDate of concertDates) {
		if (concertDate.month === viewMonth + 1 && concertDate.year === viewYear) {
			clickableDates[concertDate.date] = true;
		}
	}
	return clickableDates;

}
//  날짜 클릭 시 색상 구분
function concertDateColor() {
	const choiceDates = document.querySelectorAll('.selected');
	const today = new Date();
	const day = today.getDate()-1;

	choiceDates.forEach(choiceDate => {
		choiceDate.addEventListener('click', (event) => {
		let choiceDateDay = choiceDate.textContent;	//	예매 불가능한 날짜 선택 불가능하게 만들기
			if (day < choiceDateDay){
				choiceDates.forEach(element => element.classList.remove('choice'));
				let existingChoice = document.querySelector('.choice');
				
				
				//	클릭한 이벤트 날짜가 있다면 색 제거			
				if (existingChoice) {
					existingChoice.classList.remove('choice');
				}	
				// div 태그 선택하기위함
				event.target.classList.add('choice');								
				
				// span 값도 선택시키기 위함			
				const subSpan = event.target.closest('.selected');
				if (subSpan) {
					subSpan.classList.add('choice');						
				}	
			}
		});
	});
}




let date = new Date();
const concertSite = document.getElementById('concert-site');
const concertRuntime = document.getElementById('concert-runtime');
const concertDateView = document.getElementById('concert-date');
const concertGrade = document.getElementById('concert-grade');
const sendeDate = document.querySelectorAll('.selected');

let selectedDate = {};
const clickableDates = generateClickableDates(concertDataList);

const btnReservaiton = document.querySelector('.btn-reservation');

const viewYear = date.getFullYear();
const viewMonth = date.getMonth();

// Ticket 필드값
const ticketMaxCount = 4;


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


//  날짜 클릭시 타이틀 화면 출력
document.querySelector('.dates').addEventListener('click', (event) => {

	const clickedDate = event.target.innerText;
	const [clickedDay] = clickedDate.split('\n');

	selectedDate = concertDataList.find(date => Number(date.concertDay) === Number(clickedDay));

	const today = new Date();

	//예매 가능한 날짜
	const day = today.getDate();

	//	예매 불가능한 날짜 표기

	const concertId = selectedDate.concertId;

	if (selectedDate !== null && selectedDate.concertDay > day) {

		// 서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
		// title 화면 띄우기
		// 잔여석 띄우기
		$.ajax({
			url: 'calendar/' + concertId
			, method: 'GET'
			, dataType: 'json'
			, success: function(data) {
				//	타이틀 화면 -> 장소, 시간, 등급, 날짜 띄우기
				titleShow(data);

				//	잔여석 띄우기
				showSeatCount(data);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.log(errorThrown);
				console.log(jqXHR);
				console.log(textStatus);

			}
		});
	}

	let ticketCountCheck = 0;
	let impossibleId = null;

	if (userId !== null) {

		// 서버에서 갖고온 ID값에 대한 ConcertId와 TicketCount 수 찾기
		const concertIdCheck = selectedDate.concertId;


		for (let i = 0; i < tldList.length; i++) {
			if (tldList[i].concertId === concertIdCheck) {
				ticketCountCheck = tldList[i].ticketCount;

			} else if (ticketCountCheck === undefined) {

				ticketCountCheck = 0;
			}
			if (tldList[i].ticketCount === ticketMaxCount) {
				impossibleId = tldList[i].concertId;
			}
		}

	}
	if (ticketCountCheck < ticketMaxCount) {

		// 팝업창 주소

		const concertDate = selectedDate.concertYear + "-" + selectedDate.concertMonth + "-" + selectedDate.concertDay;
		const seatChoiceUrl = 'http://localhost:8080/ticket/seat/choice/' + concertDate;


		if (btnReservaiton.clickHandler) {
			btnReservaiton.removeEventListener('click', btnReservaiton.clickHandler);
		}
		btnReservaiton.clickHandler = () => {
			if (userId !== null) {
				if (impossibleId !== selectedDate.concertId) {
					window.open(
						seatChoiceUrl,
						'seatChoice',
						'width=1230, height=820, location=true, status=no, scrollbars=no'
						//const popupWindw = window.open(
						//seatChoiceUrl,
						//'seatChoice',
						//'width=1230, height=820, location=true, status=no, scrollbars=no');

						// 크기 조절
						//popupWindw.resizeTo(1250,900);
						//popupWindw.onresize = (_=>{
						//popupWindw.resizeTo(1250,900);
						//})
					);
				};
			} else {
				const loginBtn = confirm("로그인이 필요합니다. \n로그인 페이지로 가시겠습니까?");
				if (loginBtn) {
					location.href = "index.html";
				} else {

				}
			};
		}
		btnReservaiton.addEventListener('click', btnReservaiton.clickHandler);
	}
	else {
		alert("선택하신 날짜에 티켓 구매 가능 수량을 초과하였습니다.");
	}
});




// AJAX 내 남은 seat 수를 표현해주는 <tag>
function showSeatCount(data) {

	$("#remain-seat").empty();

	$.each(data, function(index, aa) {
		const tr = $("<tr></tr>");
		const td1 = $("<td class='col-lg-5'></td>").text(aa.remainingSeat);
		const td2 = $("<td class='col-lg-5'></td>").text(aa.remainingSeatCount);
		tr.append(td1, td2);
		$("#remain-seat").append(tr);
	});
}


function titleShow(data) {
	$("#concert-title").empty();

	const bb = data[0];
	const ul = $("<ul class='list-unstyled row offset-xs-1 concert-list'></ul>");
	const li1 = $("<li class='col-lg-6'></li>");
	const li2 = $("<li class='col-lg-4'></li>");
	const li3 = $("<li class='col-lg-6 mt-lg-5'></li>");
	const li4 = $("<li class='col-lg-4 mt-lg-5'></li>");
	const h41 = $("<h4></h4>").html("<콘서트 장소>");
	const h42 = $("<h4></h4>").html("<관람 시간>");
	const h43 = $("<h4></h4>").html("<콘서트 날짜>");
	const h44 = $("<h4></h4>").html("<관람 등급>");

	const p1 = $("<p class='concert-site offset-lg-1' id='concert-site'></p>").text(bb.concertPlace);
	const p2 = $("<p class='concert-runtime offset-lg-2' id='concert-runtime'></p>").text(bb.concertRuntime);
	const p3 = $("<p class='concert-date offset-lg-1' id='concert-date'</p>").text(bb.concertDate);
	const p4 = $("<p class='concert-grade offset-lg-2' id='concert-grade'</p>").text(bb.concertRestrictedAge);

	li1.append(h41, p1);
	li2.append(h42, p2);
	li3.append(h43, p3);
	li4.append(h44, p4);

	ul.append(li1, li2, li3, li4);
	$("#concert-title").append(ul);

}



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
//    renderCalendar();
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
	
	for (const concertDate of concertDataList) {
		const viewMonth = new Date().getMonth() + 1;
		const viewYear = new Date().getFullYear();
		const day = new Date().getDate();
		
		if (Number(concertDate.concertMonth) === viewMonth && Number(concertDate.concertYear) === viewYear) {

			const concertElements = document.querySelectorAll(`.this`);
			const concertArray = Array.from(concertElements);


			const dateIndex = Number(concertDate.concertDay);
			const dateElement = concertArray[dateIndex];

			if (dateElement) {
				dateElement.classList.add(`concert`);
				dateElement.closest('.date').classList.add('selected');
			}
			if (day >= Number(concertDate.concertDay)) {
				dateElement.classList.remove(`concert`);
				dateElement.closest('.date').classList.remove('selected');
			}
		}
	}
}


//  날짜 클릭 데이터 배열 저장 (지정날짜 외 클릭 불가)
function generateClickableDates(concertDataList) {
	const clickableDates = {};
	const viewMonth = new Date().getMonth() + 1;
	const viewYear = new Date().getFullYear();
	for (const concertDate of concertDataList) {

		if (Number(concertDate.concertMonth) === viewMonth && Number(concertDate.concertYear) === viewYear) {
			clickableDates[Number(concertDate.concertDay)] = true;
		}
	}
	return clickableDates;

}
//  날짜 클릭 시 색상 구분
function concertDateColor() {
	const choiceDates = document.querySelectorAll('.selected');
	const today = new Date();
	const day = today.getDate();

	choiceDates.forEach(choiceDate => {
		choiceDate.addEventListener('click', (event) => {
			let choiceDateDay = choiceDate.textContent;	//	예매 불가능한 날짜 선택 불가능하게 만들기
			if (day < choiceDateDay) {
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



let date = new Date();
const concertSite = document.getElementById('concert-site');
const concertRuntime = document.getElementById('concert-runtime');
const concertDate = document.getElementById('concert-date');
const concertGrade = document.getElementById('concert-grade');
const sendeDate = document.querySelectorAll('.selected');
let selectedDate = null;

const concertDates = [
    {id:1, date: 9, month: 9 , year: 2023, time : "20:30:00"},
    {id:2, date: 10, month: 9 , year: 2023, time : "18:30:00"},
    {id:3, date: 11, month: 9 , year: 2023, time : "21:30:00"}
];

const viewYear = date.getFullYear();
const viewMonth = date.getMonth();


const clickableDates = generateClickableDates(concertDates);

function renderCalendar () {


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
    if (PLDay !== 6){
        for (let i = 0; i< PLDay + 1; i++){
            prevDates.unshift(PLDate - i);
        }
    }

    //  nextDates 계산
    for (let i  = 1; i < 7 - TLDay; i++){
        nextDates.push(i);
    }

    //  Dates 합치기
    const dates = prevDates.concat(thisDates, nextDates);
    
    //  Dates 정리
    const firstDateIndex = dates.indexOf(1); // 이번달의 첫 번째 날의 인덱스
    const lastDateIndex = dates.lastIndexOf(TLDate);    //  이번달의 마지막 날의 인덱스
    
    dates.forEach((date,i) =>{
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
     const [clickedDay] = clickedDate.split('\n');
     const selectedDate = concertDates.find(date => date.date === Number(clickedDay));
     console.log(selectedDate.id);
     console.log(typeof(selectedDate.id));
     if (selectedDate) {
         const formattedDay = String(clickedDay).padStart(2, '0'); // 두 자리로 포맷팅
         concertSite.innerText = `• 서울시 강서구 양천로 125 서울문화예관 B1F`;
         concertDate.innerHTML = `• ${selectedDate.year}-${String(selectedDate.month).padStart(2, '0')}-${formattedDay} P.M ${selectedDate.time}`;
         concertGrade.innerText = `• 18등급`;
 		 concertRuntime.innerText = `• 180분`;
     }
     var concertId = selectedDate.id;
     var remainingVipSeat = $("#remaining-VIP").val();
     var remainingRSeat = $("#remaining-R").val();
     var remainingSSeat = $("#remaining-S").val();
     if(concertId.trim()==''|| remainingVipSeat.trim() =='' || remainingRSeat.trim()=='' || remainingSSeat.trim()==''){
         return;}
     //서버로 보낼 데이터 준비 : 파라미터로 만들기 . json 으로 만들기
     var sendData = {"concertId": concertId, "remaining-VIP":remainingVipSeat, "remainingR":remainingRSeat, "remainingS":remainingSSeat};
     $.ajax({
         url:'ticket/home/calendar?id='
         , method : 'GET'
         , data: JSON.stringify(sendData)
         ,contentType : 'application/json; charset=UTF-8'
         ,dataType : 'json'
         , success :function(resp){
             alert( JSON.stringify(sendData))
         }
     })


});

document.querySelector('.dates').addEventListener('click', (event) => {
    const clickedDate = event.target.innerText;
    const [clickedDay] = clickedDate.split('\n');
    const selectedDate = concertDates.find(date => date.date === Number(clickedDay));
    if (selectedDate) {
        const formattedDay = String(clickedDay).padStart(2, '0'); // 두 자리로 포맷팅

       

    }
});




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
 function todayCheck(){
    const today = new Date();
    if (viewMonth === today.getMonth() && viewYear === today.getFullYear()){
        for (let date of document.querySelectorAll('.this')) {
            if (+date.innerText === today.getDate()){
                date.setAttribute('id','today');
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
        if(concertDate.month === viewMonth+1 && concertDate.year === viewYear){
            clickableDates[concertDate.date] = true;
        }
    }
    return clickableDates;

}
//  날짜 클릭 시 색상 구분
function concertDateColor(){
    const choiceDates = document.querySelectorAll('.selected');
    choiceDates.forEach(choiceDate => {
        choiceDate.addEventListener('click', (event) => {
            choiceDates.forEach(element => element.classList.remove('choice'));
            let existngChoice = document.querySelector('.choice');
    
            if(existngChoice) {
                existngChoice.classList.remove('choice');
            }
            event.target.classList.add('choice');
            const subSpan = event.target.closest('.selected');
            if (subSpan) {
                subSpan.classList.add('choice');
            }
        });
    });
}
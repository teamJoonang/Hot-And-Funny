const container = document.querySelector('.container');
const seats = document.querySelectorAll('.row .seat');
const count = document.getElementById('count');
const closeWindow = document.querySelector('.close-window');
const allClear = document.querySelector('.all-clear');
const seatChoiceTable = document.querySelector('.seat-choice-table');
const nextPage = document.getElementById('next-page');


//로그번호
let logIndex = 0;

function updateSelectedCount() {
    const selectedSeats = document.querySelectorAll('.row .selected');
    const selectedSeatCount = +selectedSeats.length;
  
    const selectedSeatsIndex = [...selectedSeats].map((seat) => [...seats].indexOf(seat));
  
    localStorage.setItem('selectedSeats', JSON.stringify(selectedSeatsIndex));
  
    if(selectedSeatCount > 4) {
        alert("예매는 최대 4장까지만 가능합니다.")
        return;
    }
    count.textContent = selectedSeatCount;
}

//log 내용 create 메소드
function createLog(seatIndex, grade) {
    logIndex++;

    let tdElement1 = document.querySelector(`.seat-choice-table tr:nth-child(${logIndex}) td:nth-child(1)`);
    let tdElement2 = document.querySelector(`.seat-choice-table tr:nth-child(${logIndex}) td:nth-child(2)`);
    let tdElement3 = document.querySelector(`.seat-choice-table tr:nth-child(${logIndex}) td:nth-child(3)`);
    let tdElement4 = document.querySelector(`.seat-choice-table tr:nth-child(${logIndex}) td:nth-child(4)`);

    let addP = document.createElement('p');
    tdElement1.appendChild(addP);

    function gradeColorCheck() {
        if(grade=='VIP') {
            addP.style.backgroundColor = 'yellow';
        } else if(grade=='R') {
            addP.style.backgroundColor = 'green';
        } else if(grade=='S') {
            addP.style.backgroundColor = 'purple';
        } else {
            alert("p태그에서 뭔가가 잘못됬어요!");
        }
    }
    gradeColorCheck();

    tdElement2.textContent = grade;
    tdElement3.textContent = seatIndex;
    tdElement4.textContent = '취소';
}

//좌석등급 확인용 메소드
function gradeCheck(event) {
    if(event.target.classList.contains('seat-vip')) {
        return 'VIP';
    } else if (event.target.classList.contains('seat-r')) {
        return 'R' ;
    } else if (event.target.classList.contains('seat-s')) {
        return 'S' ;
    } else {
        alert("무언가 문제가 생겼어요!");
        return;
    }
}
function deleteLog(seatIndex) {
    let repeatConuter = 0;
    for(let i = 1; i <= logIndex; i++) {
        let choicedLogIndex = document.querySelector(`.seat-choice-table tr:nth-child(${i}) td:nth-child(3)`);

        let choicedLogIndexNum = Number(choicedLogIndex.textContent);
        repeatConuter++;
        if(seatIndex==choicedLogIndexNum) {
            let TargetTr = document.querySelector(`.seat-choice-table tr:nth-child(${repeatConuter})`);
            TargetTr.remove();
            createTr();

        } else {
            continue;
        }

        //---------------------테스트코드-----------------------
        // console.log(choicedLogIndex);
        // console.log(seatIndex);

        // console.log(typeof(seatIndex==choicedLogIndex));
        // if(seatIndex==choicedLogIndex) {
        //     console.log('true');
        // } else {
        //     console.log('f');
        // }
        //-----------------------------------------------------
    }
    logIndex--;
}
//<tbody> 를 안만들었는데 개발자도구 확인결과 다른곳에 생성됨 확인으로 바디 추가 및 클래스 추가함
//seat-choice-table 빈 tr 생성 메소드
function createTr() {
    const seatChoiceTable = document.querySelector('.seat-choice-table .choice-body');
    const newTr = document.createElement('tr');
    for (let i = 0; i < 4; i++) {
        const newTd = document.createElement('td');
        newTr.appendChild(newTd);
    }
    seatChoiceTable.appendChild(newTr);
}


updateSelectedCount();




//남은 좌석수 계산 메소드
function remainSeatCounter() {
    const classVIP = 'seat-vip';
    const classR = 'seat-r';
    const classS = 'seat-s';

    let countVIP = 0;
    let countR = 0;
    let countS = 0;

    seats.forEach(seat => {
        if(seat.classList.contains(classVIP) && !seat.classList.contains('occupied')) {
            countVIP++;
        } else if (seat.classList.contains(classR) && !seat.classList.contains('occupied')) {
            countR++;
        } else if (seat.classList.contains(classS) && !seat.classList.contains('occupied')) {
            countS++;
        }
    });
    const remainVIP = document.getElementById('vip-seat-remain');
    const remainR = document.getElementById('r-seat-remain');
    const remainS = document.getElementById('s-seat-remain');
    remainVIP.textContent = countVIP;
    remainR.textContent = countR;
    remainS.textContent = countS;
}
remainSeatCounter();

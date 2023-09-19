const container = document.querySelector('.container');
const seats = document.querySelectorAll('.row .seat');
const count = document.getElementById('count');
const closeWindow = document.querySelector('.close-window');
const allClear = document.querySelector('.all-clear');
const seatChoiceTable = document.querySelector('.seat-choice-table');
const nextPage = document.getElementById('next-page');

//로그번호
let logIndex = 0;

// 기 예약 좌석 비활성화 메소드
function remainSeatUpdate() {
    //서버에서 온 데이터 json 화 및 seatIndex 추출 --> 배열
    const remainSeatData = JSON.parse(document.getElementById('server-data').getAttribute('data-seat-list'));
    let occupiedSeatArray = remainSeatData.map(function(item) {return item.seatIndex;});
    seats.forEach(function (seat, index) {
        occupiedSeatArray.forEach(function (occupiedIndex) {
            if(occupiedIndex == index) {
                seat.classList.add('occupied');
            }
        }) ;
    });
    remainSeatData.forEach(function(seat) {
        if (seat.occupied) {
            let seatIndex = seat.seatIndex;
            let divElements = document.querySelectorAll('.seat'); // .seat 클래스를 가진 모든 하위 div 요소 선택
            let targetDiv = divElements[seatIndex]; // seatIndex에 해당하는 div 선택
            targetDiv.classList.add('occupied'); // 선택한 div에 occupied 클래스 추가
        }
    });
}
remainSeatUpdate();

function updateSelectedCount() {
    const selectedSeats = document.querySelectorAll('.row .selected');
    const selectedSeatCount = +selectedSeats.length;
  
    // const selectedSeatsIndex = [...selectedSeats].map((seat) => [...seats].indexOf(seat));
  
    // localStorage.setItem('selectedSeats', JSON.stringify(selectedSeatsIndex));
  
    if(selectedSeatCount > 4) {
        alert("예매는 최대 4장까지만 가능합니다.")
        return;
    }
    count.textContent = selectedSeatCount;
    btnAnimation();
}

//log 내용 create 메소드
function createLog(serverData, seatIndex, grade) {
    logIndex++;

    let tdElement1 = document.querySelector(`.seat-choice-table tr:nth-child(${logIndex}) td:nth-child(1)`);
    let tdElement2 = document.querySelector(`.seat-choice-table tr:nth-child(${logIndex}) td:nth-child(2)`);
    let tdElement3 = document.querySelector(`.seat-choice-table tr:nth-child(${logIndex}) td:nth-child(3)`);
    let tdElement4 = document.querySelector(`.seat-choice-table tr:nth-child(${logIndex}) td:nth-child(4)`);
    let tdElement5 = document.querySelector(`.seat-choice-table tr:nth-child(${logIndex}) td:nth-child(5)`);

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
    tdElement3.textContent = serverData;
    tdElement4.textContent = seatIndex;
    tdElement5.textContent = '취소';
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
        let choicedLogIndex = document.querySelector(`.seat-choice-table tr:nth-child(${i}) td:nth-child(4)`);

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
    btnAnimation();
}
//<tbody> 를 안만들었는데 개발자도구 확인결과 다른곳에 생성됨 확인으로 바디 추가 및 클래스 추가함
//seat-choice-table 빈 tr 생성 메소드
function createTr() {
    const seatChoiceTable = document.querySelector('.seat-choice-table .choice-body');
    const newTr = document.createElement('tr');
    for (let i = 0; i < 5; i++) {
        const newTd = document.createElement('td');
        newTr.appendChild(newTd);
    }
    seatChoiceTable.appendChild(newTr);
}
//좌석클릭 이벤트
container.addEventListener('click', (event) => {
    if (event.target.classList.contains('seat') && !event.target.classList.contains('occupied')) {
        
        let selectedSeats = document.querySelectorAll('.row .selected');
        let selectedSeatCount = +selectedSeats.length;

        let clickedSeatIndex = [...seats].indexOf(event.target);  // 현재 클릭한 좌석 인덱스

        // 실제 좌석 번호 매핑용 ajax
        $.ajax({
            url: concertDate + '/' + clickedSeatIndex,
            method: 'GET',
            dataType: 'text',
            success: function (data) {

                let clickedSeatGrade = gradeCheck(event);  // 현재 클릭한 좌석 등급 확인

                // 4장 선택 후 취소시 예외사항 처리
                if (event.target.classList.contains('selected')) {
                    event.target.classList.toggle('selected');
                    deleteLog(clickedSeatIndex);
                    updateSelectedCount();
                    return;
                } else if (selectedSeatCount > 3) {
                    alert("예매는 최대 4장까지만 가능합니다.");
                    return;
                }

                event.target.classList.toggle('selected');
                createLog(data, clickedSeatIndex, clickedSeatGrade);
                updateSelectedCount();
                btnAnimation();
            },
            error: function (error) {
                console.error('ajax 오류:', error);
            }
        });

    }
});

updateSelectedCount();

//뒤로가기 버튼
closeWindow.addEventListener('click', (event) => {
    window.close();
});
//좌석 초기화 버튼
allClear.addEventListener('click', (event) => {
    //log 초기화
    for(let i = 4; i >= 1; i--) {
        let TargetTr = document.querySelector(`.seat-choice-table tr:nth-child(${i})`);
        TargetTr.remove();
    }
    createTr();
    createTr();
    createTr();
    createTr();
    //log인덱스 초기화
    logIndex = 0;
    count.textContent = 0;
    //좌석 초기화
    seats.forEach(seat => {
        if(seat.classList.contains('selected')) {
            seat.classList.toggle('selected');
        }
    });
    // if (seats.classList.contains('selected') && !seats.classList.contains('occupied')) {
    //     seats.classList.toggle('seat');
    // }
    btnAnimation();
});
//티켓 취소 버튼 (부모에 리스너 주기방식)
seatChoiceTable.addEventListener('click', (event) => {
    const clickedButton = event.target;
    if (!clickedButton.matches('td:last-child')) {
        return; // td의 마지막 자식이 아니라면 무시
    }

    const clickedRow = clickedButton.closest('tr'); // 현재 버튼이 속한 tr 요소
    const rowIndex = [...clickedRow.parentElement.children].indexOf(clickedRow) + 1; // 행의 인덱스

    const seatObj = document.querySelector(`.seat-choice-table tr:nth-child(${rowIndex}) td:nth-child(4)`)
    const seatNum = Number(seatObj.textContent);

    if (clickedButton.textContent == '취소') {
        let TargetTr = document.querySelector(`.seat-choice-table tr:nth-child(${rowIndex})`);
        TargetTr.remove();
        createTr();

        let clickedSeatElement = seats[seatNum];
        clickedSeatElement.classList.toggle('selected');
        logIndex--;
        count.textContent = logIndex;
        btnAnimation();
    }
});
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
//다음페이지로 데이터 전송
nextPage.addEventListener("click", () => {
    let ticketArray = [];
    for (let i = 1; i <= logIndex; i++) {
        const grade = document.querySelector(`.seat-choice-table tr:nth-child(${i}) td:nth-child(2)`).textContent;
        const seatNum = document.querySelector(`.seat-choice-table tr:nth-child(${i}) td:nth-child(3)`).textContent;
        const seatIndex = document.querySelector(`.seat-choice-table tr:nth-child(${i}) td:nth-child(4)`).textContent;
        const ticket = { grade: grade, seatNum: seatNum, seatIndex: seatIndex };
        ticketArray.push(ticket);
    }

    const name = document.getElementById('concert-name').textContent;
    const place = document.getElementById('concert-place').textContent;
    const date = document.getElementById('concert-date').textContent;
    const time = document.getElementById('concert-time').textContent;
    // 추가 데이터 콘서트 아이디
    const concertId = document.getElementById('concert-id').getAttribute('data-concert-id');

    let concert = {
        "concertId": concertId,
        "concertName": name,
        "concertPlace": place,
        "concertDate": date,
        "concertTime": time
    };
    let priceInfo = priceInfoCheck();


    // ticketArray를 세션에 저장
    sessionStorage.setItem("concertInfo", JSON.stringify(concert));
    sessionStorage.setItem("priceInfo", JSON.stringify(priceInfo));
    sessionStorage.setItem("ticketArray", JSON.stringify(ticketArray));


    // 다음 페이지로 이동
    if(!ticketArray.length) {
        alert("좌석을 선택해 주세요.");
    } else {
        // 절대경로
        window.location.href = "/ticket/payment/check";
    }

});

//조건부 애니메이션 적용
function btnAnimation() {
    const button = document.getElementById('next-page');
    const checkTicket = count.textContent;
    if(parseInt(checkTicket) == '0') {
        button.classList.remove('blinking-button'); 
    } else {
        button.classList.add('blinking-button');
    }
}

//등급별 가격 추출 (뷰기준 - 향후 여기서 db 한번만 연결)
function priceInfoCheck() {
    let priceInfo = {};

// 각 테이블 행을 순회하면서 정보 추출
    let tableRows = document.querySelectorAll(".seat-remain-table tr");

    tableRows.forEach(function(row) {
        let cells = row.querySelectorAll("td");

        if (cells.length === 4) {
            let seatType = cells[1].textContent.trim(); // 좌석 유형 (VIP, R, S 등)
            let price = cells[2].querySelector("span").textContent.trim(); // 가격
            // let remainSeats = cells[3].querySelector("span").textContent.trim(); // 남은 좌석 수

            // 추출한 정보를 JSON 객체에 저장
            priceInfo[seatType] = {
                "price": price  //,
                // "remainSeats": remainSeats
            };
        }
    });
    return priceInfo;
}
// 숫자 포메팅 정규식
function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
// 임시 숫자 포메팅 적용 위한 메솟
function temp() {
    let tableRows = document.querySelectorAll(".seat-remain-table tr");

    tableRows.forEach(function(row) {
        let cells = row.querySelectorAll("td");
        let priceSpan = cells[2].querySelector("span");
        let priceText = priceSpan.textContent;
        let formattedPrice = formatNumberWithCommas(priceText);

        // span 태그 내의 텍스트를 업데이트하여 콤마가 추가된 값으로 변경
        priceSpan.textContent = formattedPrice;
    });
}
temp();
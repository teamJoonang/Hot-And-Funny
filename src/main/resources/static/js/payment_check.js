const prePage = document.querySelector('.pre-view-btn');
const nextViewBtn = document.querySelector('.next-view-btn');
const storedData = sessionStorage.getItem("ticketArray");
const ticketArray = JSON.parse(storedData);
const tablePosition = document.querySelector('.left-upper');

const vipNum = countingTicket("VIP");
const rNum = countingTicket("R");
const sNum = countingTicket("S");

//이전 클라이언트 세션 데이터
const concertInfo = JSON.parse(sessionStorage.getItem("concertInfo"));
const priceInfo = JSON.parse(sessionStorage.getItem("priceInfo"));

//티켓 가격정보
const VIPPrice = parseInt(deleteCommas(priceInfo["VIP"].price));
const RPrice = parseInt(deleteCommas(priceInfo["R"].price));
const SPrice = parseInt(deleteCommas(priceInfo["S"].price));
const discountPercent = 0.7;
let charge = 2000;

//드롭박스용 ajax 전용 전역변수
let totalPrice = 0;
let totalCharge = 0;
let countChoiceTicket = 0;
//티켓 생성용
let ticketInfoArray = [];

ticketView();

if(vipNum != 0) {
    tableCreate("VIP", vipNum);
}
if(rNum != 0) {
    tableCreate("R" , rNum);
}
if(sNum != 0) {
    tableCreate("S" , sNum);
}
discountPriceSet();
//드롭박스 선택에 따른 동작 로직
dropBoxChoice();
concertInfoUpdate();

//선택된 티켓 정보 업데이트 메소드
function ticketView() {
    //권장되지 않는 동적 변수 선언
    const tGrades = [document.getElementById('t1-grade'), document.getElementById('t2-grade'), document.getElementById('t3-grade'), document.getElementById('t4-grade')];
    const tNums = [document.getElementById('t1-num'), document.getElementById('t2-num'), document.getElementById('t3-num'), document.getElementById('t4-num')];

    //베이스 최종 티켓 정보 함께 업데이트
    for(let i = 0; i < ticketArray.length; i++) {
        if (ticketArray && ticketArray.length > i) {
            const ticket = ticketArray[i];
            tGrades[i].textContent = ticket.grade;
            tNums[i].textContent = ticket.seatNum;
            const ticketView = document.querySelector(`.ticket-view p:nth-child(${i+1})`);
            ticketView.textContent =tGrades[i].textContent + '석 - ' + tNums[i].textContent;
        }
        //티켓 정보 1차 업데이트 (seatGrade 는 향후 삭제처리)
        let ticketInfo = {
            "seatGrade": ticketArray[i].grade,
            "seatNumber": ticketArray[i].seatNum,
            "concertId": concertInfo.concertId,
            "userId": userId,
            "discountYn": null,
            "seatPrice": setPrice(ticketArray[i].grade)
        };
        ticketInfoArray.push(ticketInfo);
    }
}
//티켓 table 생성 메소드
function tableCreate (grade, gradeNum) {
    const table = document.createElement("table");
    table.classList.add("ticket-discount-table");
    //thead 생성
    const thead = document.createElement("thead");
    const headerRow = document.createElement("tr");
    headerRow.innerHTML = `<th><span class="grade-input">grade</span> 석</th><th colspan="4" class="left-align">| 좌석 <span class="grade-su">00</span>매를 선택하셨습니다</th>`;
    thead.appendChild(headerRow);

    //tbody 생성
    const tbody = document.createElement("tbody");
    const row1 = document.createElement("tr");
    row1.innerHTML = `
    <tr height="30">
        <td colspan="2" class="w80">기본가</td>
        <td class="w450">일반</td>
        <td><span class="t-price"></span>원</td>
        <td>
            <select class="input-opt">
                <option value="0">0 매</option>
            </select>
        </td>
    </tr>
    `
    const row2 = document.createElement("tr");
    row2.innerHTML = `
    <tr>
        <td colspan="2" rowspan="2" class="w80">기본할인</td>
        <td class="w450">장애인 본인 및 동반자 1인 30%</td>
        <td><span class="t-price"></span>원</td>
        <td>
            <select class="input-opt">
                <option value=0>0 매</option>
            </select>
        </td>
    </tr>
    `
    const row3 = document.createElement("tr");
    row3.innerHTML = `
    <tr>
        <td class="w450">국가유공상이자 본인 30%</td>
        <td><span class="t-price"></span>원</td>
        <td>
            <select class="input-opt">
                <option value="0">0 매</option>
            </select>
        </td>
    </tr>
    `;
    // 테이블 적용문
    tbody.appendChild(row1);
    tbody.appendChild(row2);
    tbody.appendChild(row3);
    table.appendChild(thead);
    table.appendChild(tbody);
    tablePosition.appendChild(table);
    // 동적내용 적용코드
    // document. 으로 할때 못찾아서 table 로 바꿈
    let gradeInput = table.querySelector('.grade-input');
    const gradeSu = table.querySelector('.grade-su');

    gradeInput.textContent = grade;

    gradeSu.textContent = gradeNum;

    function createOption(value, text) {
        const option = document.createElement('option');
        option.value = value;
        option.textContent = text;
        return option;
    }
    const inputOpts = table.querySelectorAll('.input-opt');
    for(let i = 1; i <= gradeNum; i++) {
        inputOpts.forEach(inputOpt => {
            inputOpt.appendChild(createOption(i, i + ' 매'));
        });
    }
}
//sub 메소드 (등급별 티켓 수)
function countingTicket(grade) {
    let countingNum = 0;
    ticketArray.forEach(ticket => {
        if(ticket.grade == grade) {
            countingNum++;
        }
    });
    return countingNum;
}
//할인 적용 가격설정 메소드
function discountPriceSet() {
    let tPriceAll = document.querySelectorAll('.t-price');

    tPriceAll.forEach((tPrice, index) => {
        let grade = tPrice.closest('table').querySelector('.grade-input');
        if (index === 0 || index === 3 || index === 6) {
            tPrice.textContent = formatNumberWithCommas(setPrice(grade.textContent));
        } else {
            tPrice.textContent = formatNumberWithCommas(setPrice(grade.textContent) * discountPercent);
        }
    });
}
//등급표별 가격설정 메소드
function setPrice(grade) {
    if(grade == "VIP") {
        return VIPPrice;
    } else if (grade == "R") {
        return RPrice;
    } else if (grade == "S") {
        return SPrice;
    } else {
        alert("가격 셋팅에 문제가 생겼어요");
    }
}

//드롭박스 선택에 따른 동작 메소드
function dropBoxChoice() {
    const dropDownBox = document.querySelectorAll('.input-opt');
    dropDownBox.forEach((dropBox, index) => {
        let previousOptionValue = parseInt(dropBox.value); // 이전 선택값을 저장할 변수
        dropBox.addEventListener('change', function () {
            const selectedOptionValue = parseInt(dropBox.value);
            const table = dropBox.closest('table');
            const limitCond = parseInt(table.querySelector('.grade-su').textContent);
            //ajax 용 변수추가
            const seatGrade = table.querySelector('.grade-input').textContent;

            // 현재 선택 수를 가져오기
            const currentSelectedValue = Array.from(table.querySelectorAll('.input-opt'))
                .map(opt => parseInt(opt.value))
                .reduce((acc, val) => acc + val, 0);
            //허용 범위 내에서 변경될 때만 이전 선택값 업데이트
            if (currentSelectedValue <= limitCond) {
                let diff = selectedOptionValue- previousOptionValue;
                let sendServer = {};

                    if(index == 0 || index == 3 || index == 6) {
                        sendServer = {
                            "grade": seatGrade,
                            "diff": diff,
                            "disCountYN": false
                        }
                        dropBoxAjax(sendServer);
                        // 할인 티켓 배정 로직
                        let count = 0;

                        if(diff > 0) {
                            ticketInfoArray.forEach(function (ticket) {
                                if(ticket.seatGrade == sendServer.grade && count < diff) {
                                    // 반복 변경시 null 이 앞 객체에 배치되 예외 처리
                                    if(ticket.discountYn == false) {
                                        return;
                                    } else if (ticket.discountYn == true) {
                                        return;
                                    }
                                    ticket.discountYn = false;
                                    // console.log("양수 작동");

                                    count++;
                                }
                            });
                        } else if (diff < 0) {
                            ticketInfoArray.forEach(function (ticket) {
                                if(ticket.seatGrade == sendServer.grade && count < -diff) {
                                    // 반복 변경시 null 이 앞 객체에 배치되 예외 처리
                                    if(ticket.discountYn == null) {
                                        return;
                                    } else if (ticket.discountYn == true) {
                                        return;
                                    }
                                    ticket.discountYn = null;
                                    // console.log("음수 작동");
                                    count++;
                                }
                            });
                        }
                        // console.log("no discount counter : " + count);
                        // console.log(ticketInfoArray);
                    } else {
                        sendServer = {
                            "grade": seatGrade,
                            "diff": diff,
                            "disCountYN": true
                        }
                        dropBoxAjax(sendServer);

                        // 할인 티켓 배정 로직
                        let count = 0;
                        console.log(diff);
                        if(diff > 0) {
                            ticketInfoArray.forEach(function (ticket) {
                                if(ticket.seatGrade == sendServer.grade && count < diff) {
                                    // 반복 변경시 null 이 앞 객체에 배치되 예외 처리
                                    if(ticket.discountYn == false) {
                                        return;
                                    } else if (ticket.discountYn == true) {
                                        return;
                                    }
                                    ticket.discountYn = true;
                                    // console.log("양수 작동");

                                    count++;
                                }
                            });
                        } else if (diff < 0) {
                            ticketInfoArray.forEach(function (ticket) {
                                if(ticket.seatGrade == sendServer.grade && count < -diff) {
                                    // 반복 변경시 null 이 앞 객체에 배치되 예외 처리
                                    if(ticket.discountYn == null) {
                                        return;
                                    } else if (ticket.discountYn == false) {
                                        return;
                                    }
                                    ticket.discountYn = null;
                                    // console.log("음수 작동");
                                    count++;
                                }
                            });
                        }
                        // console.log("no discount counter : " + count);
                        // console.log(ticketInfoArray);
                    }
                previousOptionValue = selectedOptionValue;

                colorApply(dropBox);

            } else {
                alert(`해당 등급의 티켓을 ${limitCond}장 이상 선택할 수 없습니다.`);
                // 이전 선택값으로 돌아가도록 드롭박스 값을 변경
                dropBox.value = previousOptionValue;
            }
        });
    });
}
/**
// function dropBoxChoice() {
//     const dropDownBox = document.querySelectorAll('.input-opt');
//     const totalSelectedNums = [0, 0, 0]; // 표 별 최대 선택수 관리

//     dropDownBox.forEach((dropBox, index) => {
//         // 이전 선택 값을 data-selected 속성에 저장
//         dropBox.setAttribute('data-selected', 0);

//         dropBox.addEventListener('change', function () {
//             const selectedOptionValue = parseInt(dropBox.value);
//             const tableIndex = Math.floor(index / 3); // 표의 인덱스 계산

//             // 이전 선택 값과 현재 선택 값 차이 계산
//             const prevSelectedValue = parseInt(dropBox.getAttribute('data-selected'));
//             let diff = selectedOptionValue - prevSelectedValue;
//             console.log("이전숫자 : " + prevSelectedValue);
//             console.log("현재숫자 : " + selectedOptionValue);
//             console.log("차이 : " + diff);
//             console.log("실제 적용되는 티켓수 : " + (totalSelectedNums[tableIndex] + diff));
//             console.log("else 시 : " + (totalSelectedNums[tableIndex]));

//             if (totalSelectedNums[tableIndex] + diff <= maxCond(dropBox)) {
//                 totalSelectedNums[tableIndex] += diff;
//             } else {
//                 alert(`해당 등급의 티켓을 ${maxCond(dropBox)}장 이상 선택할 수 없습니다.`);
//                 dropBox.selectedIndex = 0;
//             }

//             // 현재 선택 값을 data-selected 속성에 업데이트
//             dropBox.setAttribute('data-selected', selectedOptionValue);

//             colorApply(dropBox);
//         });
//     });
// }
// 티켓 선택 최대값 산출 메소드 (드롭박스초이스 메소드 내부로 녹임)
// function maxCond(dropBox) {
//     const limitCond = parseInt(dropBox.closest('table').querySelector('.grade-su').textContent);
//     return limitCond;
// }
*/
// 드롭박스 조건부 색상변경
function colorApply(dropBox) {
    if(dropBox.value != 0) {
        dropBox.closest('tr').style.backgroundColor = 'lightblue';
    } else if (dropBox.value == 0) {
        dropBox.closest('tr').style.backgroundColor = 'white';
    }
}
// 콘서트 정보 뷰 업데이트
function concertInfoUpdate() {
    const concertInfoTitle = document.querySelectorAll(".concert-info-title p");
    concertInfoTitle.forEach((pTag, index) => {
        if(index == 0) {
            pTag.textContent = concertInfo.concertName;
        } else if (index == 2) {
            pTag.textContent = concertInfo.concertDate + concertInfo.concertTime;
        } else if (index == 3) {
            pTag.textContent = concertInfo.concertPlace;
        }
    });
    const myTicketTable = document.querySelectorAll(".my-ticket-table p");
    myTicketTable.forEach((pTag, index) => {
        if(index == 0) {
            pTag.textContent = formatDate(concertInfo.concertDate);
        } else if (index == 1) {
            pTag.textContent = concertInfo.concertTime;
        }
    });
}

//드롭박스 ajax 메소드
function dropBoxAjax(sendServer) {
    $.ajax({
        url: '/ticket/payment/check',
        method: 'POST',
        contentType: 'application/json', // 헤더 설정
        data: JSON.stringify(sendServer), // 전송
        dataType: 'json',
        cache: false, // 캐싱 비활성
        success: function(data) {
            totalPrice += data;
            totalCharge += charge * sendServer.diff;

            let htmlTotalPrice = document.getElementById('total-price');
            let htmlTotalCharge = document.getElementById('total-charge');
            let htmlTotalPriceReal = document.getElementById('total-price-real');

            htmlTotalPrice.textContent = formatNumberWithCommas(totalPrice);
            htmlTotalCharge.textContent = formatNumberWithCommas(totalCharge);
            htmlTotalPriceReal.textContent = formatNumberWithCommas(totalPrice + parseInt(totalCharge));
            countChoiceTicket += sendServer.diff;
            btnAnimation();
        },
        error: function(error) {
            console.error('에러:', error);
        }
    });
}

//최종정보 중 참조값 삭제 메소드
function deleteResult() {
    ticketInfoArray.forEach(function (ticket) {
        delete ticket.seatGrade;
    });
}

//이전페이지로 전환 이벤트
prePage.addEventListener("click", () => {
    window.location.href = "/ticket/seat/choice/" + concertInfo.concertDate;
});

nextViewBtn.addEventListener("click" , async() => {
    //티켓 전체 미선택 시 예외처리
    if(countChoiceTicket < (vipNum+rNum+sNum)) {
        alert("현재 선택하신 티켓은 총" + countChoiceTicket + "장 입니다.\n" +
            "선택하셔야 하는 티켓은 " + (vipNum+rNum+sNum) + "장 입니다.")
        return;
    }
    //최종 정보 중 불필요 정보 삭제
    // deleteResult();

    // 부트페이
    try {
        const response = await Bootpay.requestPayment({
            "application_id": "6501abe500be04001ed42b93",
            "price": 100,
            "order_name": "테스트결제",
            "order_id": "TEST_ORDER_ID",
            "pg": "nicepay",
            "method": "card",
            "tax_free": 0,
            "user": {
                "id": "회원아이디",
                "username": "회원이름",
                "phone": "01000000000",
                "email": "test@test.com"
            },
            "items": [
                {
                    "id": "item_id",
                    "name": "테스트아이템",
                    "qty": 1,
                    "price": 100
                }
            ],
            "extra": {
                "open_type": "iframe",
                "card_quota": "0,2,3",
                "escrow": false
            }
        })
        console.log('실결제 완료 ', response);
    } catch (error) {
        console.error('실결제 오류 : ' , error);
        alert("결제가 취소되었습니다");
        //return;
    }

    // 다음페이지 필요 데이터 전달
    nextPageData();

    $.ajax({
        url: '/ticket/payment/check/approval',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(ticketInfoArray),
        success: function (serverData) {
            alert("티켓이 성공적으로 발급 되었습니다!");
            window.location.href = "/ticket/approval";
        },
        error: function (error) {
            console.error('결제 ajax 에러 : ', error);
            alert("오류가 발생: " + error.responseText);
        }

    });
});
// 다음페이지 데이터 전달 메소드
function nextPageData() {
    let noDiscountPrice = 0;
    let discountPrice = 0;
    let totalCharge = 0;
    ticketInfoArray.forEach(function (ticket) {
        noDiscountPrice += ticket.seatPrice;
        totalCharge += charge;
        if(ticket.discountYn) {
            let typeChange = ticket.seatPrice * 0.7;
            let price = (typeChange/100) * 100;
            discountPrice += ticket.seatPrice - price;
        }
    });
    const approvalPrice = {
        "noDiscountPrice": noDiscountPrice,
        "discountPrice": discountPrice,
        "totalCharge": totalCharge
    }
    sessionStorage.setItem("approvalPrice", JSON.stringify(approvalPrice));
}
//조건부 애니메이션 적용
function btnAnimation() {
    if(parseInt(countChoiceTicket) == (vipNum+rNum+sNum)) {
        nextViewBtn.classList.add('blinking-button');
    } else {
        nextViewBtn.classList.remove('blinking-button');
    }
}

/**
 // 정수 부호 반환 메소드
 function getSign(number) {
 return number > 0 ? 1 : number < 0 ? -1 : 0;
 }
 */
// 날짜 포멧 함수
function formatDate(inputDate) {
    const options = { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' };
    const date = new Date(inputDate);
    return date.toLocaleDateString('ko-KR', options);
}
// 숫자 콤마 포멧팅
function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
// 콤마 제거 정규식
function deleteCommas(numberString) {
    return parseInt(numberString.replace(/,/g, ''), 10);
}


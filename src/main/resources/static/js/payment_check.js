const prePage = document.querySelector('.pre-view-btn');
const storedData = sessionStorage.getItem("ticketArray");
const ticketArray = JSON.parse(storedData);
const tablePosition = document.querySelector('.left-upper');

const vipNum = countingTicket("VIP");
const rNum = countingTicket("R");
const sNum = countingTicket("S");
//티켓 가격정보
let VIPPrice = 100000;
let RPrice = 80000;
let SPrice = 60000;
let discountPercent = 0.7;
let charge = 2000;

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

//선택된 티켓 정보 업데이트 메소드
function ticketView() {
    //권장되지 않는 동적 변수 선언
    const tGrades = [document.getElementById('t1-grade'), document.getElementById('t2-grade'), document.getElementById('t3-grade'), document.getElementById('t4-grade')];
    const tNums = [document.getElementById('t1-num'), document.getElementById('t2-num'), document.getElementById('t3-num'), document.getElementById('t4-num')];
    
    for(let i = 0; i < ticketArray.length; i++) {
        if (ticketArray && ticketArray.length > i) {
            const ticket = ticketArray[i];
            tGrades[i].textContent = ticket.grade;
            tNums[i].textContent = ticket.seatNum;
            const ticketView = document.querySelector(`.ticket-view p:nth-child(${i+1})`);
            ticketView.textContent =tGrades[i].textContent + '석 - ' + tNums[i].textContent;
        }
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
// 정규표현식 숫자 포멧팅
function formatNumberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

//드롭박스 선택에 따른 동작 메소드
// function dropBoxChoice2() {
//     const discountTables = document.querySelectorAll('.ticket-discount-table');
//     discountTables.forEach(table => {
//         const 
//     });
// }

function dropBoxChoice() {
    const dropDownBox = document.querySelectorAll('.input-opt');
    let totalSelectedNum = 0;
    dropDownBox.forEach((dropBox, index) => {
        dropBox.addEventListener('change', function () {
            const selectedOptionValue = parseInt(dropBox.value);
            
            // index를 기반으로 조건을 체크.
            if (index === 0 || index === 1 || index === 2) {
                if (totalSelectedNum + selectedOptionValue <= maxCond(dropBox)) {
                    totalSelectedNum += selectedOptionValue;
                    console.log(totalSelectedNum);
                } else {
                    alert(`해당 등급의 티켓을 ${maxCond(dropBox)}장 이상 선택할 수 없습니다.`);
                    dropBox.selectedIndex = 0;
                }
            } else if (index === 3 || index === 4 || index === 5) {
                if (totalSelectedNum + selectedOptionValue <= maxCond(dropBox)) {
                    totalSelectedNum += selectedOptionValue;
                    console.log(totalSelectedNum);
                } else {
                    alert(`해당 등급의 티켓을 ${maxCond(dropBox)}장 이상 선택할 수 없습니다.`);
                    dropBox.selectedIndex = 0;
                }
            } else if (index === 6 || index === 7 || index === 8) {
                if (totalSelectedNum + selectedOptionValue <= maxCond(dropBox)) {
                    totalSelectedNum += selectedOptionValue;
                    console.log(totalSelectedNum);
                } else {
                    alert(`해당 등급의 티켓을 ${maxCond(dropBox)}장 이상 선택할 수 없습니다.`);
                    dropBox.selectedIndex = 0;
                }
            } else {
                alert("드롭박스가 뭔가가 잘못되었어요");
            }
            colorApply(dropBox);
        });
    });
}
// 티켓 선택 최대값 산출 메소드
function maxCond(dropBox) {
    const limitCond = parseInt(dropBox.closest('table').querySelector('.grade-su').textContent);
    return limitCond;
}
// 드롭박스 조건부 색상변경
function colorApply(dropBox) {
    if(dropBox.value != 0) {
        dropBox.closest('tr').style.backgroundColor = 'lightblue';
    }
}

//이전페이지로 전환 이벤트
prePage.addEventListener("click", () => {
    window.location.href = "seat_choice.html";
});
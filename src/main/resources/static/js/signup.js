function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("extraAddress").value = extraAddr;

            } else {
                document.getElementById("extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("new_address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}


// 패스워드 검증식 - 대소문자, 숫자 , 특수문자 무조건 하나 포함, 최소 8자 최대 30자
const pwRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*#?&_-])[A-Za-z\d@$!%*#?&_-]{8,30}$/;
// // 이름 검증식 - 영어(대소문자), 한글 최소 1자~20자 , 숫자와 특수문자 제외.
const nameRegex = /^[A-Za-z가-힝]{1,20}[^\d$!%*#?&_-]$/;
// // 별명 - 언더바'_' 제외 특수문자 사용불가.
const nicknameRegex =  /^[가-힣ㄱ-ㅎa-zA-z0-9]{2,20}[^$!%*#?&,-]$/;
// // 연락처 - 숫자 11글자.
const telRegex = /\d{7,11}/;
// // 나이 - 숫자만 가능 , 최소1글자 ~ 3글자.
const ageRegex = /\d/;


// err 에러메시지
// let email_err = document.getElementById('email_err');
let pw_err = document.getElementById('pw_err');
let repw_err = document.getElementById('repw_err');
let name_err = document.getElementById('name_err');
let nickname_err = document.getElementById('nickname_err');
let tel_err = document.getElementById('tel_err');
let age_err = document.getElementById('age_err');

// 이메일 인증 체크 여부
let emailConfirmChk = false;
// 이메일 & 별명 중복체크 판별 변수
let idCheck = false;
let nicknameCheck = false;
// input 검증 판별 변수
let pwCk = false;
let repwCk = false;
let nameCk = false;
let nicknameCk = false;
let telCk = false;
let ageCk = false;
// input 최종 체크
let inputCheck = false;

document.getElementById('new_password').addEventListener('change' , function (){
    let password = this.value;
    if(pwRegex.test(password)){
        pw_err.style.display = 'none';
        pwCk = true;
    }
    else {
        pw_err.style.display = 'inline-block';
        pwCk = false;

    }
});

document.getElementById('new_re_password').addEventListener('keyup' , function () {
    let password = document.getElementById('new_password').value;
    let repeatPw = this.value;
    if(password === repeatPw){
        repw_err.style.display = 'none'
        repwCk = true;
    }
    else{
        repw_err.style.display = 'inline-block';
        repwCk = false;
    }
});

document.getElementById('new_name').addEventListener('change' , function (){
    let name = this.value;
    if(nameRegex.test(name)){
        name_err.style.display = 'none';
        nameCk = true;
    }
    else {
        name_err.style.display = 'inline-block';
        nameCk = false;
    }
});

document.getElementById('new_nickname').addEventListener('change' , function () {
    let nickname = this.value;
    if(nicknameRegex.test(nickname)){
        nickname_err.style.display = 'none';
        nicknameCk = true;
    }
    else {
        nickname_err.style.display = 'inline-block';
        nicknameCk = false;
    }
});

document.getElementById('new_tel').addEventListener('change' , function () {
    let tel = this.value;
    if(telRegex.test(tel)){
        tel_err.style.display = 'none';
        telCk = true;
    }
    else {
        tel_err.style.display = 'inline-block';
        telCk = false;
    }
});

document.getElementById('new_age').addEventListener('change' , function (){
    let age = this.value;
    if(ageRegex.test(age)){
        age_err.style.display = 'none';
        ageCk = true;
    }
    else {
        age_err.style.display = 'inline-block';
        ageCk = false;
    }
});



//***********************************************************************************************
// 이메일 인증번호
document.getElementById('checkEmail')
    .addEventListener('click' , function () {
        console.log("눌림");

        let inputemail = document.getElementById('new_email').value;

        let xhr = new XMLHttpRequest();
        xhr.open('POST', '/user/mailConfirm', true);
        xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    alert('해당 이메일로 인증번호 발송이 완료 되었습니다. \n 확인해주시길 바랍니다.');
                    chkEmailConfirm(xhr.responseText);
                } else {
                    // 오류가 날 경우...
                    alert('서버 통신 오류. 관리자에게 문의 후 기다려주세요. \n 관리자 이메일 : firetrap5319@gmail.com');

                }
            }
        };
        xhr.send(inputemail);
    });


function chkEmailConfirm(code){
    let emailCodeConfirmTxt = document.getElementById('emailCodeConfirmTxt');
    let emailCodeConfrim = document.getElementById('emailCodeconfirm');
    emailCodeConfrim.addEventListener('keyup' , function (){
        let inputCode = emailCodeConfrim.value;
        if(code !== inputCode){
            emailCodeConfirmTxt.innerHTML = "<span id='codeErrorCk'>인증번호가 잘못되었습니다.</span>";
            document.getElementById('codeErrorCk').style.color='#ea10ac';
        }
        else {
            emailCodeConfirmTxt.innerHTML = "<span id='codeErrorCk'>인증번호 확인되었습니다.</span>"
            document.getElementById('codeErrorCk').style.color='green';
            emailConfirmChk = true;
        }
    });
}


// eamil 칸을 건들면 중복체크 성공해도 false 변경
document.getElementById('new_email').addEventListener('change' , function(){
    idCheck = false;
});

// nickname 칸을 건들면 중복체크 성공해도 false 변경;
document.getElementById('new_nickname').addEventListener('change' , function(){
    nicknameCheck = false;
});

// idCheck 버튼이 눌리면 ajax 요청. (중복체크)
document.getElementById('idCheck').addEventListener('click' , function(){

    let loginId = document.getElementById('new_email').value;
    let data = { loginId : loginId };

    let xhr = new XMLHttpRequest();
    xhr.open('POST' , '/user/IdCheck' , true);
    xhr.setRequestHeader('Content-Type' , 'application/json; charset=UTF-8');

    xhr.onreadystatechange = function(){
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // ok 응답시
                alert('사용가능한 이메일 입니다.');
                // 체크 확인.
                idCheck = true;
            }
            else if(xhr.readyState === 500){
                alert('서버에러 , firetrap5319@gmail.com 으로 연락바랍니다.');
            }
            else{
                // bad request 응답시
                alert('이미 사용중인 이메일 입니다.');
                document.getElementById('new_email').focus();
            }
        }
    };
    xhr.send(JSON.stringify(data));
});

// nickNameCheck 버튼 눌리면 ajax 요청. (중복체크)
document.getElementById('nickNameCheck').addEventListener('click' , function(){

    let nickname = document.getElementById('new_nickname').value;
    let data = { nickname : nickname };

    let xhr = new XMLHttpRequest();
    xhr.open('POST','/user/nicknameCheck' , true);
    xhr.setRequestHeader('Content-Type' , 'application/json; charset=UTF-8');

    xhr.onreadystatechange = function(){
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // ok 응답시
                alert('사용가능한 별명 입니다.');
                // 체크 확인.
                nicknameCheck = true;
            }
            else{
                // bad request 응답시
                alert('이미 사용중인 별명 입니다.');
                document.getElementById('new_nickname').focus();
            }
        }
    };
    xhr.send(JSON.stringify(data));

});


// send버튼 클릭시 ajax 요청
document.getElementById('loginBtn').addEventListener('click', function() {

    inputCheck = pwCk === true && repwCk === true &&
        nameCk === true && nicknameCheck === true &&
        telCk === true && ageCk === true;

    // 모든 중복체크를 진행하여 true 상태일때, 가입 진행.
    if(idCheck === true && nicknameCheck === true && emailConfirmChk === true && inputCheck === true){

        // gender radio 버튼들 가져오기
        let maleGender = document.getElementById('new_gender_male');
        let femaleGender = document.getElementById('new_gender_female');
        // gender 값 담을 변수 생성.
        let selectedGender="";
        // db는 tinyint(1) true 넣어주면 1 들어가짐.
        // 남자 체크되어 있으면 변수에 true 여자체크면 false 담기.
        if(maleGender.checked){
            selectedGender = true;
        }
        else if(femaleGender.checked){
            selectedGender = false;
        }

        let detailAddress = ' 상세주소: ';
        detailAddress += document.getElementById('detailAddress').value;
        let new_addr = document.getElementById('new_address').value;

        let userData = {
            loginId: document.getElementById('new_email').value,
            password: document.getElementById('new_password').value,
            repeatPw: document.getElementById('new_re_password').value,
            name: document.getElementById('new_name').value,
            nickname: document.getElementById('new_nickname').value,
            tel: document.getElementById('new_tel').value,
            address: new_addr + detailAddress,
            age: document.getElementById('new_age').value,
            gender : selectedGender
        };

        let xhr = new XMLHttpRequest();
        xhr.open('POST', '/user/signup', true);
        xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');

        xhr.onreadystatechange = function() {
            let responseBody = xhr.responseText;
            if (xhr.readyState === 4) {
                if (xhr.status === 201) {
                    // 회원가입 성공 처리
                    window.location="/user/login"
                }
                else if (xhr.status === 400 && responseBody === '양식을 모두 채워주세요.') {
                    alert('양식을 모두 채워주세요.');
                }
                else if (responseBody === '비밀번호가 일치하지 않습니다.'){
                    alert('비밀번호가 일치하지 않습니다.');
                }
                else {
                    // 회원가입 실패 처리
                    alert('회원가입 실패');
                }
            }
        };

        xhr.send(JSON.stringify(userData));
    }
    else if(idCheck === false){
        alert('아이디 중복 체크를 진행해주세요.');
    }
    else if(nicknameCheck === false){
        alert('별명 중복체크를 진행해주세요.');
    }
    else {
        alert('양식을 확인해주세요.');
    }
});
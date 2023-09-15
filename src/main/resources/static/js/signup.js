// 이메일 인증번호
// document.getElementById('checkEmail')
//     .addEventListener('click' , function () {
//     console.log("눌림");
//
//     let memail = document.getElementById('memail').value;
//     let data = {email : memail};
//
//     let xhr = new XMLHttpRequest();
//     xhr.open('POST', '/user/mailConfirm', true);
//     xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
//
//     xhr.onreadystatechange = function () {
//         if (xhr.readyState === 4) {
//             if (xhr.status === 200) {
//
//
//             } else {
//                 // bad request 응답시
//                 alert('못보냄');
//
//             }
//        }
//         xhr.send(JSON.stringify(data));
//
//     }
// });
// let emconfirmchk = false;
//
// $("#checkEmail").click(function() {
//     console.log($("#memail").val());
//     $.ajax({
//         type : "POST",
//         url : "user/mailconfirm",
//         contentType: "application/json",
//         data : $("#memail").val(),
//         success : function(data){
//             alert("해당 이메일로 인증번호 발송이 완료되었습니다. \n 확인부탁드립니다.")
//             console.log("data : "+data);
//             chkEmailConfirm(data, $("#memailconfirm"), $("#memailconfirmTxt"));
//         }
//     })
// });
//
// // // 이메일 인증번호 체크 함수
// function chkEmailConfirm(data , $memailconfirm , $memailconfirmTxt){
//     $("#memailconfirm").on("keyup", function(){
//         if (data != $memailconfirm.val()) { //
//             emconfirmchk = false;
//             $memailconfirmTxt.html("<span id='emconfirmchk'>인증번호가 잘못되었습니다</span>")
//             $("#emconfirmchk").css({
//                 "color" : "#FA3E3E",
//                 "font-weight" : "bold",
//                 "font-size" : "10px"
//
//             })
//             //console.log("중복아이디");
//         }
//         else { // 아니면 중복아님
//             emconfirmchk = true;
//             $("#memailconfirmTxt").html("<span id='emconfirmchk'>인증번호 확인 완료</span>")
//             }
//     })
// }



//***********************************************************************************************
// 대쉬 제거 검증식
const dashRegex = /\-/g;
// 이메일 검증식 - 대소문자 , 숫자로 1자~30자 @ 소문자,숫자에 마지막은 .소문자
const emailRegex = /^[A-Za-z0-9_]{1,30}@([a-z0-9_]+\.)+[a-z]+$/g;
// 패스워드 검증식 - 대소문자, 숫자 , 특수문자 무조건 하나 포함, 최소 8자 최대 16자
const pwRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,30}$/g;
// 이름 검증식 - 영어(대소문자), 한글 최소 1자~10자 , 숫자와 특수문자 제외.
const nameRegex = /^[A-Za-z가-힝]{1,20}[^\d$!%*#?&]$/g;
// 별명 - 언더바'_' 제외 특수문자 사용불가.
const nicknameRegex =  /^[가-힣ㄱ-ㅎa-zA-z0-9]{2,20}[^$!%*#?&]$/g;
// 연락처 - 숫자 11글자.
const telRegex = /\d{11}/g;
// // 주소 - 한글 , 영어(대소문자) 가능 , 특수문자 사용불가. -는 가능.
// const addrRegex =
// 나이 - 숫자만 가능 , 최소1글자 ~ 3글자.
const ageRegex = /\d{1,3}/g;


// err 에러메시지
let email_err = document.getElementById('email_err');
let pw_err = document.getElementById('pw_err');
let repw_err = document.getElementById('repw_err');
let name_err = document.getElementById('name_err');
let nickname_err = document.getElementById('nickname_err');
let tel_err = document.getElementById('tel_err');
let addr_err = document.getElementById('addr_err');
let age_err = document.getElementById('age_err');

// 모든 input 값들
let loginId = document.getElementById('new_email').value
let password = document.getElementById('new_password').value
let repeatPw = document.getElementById('new_re_password').value
let name = document.getElementById('new_name').value
let nickname = document.getElementById('new_nickname').value
let tel = document.getElementById('new_tel').value
let address = document.getElementById('new_addr').value
let age = document.getElementById('new_age').value


// 이메일 & 별명 중복체크 판별 변수
let idCheck = false;
let nicknameCheck = false;
// input 검증 판별 변수
let inputCheck = false;

// 이메일 체크
function isEmailGood (loginId) {
    if(emailRegex.test(loginId)){
        email_err.style.display = 'none';
        return true;
    }else {
        email_err.style.display = 'block';
        return false;
    }
};
// 비밀번호 체크
function isPasswordGood (password) {
    if(pwRegex.test(password)){
        pw_err.style.display = 'none';
        return true;
    }else{
        pw_err.style.display = 'block';
        return false;
    }
}

// let isGood = {emailRegex , }




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

    // 모든 중복체크를 진행하여 true 상태일때, 가입 진행.
    if(idCheck == true && nicknameCheck == true){

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

        let userData = {
            loginId: document.getElementById('new_email').value,
            password: document.getElementById('new_password').value,
            repeatPw: document.getElementById('new_re_password').value,
            name: document.getElementById('new_name').value,
            nickname: document.getElementById('new_nickname').value,
            tel: document.getElementById('new_tel').value,
            address: document.getElementById('new_addr').value,
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
    else if(idCheck == false){
        alert('아이디 중복 체크를 진행해주세요.');
    }
    else if(nicknameCheck == false){
        alert('별명 중복체크를 진행해주세요.');
    }
    else {
        alert('중복체크를 진행해주세요.');
    }
});
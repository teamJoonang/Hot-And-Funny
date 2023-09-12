
// err 에러메시지
// let email_err = document.getElementById('email_err');
// let pw_err = document.getElementById('pw_err');
// let repw_err = document.getElementById('repw_err');
// let name_err = document.getElementById('name_err');
// let nickname_err = document.getElementById('nickname_err');
// let tel_err = document.getElementById('tel_err');
// let addr_err = document.getElementById('addr_err');
// let age_err = document.getElementById('age_err');
//
//
//
// // 대쉬 제거 검증식
// const dashRegex = /\-/g;
//
// // 이메일 검증식 - 대소문자 , 숫자로 1자~30자 @ 소문자,숫자에 마지막은 .소문자
// const emailRegex = /^[A-Za-z0-9_]{1,30}@([a-z0-9_]+\.)+[a-z]+$/g;
// // 패스워드 검증식 - 대소문자, 숫자 , 특수문자 무조건 하나 포함, 최소 8자 최대 16자
// const pwRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/g;
// // 이름 검증식 - 영어(대소문자), 한글 최소 1자~10자 , 숫자와 특수문자 제외.
// const nameRegex = /^[A-Za-z가-힝]{1,10}[^\d$!%*#?&]$/g;
// // 별명 - 언더바'_' 제외 특수문자 사용불가.
// const nicknameRegex =  /^[가-힣ㄱ-ㅎa-zA-z0-9]{2,10}[^$!%*#?&]$/g;
// // 연락처 - 숫자 11글자.
// const telRegex = /\d{11}/g;
// // // 주소 - 한글 , 영어(대소문자) 가능 , 특수문자 사용불가. -는 가능.
// // const addrRegex =
// // 나이 - 숫자만 가능 , 최소1글자 ~ 3글자.
// const ageRegex = /\d{1,3}/g;


// 회원가입 검증
// function valid_signup(){
//
//     // input
//     let new_email = document.getElementById('new_email');
//     let new_password = document.getElementById('new_password');
//     let new_re_password = document.getElementById('new_re_password');
//     let new_name = document.getElementById('new_name');
//     let new_nickname = document.getElementById('new_nickname');
//     let new_tel = document.getElementById('new_tel');
//     let new_age = document.getElementById('new_age');
//
//     // 혹시 모르니 -(대쉬)제거한 tel 값
//     let tel_value = new_tel.value.replace(dashRegex, '');
//     // 혹시 모르니 -(대쉬)제거한 tel 값
//     let age_value = new_age.value.replace(dashRegex , '');
//
//     // 에러 메시지를 숨김으로 초기화
//     email_err.style.display = 'none';
//     pw_err.style.display = 'none';
//     repw_err.style.display = 'none';
//     name_err.style.display = 'none';
//     nickname_err.style.display = 'none';
//     tel_err.style.display = 'none';
//     age_err.style.display = 'none';
//
//
//     if(emailRegex.test(new_email.value)){
//         email_err.style.display = 'block';
//         return false;
//     }
//
//     if(pwRegex.test(new_password.value)){
//         pw_err.style.display = 'block';
//         return false;
//     }
//
//     if(new_password.value !== new_re_password.value){
//         repw_err.style.display = 'block';
//         return false;
//     }
//
//     if(nameRegex.test(new_name.value)){
//         name_err.style.display = 'block';
//         return false;
//     }
//
//     if(nicknameRegex.test(new_nickname.value)){
//         nickname_err.style.display = 'block';
//         return false;
//     }
//
//     if(telRegex.test(tel_value)){
//         tel_err.style.display = 'block';
//         return false;
//     }
//
//     if(ageRegex.test(age_value)){
//         age_err.style.display = 'block';
//         return false;
//     }
// }


// send버튼 클릭시 ajax 요청
document.getElementById('loginBtn').addEventListener('click', function() {

    let maleGender = document.getElementById('new_gender_male');
    let femaleGender = document.getElementById('new_gender_female');

    let selectedGender="";

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
        if (xhr.readyState === 4) {
            if (xhr.status === 201) {
                // 회원가입 성공 처리
                window.location="/user/login"
            }
            else if (xhr.status === 400) {
                // 아이디를 찾지 못했을 때의 처리
                alert('양식을 채워 주세요.');
            }
            else {
                // 회원가입 실패 처리
                console.error('회원가입 실패');
            }
        }
    };

    xhr.send(JSON.stringify(userData));
});


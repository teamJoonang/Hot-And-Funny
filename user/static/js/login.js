
let email = document.getElementById('login_id');
let pw = document.getElementById('login_password');



// 앞단 form 검증 로직
function vaild_loginForm(){

    // 혹시 required를 뚫을 경우를 위해.
    if(email.value.trim() === "" || email.value == null ){
        alert("이메일을 입력해주세요.");
        return false;
    }

    if(pw.value.trim() === "" || pw.value == null){
        alert("비밀번호를 입력해주세요.");
        return false;
    }

    if(vaild_pw(pw) == false){
        alert("8자리 이상, 대소문자 , 특수문자 포함 필요.");
        return false;
    };

    // ajax로 서버에 보낸다.
    let xhr = new XMLHttpRequest();
    xhr.open("POST" , "/user/login" , true)




}

function vaild_pw(pw){
    // 비밀번호 8자리 이상, 대소문자, 특수문자를 모두 포함하도록.
    let pwPattern =  /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=!]).{8,}$/;
    
    return pwPattern.test(pw);
}



// function validLoginForm() {
//     // 필요한 유효성 검사를 수행하세요.
//     // 예를 들어, 이메일과 비밀번호의 형식을 확인하는 등의 작업을 수행할 수 있습니다.
//
//     // AJAX 요청 전송 코드
//     sendLoginRequest();
//
//     // 폼 제출을 막음
//     return false;
// }


document.getElementById('loginBtn').addEventListener('click' , function (){
    sendLoginRequest();
});

function sendLoginRequest() {
    // 사용자 입력 데이터 수집
    const loginId = document.getElementById('login_id').value;
    const loginPassword = document.getElementById('login_password').value;

    // 요청 생성
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/user/login', true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');

    // 요청 완료 시 동작 정의
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // 로그인 성공 처리
                // alert('로그인 성공!');
                window.location="/"
            } else if(xhr.status === 400) {
                // 로그인 실패 처리
                alert('로그인 실패 : 모든 양식을 채워주세요.');

            }
            else{
                alert('로그인 실패');
            }
        }
    };

    // 요청 전송
    const data = JSON.stringify({ loginId: loginId, password: loginPassword });
    xhr.send(data);
}

// // 추가 코드, 카카오 및 네이버 로그인을 위한 함수들
// function loginWithKakao() {
//     // 카카오 로그인 로직을 구현하세요.
// }
//
// function loginWithNaver() {
//     // 네이버 로그인 로직을 구현하세요.
// }
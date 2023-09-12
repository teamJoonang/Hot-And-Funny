// 아이디/비번 찾기 셀렉트 버튼
let findUserIdBtn = document.getElementById('findUserIdBtn');
let findUserPwBtn = document.getElementById('findUserPwBtn');
// 아이디 폼
let findIdform = document.getElementById('findUserId');
// 비밀번호 폼
let findPwform = document.getElementById('findUserPassword');
// 아이디 폼의 send 버튼
let findIdBtn = document.getElementById('findIdBtn');

// findUserIdBtn 클릭 이벤트 발생시, id form만을 보여준다.
findUserIdBtn.addEventListener('click' , function(){
    // 혹시 모르니 id form의 block 처리 한번 해주고
    findIdform.style.display = 'block';
    // pw form은 안보이도록.
    if(findPwform.style.display !== 'none'){
        findPwform.style.display = 'none';
    }
});

// findPwBtn 클릭 이벤트 발생시 pw form만 보여준다.
findUserPwBtn.addEventListener('click' , function(){
    // 혹시 모르니 pw form의 block 처리.
    findPwform.style.display = 'block';
    // id form은 안보이도록.
    if(findIdform.style.display != 'none'){
        findIdform.style.display = 'none';
    }
});


// ***************************** ajax ********************************************

// 아이디 찾기 버튼 클릭 시
document.getElementById('findIdBtn').addEventListener('click', function () {
    let userEmail = document.getElementById('user_email').value;

    // AJAX 요청을 수행하고 서버로 userEmail을 전송
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/user/findId', true);
    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // 성공적으로 아이디를 찾았을 때의 처리
                alert('존재하는 회원 아이디 입니다. ');
            }
            else if (xhr.status === 400) {
                // 아이디를 찾지 못했을 때의 처리
                alert('양식을 채워 주세요.');
            }
            else {
                alert('아이디를 찾을 수 없습니다.')
            }
        }
    };
    // dto의 멤버와 자동파싱되도록 같은 이름으로.
    xhr.send(JSON.stringify({ loginId : userEmail }));
});

// 비밀번호 찾기 버튼 클릭 시
document.getElementById('findPwBtn').addEventListener('click', function () {
    const userId = document.getElementById('user_id').value;
    const userName = document.getElementById('user_name').value;
    const userTel = document.getElementById('user_tel').value;

    // AJAX 요청을 수행하고 서버로 userId, userName, userTel을 전송
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/user/findPw', true);
    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // 성공적으로 비밀번호를 찾았을 때의 처리
                // const response = JSON.parse(xhr.responseText);
                alert('비밀번호 재설정을 진행하겠습니다.');
                window.location="/user/reset";
            }
            else if(xhr.status === 400) {
                // 비밀번호를 찾지 못했을 때의 처리
                alert('모든 양식을 채워 주세요.');
            }
            else {
                alert('검색되는 회원을 찾을 수 없습니다.')
            }
        }
    };

    xhr.send(JSON.stringify({ loginId: userId, name: userName, tel: userTel }));
});





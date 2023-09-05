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




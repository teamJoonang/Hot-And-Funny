let findIdBtn = document.getElementById('findUserIdBtn');
let findUserPwBtn = document.getElementById('findUserPwBtn');
let findIdform = document.getElementById('findUserId');
let findPwform = document.getElementById('findUserPassword');

// findIdBtn 클릭 이벤트 발생시, id form만을 보여준다.
findIdBtn.addEventListener('click' , function(){
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
})
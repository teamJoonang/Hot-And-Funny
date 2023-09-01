let findIdBtn = document.getElementById('findUserIdBtn');
let findUserPwBtn = document.getElementById('findUserPwBtn');
let findIdform = document.getElementById('findUserId');
let findPwform = document.getElementById('findUserPassword');

findIdBtn.addEventListener('click' , function(){

    findIdform.style.display = 'block';
    if(findPwform.style.display !== 'none'){
        findPwform.style.display = 'none';
    }
});

findUserPwBtn.addEventListener('click' , function(){
    findPwform.style.display = 'block';
    if(findIdform.style.display != 'none'){
        findIdform.style.display = 'none';
    }
})
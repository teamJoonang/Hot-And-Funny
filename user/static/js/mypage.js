


// document.addEventListener('DOMContentLoaded' , function(){
    

// });

let modifiyBtn = document.getElementById('modifyBtn');
let saveBtn = document.getElementById('saveBtn');

let modifiyBox = document.getElementById('modifyBox');
let saveBox = document.getElementById('saveBox');

let inputTag = document.querySelectorAll('input');

// 수정버튼 클릭시.
modifiyBtn.addEventListener('click', function () {
    // 모든 input 태그의 속성 readonly 제거.
    inputTag.forEach(function (input) {
        input.removeAttribute('readonly' , 'readonly');
    });
    //  수정버튼은 안보이고 등록버튼은 보여라.
    modifiyBox.style.display = 'none';
    saveBox.style.display = 'block';
});

// 등록버튼 클릭시.
saveBtn.addEventListener('click', function () {
    // 모든 input 태그의 속성 readonly 설정
    inputTag.forEach(function (input) {
        input.setAttribute('readonly' , 'readonly');
    });
    //  수정버튼은 보이고 등록버튼은 비활성화하기.
    saveBox.style.display = 'none';
    modifiyBox.style.display = 'block';
});


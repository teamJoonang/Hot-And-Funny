// 수정버튼과 div 박스
let modifiyBox = document.getElementById('modifyBox');
let modifiyBtn = document.getElementById('modifyBtn');
// 등록버튼과 div박스
let saveBox = document.getElementById('saveBox');
let saveBtn = document.getElementById('saveBtn');
// 모든 input tag들.
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

    // sweetalert2 사용 -confirm형태로 묻기
    Swal.fire({
        title: '수정된 회원정보를 등록할까요?',
        showDenyButton: true,
        confirmButtonText: '등록',
        denyButtonText: `취소`,
    })
    .then((result) => {
        if (result.isConfirmed) {
            Swal.fire('등록되었습니다.', '', 'success')
        } 
        else if (result.isDenied) {
            Swal.fire('등록되지 않았습니다.', '', 'error')
        }
    });

    // 모든 input 태그의 속성 readonly 설정
    inputTag.forEach(function (input) {
        input.setAttribute('readonly' , 'readonly');
    });
    //  수정버튼은 보이고 등록버튼은 비활성화하기.
    saveBox.style.display = 'none';
    modifiyBox.style.display = 'block';

    
});




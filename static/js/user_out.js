let etcBox = document.getElementById('etcBox');
let selectUserOutReason = document.getElementById('selectUserOutReason');
let userOutBtn = document.getElementById('userOutBtn');

// 유저의 이탈사유 선택박스에 chang 이벤트 발생시.
selectUserOutReason.addEventListener('change' , function(){
    // option에서 selected 된 value 가져오고
    let selectedVal = selectUserOutReason.value;
    // 만약 그게 etc면 etcBox를 보여줘라
    if(selectedVal === "etc"){
        etcBox.style.display = "block";
    }
    // 아니라면 숨겨놔라.
    else {
        etcBox.style.display = "none";
    }
});

userOutBtn.addEventListener('click' , function(){

    // sweetalert2 사용 -confirm형태로 묻기
    Swal.fire({
        title: '정말로 탈퇴하시겠습니까?',
        showDenyButton: true,
        confirmButtonText: '네',
        denyButtonText: '아니요.',
    })
    .then((result) => {
        if (result.isConfirmed) {
            Swal.fire('탈퇴 처리되었습니다.', '회원정보는 즉각 삭제됩니다, 감사합니다.', 'success');


        } 
        else if (result.isDenied) {
            Swal.fire('취소 되었습니다.', '저희와 다시 함께해주셔서 감사합니다!', 'info')
        }
    });
});


// 기존 별명 저장 변수.
let oldNickname = "";
// html이 로드되는 순간 기존 별명값을 저장하라.
window.onload = function (){
    oldNickname = document.getElementById('user_nickname').value;
};

// 별명 중복체크 판별 변수
let nicknameCheck = false;

// nickname 칸을 건들면 중복체크 성공해도 false 변경;
document.getElementById('user_nickname').addEventListener('change' , function(){
    nicknameCheck = false;
});

// nickNameCheck 버튼 눌리면 ajax 요청. (중복체크)
document.getElementById('nickNameCk').addEventListener('click' , function (){
    let nickname = document.getElementById('user_nickname').value;
    let data = {nickname : nickname , oldNickname : oldNickname};

    let xhr = new XMLHttpRequest();
    xhr.open('POST','/user/nicknameModifyCk' , true);
    xhr.setRequestHeader('Content-Type' , 'application/json; charset=UTF-8');

    xhr.onreadystatechange = function(){
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // ok 응답시
                Swal.fire('사용가능한 별명 입니다.');
                // 체크 확인.
                nicknameCheck = true;
            }
            else{
                // bad request 응답시
                Swal.fire('이미 사용중인 별명 입니다.');
                document.getElementById('user_nickname').focus();
            }
        }
    };
    xhr.send(JSON.stringify(data));

});


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
    // 이메일은 수정되어서는 안됨. readonly 유지하도록.
    document.getElementById('user_login_id').setAttribute('readonly' , 'readonly');
    //  수정버튼은 안보이고 등록버튼은 보여라.
    modifiyBox.style.display = 'none';
    saveBox.style.display = 'block';
});

// 등록버튼 클릭시.
saveBtn.addEventListener('click', function () {

    if(nicknameCheck == true){

        // gender radio 버튼들 가져오기
        let maleGender = document.getElementById('new_gender_male');
        let femaleGender = document.getElementById('new_gender_female');
        // gender 값 담을 변수 생성.
        let selectedGender="";
        // db는 tinyint(1) true 넣어주면 1 들어가짐.
        // 남자 체크되어 있으면 변수에 true 여자체크면 false 담기.
        if(maleGender.checked){
            selectedGender = true;
        }
        else if(femaleGender.checked){
            selectedGender = false;
        }

        // sweetalert2 사용 -confirm형태로 묻기
        Swal.fire({
            title: '수정된 회원정보를 등록할까요?',
            showDenyButton: true,
            confirmButtonText: '등록',
            denyButtonText: `취소`,
        }).then((result) => {
            // 등록을 누를 경우와 아닌 경우.
            if (result.isConfirmed) {

                let userData = {
                    loginId: document.getElementById('user_login_id').value,
                    name: document.getElementById('user_name').value,
                    nickname: document.getElementById('user_nickname').value,
                    tel: document.getElementById('user_tel').value,
                    address: document.getElementById('user_addr').value,
                    age: document.getElementById('user_age').value,
                    gender : selectedGender
                };

                // ajax 사용
                let xhr = new XMLHttpRequest();
                xhr.open('POST', '/user/myinfo', true);
                xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');

                xhr.onreadystatechange = function() {
                    let responseBody = xhr.responseText;
                    if (xhr.readyState === 4) {
                        if (xhr.status === 200) {
                            // update 성공처리 , 다시 내정보로 이동.
                            Swal.fire('새로운 회원 정보로 등록되었습니다.', '', 'success');

                            // 모든 input 태그의 속성 readonly 설정
                            inputTag.forEach(function (input) {
                                input.setAttribute('readonly' , 'readonly');
                            });

                            //  수정버튼은 보이고 등록버튼은 비활성화하기.
                            saveBox.style.display = 'none';
                            modifiyBox.style.display = 'block';
                        }
                        else if (xhr.status === 400 && responseBody === '양식을 모두 채워주세요.') {
                            Swal.fire('양식을 모두 채워주세요.' , '' , 'error');
                        }
                        else if(responseBody === '회원정보 수정 실패.') {
                            // 회원가입 실패 처리
                            Swal.fire('서버 오류' , '' , 'error');
                        }

                    }
                };
                xhr.send(JSON.stringify(userData));
            }
            else if (result.isDenied) {
                Swal.fire('등록되지 않았습니다.', '', 'error')
            }
        });
    }
    else {
        Swal.fire({
            title: '별명 중복체크를 진행해주세요!',
            icon: 'error'
        });
    }

});







let etcBox = document.getElementById('etcBox');
let selectUserOutReason = document.getElementById('selectUserOutReason');

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
})


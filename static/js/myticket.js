let table = document.getElementById('ticket_list');
let rows = table.querySelectorAll('tbody tr');




rows.forEach(function(row) {
    let cancelTd = row.querySelector('td:last-child');

    cancelTd.addEventListener('click' , function(){

        // sweetalert2 사용 -confirm형태로 묻기
        Swal.fire({
            title: '티켓을 취소할까요?',
            icon: 'question',
            html:
            '어려운 점이 계시다면 담당자와 ' +
            '<a href="ask_admin_list.html" style="color: blue; font-weight: bold;">문의</a> ' +
            '도 가능합니다.',
            showDenyButton: true,
            confirmButtonText: '네',
            denyButtonText: `아니요`,
        })
        .then((result) => {
            if (result.isConfirmed) {
                Swal.fire('취소되었습니다.', '환불에는 일부 시간이 소요됩니다.', 'success');
            } 
        });
        
        let tds = row.querySelectorAll('td');

        let td_values = [];
        tds.forEach(function(td){
            td_values.push(td.textContent.trim());
        });

        console.log('클릭된 행의 데이터: ' , td_values);
        // ['dkalfk836', 'Tintop 콘서트 1일차', 'D-A53', '2023.09.09', '18:30', 'LG아트센터', '2023.08.31 21시:32분:12초', '이현승', '유효', '취소']
    });
});
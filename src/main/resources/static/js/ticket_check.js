const btnHome = document.getElementById("btn-home");


/*
$.ajax({
      url: '/ticket/ticket/check/'
      , method: 'GET'
      , dataType: 'json'
      , data: JSON.stringify({
         concertDate: concertDateEx,
         userId: userId
      })
      ,contentType: 'application/json'
      , success: function(data) {
         console.log(data);

      },
      error: function(jqXHR, textStatus, errorThrown) {
         console.log(errorThrown);
         console.log(jqXHR);
         console.log(textStatus);
         alert("실패");
      }
   });
*/
//  홈 클릭시 index 화면으로
btnHome.addEventListener("click",()=>{
    window.opener.location.href="/";
    self.close();
});

// const seatGrade = document.querySelector('.seat-grade').textContent;

function gradeColorAccept() {
    const tickets = document.querySelectorAll('.ticket');
    tickets.forEach(ticket => {
        const seatGradeElement = ticket.querySelector('.ticket-left .seat-grade');
        const backColor = ticket.querySelector('.color');
        const seatGrade = seatGradeElement.textContent.trim();

        if (seatGrade === "VIP") {
            backColor.style.backgroundColor = "yellow";
        } else if (seatGrade === "R") {
            backColor.style.backgroundColor = "green";
        } else if (seatGrade === "S") {
            backColor.style.backgroundColor = "purple";
        }
    });

}
window.onload = function () {
    gradeColorAccept();
};
const btnHome = document.getElementById("btn-home");

//  홈 클릭시 index 화면으로
btnHome.addEventListener("click",()=>{
    window.opener.location.href="/";
    self.close();
});
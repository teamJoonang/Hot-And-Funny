
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawCharts);

window.addEventListener('resize', drawCharts);

function drawCharts() {
  drawAreaChart();
  drawBarChart();
  drawDonutChart();
  drawSecondDonutChart();
  drawBar2Chart();
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function drawAreaChart() {
  // 연령대별 통계 데이터를 가져오는 AJAX 요청
  $.ajax({
    url: '/admin/stat/area',
    method: 'GET',
    dataType: 'json',
    success: function (areaGroupJsonArray) {
      // 데이터 배열 초기화
      var data = [];

      // 데이터 배열에 헤더 추가
      data.push(['매출 현황', '']);

      // 데이터 배열에 통계 데이터 추가
      for (var i = 0; i < areaGroupJsonArray.length; i++) {
        data.push([areaGroupJsonArray[i].concertDate + '일차 콘서트', areaGroupJsonArray[i].totalPrice]);
      }



      // 데이터 배열을 사용하여 차트 그리기
      var chartData = google.visualization.arrayToDataTable(data);

      var options = {
        title: '매출 추이',
        curveType: 'function',
        bars: 'vertical', // 세로 바 차트로 설정
        legend: { position: 'none' }
      };

      var chart = new google.visualization.BarChart(document.getElementById('areaChart'));
      chart.draw(chartData, options);
    },
    error: function (error) {
      console.error('데이터를 가져오는 중 오류 발생:', error);
    }
  });
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////









///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function drawBarChart() {
  // 연령대별 통계 데이터를 가져오는 AJAX 요청
  $.ajax({
    url: '/admin/stat/bar',
    method: 'GET',
    dataType: 'json',
    success: function (reservationGroupJsonArray) {
      // 데이터를 DataTable 형식으로 변환
      console.log(reservationGroupJsonArray);
      var data = new google.visualization.DataTable();
      
      data.addColumn('string', '일자별 콘서트');
      data.addColumn('number', '예매율');
      


      
      
      var ConcertDates = ['1일차 콘서트', '2일차 콘서트', '3일차 콘서트']
      for (var i = 0; i < reservationGroupJsonArray.length; i++) {
        // '연령대별'은 숫자 배열의 인덱스로 표현하고, 'Count'는 해당 숫자 값을 사용합니다.
        var ConcertDate = ConcertDates[i];
        data.addRow([ConcertDate, reservationGroupJsonArray[i] / 340]);
      }

	  var options = {
	    title: '',
	    bars: 'vertical',
	    legend: { position: 'none' }
	  };
	
	  var chart = new google.visualization.BarChart(document.getElementById('barChart'));
	
	  chart.draw(data, options);
    },
    error: function (error) {
      console.error('데이터를 가져오는 중 오류 발생:', error);
    }
  });
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



/*function drawDonutChart() {
  var data = google.visualization.arrayToDataTable([
    ['성별', '티켓 예매 수'],
    ['남성', 10],
    ['여성', 8]
]);

  var options = {
    title: '성비별',
    pieHole: 1,
    legend: { position: 'left' },
    chartArea: { 
      width: '80%',  // 차트 영역의 너비 조정
      height: '80%'  // 차트 영역의 높이 조정
    }
  };

  
  var chart = new google.visualization.PieChart(document.getElementById('donutChart'));

  chart.draw(data, options);

  
}*/










function drawSecondDonutChart() {
  // 연령대별 통계 데이터를 가져오는 AJAX 요청
  $.ajax({
    url: '/admin/stat/seconddonut',
    method: 'GET',
    dataType: 'json',
    success: function (ageGroupJsonArray) {
      // 데이터를 DataTable 형식으로 변환
      console.log(ageGroupJsonArray);
      var data = new google.visualization.DataTable();
      
      data.addColumn('string', '연령대별');
      data.addColumn('number', 'Count');
      
      
            // 하드코딩된 레이블 배열
      var ageLabels = ['10대', '20대', '30대', '40대 이상'];
      for (var i = 0; i < ageGroupJsonArray.length; i++) {
        // '연령대별'은 숫자 배열의 인덱스로 표현하고, 'Count'는 해당 숫자 값을 사용합니다.
        var ageLabel = ageLabels[i];
        data.addRow([ageLabel, ageGroupJsonArray[i]]);
      }

      var options = {
        title: '연령대별 통계',
        pieHole: 0.4,
        legend: { position: 'bottom' },
        chartArea: { width: '80%', height: '80%' }
      };

      // 차트 그리기
      var chart = new google.visualization.PieChart(document.getElementById('secondDonutChart'));
      chart.draw(data, options);
    },
    error: function (error) {
      console.error('데이터를 가져오는 중 오류 발생:', error);
    }
  });
}

  


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function drawDonutChart() {
  // 연령대별 통계 데이터를 가져오는 AJAX 요청
  $.ajax({
    url: '/admin/stat/donut',
    method: 'GET',
    dataType: 'json',
    success: function (genderGroupJsonArray) {
      // 데이터를 DataTable 형식으로 변환
      console.log(genderGroupJsonArray);
      var data = new google.visualization.DataTable();
      
      data.addColumn('string', '성비별');
      data.addColumn('number', 'Count');
      
      
            // 하드코딩된 레이블 배열
      var genderLabels = ['여성', '남성'];
      for (var i = 0; i < genderGroupJsonArray.length; i++) {
        // '연령대별'은 숫자 배열의 인덱스로 표현하고, 'Count'는 해당 숫자 값을 사용합니다.
        var genderLabel = genderLabels[i];
        data.addRow([genderLabel, genderGroupJsonArray[i]]);
      }

      var options = {
        title: '성비별 통계',
        pieHole: 1,
        legend: { position: 'left' },
        chartArea: { width: '80%', height: '80%' }
      };

      // 차트 그리기
      var chart = new google.visualization.PieChart(document.getElementById('donutChart'));
      chart.draw(data, options);
    },
    error: function (error) {
      console.error('데이터를 가져오는 중 오류 발생:', error);
    }
  });
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////







  function drawBar2Chart() {
    var data = google.visualization.arrayToDataTable([
      ['일자별 콘서트', 'S석 남은 좌석 수', 'R석 남은 좌석 수', 'VIP석 남은 좌석 수'],
      ['1일차', 100, 50, 30],
      ['2일차', 90, 45, 25],
      ['3일차', 80, 40, 20]
    ]);
  
    var options = {
      title: '일자별 남은 좌석 수',
      bars: 'vertical',
      legend: { position: 'bottom' }
    };
  
    var chart = new google.visualization.BarChart(document.getElementById('bar2Chart'));
  
    chart.draw(data, options);
  }

  
  
  
  
  
  
  
  function drawBar2Chart() {
  // 좌석현황 통계 데이터를 가져오는 AJAX 요청
  $.ajax({
    url: '/stat/bar2',
    method: 'GET',
    dataType: 'json',
    success: function (seatGroupJsonArray) {
      // 데이터를 원하는 형식으로 가공
      var seatDates = [];
      for (var i = 0; i < seatGroupJsonArray.length; i++) {
        var concertData = seatGroupJsonArray[i];
        seatDates.push({
          concert_id: concertData.concert_id,
          firstConDay: concertData.firstConDay,
          secondConDay: concertData.secondConDay,
          thirdConDay: concertData.thirdConDay
        });
      }

      // 데이터를 DataTable 형식으로 변환
      var data = new google.visualization.DataTable();
      data.addColumn('string', '콘서트');
      data.addColumn('number', 'S석');
      data.addColumn('number', 'R석');
      data.addColumn('number', 'VIP석');
      
      for (var i = 0; i < seatDates.length; i++) {
        var concertData = seatDates[i];
        data.addRow([
          '콘서트 ' + concertData.concert_id + '일차',
          340 - concertData.firstConDay,
          340 - concertData.secondConDay,
          340 - concertData.thirdConDay
        ]);
      }

      var options = {
        title: '일자별 남은 좌석 수',
        bars: 'vertical',
        legend: { position: 'bottom' }
      };

      var chart = new google.visualization.BarChart(document.getElementById('bar2Chart'));
      chart.draw(data, options);
    },
    error: function (error) {
      console.error('데이터를 가져오는 중 오류 발생:', error);
    }
  });
}
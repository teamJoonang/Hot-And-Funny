
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



function drawAreaChart() {
  var data = google.visualization.arrayToDataTable([
    ['Day', '1일차 콘서트', '2일차 콘서트', '3일차 콘서트'],
    ['09.01', 1000, 400, 800],
    ['09.02', 1170, 460, 760],
    ['09.03', 660, 1120, 1324],
    ['09.04', 1030, 540, 1524]
  ]);

  var options = {
    title: '매출 추이',
    curveType: 'function',
    legend: { position: 'bottom' }
  };

  var chart = new google.visualization.AreaChart(document.getElementById('areaChart'));

  chart.draw(data, options);
}








///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function drawBarChart() {
  // 연령대별 통계 데이터를 가져오는 AJAX 요청
  $.ajax({
    url: '/stat/bar',
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



function drawDonutChart() {
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

  
}










			// AJAX 요청을 통해 데이터 가져오기
//			$.ajax({
//			  url: '/statistics-data',
//			  method: 'GET',
//			  dataType: 'JSON', // JSON 형식으로 데이터를 응답받음
//			  success: function(data) {
//			    // 데이터를 파싱하여 변수에 할당
//			    let teen = data.Teen;
//			    let twenty = data.Twenty;
//			    let thirty = data.Thirty;
//			    let forty = data.Forty;
//			    
//			    // 이제 이 변수들을 사용할 수 있음
//			    console.log(teen, twenty, thirty, forty);
//			    
//			    // 그리고 원하는 함수(예: drawSecondDonutChart)를 호출하여 차트를 그릴 수 있음
//			    drawSecondDonutChart(teen, twenty, thirty, forty);
//			  },
//			  error: function(error) {
//			    console.error('데이터를 가져오는 중 오류 발생:', error);
//			  }
//			});
//















function drawSecondDonutChart() {
  // 연령대별 통계 데이터를 가져오는 AJAX 요청
  $.ajax({
    url: '/stat/seconddonut',
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

  


  // console.log(jsonData);

  // var data1 = new google.visualization.DataTable(jsonData);
  // console.log(data1);
  // var data = google.visualization.arrayToDataTable([
  //   ['연령대별', 'Count'],
  //   ['10대', 10],
  //   ['20대', 3],
  //   ['30대', 1],
  //   ['40대 이상', 2]
  // ]);
  // console.log(data);

  // var options = {
  //   title: '연령대별',
  //   pieHole: 0.4,
  //   legend: { position: 'bottom' },
  //   chartArea: { width: '80%', height: '80%'}
  //
  // };

  // var chart = new google.visualization.PieChart(document.getElementById('secondDonutChart'));
  // chart.draw(data, options);










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



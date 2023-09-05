
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

function drawBarChart() {
  var data = google.visualization.arrayToDataTable([
    ['일자별 콘서트', '예매율', {role: 'style'}],
    ['1일차', 10, 'color: #3366cc'],
    ['2일차', 11, 'color: #dc3912'],
    ['3일차', 66, 'color: #ff9900']
  ]);

  var options = {
    title: '',
    bars: 'vertical',
    legend: { position: 'none' }
  };

  var chart = new google.visualization.BarChart(document.getElementById('barChart'));

  chart.draw(data, options);
}

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


function drawSecondDonutChart() {
    var data = google.visualization.arrayToDataTable([
      ['연령대별', 'Count'],
      ['10대', 110],
      ['20대', 250],
      ['30대', 80],
      ['40대 이상', 60]
    ]);
  
    var options = {
      title: '연령대별',
      pieHole: 0.4,
      legend: { position: 'bottom' },
      chartArea: { width: '80%', height: '80%'}
      
    };

    
  
    var chart = new google.visualization.PieChart(document.getElementById('secondDonutChart'));
  
    chart.draw(data, options);
    
  }



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



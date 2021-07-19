<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html>

<head>
	<title>Статистика</title>
	
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	 
	<!-- style for page shape -->	
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/page_shape.css" />
	
	<!-- import Bootstrap and jQuery -->	  
	<c:import url="../view/fragments/bootstrap.jsp"></c:import>
	
		<!-- Style of the jumbotron for vertical centering when using navbar -->
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/jumbotron-vertCenter-when-navbar-used.css">

	<!-- style for Statistics Data -->	
	<link type="text/css"
		  rel="stylesheet"
		  href="${pageContext.request.contextPath}/resources/css/statistics.css" />

	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
	<script src="https://www.gstatic.com/charts/loader.js"></script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">



	<!-- TESTING MONT PICKER -->
	<!--  importing bootstrap-datepicker for selecting date -->
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-datepicker3.min.css"/>" type="text/css" />
	<script src="<c:url value="/resources/js/bootstrap-datepicker.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap-datepicker.new.bg.min.js"/>" charset="UTF-8"></script>



<script type="text/javascript">

var selectedYear = ${diplayedYear};
var maxRange =  null;

var currentWindowHight = $(window).height();

</script>


<script type="text/javascript">
    
	google.charts.load('current', {
    	packages: ['controls', 'corechart'], 'language': 'bg',
    	 "callback": drawMyChart
	});
	
	google.setOnLoadCallback(drawMyChart);
	
	
	function drawMyChart() {
	  	  
    var data = new google.visualization.DataTable();
    
    data.addColumn('date', 'Date');
    data.addColumn('number', 'Заявки');

    data.addRows([
		<c:forEach items="${chartData}" var="entry" varStatus="loop">
			[new Date(<c:out value="${entry.year}"/>, <c:out value="${entry.month}"/>, <c:out value="${entry.day}"/>), <c:out value="${entry.requests}"/> ],
		</c:forEach>
	]);
    
    
    if(currentWindowHight < 600){
    	currentWindowHight = 600;
    }
    

	var chart = new google.visualization.ChartWrapper({
		chartType: 'LineChart',
		containerId: 'chart-area',
		options: {
			height: currentWindowHight*0.4,
			legend: {
				alignment: 'end',
				position: 'top'
				},
			animation: {
				duration: 500,
				easing: 'in',
				startup: true
				},
			chartArea: {
				height: '100%',
				width: '100%',
				top: 50,
				left: 50,
				right: 50,
				bottom: 50,
				},
			hAxis: {
				title: 'Дата',
				format: 'dd-MMM-yyyy',
				gridlines: { 
					interval: 1,   
					},		           	
				minorGridlines: {
					count:0
					}
				},
			vAxis : {
				title: 'Заявки',				
				format:"#",				
				gridlines: {
					
					
					},
				minorGridlines: {
					count:0
					},
				viewWindow: {
					min: 0, max:maxRange
					}				
				}
			}
		});
    
        
	var rangeFilter = new google.visualization.ControlWrapper({
    	controlType: 'ChartRangeFilter',
		containerId: 'filter-range',
		options: {
			filterColumnIndex: 0,
			ui: {
				chartType: 'LineChart',
				
				chartOptions: {
					height: currentWindowHight*0.1,
					chartArea: {
						width: '100%',
						left: 10,
						right: 10,
						backgroundColor: '#f5f5f5'
						},					
					hAxis: { 
						format: 'MM-yy'
						},         
					backgroundColor: {fill: '#f5f5f5'}
					},
				
				minRangeSize:2332800000,
				snapToData:true
			},        
		} 
    });   

    google.visualization.events.addListener(rangeFilter, 'statechange', function () {
        
    	var stateDate = rangeFilter.getState();        
        var startDate = stateDate.range.start;
        var endDate = stateDate.range.end;
        
        document.getElementById('start').innerHTML =  new Date(startDate.getTime()).toLocaleDateString("bg-BG");
        document.getElementById('end').innerHTML = new Date(endDate.getTime()).toLocaleDateString("bg-BG");
      }); 

    
    $('.date').on('changeMonth',function(e) {    	
        
    	var calendarStartDate = new Date(e.date.getFullYear(), e.date.getMonth(), 1);
        var calendarEndDate = new Date(e.date.getFullYear(), e.date.getMonth() + 1, 0);
    	 
        rangeFilter.setState({
			range: {
				start: calendarStartDate,
				end: calendarEndDate
				}
		});
    
      document.getElementById('start').innerHTML = calendarStartDate.toLocaleDateString("bg-BG");
      document.getElementById('end').innerHTML = calendarEndDate.toLocaleDateString("bg-BG");
    	
      rangeFilter.draw();
    });
    

    $('#reset-button').on('click', function (reset) {

        document.getElementById('start').innerHTML = new Date(String(selectedYear) + "-01-01").toLocaleDateString("bg-BG");
        document.getElementById('end').innerHTML = new Date(String(selectedYear) + "-12-31").toLocaleDateString("bg-BG"); 
  
    	rangeFilter.setState(null);
        rangeFilter.draw();      

        resetDate();            
	});
    
        
    var dashboard = new google.visualization.Dashboard(document.getElementById('dashboard'));

    dashboard.bind(rangeFilter, chart);
    dashboard.draw(data);
  };
  
</script>


<script type="text/javascript">
$(document).ready(function () {
    
    $('.date').datepicker({
        format: "dd-mm-yyyy",
        startDate: "01-01-" + String(selectedYear),
        endDate: "31-12-" + String(selectedYear),
        language: 'bg',
        orientation: "bottom auto",
        weekStart: 1,
        autoclose: true,
        startView: 1,
        minViewMode: 1,
        maxViewMode: 1,        
        immediateUpdates:true,
    });
   
});


function resetDate() {
	$('.date').datepicker('clearDates');
	$('.date').datepicker('update');
}

</script>


<script type="text/javascript">

$(document).ready(function () {
	
    $('#switchRelative').on('click', function ()  {

    	maxRange = null;
        document.getElementById('start').innerHTML = new Date(String(selectedYear) + "-01-01").toLocaleDateString("bg-BG");
        document.getElementById('end').innerHTML = new Date(String(selectedYear) + "-12-31").toLocaleDateString("bg-BG");    	
    	document.getElementById('textTypeOfChart').innerHTML = "Релативна";  
    	drawMyChart();
    	resetDate();    	 
      });
    
    
    $('#switchAbsolute').on('click', function ()  {
    	
    	maxRange = <c:out value="${maxRange}"/>;
        document.getElementById('start').innerHTML = new Date(String(selectedYear) + "-01-01").toLocaleDateString("bg-BG");
        document.getElementById('end').innerHTML = new Date(String(selectedYear) + "-12-31").toLocaleDateString("bg-BG");    	
    	document.getElementById('textTypeOfChart').innerHTML = "Абсолютна"; 
    	drawMyChart();
    	resetDate();    	
      });
  
});

</script>

</head>

<body>

	<!-- Import the header menu -->
	<c:import url="../view/fragments/header.jsp"></c:import>

	<div class="jumbotron vertical-center">
		<div class="container" id="container" >

			<div class="row">

				<div class="panel panel-primary panel-statistics">
					<div id='testPanel' class="panel-heading text-center panel-heading-statistics">Статистика за ${diplayedYear} г.</div>
						<div id="dashboard" class="dashboard">

							<div class="row statistics-menu">
								<div class="col-xs-6">
								
									<button  class="date btn btn-primary" ><span class="glyphicon glyphicon-calendar"></span> Избор на месец</button>																	
									<button  class="btn btn-primary" type="button" id="reset-button"><span class="glyphicon glyphicon-retweet"></span> Нулиране</button>
									
									<div class="btn-group">
								  		<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								  			<span class="glyphicon glyphicon-stats"></span> Графика <span class="caret"></span>
										</button>
										<ul class="dropdown-menu" >
											<li><a  type="button"   id="switchRelative" >Релативна</a></li>
											<li><a  type="button" id="switchAbsolute">Абсолютна</a></li>
										</ul>
									</div>
								</div>
								
								<div class="col-xs-6 text-right text-graphic-type">					
									Тип графика: <span id="textTypeOfChart" class="text-graphic-type-colorful">Релативна</span>									
								</div>
							</div>
						
							<div class="row text-center date-range" >	
							    Начална Дата: <span id='start' class="date-range-colorful">1.01.${diplayedYear} г.</span>
							    &nbsp; Крайна Дата: <span id='end' class="date-range-colorful">31.12.${diplayedYear} г.</span> 						    
							</div>

							<!-- Adding Charts -->
							<div id="chart-area" class="chart-area"></div>
							
							<div class="well filter-range-well">
								<div id="filter-range"></div>
							</div>
							 							
							<div class="row chose-year-btn">
								<div class="col-sm-6">					
									<a class="btn btn-primary" href="${pageContext.request.contextPath}/statistics/select" role="button"><i class="glyphicon glyphicon-arrow-left"></i> Избор на година</a>										
								</div>							
							</div>
						</div>
				</div>
			</div>
		</div>
	</div>
	
</body>

</html>
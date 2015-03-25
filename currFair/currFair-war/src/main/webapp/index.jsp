<html>
<head>
<script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="https://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
<script>
function consumeMessage(){
$.ajax({
    type: 'POST', 
    url: '/api/messages/consume',
    contentType: 'application/json; charset=UTF-8',
    dataType: 'JSON',
    data: $("#input").val(),
    success: function(data) {
    	createReport(data);
    	loadMap();
    },
    error: function(data) {
        alert('fail');
    }

});

}
$(document).ready(function(){
$.ajax({
    type: 'GET', 
    url: '/api/messages/list',
    success: function(data) {
    	createReport(data);
    },
    error: function(data) {
        alert('fail');
    }

});
loadMap();
});

function createReport(data){
	var html="<div class='header'>Reports</div><br>";
	html= html+"<table><tr><td>UserId</td><td>CurrencyFrom</td>+<td>CurrencyTo</td>+<td>AmountSell</td>+<td>AmountBuy</td>+<td>Rate</td>+<td>TimePlaced</td>"
	+ "<td>OriCountry</td>";
	$.each(data, function(k,v) {
		var key = k;
		
		html= html+ "<tr><td colspan=8><bold>"+key+"</bold></td></tr>";
		
		  $.each(this, function(k, v) {
		    	var key = k;
		    	var value = v;
		    	html = html + "<tr><td>"+ value.userId + "</td><td>" + value.currencyFrom + "</td><td>"+ value.currencyTo + "</td><td>"+value.amountSell + "</td><td>"
		    	+ value.amountBuy + "</td><td>" + value.rate + "</td><td>" + value.timePlaced + "</td><td>" + value.originatingCountry+"</td></tr>" ;
		    
		  });
		  
		  
		});
	html= html+"</table>";
	$( "#report" ).html(html);
}
function searchReports(){
	$.ajax({
	    type: 'GET', 
	    url: '/api/messages/'+ $("#searchString").val()+'/listMessagesByUserId',
	   
	    success: function(data) {
	    	var html="<b>Messages</b><br>";
	    	html= html+"<table><tr><td>UserId</td><td>CurrencyFrom</td>+<td>CurrencyTo</td>+<td>AmountSell</td>+<td>AmountBuy</td>+<td>Rate</td>+<td>TimePlaced</td>"
	    	+ "<td>OriCountry</td>";
	    
	    		
	    		  $.each(data, function(k, v) {
	    		    	var key = k;
	    		    	var value = v;
	    		    	html = html + "<tr><td>"+ value.userId + "</td><td>" + value.currencyFrom + "</td><td>"+ value.currencyTo + "</td><td>"+value.amountSell + "</td><td>"
	    		    	+ value.amountBuy + "</td><td>" + value.rate + "</td><td>" + value.timePlaced + "</td><td>" + value.originatingCountry+"</td></tr>" ;
	    		    
	    		  });
	    		  
	    		  
	    		
	    	html= html+"</table>";
	    	$( "#searchResults" ).html(html);
	    },
	    error: function(data) {
	        alert('fail');
	    }

	});
	}

function loadMap(){
	
	$.ajax({
	    type: 'GET', 
	    url: '/api/messages/messagesLocations',
	   
	    success: function(data) {
	    	map = new google.maps.Map(document.getElementById('map'), {
	    		zoom: 2,
	            center: new google.maps.LatLng(43.7231608,-79.3970677),
	    		   mapTypeId: google.maps.MapTypeId.ROADMAP
	    		});
	    	var marker, i;
	    	 var bounds = new google.maps.LatLngBounds();
	    	gmarkers = [];
	    		  $.each(data, function(k, v) {
	    		    	var key = k;
	    		    	var value = v;
	    		    	marker = new google.maps.Marker({
	    		    	       position: new google.maps.LatLng(value.lat, value.lng),
	    		    	       map: map
	    		    	   });
	    		    	 bounds.extend(marker.position);
	    		    
	    		  });
	    		  map.fitBounds(bounds);
	  				map.panToBounds(bounds); 
	    	
	    },
	    error: function(data) {
	        alert('fail');
	    }

	});


}
</script>
<style>
table td {
    width: 100px;
    overflow: hidden;
    display: inline-block;
    white-space: nowrap;
}
.header {
    height:20px;
    background:#F0F0F0;
    border:1px solid #CCC;
    margin:0px auto;
    text-align: center;
}
</style>
</head>
<body>
	<div id="consume">
		<p>Please enter the json in the box in the format below<br/>
				
		[{"userId": "134256", "currencyFrom": "EUR", "currencyTo": "INR", "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "24-JAN-15 10:27:44", "originatingCountry" : "FR"},{"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "24-JAN-15 10:27:44", "originatingCountry" : "FR"}]
		
		</p>
		<textarea rows="20" cols="100" id="input" name="inp">
		</textarea>
		<input type="button" value="SUBMIT" onclick="consumeMessage();"/>
	</div>
	<div id="report" style="width: 900px; height: 500px;float:left;margin-top:10px;overflow:scroll;">
	</div>
	<div class='header' style="width: 900px;float:right;margin-top:10px;">Geographical view of Messages Originated</div>
	</div>
	<div id="map" style="width: 900px; height: 500px;float:right;margin-top:10px;">
	
	</div>
	<div id="search" style="width: 900px; height: 500px;float:left;margin-top:10px;overflow:scroll;">
	<bold>Search Messages by userid:</bold>
	<input type="text" name="searchString" id="searchString" ></input>
	<input type="button" value="SEARCH" onclick="searchReports();"/>
		<div id="searchResults" >
	</div>
	</div>
	
	
</body>

</html>
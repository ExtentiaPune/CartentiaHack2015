'use strict';

function DashboardController(){
	this.serviceLayerObj = new ServiceLayer();
	this.fetchStatuses();
	document.getElementById('fullmap').style.display = 'none';
}

DashboardController.prototype.removeEventListeners = function(){
	var orderStatusElem = document.getElementById('orderStatus');
	var orderlistElem = document.getElementsByClassName('orderlist');
	$(orderStatusElem).unbind('change', this.statusChangeHandler);
	$(orderlistElem).unbind('click', this.orderClickHandler);
};

DashboardController.prototype.addEventListeners = function(){
	this.removeEventListeners();
	var orderStatusElem = document.getElementById('orderStatus');
	var orderlistElem = document.getElementsByClassName('orderlist');
	var rejectorderElem = document.getElementsByClassName('rejectorder')[0];
	var deliverorderElem = document.getElementsByClassName('deliverorder')[0];
	$(orderStatusElem).bind('change', { context: this}, this.statusChangeHandler);
	$(orderlistElem).bind('click', { context: this}, this.orderClickHandler);
	var showonmapElem = document.getElementsByClassName('showonmap')[0];
	$(showonmapElem).bind('click', { context: this}, this.showMapHandler);
	$(rejectorderElem).bind('click', {context: this}, this.rejectOrderHandler);
	$(deliverorderElem).bind('click', {context: this}, this.deliverorderHandler);
}

DashboardController.prototype.rejectOrderHandler = function(event){
	var that = event.data.context;

	var reqObj = {
		'type': 'POST',
		'url': '/rejectorder',
		'data': {'id': event.target.id}
	};

	that.serviceLayerObj.fetchData(reqObj).done(function(pendingId){
		that.fetchOrdersByStatus($(event.target).data('pending'));
	});
};

DashboardController.prototype.deliverorderHandler = function(event){
	var that = event.data.context;

	var reqObj = {
		'type': 'POST',
		'url': '/deliverorder',
		'data': {'id': event.target.id}
	};

	that.serviceLayerObj.fetchData(reqObj).done(function(pendingId){
		that.fetchOrdersByStatus($(event.target).data('pending'));
	});
};

DashboardController.prototype.showMapHandler = function(event){
	var that = event.data.context;
	document.getElementById('map').style.display = 'block';
	document.getElementById('orderdetails').style.display = 'none';
	that.showMap(event.target.id);
};

DashboardController.prototype.showMap = function(latlngStr){
	var latlngObj = {};
	var lat = parseFloat((latlngStr)[0]);
	var lng = parseFloat((latlngStr)[1]);
	latlngObj.lat = lat;
	latlngObj.lng = lng;

	var map = new google.maps.Map(document.getElementById('map'), {
		zoom: 10,
		center: latlngObj
	});

	var marker = new google.maps.Marker({
		position: latlngObj,
		map: map,
		title: ''
	});
};

DashboardController.prototype.showClusters = function(){
	var reqObj = {
		'type': 'GET',
		'url': '/fetchlatlngs'
	};

	this.serviceLayerObj.fetchData(reqObj).done(function(response){
		console.log(response);
		var response = JSON.parse(response);
		if(response.length){
			var latlngArr = [];
			for(var i = 0; i < response.length; i++){
				var latlngStr = response[i].loclatlong;
				var latlngObj = {};
				var lat = parseFloat((latlngStr)[0]);
				var lng = parseFloat((latlngStr)[1]);
				latlngObj.lat = lat;
				latlngObj.lng = lng;
				latlngArr.push(latlngObj);				
			}
			var map = new google.maps.Map(document.getElementById('fullmap'), {
				zoom: 14,
				center: latlngArr[0]
			});
			// var markers = [];
	        for (var i = 0; i < latlngArr.length; i++) {
	        	var lObj = latlngArr[i];
	        		new google.maps.Marker({
						position: lObj,
						map: map,
						title: ''
					});
	        	// var latLng = new google.maps.LatLng(lObj.lat, lObj.lng);
	        	// var marker = new google.maps.Marker({
	         //    	position: latLng
	        	// });
	         //  	markers.push(marker);
	        }
	        // var markerCluster = new MarkerClusterer(map, markers);

		}
		document.getElementById('fullmap').style.display = 'block';
		document.getElementsByClassName('content')[0].style.display = 'none';
		document.getElementsByClassName('sidebar')[0].style.display = 'none';
	}).fail(function(err){
		console.log(err);
	});	
};

DashboardController.prototype.orderClickHandler = function(event){
	$('.orderlist').removeClass('active');
	var that = event.data.context;
	document.getElementById('orderdetails').style.display = 'block';
	document.getElementById('map').style.display = 'none';
	$(event.target).addClass('active');
	var reqObj = {
		'type': 'POST',
		'url': '/fetchorderdetails',
		'data': {'id': event.target.id}
	};

	that.serviceLayerObj.fetchData(reqObj).done(function(response){
		//console.log(response);
		document.getElementById('orderdetails').innerHTML = response;
		that.addEventListeners();
	}).fail(function(err){
		console.log(err);
	});	
};

DashboardController.prototype.statusChangeHandler = function(event){
	var that = event.data.context;
	document.getElementById('orders').innerHTML = '';
	that.fetchOrdersByStatus(event.target.value);
};

DashboardController.prototype.fetchOrdersByStatus = function(statusID){
	document.getElementById('orders').innerHTML = '';
	document.getElementById('orderdetails').innerHTML = '';
	var that = this;console.log(statusID);
	
	var reqObj = {
		'type': 'POST',
		'url': '/fetchordersbystatus',
		'data': {'id': statusID}
	};

	this.serviceLayerObj.fetchData(reqObj).done(function(response){
		document.getElementById('orders').innerHTML = response;
		that.addEventListeners();
		console.log($('.orderlist')[0]);
		$($('.orderlist')[0]).trigger('click');
	}).fail(function(err){
		console.log(err);
	});
};

DashboardController.prototype.fetchStatuses = function(){
	var that = this;
	
	var reqObj = {
		'type': 'GET',
		'url': '/fetchstatuses'
	};

	this.serviceLayerObj.fetchData(reqObj).done(function(response){
		//console.log(response);
		//var response = JSON.parse(response);
		document.getElementById('orderstatusparent').innerHTML = response;
		that.fetchOrdersByStatus(document.getElementById('orderStatus').value);
		document.getElementsByClassName('content')[0].style.display = 'block';
		document.getElementsByClassName('sidebar')[0].style.display = 'block';

		that.addEventListeners();
		// if(response.length > 0){
		// 	for(var i = 0; i < response.length; i++){
		// 		if(response[i].status == 'Pending'){
		// 			that.fetchOrdersByStatus(response[i]._id);					
		// 		}
		// 	}
		// }
	}).fail(function(err){
		console.log(err);
	});
};
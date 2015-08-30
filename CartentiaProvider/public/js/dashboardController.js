'use strict';

function DashboardController(){
	this.serviceLayerObj = new ServiceLayer();
	this.fetchStatuses();
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
	$(orderStatusElem).bind('change', { context: this}, this.statusChangeHandler);
	$(orderlistElem).bind('click', { context: this}, this.orderClickHandler);
	var showonmapElem = document.getElementsByClassName('showonmap')[0];
	$(showonmapElem).bind('click', { context: this}, this.showMapHandler);
}

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

DashboardController.prototype.orderClickHandler = function(event){
	var that = event.data.context;
	document.getElementById('orderdetails').style.display = 'block';
	document.getElementById('map').style.display = 'none';
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
	console.log('fetch orders...');
	var that = this;
	
	var reqObj = {
		'type': 'POST',
		'url': '/fetchordersbystatus',
		'data': {'id': statusID}
	};

	this.serviceLayerObj.fetchData(reqObj).done(function(response){
		//console.log(response);
		document.getElementById('orders').innerHTML = response;
		that.addEventListeners();
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
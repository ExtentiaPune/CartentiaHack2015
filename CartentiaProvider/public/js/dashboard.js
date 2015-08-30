'use strict';

window.onload = init;
var dashboardControllerObject = null;

function init(){
	console.log('here in');
	dashboardControllerObject = new DashboardController();
}

function showfullmap(){
	dashboardControllerObject.showClusters();
}

function initMap(){
	
}
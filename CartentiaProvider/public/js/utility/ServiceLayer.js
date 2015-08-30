'use strict';

function ServiceLayer(){

}

ServiceLayer.prototype.fetchData = function(reqObj){
	var $defferred = new $.Deferred();
	var xmlhttpRequestObj = new XMLHttpRequest();
	xmlhttpRequestObj.open(reqObj.type, reqObj.url);
	xmlhttpRequestObj.setRequestHeader('Content-Type', 'application/json');
	xmlhttpRequestObj.onreadystatechange = function(){
		//console.log(xmlhttpRequestObj);
		if(xmlhttpRequestObj.status == 200 && xmlhttpRequestObj.readyState == 4){
			$defferred.resolve(xmlhttpRequestObj.responseText);
		}
	}
	if(reqObj.data){
		xmlhttpRequestObj.send(JSON.stringify(reqObj.data));
	} else{
		xmlhttpRequestObj.send();
	}
	return $defferred.promise();
};
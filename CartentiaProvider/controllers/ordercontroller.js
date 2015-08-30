var orderModel = require('../model/orders');
var orderStatusModel = require('../model/orderstatus');
var Promise = require('promise');
var CONSTANT = require('../utilities/Constant').CONSTANT;

function OrderController(){

}

OrderController.prototype.fetchOrderLocations = function(){
	return new Promise(function(resolve, reject){
		orderModel.find({}, {loclatlong: 1, _id: 0}, function(err, locations){
			if(err) reject(err);
			else{
				resolve(locations);
			}
		});
	});
};

OrderController.prototype.rejectOrder = function(orderId){
	return new Promise(function(resolve, reject){
		orderStatusModel.find({}, function(err, statuses){
			if(err) reject(err);
			else{
				if(statuses.length){
					var declineId = null;
					var pendingId = null;
					for(var i = 0; i < statuses.length; i++){
						if(statuses[i].status == 'Pending'){
							pendingId = statuses[i]._id;
						}
						if(statuses[i].status == 'Declined'){
							declineId = statuses[i]._id;
						}
					}

					if(declineId){
						orderModel.update({_id: orderId}, {statusID: declineId}, function(err, order){
							if (err) reject(err)
							else{
								resolve(pendingId);
							}
						});
					}
				}
			}
		});
	});
};

OrderController.prototype.deliverOrder = function(orderId){
	return new Promise(function(resolve, reject){
		orderStatusModel.find({}, function(err, statuses){
			if(err) reject(err);
			else{
				if(statuses.length){
					var deliveredId = null;
					var pendingId = null;
					for(var i = 0; i < statuses.length; i++){
						if(statuses[i].status == 'Pending'){
							pendingId = statuses[i]._id;
						}
						if(statuses[i].status == 'Delivered'){
							deliveredId = statuses[i]._id;
						}
					}

					if(deliveredId){
						orderModel.update({_id: orderId}, {statusID: deliveredId}, function(err, order){
							if (err) reject(err)
							else{
								resolve(pendingId);
							}
						});
					}
				}
			}
		});
	});
};

module.exports = { 'OrderController': new OrderController() }
var orderdetailModel = require('../model/orderdetails');
var Promise = require('promise');
var CONSTANT = require('../utilities/Constant').CONSTANT;

function OrderDetailsController(){

}

OrderDetailsController.prototype.getDetailsByOrder = function(orderId){
	return new Promise(function(resolve, reject){
		orderdetailModel.find({orderID: orderId}, function(err, orders){
			if(err) reject(err);
			else{
				var options = [{
					path: 'orderID',
					model: CONSTANT.SCHEMA.ORDER
				},{
					path: 'productID',
					model: CONSTANT.SCHEMA.PRODUCT					
				}]

				orderdetailModel.populate(orders, options, function(err, order){
					if (err) reject(err);
					else{
						var opts = [{
							path: 'orderID.statusID',
							model: CONSTANT.SCHEMA.STATUS
						}, {
							path: 'orderID.userID',
							model: CONSTANT.SCHEMA.USER							
						}];

						orderdetailModel.populate(order, opts, function(err, odr){
							resolve(odr);	
						});
					}
				});
			}
		});
	});
}

module.exports = { 'OrderDetailsController': new OrderDetailsController() }
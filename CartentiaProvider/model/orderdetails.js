var restful = require('node-restful');
var mongoose = restful.mongoose;
var CONSTANT = require('../utilities/Constant').CONSTANT;

var orderDetailsSchema = mongoose.Schema({
	ID: Number,
	orderID: { type: mongoose.Schema.Types.ObjectId, ref: CONSTANT.SCHEMA.ORDER },
	productID: { type: mongoose.Schema.Types.ObjectId, ref: CONSTANT.SCHEMA.PRODUCT },
	quantity: Number,
	price: Number
});

module.exports = restful.model(CONSTANT.SCHEMA.ORDERDETAILS, orderDetailsSchema);
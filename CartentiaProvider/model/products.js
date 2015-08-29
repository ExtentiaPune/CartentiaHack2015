var restful = require('node-restful');
var mongoose = restful.mongoose;
var CONSTANT = require('../utilities/Constant').CONSTANT;

var productSchema = mongoose.Schema({
	productID: Number,
	productName: String,
	productImage: String,
	productDescription: String,
	productCategory: String,
	defaultQty: String,
	price: Number
});

module.exports = restful.model(CONSTANT.SCHEMA.PRODUCT, productSchema);
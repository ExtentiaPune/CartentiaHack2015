var restful = require('node-restful');
var mongoose = restful.mongoose;
var CONSTANT = require('../utilities/Constant').CONSTANT;

var cartSchema = mongoose.Schema({
	ID: Number,
	userID: { type: mongoose.Schema.Types.ObjectId, ref: CONSTANT.SCHEMA.USER },
	productID: { type: mongoose.Schema.Types.ObjectId, ref: CONSTANT.SCHEMA.PRODUCT },
	groupID: { type: mongoose.Schema.Types.ObjectId, ref: CONSTANT.SCHEMA.GROUP }
});

module.exports = restful.model(CONSTANT.SCHEMA.CART, cartSchema);
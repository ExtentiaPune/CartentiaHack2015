var restful = require('node-restful');
var mongoose = restful.mongoose;
var CONSTANT = require('../utilities/Constant').CONSTANT;

var orderSchema = mongoose.Schema({
	ID: Number,
	date: String,
	userID: { type: mongoose.Schema.Types.ObjectId, ref: CONSTANT.SCHEMA.USER },
	statusID: { type: mongoose.Schema.Types.ObjectId, ref: CONSTANT.SCHEMA.STATUS },
	groupID: { type: mongoose.Schema.Types.ObjectId, ref: CONSTANT.SCHEMA.GROUP },
	loclatlong: String
});

module.exports = restful.model(CONSTANT.SCHEMA.ORDER, orderSchema);
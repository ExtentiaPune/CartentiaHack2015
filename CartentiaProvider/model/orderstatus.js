var restful = require('node-restful');
var mongoose = restful.mongoose;
var CONSTANT = require('../utilities/Constant').CONSTANT;

var orderstatusSchema = mongoose.Schema({
	ID: Number,
	status: String
});

module.exports = restful.model(CONSTANT.SCHEMA.STATUS, orderstatusSchema);
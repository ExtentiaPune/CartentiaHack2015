var restful = require('node-restful');
var mongoose = restful.mongoose;
var CONSTANT = require('../utilities/Constant').CONSTANT;

var groupSchema = mongoose.Schema({
	ID: Number,
	name: String
});

module.exports = restful.model(CONSTANT.SCHEMA.GROUP, groupSchema);
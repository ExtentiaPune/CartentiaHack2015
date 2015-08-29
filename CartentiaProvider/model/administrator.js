var restful = require('node-restful');
var mongoose = restful.mongoose;
var CONSTANT = require('../utilities/Constant').CONSTANT;

var administratorSchema = mongoose.Schema({
	id: Number,
	name: String,
	username: String,
	password: String
});

module.exports = restful.model(CONSTANT.SCHEMA.ADMINISTRATOR, administratorSchema);
var restful = require('node-restful');
var mongoose = restful.mongoose;
var CONSTANT = require('../utilities/Constant').CONSTANT;

var userSchema = mongoose.Schema({
	ID: Number,
	name: String,
	username: String,
	password: String,
	groupID: { type: mongoose.Schema.Types.ObjectId, ref: CONSTANT.SCHEMA.GROUP },
	role: { type: mongoose.Schema.Types.ObjectId, ref: CONSTANT.SCHEMA.ROLE },
	regID: String,
	parentID: Number
});

module.exports = restful.model(CONSTANT.SCHEMA.USER, userSchema);
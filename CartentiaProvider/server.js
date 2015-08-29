var express = require('express');
var app = express();
var restful = require('node-restful');
var mongoose = restful.mongoose;
var bodyParser = require('body-parser');
var seeder = require('./helper/Seeder.js');
var path = require('path');


mongoose.connect('mongodb://localhost/cartentia');

mongoose.connection.on('open', function(){
	seeder.PopulateDB;
});



// Models
var adminModel = require('./model/administrator');
var userModel = require('./model/user');
var groupModel = require('./model/group');
var roleModel = require('./model/role');
var statusModel = require('./model/orderstatus');
var orderModel = require('./model/orders');
var productModel = require('./model/products');
var orderDetailModel = require('./model/orderdetails');
var cartModel = require('./model/cart');

// Create Methods
userModel.methods(['get', 'post', 'delete']);
groupModel.methods(['get', 'post', 'delete']);
roleModel.methods(['get', 'post', 'delete']);
statusModel.methods(['get', 'post', 'delete']);
orderModel.methods(['get', 'post', 'delete']);
productModel.methods(['get', 'post', 'delete']);
cartModel.methods(['get', 'post', 'delete']);
orderDetailModel.methods(['get', 'post', 'delete']);

// Register APIs
userModel.register(app, '/api/users');
groupModel.register(app, '/api/groups');
roleModel.register(app, '/api/roles');
statusModel.register(app, '/api/orderstatus');
orderModel.register(app, '/api/orders');
productModel.register(app, '/api/products');
cartModel.register(app, '/api/cart');
orderDetailModel.register(app, '/api/orderdetails');

app.use(bodyParser.urlencoded({'extended':'true'}));
app.use(bodyParser.json());
app.use(express.static(path.join(__dirname + '/public')))
app.set('view engine', 'ejs');

app.get('/', function(req, res){
	res.sendfile('/index.html');
});

app.post('/validateuser', function(req, res){
	adminModel.find({username: req.body.username, password: req.body.password}, function(err, admin){
		if(admin.length > 0){
			res.redirect('dashboard');
		}
	});
});

app.get('/dashboard', function(req, res){
	res.render('dashboard');
});

app.listen(4001);
console.log('Server listening at port 4001');
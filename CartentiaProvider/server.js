var express = require('express');
var app = express();
var restful = require('node-restful');
var mongoose = restful.mongoose;
var bodyParser = require('body-parser');
var seeder = require('./helper/Seeder.js');
var path = require('path');
var gcm = require('node-gcm');
var message = new gcm.Message();


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

// Controllers
var orderDetailsControllerObj = require('./controllers/orderdetailcontroller').OrderDetailsController;
var orderControllerObj = require('./controllers/ordercontroller').OrderController;

// Create Methods
userModel.methods(['get', 'post', 'delete']);
groupModel.methods(['get', 'post', 'delete']);
roleModel.methods(['get', 'post', 'delete']);
statusModel.methods(['get', 'post', 'delete']);
orderModel.methods(['get', 'post', 'delete']);
productModel.methods(['get', 'post', 'delete']);
cartModel.methods(['get', 'delete']);
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

app.post('/fetchorderdetails', function(req, res){
	orderDetailsControllerObj.getDetailsByOrder(req.body.id).done(function(orderdetails){
		res.render('partials/orderdetails', {data: orderdetails});
	});
});

app.post('/fetchordersbystatus', function(req, res){
	orderModel.find({statusID: req.body.id}, function(err, orders){
		res.render('partials/order', {data: orders});
	});
});

app.get('/fetchstatuses', function(req, res){
	statusModel.find({}, function(err, statuses){
		res.render('partials/orderstatus', {data: statuses});
	});
});

app.get('/fetchlatlngs', function(req, res){
	orderControllerObj.fetchOrderLocations().done(function(locations){
		res.send(locations);
	});
});

app.post('/rejectorder', function(req, res){
	orderControllerObj.rejectOrder(req.body.id).done(function(result){
		res.send(result);
	});
});

app.post('/deliverorder', function(req, res){
	orderControllerObj.deliverOrder(req.body.id).done(function(result){
		message.addData('Order Status', 'Your order will be delivered shortly. Thanks for shopping at Cartentia.');

		var regIds = ['dlVRGVKpJNA:APA91bGnVh1YA3hVagBgh0U5JQgjnlZc2Jy-Ip8WC3G_x7j6KNuBe8Q8GA8o0nTmMWZ27WCwU4jXidi5qIoYS3Owi8R2sdq2C7K1Ii11ljTdkQkzPmYsCwze0qoygO3lkP6Om1GtJEuQ'];

		// Set up the sender with you API key
		var sender = new gcm.Sender('AIzaSyB6s0Tqe7jPhF17rgkj_td8e0uKjeRVC-8');

		//Now the sender can be used to send messages
		sender.send(message, regIds, function (err, result) {
		    if(err) console.error(err);
		});
		res.send(result);
	});
});

app.post('/cart', function(req, res){
console.log(req.body);
	var cartModelObj = new cartModel(req.body);

	cartModelObj.save(function(err, cart){
		if(err) res.send(err);
		else res.send(cart);
	});
});

app.post('/placeorder', function(req, res){
	orderModel.findOne({ }, {ID: 1}).sort('-ID').exec(function (err, member) {

    var idVal = member.ID + 1;
	console.log(idVal);
	var odrObj = {
		ID: idVal,
		date: new Date().toDateString(),
		userID: req.body.userID,
		statusID: req.body.statusID,
		groupID: req.body.groupID,
		loclatlong: req.body.loclatlong		
	};
	var orderModelObj = new orderModel(odrObj);
	orderModelObj.save(function(err, order){
		var counter = 1;
		for(var i = 0; i < req.body.products.length; i++){
			var obj = req.body.products[i];
			obj.orderID = order._id;
			var odrDetailsObj = new orderDetailModel(obj);
			odrDetailsObj.save(function(err, det){
				counter++;
				if(counter == req.body.products.length){
					console.log('in delete...');
					cartModel.find({}).remove().exec(function(err, resp){
						res.sendStatus(200);
					});
				}
			});
		};
	});
  });
});

app.get('/dashboard', function(req, res){
	res.render('dashboard');
});

app.listen(8001);
console.log('Server listening at port 8001');
var restful = require('node-restful');
var mongoose = restful.mongoose;
var adminModel = require('../model/administrator');
var userModel = require('../model/user');
var groupModel = require('../model/group');
var roleModel = require('../model/role');
var statusModel = require('../model/orderstatus');
var orderModel = require('../model/orders');
var productModel = require('../model/products');
var orderDetailModel = require('../model/orderdetails');
var cartModel = require('../model/cart');
var async = require('async');
var Promise = require('promise');
var CONSTANT = require('../utilities/Constant').CONSTANT;

function PopulateDB(){
	this.writeToDB();
}

PopulateDB.prototype.writeToDB = function(){
	return new Promise(function(resolve, reject){
		console.log('Writing to DB....');

		var locals = {};

		var dateString = new Date().toDateString();

		var administrators = [{"id":1,"name":"Abdi Shaikh","username":"abdi","password":"admin123"}];
		var products = [{"productID":200000001,"productName":"Amul Milk","productImage":"/images/amulmilk.png","productDescription":"","productCategory":"Milk","defaultQty":"1 ltr","price":56},{"productID":200000002,"productName":"Nestle Milk","productImage":"/images/nestlemilk.png","productDescription":"","productCategory":"Milk","defaultQty":"1 ltr","price":60},{"productID":200000003,"productName":"Bourbon Biscuits","productImage":"/images/bourbonbiscuits.png","productDescription":"","productCategory":"Biscuits","defaultQty":"120 gm","price":19},{"productID":200000004,"productName":"Britannia Biscuits","productImage":"/images/britanniabiscuits.png","productDescription":"","productCategory":"Biscuits","defaultQty":"110 gm","price":19},{"productID":200000005,"productName":"Parle-G Biscuits","productImage":"/images/parle-gbiscuits.png","productDescription":"","productCategory":"Biscuits","defaultQty":"70 gm","price":5},{"productID":200000006,"productName":"Amul Butter","productImage":"/images/amulbutter.png","productDescription":"","productCategory":"Butter","defaultQty":"100 gm","price":37},{"productID":200000007,"productName":"Nutralite Butter","productImage":"/images/nutralitebutter.png","productDescription":"","productCategory":"Butter","defaultQty":"100 gm","price":32},{"productID":200000008,"productName":"DanOne Yoghurt","productImage":"/images/danoneyoghurt.png","productDescription":"","productCategory":"Yoghurt","defaultQty":"80 gm","price":35},{"productID":200000009,"productName":"Nestle Yoghurt","productImage":"/images/nestleyoghurt.png","productDescription":"","productCategory":"Yoghurt","defaultQty":"100 gm","price":30},{"productID":200000010,"productName":"Kissan Jam","productImage":"/images/kissanjam.png","productDescription":"","productCategory":"Jam","defaultQty":"200 gm","price":50},{"productID":200000011,"productName":"Mala's Jam","productImage":"/images/malasjam.png","productDescription":"","productCategory":"Jam","defaultQty":"200 gm","price":35},{"productID":2000000012,"productName":"Parry's Sugar","productImage":"/images/parryssugar.png","productDescription":"","productCategory":"Sugar","defaultQty":"1 Kg","price":60},{"productID":200000013,"productName":"Organic Sugar","productImage":"/images/organicsugar.png","productDescription":"","productCategory":"Sugar","defaultQty":"1 Kg","price":120},{"productID":200000014,"productName":"Fresh Eggs","productImage":"/images/fresheggs.png","productDescription":"","productCategory":"Eggs","defaultQty":"6 pcs","price":28},{"productID":200000015,"productName":"Best Eggs","productImage":"/images/besteggs.png","productDescription":"","productCategory":"Eggs","defaultQty":"6 pcs","price":48},{"productID":200000016,"productName":"Gemini Sunflower Oil","productImage":"/images/geminisunfloweroil.png","productDescription":"","productCategory":"Oil","defaultQty":"5 ltr","price":465},{"productID":200000017,"productName":"Saffola Sunflower Oil","productImage":"/images/amulmilk.png","productDescription":"","productCategory":"Oil","defaultQty":"5 ltr","price":350},{"productID":200000018,"productName":"Aashirwad Atta","productImage":"/images/aashirwadatta.png","productDescription":"","productCategory":"Wheat","defaultQty":"10 Kgs","price":339},{"productID":200000019,"productName":"PillsBury Chakki Fresh Atta","productImage":"/images/pillsburychakkifreshatta.png","productDescription":"","productCategory":"Wheat","defaultQty":"10 kgs","price":309},{"productID":200000020,"productName":"Charminar Basmati Rice","productImage":"/images/charminarbasmatirice.png","productDescription":"","productCategory":"Rice","defaultQty":"5 Kg","price":375},{"productID":200000021,"productName":"India Gate Basmati Rice","productImage":"/images/indiagatebasmatirice.png","productDescription":"","productCategory":"Rice","defaultQty":"5 Kg","price":325}]
		var orderStatuses = [{"ID":1,"status":"Delivered"},{"ID":2,"status":"Declined"},{"ID":3,"status":"Pending"}]
		var groups = [{"ID":1,"name":"Shaikh Family"},{"ID":2,"name":"Bhosale Family"}]
		var providers = [{"Name":"Large Basket","productID":200000001,"price":58},{"Name":"My Banaya","productID":200000001,"price":59},{"Name":"D Mart","productID":200000001,"price":56},{"Name":"D Mart","productID":200000002,"price":60},{"Name":"My Banaya","productID":200000002,"price":58},{"Name":"Large Basket","productID":200000002,"price":59},{"Name":"D Mart","productID":200000003,"price":19},{"Name":"My Banaya","productID":200000003,"price":19.5},{"Name":"Large Basket","productID":200000003,"price":18.5},{"Name":"D Mart","productID":200000004,"price":19},{"Name":"My Banaya","productID":200000004,"price":19.5},{"Name":"Large Basket","productID":200000004,"price":18.5},{"Name":"D Mart","productID":200000005,"price":5},{"Name":"My Banaya","productID":200000005,"price":5},{"Name":"Large Basket","productID":200000005,"price":5},{"Name":"D Mart","productID":200000006,"price":37},{"Name":"My Banaya","productID":200000006,"price":37.5},{"Name":"Large Basket","productID":200000006,"price":36.5},{"Name":"D Mart","productID":200000007,"price":32.5},{"Name":"My Banaya","productID":200000007,"price":30.5},{"Name":"Large Basket","productID":200000007,"price":31.5},{"Name":"D Mart","productID":200000008,"price":35},{"Name":"My Banaya","productID":200000008,"price":35},{"Name":"Large Basket","productID":200000008,"price":35},{"Name":"D Mart","productID":200000009,"price":30},{"Name":"My Banaya","productID":200000009,"price":30.5},{"Name":"Large Basket","productID":200000009,"price":30.5},{"Name":"D Mart","productID":200000010,"price":50},{"Name":"My Banaya","productID":200000010,"price":49.5},{"Name":"Grren Tokri","productID":200000010,"price":47},{"Name":"D Mart","productID":200000011,"price":35},{"Name":"My Banaya","productID":200000011,"price":36},{"Name":"Green Tokri","productID":200000011,"price":33.12},{"Name":"D Mart","productID":200000012,"price":59},{"Name":"My Banaya","productID":200000012,"price":59.5},{"Name":"Large Basket","productID":200000012,"price":50},{"Name":"D Mart","productID":200000013,"price":120},{"Name":"My Banaya","productID":200000013,"price":119.5},{"Name":"Large Basket","productID":200000013,"price":121.5},{"Name":"D Mart","productID":200000014,"price":28},{"Name":"My Banaya","productID":200000014,"price":27.5},{"Name":"Green Tokri","productID":200000014,"price":25},{"Name":"D Mart","productID":200000015,"price":48},{"Name":"My Banaya","productID":200000015,"price":48},{"Name":"Green Tokri","productID":200000015,"price":47.5},{"Name":"D Mart","productID":200000016,"price":465},{"Name":"My Banaya","productID":200000016,"price":450},{"Name":"Large Basket","productID":200000016,"price":463},{"Name":"D Mart","productID":200000017,"price":350},{"Name":"My Banaya","productID":200000017,"price":348},{"Name":"Large Basket","productID":200000017,"price":351},{"Name":"D Mart","productID":200000018,"price":339},{"Name":"My Banaya","productID":200000018,"price":339},{"Name":"Large Basket","productID":200000018,"price":339},{"Name":"D Mart","productID":200000019,"price":309},{"Name":"My Banaya","productID":200000019,"price":310},{"Name":"Large Basket","productID":200000019,"price":309},{"Name":"D Mart","productID":200000020,"price":375},{"Name":"My Banaya","productID":200000020,"price":372},{"Name":"Large Basket","productID":200000020,"price":374},{"Name":"D Mart","productID":200000021,"price":325},{"Name":"My Banaya","productID":200000021,"price":325},{"Name":"Large Basket","productID":200000021,"price":325}]
		var users = [{"ID":1,"name":"Primary User","username":"nfcuser1","password":"user123","role":1,"parentID":null,"groupID":1,"regID":"APA91bE1A3XaWUFMmx5xjx4SSPmCWtaE98Tywetb_dknhu896PEkE626kxp07WS4KMuApxRzIGZzLJFmnllWyWsSh6yT9wP-HQ76mPo6pdcfwgcIOfrJE7oSOnD1j1rgyF8s353AqQJn"},{"ID":2,"name":"Secondary User","username":"nfcuser2","password":"user123","role":2,"parentID":1,"groupID":1,"regID":"APA91bE1A3XaWUFMmx5xjx4SSPmCWtaE98Tywetb_dknhu896PEkE626kxp07WS4KMuApxRzIGZzLJFmnllWyWsSh6yT9wP-HQ76mPo6pdcfwgcIOfrJE7oSOnD1j1rgyF8s353AqQJn"}]
		var roles = [{"ID":1,"Name":"Primary"},{"ID":2,"Name":"Secondary"}]
		var orders = [{"ID":1,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.957386,77.597766"},{"ID":2,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.940824,77.629352"},{"ID":3,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.914179, 77.578754"},{"ID":4,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.891087, 77.546139"},{"ID":5,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.876696, 77.700977"},{"ID":6,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.968050, 77.684155"},{"ID":7,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.982436, 77.663555"},{"ID":8,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.837869, 77.696857"},{"ID":9,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.926560, 77.767925"},{"ID":10,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.960689, 77.765179"},{"ID":11,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.971730, 77.674198"},{"ID":12,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.949648, 77.757282"},{"ID":13,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.979425, 77.682781"},{"ID":14,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.999479, 77.634941"},{"ID":15,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"13.012134, 77.652663"},{"ID":16,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"13.003501, 77.651813"},{"ID":17,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.991851, 77.666319"},{"ID":18,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"13.010715, 77.651328"},{"ID":19,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"13.004979, 77.665712"},{"ID":20,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"13.003619, 77.650660"},{"ID":21,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"13.002200, 77.626748"},{"ID":22,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.936933, 77.491550"},{"ID":23,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.911501, 77.497044"},{"ID":24,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.852597, 77.462025"},{"ID":25,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.934256, 77.445545"},{"ID":26,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"13.007860, 77.436619"},{"ID":27,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.973638,77.607164"},{"ID":28,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.971840,77.606134"},{"ID":29,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.969916,77.599998"},{"ID":30,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.967700,77.602143"},{"ID":31,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.967156,77.593431"},{"ID":32,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.969498,77.607722"},{"ID":33,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.967867,77.609868"},{"ID":34,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.974600,77.606006"},{"ID":35,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.973764,77.609782"},{"ID":36,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.972049,77.613301"},{"ID":37,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.973931,77.607336"},{"ID":38,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.977109,77.612314"},{"ID":39,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.971338,77.599783"},{"ID":40,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.969289,77.597337"},{"ID":41,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.964438,77.613215"},{"ID":42,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.976231,77.618108"},{"ID":43,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.974349,77.616735"},{"ID":44,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.955655,77.616735"},{"ID":45,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.953689,77.622056"},{"ID":46,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.952267,77.614117"},{"ID":47,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.953062,77.623086"},{"ID":48,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.951347,77.599869"},{"ID":49,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.940824,77.599053"},{"ID":50,"date":dateString,"userID":1,"statusID":3,"groupID":1,"loclatlong":"12.961217,77.603087"}]
		var orderdetails = [{"ID":1,"orderID":1,"productID":200000002,"quantity":1,"price":55},{"ID":2,"orderID":1,"productID":200000016,"quantity":1,"price":463},{"ID":3,"orderID":1,"productID":200000017,"quantity":2,"price":700},{"ID":4,"orderID":2,"productID":200000013,"quantity":1,"price":119.5},{"ID":5,"orderID":2,"productID":200000014,"quantity":3,"price":75},{"ID":6,"orderID":2,"productID":200000015,"quantity":1,"price":48},{"ID":7,"orderID":3,"productID":200000018,"quantity":1,"price":339},{"ID":8,"orderID":3,"productID":200000017,"quantity":2,"price":700},{"ID":9,"orderID":3,"productID":200000017,"quantity":1,"price":348},{"ID":10,"orderID":4,"productID":200000001,"quantity":1,"price":58},{"ID":11,"orderID":4,"productID":200000003,"quantity":2,"price":38},{"ID":12,"orderID":4,"productID":200000003,"quantity":2,"price":39},{"ID":13,"orderID":5,"productID":200000001,"quantity":1,"price":58},{"ID":14,"orderID":5,"productID":200000001,"quantity":1,"price":59},{"ID":15,"orderID":5,"productID":200000002,"quantity":2,"price":120},{"ID":16,"orderID":6,"productID":200000006,"quantity":1,"price":37.5},{"ID":17,"orderID":6,"productID":200000007,"quantity":1,"price":32.5},{"ID":18,"orderID":6,"productID":200000007,"quantity":2,"price":61},{"ID":19,"orderID":7,"productID":200000006,"quantity":1,"price":37.5},{"ID":20,"orderID":7,"productID":200000008,"quantity":1,"price":35},{"ID":21,"orderID":7,"productID":200000010,"quantity":2,"price":100},{"ID":22,"orderID":8,"productID":200000011,"quantity":1,"price":36},{"ID":23,"orderID":8,"productID":200000012,"quantity":2,"price":100},{"ID":24,"orderID":8,"productID":200000008,"quantity":1,"price":35},{"ID":25,"orderID":9,"productID":200000007,"quantity":1,"price":30.5},{"ID":27,"orderID":9,"productID":200000009,"quantity":2,"price":60},{"ID":28,"orderID":10,"productID":200000021,"quantity":1,"price":325},{"ID":29,"orderID":10,"productID":200000020,"quantity":1,"price":375},{"ID":30,"orderID":10,"productID":200000019,"quantity":2,"price":618},{"ID":31,"orderID":11,"productID":200000015,"quantity":1,"price":47.5},{"ID":32,"orderID":11,"productID":200000016,"quantity":1,"price":463},{"ID":33,"orderID":11,"productID":200000019,"quantity":3,"price":930},{"ID":34,"orderID":12,"productID":200000015,"quantity":1,"price":47.5},{"ID":35,"orderID":12,"productID":200000017,"quantity":2,"price":702},{"ID":36,"orderID":12,"productID":200000019,"quantity":3,"price":930},{"ID":37,"orderID":13,"productID":200000014,"quantity":1,"price":27.5},{"ID":38,"orderID":13,"productID":200000017,"quantity":2,"price":702},{"ID":39,"orderID":13,"productID":200000019,"quantity":3,"price":930},{"ID":40,"orderID":14,"productID":200000009,"quantity":1,"price":30.5},{"ID":41,"orderID":14,"productID":200000010,"quantity":4,"price":200},{"ID":42,"orderID":14,"productID":200000008,"quantity":2,"price":70},{"ID":43,"orderID":15,"productID":200000011,"quantity":1,"price":33.12},{"ID":44,"orderID":15,"productID":200000013,"quantity":1,"price":119.5},{"ID":45,"orderID":15,"productID":200000008,"quantity":2,"price":70},{"ID":46,"orderID":16,"productID":200000015,"quantity":1,"price":48},{"ID":47,"orderID":16,"productID":200000017,"quantity":1,"price":348},{"ID":48,"orderID":16,"productID":200000017,"quantity":2,"price":620},{"ID":49,"orderID":17,"productID":200000018,"quantity":1,"price":339},{"ID":50,"orderID":17,"productID":200000017,"quantity":1,"price":348},{"ID":51,"orderID":17,"productID":200000017,"quantity":2,"price":620},{"ID":52,"orderID":18,"productID":200000015,"quantity":1,"price":48},{"ID":53,"orderID":18,"productID":200000015,"quantity":1,"price":47.5},{"ID":54,"orderID":18,"productID":200000016,"quantity":2,"price":900},{"ID":55,"orderID":19,"productID":200000013,"quantity":1,"price":119.5},{"ID":56,"orderID":19,"productID":200000014,"quantity":1,"price":27.5},{"ID":57,"orderID":19,"productID":200000016,"quantity":1,"price":465},{"ID":58,"orderID":21,"productID":200000013,"quantity":3,"price":360},{"ID":59,"orderID":21,"productID":200000014,"quantity":1,"price":27.5},{"ID":60,"orderID":21,"productID":200000016,"quantity":2,"price":900},{"ID":61,"orderID":22,"productID":200000013,"quantity":1,"price":121.5},{"ID":62,"orderID":22,"productID":200000014,"quantity":1,"price":27.5},{"ID":63,"orderID":22,"productID":200000017,"quantity":2,"price":702},{"ID":64,"orderID":20,"productID":200000013,"quantity":4,"price":480},{"ID":65,"orderID":20,"productID":200000014,"quantity":1,"price":27.5},{"ID":66,"orderID":20,"productID":200000019,"quantity":2,"price":618},{"ID":67,"orderID":23,"productID":200000001,"quantity":2,"price":118},{"ID":68,"orderID":23,"productID":200000003,"quantity":3,"price":57},{"ID":69,"orderID":23,"productID":200000002,"quantity":1,"price":58},{"ID":70,"orderID":24,"productID":200000001,"quantity":2,"price":118},{"ID":71,"orderID":24,"productID":200000006,"quantity":1,"price":37},{"ID":72,"orderID":24,"productID":200000005,"quantity":5,"price":25},{"ID":73,"orderID":25,"productID":200000010,"quantity":3,"price":150},{"ID":74,"orderID":25,"productID":200000008,"quantity":2,"price":70},{"ID":75,"orderID":25,"productID":200000009,"quantity":1,"price":30.5},{"ID":76,"orderID":26,"productID":200000010,"quantity":1,"price":47},{"ID":77,"orderID":26,"productID":200000011,"quantity":2,"price":72},{"ID":78,"orderID":26,"productID":200000013,"quantity":1,"price":121.5},{"ID":79,"orderID":27,"productID":200000001,"quantity":1,"price":58},{"ID":80,"orderID":27,"productID":200000002,"quantity":1,"price":58},{"ID":81,"orderID":27,"productID":200000003,"quantity":3,"price":57},{"ID":82,"orderID":28,"productID":200000002,"quantity":2,"price":118},{"ID":83,"orderID":28,"productID":200000003,"quantity":2,"price":37},{"ID":84,"orderID":28,"productID":200000004,"quantity":1,"price":19},{"ID":85,"orderID":29,"productID":200000005,"quantity":3,"price":15},{"ID":86,"orderID":29,"productID":200000006,"quantity":3,"price":111},{"ID":87,"orderID":29,"productID":200000007,"quantity":2,"price":65},{"ID":88,"orderID":30,"productID":200000008,"quantity":2,"price":70},{"ID":89,"orderID":30,"productID":200000009,"quantity":1,"price":30.5},{"ID":90,"orderID":30,"productID":200000010,"quantity":1,"price":49.5},{"ID":91,"orderID":31,"productID":200000011,"quantity":3,"price":105},{"ID":92,"orderID":31,"productID":200000012,"quantity":2,"price":100},{"ID":93,"orderID":31,"productID":200000013,"quantity":2,"price":240},{"ID":94,"orderID":32,"productID":200000014,"quantity":1,"price":27.5},{"ID":95,"orderID":32,"productID":200000015,"quantity":1,"price":48},{"ID":96,"orderID":32,"productID":200000016,"quantity":3,"price":1350},{"ID":97,"orderID":33,"productID":200000017,"quantity":1,"price":348},{"ID":98,"orderID":33,"productID":200000018,"quantity":1,"price":339},{"ID":99,"orderID":33,"productID":2000000019,"quantity":1,"price":55},{"ID":100,"orderID":34,"productID":200000020,"quantity":1,"price":375},{"ID":101,"orderID":34,"productID":200000021,"quantity":1,"price":325},{"ID":102,"orderID":34,"productID":200000005,"quantity":2,"price":10},{"ID":103,"orderID":35,"productID":200000009,"quantity":2,"price":60},{"ID":104,"orderID":35,"productID":200000006,"quantity":1,"price":37.5},{"ID":105,"orderID":35,"productID":200000013,"quantity":1,"price":120},{"ID":106,"orderID":36,"productID":200000004,"quantity":3,"price":57},{"ID":107,"orderID":36,"productID":200000015,"quantity":2,"price":95},{"ID":108,"orderID":36,"productID":200000018,"quantity":1,"price":339},{"ID":109,"orderID":37,"productID":200000005,"quantity":2,"price":10},{"ID":110,"orderID":37,"productID":200000011,"quantity":1,"price":36},{"ID":111,"orderID":37,"productID":200000006,"quantity":3,"price":109.5},{"ID":112,"orderID":38,"productID":200000014,"quantity":2,"price":50},{"ID":113,"orderID":38,"productID":200000019,"quantity":2,"price":620},{"ID":114,"orderID":38,"productID":200000020,"quantity":1,"price":372},{"ID":115,"orderID":39,"productID":200000021,"quantity":1,"price":325},{"ID":116,"orderID":39,"productID":200000019,"quantity":3,"price":930},{"ID":117,"orderID":39,"productID":200000016,"quantity":1,"price":450},{"ID":118,"orderID":40,"productID":200000002,"quantity":2,"price":156},{"ID":119,"orderID":40,"productID":200000003,"quantity":3,"price":57},{"ID":120,"orderID":40,"productID":200000004,"quantity":1,"price":19},{"ID":121,"orderID":41,"productID":200000010,"quantity":3,"price":141},{"ID":122,"orderID":41,"productID":200000015,"quantity":2,"price":95},{"ID":123,"orderID":41,"productID":200000017,"quantity":2,"price":700},{"ID":124,"orderID":42,"productID":200000019,"quantity":1,"price":310},{"ID":125,"orderID":42,"productID":200000020,"quantity":1,"price":374},{"ID":126,"orderID":42,"productID":200000016,"quantity":1,"price":450},{"ID":127,"orderID":43,"productID":200000019,"quantity":2,"price":620},{"ID":128,"orderID":43,"productID":200000007,"quantity":2,"price":61},{"ID":129,"orderID":43,"productID":200000002,"quantity":1,"price":59},{"ID":130,"orderID":44,"productID":200000013,"quantity":1,"price":120},{"ID":131,"orderID":44,"productID":200000004,"quantity":3,"price":55.5},{"ID":132,"orderID":44,"productID":200000015,"quantity":2,"price":96},{"ID":133,"orderID":45,"productID":200000015,"quantity":2,"price":95},{"ID":134,"orderID":45,"productID":200000002,"quantity":1,"price":58},{"ID":135,"orderID":45,"productID":200000013,"quantity":1,"price":120},{"ID":136,"orderID":46,"productID":200000004,"quantity":1,"price":19.5},{"ID":137,"orderID":46,"productID":200000012,"quantity":2,"price":100},{"ID":138,"orderID":46,"productID":200000014,"quantity":2,"price":56},{"ID":139,"orderID":47,"productID":200000002,"quantity":3,"price":180},{"ID":140,"orderID":47,"productID":200000013,"quantity":1,"price":119.5},{"ID":141,"orderID":47,"productID":200000004,"quantity":3,"price":57},{"ID":142,"orderID":47,"productID":200000015,"quantity":2,"price":96},{"ID":143,"orderID":47,"productID":200000016,"quantity":2,"price":900},{"ID":144,"orderID":48,"productID":200000015,"quantity":2,"price":95},{"ID":145,"orderID":48,"productID":200000013,"quantity":1,"price":120},{"ID":146,"orderID":48,"productID":200000004,"quantity":3,"price":57},{"ID":147,"orderID":48,"productID":200000019,"quantity":1,"price":309},{"ID":148,"orderID":48,"productID":200000014,"quantity":2,"price":56},{"ID":149,"orderID":49,"productID":200000001,"quantity":2,"price":118},{"ID":150,"orderID":49,"productID":200000002,"quantity":1,"price":58},{"ID":151,"orderID":49,"productID":200000003,"quantity":3,"price":55.5},{"ID":152,"orderID":49,"productID":200000004,"quantity":2,"price":37},{"ID":153,"orderID":49,"productID":200000005,"quantity":2,"price":10},{"ID":154,"orderID":50,"productID":200000010,"quantity":2,"price":100},{"ID":155,"orderID":50,"productID":200000011,"quantity":1,"price":35},{"ID":156,"orderID":50,"productID":200000012,"quantity":3,"price":150},{"ID":157,"orderID":50,"productID":200000013,"quantity":2,"price":240},{"ID":158,"orderID":50,"productID":200000014,"quantity":2,"price":50},{"ID":159,"orderID":50,"productID":200000015,"quantity":2,"price":95}]
		var carts = [{"ID":1,"userID":1,"productID":200000014,"groupID":1},{"ID":2,"userID":2,"productID":200000015,"groupID":1}]


		async.series([function(callback){
			adminModel.find({}, function(err, adminsIn){
				if(adminsIn.length == 0){
					locals[CONSTANT.SCHEMA.ADMINISTRATOR] = {};
					async.forEach(administrators, function(eachAdmin){
						var adminModelObj = new adminModel(eachAdmin);
						adminModelObj.save(function(err, admin){
							console.log('Administrator inserted: ' + admin._id);
							locals[CONSTANT.SCHEMA.ADMINISTRATOR][admin.id] = admin._id;
							callback();
						});
					});
				}else{
					callback();
				}
			});
		}
		,function(callback){
			groupModel.find({}, function(err, groupsIn){
				if(groupsIn.length == 0){
					locals[CONSTANT.SCHEMA.GROUP] = {};
					async.forEach(groups, function(eachGroup){
						var groupModelObj = new groupModel(eachGroup);
						groupModelObj.save(function(err, group){
							console.log('Group inserted: ' + group._id);
							locals[CONSTANT.SCHEMA.GROUP][group.ID] = group._id;
							callback();
						});
					});
				}else{
					callback();
				}
			});
		},function(callback){
			roleModel.find({}, function(err, rolesIn){
				if(rolesIn.length == 0){
					locals[CONSTANT.SCHEMA.ROLE] = {};
					async.forEach(roles, function(eachRole){
						var roleModelObj = new roleModel(eachRole);
						roleModelObj.save(function(err, role){
							console.log('Role inserted: ' + role._id);
							locals[CONSTANT.SCHEMA.ROLE][role.ID] = role._id;
							callback();
						});
					});
				}else{
					callback();
				}
			});
		},function(callback){
			statusModel.find({}, function(err, statusIn){
				if(statusIn.length == 0){
					locals[CONSTANT.SCHEMA.STATUS] = {};
					async.forEach(orderStatuses, function(eachStatus){
						var statusModelObj = new statusModel(eachStatus);
						statusModelObj.save(function(err, status){
							console.log('Status inserted: ' + status._id);
							locals[CONSTANT.SCHEMA.STATUS][status.ID] = status._id;
							callback();
						});
					});
				}else{
					callback();
				}
			});
		},function(callback){
			userModel.find({}, function(err, usersIn){
				if(usersIn.length == 0){
					locals[CONSTANT.SCHEMA.USER] = {};
					async.forEach(users, function(eachUSER){
						if(eachUSER.groupID){
							eachUSER.groupID = locals[CONSTANT.SCHEMA.GROUP][eachUSER.groupID];
						}
						if(eachUSER.role){
							eachUSER.role = locals[CONSTANT.SCHEMA.ROLE][eachUSER.role];
						}
						var userModelObj = new userModel(eachUSER);
						userModelObj.save(function(err, user){
							console.log('User inserted: ' + user._id);
							locals[CONSTANT.SCHEMA.USER][user.ID] = user._id;
							callback();
						});
					});
				}else{
					callback();
				}
			});
		},function(callback){
			orderModel.find({}, function(err, ordersIn){
				if(ordersIn.length == 0){
					locals[CONSTANT.SCHEMA.ORDER] = {};
					async.forEach(orders, function(eachOrder){
						if(eachOrder.groupID){
							eachOrder.groupID = locals[CONSTANT.SCHEMA.GROUP][eachOrder.groupID];
						}
						if(eachOrder.statusID){
							eachOrder.statusID = locals[CONSTANT.SCHEMA.STATUS][eachOrder.statusID];
						}
						if(eachOrder.userID){
							eachOrder.userID = locals[CONSTANT.SCHEMA.USER][eachOrder.userID];
						}
						var orderModelObj = new orderModel(eachOrder);
						orderModelObj.save(function(err, order){
							console.log('Order inserted: ' + order._id);
							locals[CONSTANT.SCHEMA.ORDER][order.ID] = order._id;
							callback();
						});
					});
				}else{
					callback();
				}
			});
		},function(callback){
			productModel.find({}, function(err, productsIn){
				if(productsIn.length == 0){
					locals[CONSTANT.SCHEMA.PRODUCT] = {};
					async.forEach(products, function(eachProduct){
						var productModelObj = new productModel(eachProduct);
						productModelObj.save(function(err, product){
							console.log('Product inserted: ' + product._id);
							locals[CONSTANT.SCHEMA.PRODUCT][product.productID] = product._id;
							callback();
						});
					});
				}else{
					callback();
				}
			});
		},function(callback){
			orderDetailModel.find({}, function(err, detailsIn){
				if(detailsIn.length == 0){
					locals[CONSTANT.SCHEMA.ORDERDETAILS] = {};
					async.forEach(orderdetails, function(eachOrderDetail){
						if(eachOrderDetail.orderID){
							eachOrderDetail.orderID = locals[CONSTANT.SCHEMA.ORDER][eachOrderDetail.orderID];
						}
						if(eachOrderDetail.productID){
							eachOrderDetail.productID = locals[CONSTANT.SCHEMA.PRODUCT][eachOrderDetail.productID];
						}
						var orderDetailModelObj = new orderDetailModel(eachOrderDetail);
						orderDetailModelObj.save(function(err, orderDetail){
							console.log('Order Details inserted: ' + orderDetail._id);
							locals[CONSTANT.SCHEMA.ORDERDETAILS][orderDetail.ID] = orderDetail._id;
							callback();
						});
					});
				}else{
					callback();
				}
			});
		},function(callback){
			cartModel.find({}, function(err, cartsIn){
				if(cartsIn.length == 0){
					locals[CONSTANT.SCHEMA.CART] = {};
					async.forEach(carts, function(eachCart){
						if(eachCart.userID){
							eachCart.userID = locals[CONSTANT.SCHEMA.USER][eachCart.userID];
						}
						if(eachCart.productID){
							eachCart.productID = locals[CONSTANT.SCHEMA.PRODUCT][eachCart.productID];
						}
						if(eachCart.groupID){
							eachCart.groupID = locals[CONSTANT.SCHEMA.GROUP][eachCart.groupID];
						}
						var cartModelObj = new cartModel(eachCart);
						cartModelObj.save(function(err, cart){
							console.log('Cart inserted: ' + cart._id);
							locals[CONSTANT.SCHEMA.ORDERDETAILS][cart.ID] = cart._id;
							callback();
						});
					});
				}else{
					callback();
				}
			});
		}		
		], function(results){
			resolve(results);
		});
	});
};

module.exports = { 'PopulateDB': new PopulateDB() }
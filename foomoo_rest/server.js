var express = require("express");
var mysql = require("mysql");
var bodyParser = require("body-parser");
var md5 = require("MD5");
var rest = require("./REST.js");
var app = express();

function REST() {
	var self = this;
	self.connectMysql();
}

REST.prototype.connectMysql = function() {
	var self = this;
	var pool = mysql.createPool({
		connectionLimit : 100,
		host 		: "foomoo.ctibdyidax0w.us-west-2.rds.amazonaws.com",
		user		: "foomoo_admin",
		password	: "foomooftw",
		database	: "foomoo",
		debug		: "false"
	});
	pool.getConnection(function(err, connection) {
		if (err) {
			self.stop(err);
		} else {
			self.configureExpress(connection);
		}
	});
};

REST.prototype.configureExpress = function(connection) {
	var self = this;
	app.use(bodyParser.urlencoded({ extended: true}));
	app.use(bodyParser.json());
	var router = express.Router();
	app.use("/api", router);
	var rest_router = new rest(router, connection, md5);
	self.startServer();
};

REST.prototype.startServer = function() {
	app.listen(3333, function() {
		console.log("Port 3333 is working");
	});
};

REST.prototype.stop = function(err) {
	console.log("ISSUE WITH MYSQL: \n" + err);
	process.exit(1);
};

new REST();

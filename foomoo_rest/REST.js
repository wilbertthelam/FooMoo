var mysql = require("mysql");
function REST_ROUTER(router, connection, md5) {
	var self = this;
	self.handleRoutes(router, connection, md5);
}

REST_ROUTER.prototype.handleRoutes = function(router, connection, md5) {
	// DEFAULT ROUTE
	router.get("/", function(req, res) {
		res.json({"Message" : "This is the FooMoo REST API!"});
	});

	// GET USERS
	router.get("/users", function(req, res){
		var query = "SELECT * FROM ??";
		var table = ["users"];
		query = mysql.format(query, table);
		connection.query(query, function(err, rows) {
			if (err) {
				res.json({"Error": true, "Message" : "Error in MySQL query"});
			} else {
				res.json({"Error": false, "Message" : "Success", "Results" : rows});
			}
		});
	});

	// GET USER BASED OFF ID
	router.get("/users/:user_id", function(req, res){
		var query = "SELECT * FROM ?? WHERE ??=?";
		var table = ["users", "user_id", req.params.user_id];
		query = mysql.format(query, table);
		connection.query(query, function(err, rows) {
			if (err) {
				res.json({"Error": true, "Message" : "Error in MySQL query"});
			} else {
				res.json({"Error": false, "Message" : "Success", "Results" : rows});
			}
		});
	});

	// GET EVENT DATA
	router.get("/events", function(req, res){
		var query = "SELECT * FROM ??";
		var table = ["events"];
		query = mysql.format(query, table);
		connection.query(query, function(err, rows) {
			if (err) {
				res.json({"Error": true, "Message" : "Error in MySQL query"});
			} else {
				res.json({"Error": false, "Message" : "Success", "Results" : rows});
			}
		});
	});

	// GET EVENT DATA BASED OFF ID
	router.get("/events/:event_id", function(req, res){
		var query = "SELECT * FROM ?? WHERE ??=?";
		var table = ["events", "event_id", req.params.event_id];
		query = mysql.format(query, table);
		connection.query(query, function(err, rows) {
			if (err) {
				res.json({"Error": true, "Message" : "Error in MySQL query"});
			} else {
				res.json({"Error": false, "Message" : "Success", "Results" : rows});
			}
		});
	});

	// GET EVENT DATA BASED OFF USER ID --- FIX TO PREVENT INJECTION
	router.get("/eventsByUserId/:user_id", function(req, res){
		var query = "SELECT * FROM events, event_people where events.event_id = event_people.event_id and event_people.user_id =" + req.params.user_id;
		//var table = ["events", "event_people", "events.event_id", "event_people.event_id", "event_people.user_id", req.params.user_id];
		//query = mysql.format(query, table);
		console.log(query);
		connection.query(query, function(err, rows) {
			if (err) {
				res.json({"Error": true, "Message" : "Error in MySQL query"});
			} else {
				res.json({"Error": false, "Message" : "Success", "Results" : rows});
			}
		});
	});

	// GET USER DATA BASED OFF EVENT ID --- FIX TO PREVENT INJECTION
	router.get("/usersByEventId/:event_id", function(req, res){
		var query = "SELECT * FROM users, event_people where event_people.user_id = users.user_id and event_people.event_id =" + req.params.event_id;
		//var table = ["events", "event_people", "events.event_id", "event_people.event_id", "event_people.user_id", req.params.user_id];
		//query = mysql.format(query, table);
		console.log(query);
		connection.query(query, function(err, rows) {
			if (err) {
				res.json({"Error": true, "Message" : "Error in MySQL query"});
			} else {
				res.json({"Error": false, "Message" : "Success", "Results" : rows});
			}
		});
	});

	// GET ALL CRAVINGS IN THE GROUP}
	router.get("/getGroupCravings/:event_id", function(req, res) {
		var query = "SELECT craving, votes FROM event_cravings e WHERE e.event_id=" + req.params.event_id + " order by votes desc";
		console.log(query);
		connection.query(query, function(err, rows) {
			if (err) {
				res.json({"Error": true, "Message" : "Error in MySQL query"});
			} else {
				res.json({"Error": false, "Message" : "Success", "Results" : rows});
			}
		});
	});

	// GET USER CRAVING GIVEN EVENT AND USER ID
	router.get("/getUserCravingsByEvent/:user_id/:event_id", function(req, res) {
		var query = "SELECT user_craving FROM event_people e WHERE e.event_id=" + req.params.event_id + " and e.user_id=" + req.params.user_id;
		console.log(query);
		connection.query(query, function(err, rows) {
			if (err) {
				res.json({"Error": true, "Message" : "Error in MySQL query"});
			}  else {
				res.json({"Error": false, "Message" : "Success", "Results" : rows});
			}
		});
	});



};

module.exports = REST_ROUTER;
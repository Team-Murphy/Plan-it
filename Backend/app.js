//import all npm packages needed
var express = require('express');
var mongoose = require('mongoose');
var bodyParser = require('body-parser');
var increment = require('mongoose-auto-increment');

var database = require('./database');
//import config file
var config = require('./config');

//import all controllers
var eventCtrl = require('./controllers/event');
var userCtrl = require('./controllers/user');

var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: false
}));
app.disable('etag');

//Enable CORS on server
app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header('Access-Control-Allow-Methods', 'DELETE');
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  next();
});

database.createConnection();

app.post("/events", eventCtrl.createEvent);
app.get("/events/:id", eventCtrl.getEventById);
app.get("/events", eventCtrl.getAllEvents);
app.post("/user", userCtrl.createUser);
app.post("/events/:id/update", eventCtrl.updateEvent);
app.delete("/events/:id", eventCtrl.deleteEvent);
app.post("/events/:id/list", eventCtrl.createListItem);
app.get("/events/:id/list", eventCtrl.getListItems);
app.post("/user/:id/friend", userCtrl.addNewFriend);
app.get("/user/:id/friend", userCtrl.getAllFriends);
app.get("/search/:id/user", userCtrl.findUserByID);
app.get("/search/:param/friend", userCtrl.findUserByFriendlyNameOrEmail);


app.listen(3000, function() {
  console.log("Server is running on port 3000");
});

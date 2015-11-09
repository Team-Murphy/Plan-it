var database = module.exports;

var mongoose = require('mongoose');
var increment = require('mongoose-auto-increment');
var config = require('./config');
var eventjs = require('./models/event');
var listjs = require('./models/list');
var userjs = require('./models/user')

database.createConnection = function() {
  mongoose.connect(config.db);
  var db = mongoose.connection;
  increment.initialize(db);
  this.initListIncrement();
  this.initEventIncrement();
  this.initUserIncrement();
  db.on('error', console.error.bind(console, 'connection error:'));
  db.on('open', function() {
    console.log("connection made to database");
  });
}

database.initEventIncrement = function() {
  eventjs.plugin(increment.plugin, {
    model: "Event",
    field: 'EventID',
    startAt: 1
  });
}

database.initListIncrement = function() {
  listjs.plugin(increment.plugin, {
    model: "List",
    field: 'ListID',
    startAt: 1
  });
}

//Create autonumber field on UserID field in User model
database.initUserIncrement = function(){
  userjs.plugin(increment.plugin, {
    model: "User", field: "UserID", startAt: 1
  }
  );
};

//database.connection = db;
//Create List Model
database.listModel = mongoose.model("List", listjs);
//Create Event Model
database.eventModel = mongoose.model("Event", eventjs);
//Create User model
database.userModel = mongoose.model("User", userjs);

database.eventModel.calculateEst = function(eventid, callback) {
    console.log('event id is: ' + eventid);
    database.eventModel.aggregate([{
      $unwind: "$itemList"
    }, {
      $group: {
        _id: null,
        estCost: {
          $sum: "$itemList.estCost",
        },
        actCost: {
          $sum: "$itemList.actCost"
        }
      }
    }], function(err, result) {
      if (err) {
        console.log(err);
        return;
      }
      callback(result);
    });
  }

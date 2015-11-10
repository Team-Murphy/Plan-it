var database = require('../database');

var event = module.exports;

event.createEvent = function(req, res, next) {
  var event = new database.eventModel({
    "what": req.body.what,
    "why": req.body.why,
    "where": req.body.where,
    "when": req.body.when,
    "endDate": req.body.endDate,
    "fromTime" : req.body.from,
    "toTime" : req.body.to,
    "picture": req.body.picture
  });

  event.save(function(err) {
    if (err)
      console.log(err);
    else
      res.sendStatus(201);
  });
}

event.getEventById = function(req, res, next) {
  database.eventModel.findOne({
    "EventID": req.params.id
  }, function(err, event) {
    if (err)
      console.log(err);
    else
      res.json(event);
  });
}

event.getAllEvents = function(req, res, next) {
  database.eventModel.find({}, function(err, events) {
    if (err)
      console.log(err);
    else {
      res.json(events);
    }
  });
}

event.updateEvent = function(req, res, next) {

}

event.deleteEvent = function(req, res, next) {

}

event.createListItem = function(req, res, next) {
  database.eventModel.findOne({
    "EventID": req.params.id
  }, function(err, event) {
    if (err)
      console.log(err);
    else {
      var list = new database.listModel({
        "item": req.body.item,
        "estCost": req.body.estCost,
        "actCost": req.body.actCost
      });
      event.itemList.push(list);

      database.eventModel.calculateEst(req.params.id, function(result) {
        event.totalEstCost = result[0].estCost;
        event.totalActCost = result[0].actCost;
        event.save(function(err) {
          if (err)
            console.log(err);
          else
            res.sendStatus(201);
        });
      });
    }
  });
}

event.getListItems = function(req, res, next) {
  database.eventModel.findOne({
    "EventID": req.params.id
  }, function(err, events) {
    if (err)
      console.log(err);
    else {
      res.json(events.itemList);
    }
  });
}

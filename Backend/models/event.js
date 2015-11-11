var mongoose = require('mongoose');
var database = require('../database');
var list = require('./list');

var event = new mongoose.Schema({
  EventID: Number,
  Members: [{
    UserId: mongoose.Schema.ObjectId,
    isAttending: { type: String, enum: ['Invited', 'Attending', 'Declined', 'Left', 'Owner'] },
    Nofications: [{
        itemList: Boolean,
        messageBoard: Boolean,

    }]
  }],
  itemList: [list],
  totalEstCost: {type: Number, default: 0},
  totalActCost: {type: Number, default: 0},
  what: String,
  why: String,
  where: String,
  when: String,
  endDate: String,
  picture: String,
  fromTime: String,
  toTime: String
});

module.exports = event;

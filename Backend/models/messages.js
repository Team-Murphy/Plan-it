//Load mongoose model and Schema
var mongoose = require('mongoose');
var Schema = mongoose.Schema;
//Load Database Configuration file
var database = require('../database');

var messagesSchema = new Schema(
  {
    events: [{ message: String, eventID: Number, userID: Number, time: {type: Date, default: Date.now} }]
  }
);

module.exports = messagesSchema;

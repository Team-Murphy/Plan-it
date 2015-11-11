var mongoose = require('mongoose');

var list = new mongoose.Schema({
    _id: false,
    ListID: Number,
    item: String,
    whoseBringing: Number,
    estCost: Number,
    actCost: Number
});

module.exports = list;

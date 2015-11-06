//Load datbase connection file
var database = require('../database');
//Load the user model Schema
var userSchema = require('../models/user');


var user = module.exports;

//Create a new user
user.createUser = function(req, res, next ){

  //Create new user object
  var userObj = new database.userModel({
    "friendlyName": req.body.friendlyName,
    "email": req.body.email,
    "hashPassword": req.body.hashPassword,
  });

  //Find an existing email address, due to unique constraint on field
  database.userModel.findOne({"email": userObj.email}, function(err, data){
    //If no errors found
    if(!err)
    {
      //If email already exists
      if(data)
      {
      console.log("Email already registered.")
      res.status(409).send('Duplicate email address found.')
      }
      else
      {
        //Save the new user to database
        userObj.save(function(err) {
          if (err)
            console.log(err);
          else
            res.sendStatus(201);
        });
      }
    }
    else {
      res.sendStatus(500);
    }
  });

};


user.findUserByID = function(req, res, next)
{

};

user.updateUser = function(req, res, next)
{

};

//Add a friend to an existing user
user.addNewFriend = function(req, res, next)
{
  //Find and return the data for the selected user and find if the user exists
  database.userModel.findOne({"UserID": req.params.id}, function(err, data){
    if(!err){
      if(!data)
      {
        console.log("User was not found.")
        res.status(409).send("User not found.");
      }
      else {
        //If the User exists find if the friend has already been added to the friends list
        database.userModel.findOne({"friendList.userID": req.body.userID}, function(err, friend){
          if(friend)
          {
            res.status(409).send("User is already in friends list.");
            console.log("User is already a friend.");
          }
          else {
              //If the friend is not already on the friends list then
              //Create a new friend list item
              var item = {userID: req.body.userID};

              //Add the item to the friends list and then save it to the user
              data.friendList.push(item);
              data.save(function(err) {
                if (err)
                {
                  console.log(err);
                  res.sendStatus(500);
                }
                else{
                  console.log('Friend added.')
                  res.sendStatus(201);
                }
              });
          }
        }
      );

      }
    }
    else{
          res.sendStatus(500);
        }
  }
);
}

//Retrieving User's Friend List
user.getAllFriends = function(req, res, next)
{
  //console.log("hit");
  database.userModel.find({"UserID": req.params.id}, function(err, events) {
    if (err)
      console.log(err);
    else {
      res.json(events[0].friendList);
    }
  });
};

user.searchFriend = function(req, res, next){

}

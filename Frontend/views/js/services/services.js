//Angular service to enable HTTP requests for Events API
angular.module('services', [])
.service("ServiceForEvents" , ['$http', function ($http) {
    this.addEvent = function(data)
    {
      return $http({url:'http://localhost:3000/events', method: "POST", data: data});
    }
    this.getAllEvents = function(data)
    {
      return $http({url:'http://localhost:3000/events', method: "GET"});
    }
    this.getEventById = function(data)
    {
      return $http({url:'http://localhost:3000/events/' + data , method: "GET"});
    }
    this.deleteEvent = function (data){
      return $http({ url: 'http://localhost:3000/events/' + data , method: "DELETE"});
    }

}
])
.service("ServiceForUser" , ['$http', function ($http) {
    this.registerUser = function(data)
    {
      return $http({url:'http://localhost:3000/user', method: "POST", data: data});
    }
    this.loginUser = function(data)
    {
      return $http({url:'http://localhost:3000/user', method: "POST", data: data});
    }
}
]);
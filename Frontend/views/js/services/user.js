//Angular service to enable HTTP requests for Events API
angular.module('services', [])
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
angular.module('eventController', [])
.controller('CreateEventController', function ($window, $scope, ServiceForEvents){
  //Create a scope to determine if form has been submitted
  $scope.submitted = false;

//Method called when the form is submitted
$scope.createEvent = function(){
  console.log($scope.event);
    //If the form fields are all valid
  if ($scope.new_event_form.$valid) {
    console.log($scope.new_event_form);
    //Call the addEvent method of Event Service to create a new event.
  ServiceForEvents.addEvent($scope.event).success(function (data)
  {
    console.log("Event Created.")
    $window.location.reload();  
  });
}
else {
    $scope.new_event_form.submitted = true;
}
}

})
.controller('EventListController', function ($scope, ServiceForEvents){
  ServiceForEvents.getAllEvents().success(function (data)
  {
      $scope.eventList = data;
      console.log(data);
  }
  );

})
.controller('EventDetailsController', function ($window, $scope, $stateParams, ServiceForEvents){
  $scope.id = $stateParams.eventID;


  console.log($scope.id);
  ServiceForEvents.getEventById($scope.id).success(function (data)
  {
    $scope.event = data;
  }
  );

  $scope.deleteEvent = function () {
    console.log("about to delete event")
    if (confirm("Are you sure you want to delete the event?") == true)
        ServiceForEvents.deleteEvent($scope.id).success(function (data) {});
    $window.location.reload();  
  };

})

// angular.module('userController', [])
.controller('RegisterUserController', function ($scope, $location, ServiceForUser) {
    
    //Create a scope to determine if form as been submitted
    $scope.submitted = false;
    //Method that is called when the form is submitted
    $scope.signupForm = function () {  
        //If the form is fields are valid
        if ($scope.register_form.$valid) {
            console.log($scope.register_form);
            ServiceForUser.registerUser($scope.user).success(function (data) {
                if(data.error != null){
                    $location.path('/Error');
                }
                else
                {
                    $location.path('/events');
                }
                console.log("Saved User.");
                //Reset values on the page once new user is registered.
                $scope.user.email = "";
                $scope.user.friendlyName = "";
                $scope.user.hashPassword = "";
                $scope.register_form.$setPristine();
                $scope.register_form.submitted = false;
                //$location.path('/Login');
            }
            );           
           
          //If the form is not valid, tell that the form has been submitted to display validations  
        } else {
            $scope.register_form.submitted = true;
            console.log($scope.register_form);
            console.log($scope.submitted);
        }
    }

});


angular.module('eventController', [])
.controller('CreateEventController', function ($scope, ServiceForEvents){
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
.controller('EventDetailsController', function ($scope, $stateParams, ServiceForEvents){
  $scope.id = $stateParams.eventID;


  console.log($scope.id);
  ServiceForEvents.getEventById($scope.id).success(function (data)
  {
    $scope.event = data;
  }
);
});

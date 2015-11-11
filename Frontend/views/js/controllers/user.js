angular.module('userController', [])
.controller('RegisterUserController', function ($scope, $location, ServiceForUser) {
    
    //Create a scope to determine if form as been submitted
    $scope.submitted = false;
    //Method that is called when the form is submitted
    $scope.signupForm = function () {
        //If the form is fields are valid
        if ($scope.register_form.$valid) {
      // Method from service is used to call express route to register a new doctor
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
                //Reset values on the page once new doctor is registered.
                $scope.user.email = "";
                $scope.user.friendlyName = "";
                $scope.user.hashPassword = "";
                $scope.register_form.$setPristine();
                $scope.register_form.submitted = false;
                //$location.path('/Login');
            }
            );           
            //$scope.doctor.password = "";
            //$scope.doctor.username = "";
            
          //If the form is not valid, tell that the form has been submitted to display validations  
        } else {
            $scope.register_form.submitted = true;
            console.log($scope.register_form);
            console.log($scope.submitted);
        }
    }

});

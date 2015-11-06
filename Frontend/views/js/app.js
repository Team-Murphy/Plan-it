// var app = angular.module('planItApp', ['ngRoute']);
var app = angular.module('planItApp', ['ui.router', 'services', 'eventController']);

//to route views on single page
app.config(['$stateProvider',
  function ($stateProvider) {
    $stateProvider
        .state('/', {
            url: '',
            views: {
                    templateUrl: 'index.html'
            }
        })
        .state('signup', {
            url: '/signup',
            views: {
                'generalView': {
                    templateUrl: 'signup.html'
                }
            }
        })
        .state('event', {
            url: '/event',
            views: {
                'generalView': {
                    templateUrl: 'event.html', controller: 'EventListController'
                }
            }
        })
        .state('event.details', {
            url: '/eventinfo/:eventID',
            views: {
                'rightView': {
                    templateUrl: 'event.details2.html', controller:'EventDetailsController'
                }
            }
        })
        .state('event.chat', {
            url: '',
            views: {
                'rightView': {
                    templateUrl: 'event.chat.html'
                }
            }
        })
        .state('event.items', {
            url: '',
            views: {
                'rightView': {
                    templateUrl: 'event.items.html'
                }
            }
        })
        .state('event.budget', {
            url: '',
            views: {
                'rightView': {
                    templateUrl: 'event.budget.html'
                }
            }
        })
        .state('event.create', {
            url: '',
            views: {
                'rightView': {
                    templateUrl: 'event.create.html', controller: 'CreateEventController'
                }
           }
        })
        .state('event.edit', {
            url: '',
            views: {
                'rightView': {
                    templateUrl: 'event.edit.html'
                }
            }
        })
        .state('contact', {
            url: '/contact',
            views: {
                'generalView': {
                    templateUrl: 'contact.html'
                }
            }
        })

    }]);

//to extract socket object to be used in controller
app.factory('socket', function(){
  return io.connect('http://localhost:4030');
});

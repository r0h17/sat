(function () {
    'use strict';

    angular
        .module('app')
        .factory('AuthenticationService', AuthenticationService);

    AuthenticationService.$inject = ['$http', '$rootScope', '$timeout', 'UserService'];
    function AuthenticationService($http, $rootScope, $timeout, UserService) {
        var service = {};

        service.Login = Login;
        service.SetCredentials = SetCredentials;
        service.ClearCredentials = ClearCredentials;

        return service;

        function Login(username, password, callback) {

            /* Dummy authentication for testing, uses $timeout to simulate api call
             ----------------------------------------------*/
            /*$timeout(function () {
                var response;
                UserService.GetByUsername(username)
                    .then(function (user) {
                        if (user !== null && user.password === password) {
                            response = { success: true };
                        } else {
                            response = { success: false, message: 'Username or password is incorrect' };
                        }
                        callback(response);
                    });
            }, 1000);*/

            /* Use this for real authentication
             ----------------------------------------------*/
            $http.post('http://'+window.location.hostname+':8080/api/authenticate', { username: username, password: password })
                .success(function (response) {
                    callback(response);
                });

        }

        function SetCredentials(username, password) {
            var authdata = btoa(username + ':' + password);

            $rootScope.globals = {
                currentUser: {
                    username: username,
                    authdata: authdata
                }
            };
            console.log("GLobal User Data Set")

            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            //$cookieStore.put('globals', $rootScope.globals);
        }

        function ClearCredentials() {
            $rootScope.globals = {};
            //$cookieStore.remove('globals');
            $http.defaults.headers.common.Authorization = 'Basic';
        }
    }

})();

(function () {
    'use strict';

    angular
        .module('app')
        .controller('GradesController', GradesController);

    GradesController.$inject = ['$location', 'UserService', '$rootScope','GradeService','FlashService','$routeParams'];
    function GradesController($location, UserService, $rootScope, GradeService, FlashService, $routeParams) {
        var vm = this;

        vm.testId = $routeParams.testId;
        vm.grades = [];
        vm.user = null;
        vm.test = null;
        vm.updateUserGrade = updateUserGrade;
        vm.getTemplate = getTemplate;
        vm.reset=reset;
        vm.editGrade = editGrade;

        initController();

        function initController() {
            loadTestSchedules();
            loadCurrentUser();
            loadTest();
        }

        function loadCurrentUser() {
          UserService.GetByUsername($rootScope.globals.currentUser.username)
              .then(function (user) {
                  vm.user = user;
              });
        }
        function loadTest(){
          GradeService.GetTest(vm.testId)
              .then(function (test) {
                  vm.test = test;
              });
        }
        function loadTestSchedules(){
          GradeService.GetGrades(vm.testId)
              .then(function (grades) {
                  vm.grades = grades;
              });
        }

        function editGrade(userGrade){
            vm.selected = angular.copy(userGrade);
        }
        function reset(){vm.selected={};loadTestSchedules();}
        function getTemplate(userGrade) {
            if (vm.selected && userGrade.userName === vm.selected.userName){
    			       return 'edit';
    		    }
            else return 'display';
        };
        function updateUserGrade(userGrade) {

          GradeService.Save(userGrade, function (response) {
              if (response.usersByUserId) {
                  console.log("saved");
                  reset();
              } else {
                  FlashService.Error(response.message);
              }
          });


      	};
    }

})();

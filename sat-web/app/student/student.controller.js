(function () {
    'use strict';

    angular
        .module('app')
        .controller('StudentController', StudentController);

    StudentController.$inject = ['GradeService', 'UserService', '$rootScope'];
    function StudentController(GradeService, UserService, $rootScope) {
        var vm = this;

        vm.user = null;
        vm.testGrades = [];

        vm.enrollTest = enrollTest;
        vm.deleteTest = deleteTest;
        initController();

        function initController() {
          UserService.GetByUsername($rootScope.globals.currentUser.username)
              .then(function (user) {
                  vm.user = user;
                  return GradeService.GetByUserId(user.id);
              }).then(function (grades) {
                vm.testGrades = grades;
              });

        }

        function deleteTest(id){
          var index = -1;
      		var testArray = eval( vm.testGrades );
      		for( var i = 0; i < testArray.length; i++ ) {
      			if( testArray[i].id === id ) {
      				index = i;
      				break;
      			}
      		}
      		vm.testGrades.splice( index, 1 );
        }
        function enrollTest(){
          vm.testGrades.push(
            {"id":3,"date":"12/22/2016","courseName":"Math","grade":"6.7"}
          );
          $('#myModal').modal('toggle');
        }
    }

})();

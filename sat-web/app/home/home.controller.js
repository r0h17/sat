(function () {
    'use strict';

    angular
        .module('app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$location', 'UserService', '$rootScope','GradeService'];
    function HomeController($location, UserService, $rootScope, GradeService) {
        var vm = this;

        vm.user = null;
        vm.practiceTests = [

        ];
        vm.availableStudents = [];
        vm.exams = [];
        vm.courses = [];
        vm.deleteUser = deleteUser;
        vm.scheduleExam = scheduleExam;
        vm.addSchedule = addSchedule;
        vm.deleteTest = deleteTest;
        vm.getAllCourses = getAllCourses;
        vm.enterGrades = enterGrades;
        initController();

        $rootScope.$watch('examType', function (newValue, oldValue) {
            console.log(newValue, oldValue);
        })

        function getAllCourses(examId) {
          GradeService.GetAllCoursesForExam(examId)
              .then(function (courses) {
                  vm.courses = courses;
              });
        }
        function initController() {
            loadCurrentUser();
            loadTestSchedules();
            loadAllExams();
            loadAllStudents();
        }

        function loadTestSchedules(){
          GradeService.GetAllTestSchedules($rootScope.globals.currentUser.id)
              .then(function (practiceTests) {
                  vm.practiceTests = practiceTests;
              });
        }

        function loadCurrentUser() {
          UserService.GetByUsername($rootScope.globals.currentUser.username)
              .then(function (user) {
                  vm.user = user;
              });
        }

        function loadAllExams() {
            GradeService.GetAllExam()
                .then(function (exams) {
                    vm.exams = exams;
                });
        }

        function loadAllStudents(){
          //vm.availableStudents

        }

        function deleteUser(id) {
            UserService.Delete(id)
            .then(function () {
                loadAllUsers();
            });
        }

        function scheduleExam(){
          //TODO
          //Schedule A new tests
        }
        function deleteTest(id){
          var index = -1;
      		var testArray = eval( vm.practiceTests );
      		for( var i = 0; i < testArray.length; i++ ) {
      			if( testArray[i].id === id ) {
      				index = i;
      				break;
      			}
      		}
      		vm.practiceTests.splice( index, 1 );
        }
        function enterGrades(testId){
          $location.path('/grades').search({testId: testId});
        }

        function addSchedule(){

          vm.dataLoading = true;
          GradeService.CreateExamSchedule(vm.schedule)
              .then(function (response) {
                  if (response.id) {
                      vm.practiceTests.push(
                        response
                      );
                      $('#myModal').modal('toggle');
                  }
                  vm.dataLoading = false;
              });

        }

        function submitGrades(){
          //TODO
          //Submit user Grades
        }
    }

})();

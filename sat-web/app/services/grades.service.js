(function () {
    'use strict';

    angular
        .module('app')
        .factory('GradeService', GradeService);

    GradeService.$inject = ['$http'];
    function GradeService($http) {
        var service = {};

        service.GetByUserId = GetByUserId;
        service.GetAllExam = GetAllExam;
        service.GetAllCoursesForExam = GetAllCoursesForExam;
        service.CreateExamSchedule = CreateExamSchedule;
        service.GetAllTestSchedules = GetAllTestSchedules;
        service.GetGrades = GetGrades;
        service.Save = save;
        service.GetTest = GetTest;

        return service;

        function GetByUserId(user_id) {
            return $http.get('http://'+window.location.hostname+':8080/api/grades/'+user_id).then(handleSuccess, handleError('Error getting your grades'));
        }

        function GetAllExam(){
            return $http.get('http://'+window.location.hostname+':8080/api/exams').then(handleSuccess, handleError('Error getting Exams'));
        }

        function GetAllCoursesForExam(examId){
            return $http.get('http://'+window.location.hostname+':8080/api/courses?examid='+examId).then(handleSuccess, handleError('Error getting Courses'));
        }

        function CreateExamSchedule(schedule){
            return $http.post('http://'+window.location.hostname+':8080/api/schedule', schedule).then(handleSuccess, handleError('Error creating schedule'));
        }

        function GetAllTestSchedules(userId){
            return $http.get('http://'+window.location.hostname+':8080/api/schedule').then(handleSuccess, handleError('Error getting Schedules'));
        }

        function GetGrades(testId){
            return $http.get('http://'+window.location.hostname+':8080/api/grades?testId='+testId).then(handleSuccess, handleError('Error getting Schedules'));
        }

        function GetTest(testId){
            return $http.get('http://'+window.location.hostname+':8080/api/schedule/'+testId).then(handleSuccess, handleError('Error getting Schedules'));
        }

        function save(userGrade, callback){
          $http.post('http://'+window.location.hostname+':8080/api/grades', [userGrade])
              .success(function (response) {
                  callback(response);
              });
        }

        // private functions

        function handleSuccess(res) {
            return res.data;
        }

        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
    }

})();

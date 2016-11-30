# sat



cURLs
```
curl -X OPTIONS -H "Access-Control-Request-Method: POST" -H "Origin: http://192.168.99.100:8000" -H "Referer: http://192.168.99.100:8000/" -H "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36" -H "Access-Control-Request-Headers: authorization, content-type" -H "Cache-Control: no-cache" -H "Postman-Token: 42026793-2d4e-472e-172f-0bca15ff9513" "http://192.168.99.100:8080/api/authenticate"
```

```
curl -X POST -H "Authorization: Basic cm9oaXRuYmFAZ21haWwuY29tOnRlc3Q=" -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: b4b3a288-ea53-0aa4-dd32-7d7fb5195055" -d '[
  {
    "firstName": "Test",
    "lastName": "test",
    "courseName": "English",
    "examName": "SAT",
    "date": "2016-11-20",
    "grade": 9.1,
    "userName": "test@test.com",
    "testId": 1
  }
]' "http://localhost:8080/api/grades"
```

```
curl -X GET -H "Authorization: Basic dGVzdEB0ZXN0LmNvbTp0ZXN0" -H "Cache-Control: no-cache" -H "Postman-Token: 85e84621-d606-57b1-f8be-ad026ec2b1c7" "http://localhost:8080/api/users?type=S"
```

```
curl -X GET -H "Authorization: Basic dGVzdEB0ZXN0LmNvbTp0ZXN0" -H "Cache-Control: no-cache" -H "Postman-Token: 145b38e3-962a-6a1c-979c-94881ae88bf2" "http://localhost:8080/api/exams"
```

```
curl -X GET -H "Authorization: Basic dGVzdEB0ZXN0LmNvbTp0ZXN0" -H "Cache-Control: no-cache" -H "Postman-Token: 24a89035-3d05-a5ab-5914-153797a93c5a" "http://localhost:8080/api/courses?examid=1"
```

```
curl -X GET -H "Authorization: Basic dGVzdEB0ZXN0LmNvbTp0ZXN0" -H "Cache-Control: no-cache" -H "Postman-Token: 90c3d5c5-c8a9-abdf-4446-73e07e18ee48" "http://localhost:8080/api/grades?testId=1"
```

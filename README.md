# Task_Tracker 
Rest application for task management.
Technology used:
Spring Boot
Spring Data JPA 
Swagger 2
PostgreSQL
Junit 5 

To start use this application: 
1) Clone project: https://github.com/svistylin/Task_Tracker.git 
2) Download and install PostgreSQL https://www.postgresql.org/download/ or make Docker conatainer https://hub.docker.com/_/postgres. 
3) Open project in your IDE.
4) Set your Database connection login and password to application.properties file. DEFAULT database login:postgres, password:12345.
5) Run TaskTrackerApplication.class
6) Open any web browser and follow this link http://localhost:9091/swagger-ui.html#/  
7) For use some endpoints, which require to be autenticate please send post request to /api/login, DEFAULT user data is: login: misha  password: misha or create new user              (/api/registration) 
8) Enjoy :)
   

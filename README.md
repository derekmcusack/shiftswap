# shiftswap


This application is built for the purpose of shift workers swapping their shifts.

You need to update application.properties with your database connection credentials and email server info and credentials to run the application.
 
The framework will build the MySql database when the application is started.
However, before you register a new user you need to run the following SQL queries on the new database - otherwise you will get an exception when you try to register:

INSERT INTO `role` VALUES (1,'ADMIN');

INSERT INTO `role` VALUES (2,'USER');


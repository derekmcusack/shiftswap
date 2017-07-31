# shiftswap #


** An application for shift workers to use for swapping shifts **


- - - -
** To Run The Application **
* Update application.properties with your database connection credentials
* Update application.properties with your email server settings and credentials 
 
The framework will build the MySql database when the application is started:
you simply need to first create a database and then provide the name of the database 
when setting the spring.datasource.url in application.properties.

- - - -
** After starting the Application and before registering any users **

* Run the following SQL queries on the new database (otherwise you will get an exception when you try to register):

INSERT INTO `role` VALUES (1,'ADMIN');

INSERT INTO `role` VALUES (2,'USER');

- - - -
** To grant ADMIN privileges to a User **

update user_role set roleid = 1 where userid = (ID OF USER YOU WANT TO MAKE ADMIN);


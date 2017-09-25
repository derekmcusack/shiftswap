# shiftswap #


** An application for shift workers to use for swapping shifts **
- - - -
Note: I realise that the database structure for this application is not fully normalised, and ideally there should be two tables for the process of swapping shifts (swapOriginator and shiftSwap). This was my original intention, but when I encountered difficulty implementing this structure I decided to use just one table as a work-around. 

This then enabled me to deliver the promised functionality within the limited time available.

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


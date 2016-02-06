# MySQLPoc
POC with mySQL using restaurant administration as example

First of all:

$ mysql.server start;
$ mysql -u root;

mysql> CREATE DATABASE restaurant;
mysql> CREATE TABLE users(
           username varchar(20) PRIMARY KEY,
           password varchar (20),
           fullname varchar(50),
           type_of_user varchar(15)
       );

mysql> INSERT INTO users (username, password, fullname, type_of_user) VALUES ('admin', 'pass', 'Administrator', 'admin');
mysql> INSERT INTO users (username, password, fullname, type_of_user) VALUES ('client', '1234', 'Client', 'client');

DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS single_order;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS recipes;
DROP TABLE IF EXISTS plates;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS ingredients;

CREATE TABLE users(
    username varchar(20) PRIMARY KEY,
    password varchar (20),
    fullname varchar(50),
    type_of_user varchar(15)
);

INSERT INTO users (username, password, fullname, type_of_user) VALUES ('admin', 'pass', 'Administrator', 'admin');
INSERT INTO users (username, password, fullname, type_of_user) VALUES ('client', '1234', 'Cliente', 'client');

CREATE TABLE ingredients(
	i_name varchar(20) PRIMARY KEY,
	stock decimal(6,2),
	unit_of_weight varchar(20)
);

CREATE TABLE categories(
	c_name varchar(20) PRIMARY KEY,
	ordered varchar(20),
	description varchar(50)
);

CREATE TABLE plates(
	p_name varchar(20) PRIMARY KEY,
	description varchar(50),
	difficulty varchar(20),
	photo varchar(20),
	price decimal(6,2),
	category varchar(50),
	CONSTRAINT fk_category FOREIGN KEY(category)REFERENCES categories(c_name)
);

CREATE TABLE recipes(
	p_name varchar(20),
	i_name varchar(20),
	amount decimal(6,2),
	CONSTRAINT fk_p_name FOREIGN KEY(p_name)REFERENCES plates(p_name),
	CONSTRAINT fk_i_name FOREIGN KEY(i_name)REFERENCES ingredients(i_name)
);

CREATE TABLE orders(
    order_number varchar(20),
    username varchar(20),
    date date,
    price decimal(6,2),
    CONSTRAINT fk_username FOREIGN KEY(username)REFERENCES users(username)
);

CREATE TABLE single_order(
    order_number varchar(20),
    plate varchar(20),
    amount int unsigned
);


INSERT INTO ingredients (i_name,stock,unit_of_weight) VALUES ('macarrones',10.00,'kg');
INSERT INTO ingredients (i_name,stock,unit_of_weight) VALUES ('tomate',5.00,'kg');
INSERT INTO ingredients (i_name,stock,unit_of_weight) VALUES ('patatas',20.00,'kg');
INSERT INTO ingredients (i_name,stock,unit_of_weight) VALUES ('pollo',10.00,'kg');

INSERT INTO categories (c_name,ordered,description) VALUES ('casera','pepe','comida casera');
INSERT INTO categories (c_name,ordered,description) VALUES ('futurista','juan','comida futurista');

INSERT INTO plates (p_name,description,difficulty,photo,price,category) VALUES ('macarrones','macarrones con tomate','facil','macarones.jpg',3.50,'casera');
INSERT INTO plates (p_name,description,difficulty,photo,price,category) VALUES ('pollo','pollo con patatas','media','pollo.jpg',6.95,'casera');


INSERT INTO recipes (p_name,i_name,amount) VALUES ('macarrones','macarrones',200.00);
INSERT INTO recipes (p_name,i_name,amount) VALUES ('macarrones','tomate',200.00);
INSERT INTO recipes (p_name,i_name,amount) VALUES ('pollo','pollo',150.00);
INSERT INTO recipes (p_name,i_name,amount) VALUES ('pollo','patatas',3.00);
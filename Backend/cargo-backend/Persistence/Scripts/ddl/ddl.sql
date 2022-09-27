-- Sample Usage to call Procedures via terminal
-- call proc_get_car_make_v1dot0(0,@a,@b);
-- select @a,@b;

drop database if exists cargo;
create database cargo;
use cargo;
create table tbl_make(
c_make_id int NOT NULL AUTO_INCREMENT,
c_make_name varchar(30) NOT NULL, 
c_make_description varchar(500) ,
c_make_status tinyint(1) NOT NULL DEFAULT '1',
PRIMARY KEY (c_make_id),
UNIQUE KEY (c_make_name)
);

insert into tbl_make
(c_make_name,c_make_description,c_make_status)
values
("Volkswagen","Volkswagen desc",1),
("Ford","Ford desc",1),
("Chevrolet","Chevrolet desc",1),
("Honda","Honda desc",1);


create table tbl_body_type(
c_body_type_id int NOT NULL AUTO_INCREMENT,
c_body_type_name varchar(30) NOT NULL, 
c_body_type_description varchar(500) ,
c_body_type_status tinyint(1) NOT NULL DEFAULT '1',
PRIMARY KEY (c_body_type_id),
UNIQUE KEY (c_body_type_name)
);

insert into tbl_body_type
(c_body_type_name,c_body_type_description,c_body_type_status)
values
("Hatchback","Hatchback desc",1),
("Sedan","Sedan desc",1),
("SUV","SUV",1);

create table tbl_model(
c_model_id int NOT NULL AUTO_INCREMENT,
c_model_name varchar(30) NOT NULL, 
c_model_description varchar(500) ,
c_model_status tinyint(1) NOT NULL DEFAULT '1', 
c_make_id int NOT NULL,
c_body_type_id int NOT NULL,
PRIMARY KEY (c_model_id),
UNIQUE KEY (c_model_name),
FOREIGN KEY (c_make_id) REFERENCES tbl_make(c_make_id)
);

insert into tbl_model
(c_model_name,c_model_description,c_model_status,c_make_id,c_body_type_id)
values
("Polo","Polo desc",1,1,1),
("Vento","Vento desc",1,1,2),
("Virtus","Virtus desc",1,1,2),
("Taigun","Taigun desc",1,1,3),
("T-Roc","T-Roc desc",1,1,3);


create table tbl_color(
c_color_id int NOT NULL AUTO_INCREMENT,
c_color_name varchar(30) NOT NULL, 
c_color_description varchar(500), 
PRIMARY KEY (c_color_id),
UNIQUE KEY (c_color_name)
);

insert into tbl_color
(c_color_name,c_color_description)
values
("White","White desc"),
("Red","Red desc"),
("Silver","Silver desc");


create table tbl_fuel_type(
c_fuel_type_id int NOT NULL AUTO_INCREMENT,
c_fuel_type_name varchar(30) NOT NULL, 
c_fuel_type_description varchar(500) ,
c_fuel_type_status tinyint(1) NOT NULL DEFAULT '1',
PRIMARY KEY (c_fuel_type_id),
UNIQUE KEY (c_fuel_type_name)
);

insert into tbl_fuel_type
(c_fuel_type_name,c_fuel_type_description,c_fuel_type_status)
values
("Petrol","Petrol desc",1),
("Diesel","Diesel desc",1),
("Hybrid","Hybrid desc",1),
("Electric","Electric desc",1);


create table tbl_transmission_type(
c_transmission_type_id int NOT NULL AUTO_INCREMENT,
c_transmission_type_name varchar(30) NOT NULL, 
c_transmission_type_description varchar(500) ,
c_transmission_type_status tinyint(1) NOT NULL DEFAULT '1',
PRIMARY KEY (c_transmission_type_id),
UNIQUE KEY (c_transmission_type_name)
);

insert into tbl_transmission_type
(c_transmission_type_name,c_transmission_type_description,c_transmission_type_status)
values
("Manual","Manual desc",1),
("AMT","AMT desc",1),
("Automatic","Automatic desc",1);


create table tbl_variant(
c_variant_id int NOT NULL AUTO_INCREMENT,
c_variant_name varchar(30) NOT NULL, 
c_variant_description varchar(500) ,
c_variant_status tinyint(1) NOT NULL DEFAULT '1',
c_model_id int NOT NULL,
c_color_id int NOT NULL,
c_fuel_type_id int NOT NULL,
c_transmission_type_id int NOT NULL,
c_mileage BIGINT(20) NOT NULL,
c_manufacturing_date DATE NOT NULL,
c_price_per_kilometer BIGINT(20) NOT NULL,
c_kilometers_driven BIGINT(20) NOT NULL,
c_number_plate varchar(30) NOT NULL, 
PRIMARY KEY (c_variant_id),
FOREIGN KEY (c_model_id) REFERENCES tbl_model(c_model_id),
FOREIGN KEY (c_color_id) REFERENCES tbl_color(c_color_id),
FOREIGN KEY (c_fuel_type_id) REFERENCES tbl_fuel_type(c_fuel_type_id),
FOREIGN KEY (c_transmission_type_id) REFERENCES tbl_transmission_type(c_transmission_type_id)
);

insert into tbl_variant
(c_variant_name,c_variant_description,c_variant_status,c_model_id,c_color_id,c_fuel_type_id,c_transmission_type_id,c_mileage,
c_manufacturing_date,c_price_per_kilometer,c_kilometers_driven,c_number_plate)
values
("Highline 1.2 ","Highline 1.2 desc",1,1,1,1,1,15,"2020-01-01",12,1000,"Cargo"),
("Highline 1.2 ","Highline 1.2 desc",1,1,1,1,2,13,"2020-02-01",14,1500,"Cargo"),
("Highline 1.2 ","Highline 1.2 desc",1,1,1,1,3,11,"2020-02-01",16,1200,"Cargo"),

("Highline 1.2 ","Highline 1.2 desc",1,1,2,1,1,15,"2020-01-01",12,1000,"Cargo"),
("Highline 1.2 ","Highline 1.2 desc",1,1,2,1,2,13,"2020-02-01",14,1500,"Cargo"),
("Highline 1.2 ","Highline 1.2 desc",1,1,2,1,3,11,"2020-02-01",16,1200,"Cargo"),

("Highline 1.2 ","Highline 1.2 desc",1,1,3,1,1,15,"2020-01-01",12,1000,"Cargo"),
("Highline 1.2 ","Highline 1.2 desc",1,1,3,1,2,13,"2020-02-01",14,1500,"Cargo"),
("Highline 1.2 ","Highline 1.2 desc",1,1,3,1,3,11,"2020-02-01",16,1200,"Cargo"),

("Highline 1.2 ","Highline 1.2 desc",1,1,1,2,1,15,"2020-01-01",12,1000,"Cargo"),
("Highline 1.2 ","Highline 1.2 desc",1,1,1,2,2,13,"2020-02-01",14,1500,"Cargo"),
("Highline 1.2 ","Highline 1.2 desc",1,1,1,2,3,11,"2020-02-01",16,1200,"Cargo"),

("Highline 1.2 ","Highline 1.2 desc",1,1,2,2,1,15,"2020-01-01",12,1000,"Cargo"),
("Highline 1.2 ","Highline 1.2 desc",1,1,2,2,2,13,"2020-02-01",14,1500,"Cargo"),
("Highline 1.2 ","Highline 1.2 desc",1,1,2,2,3,11,"2020-02-01",16,1200,"Cargo"),

("Highline 1.2 ","Highline 1.2 desc",1,1,3,2,1,15,"2020-01-01",12,1000,"Cargo"),
("Highline 1.2 ","Highline 1.2 desc",1,1,3,2,2,13,"2020-02-01",14,1500,"Cargo"),
("Highline 1.2 ","Highline 1.2 desc",1,1,3,2,3,11,"2020-02-01",16,1200,"Cargo");


create table tbl_variant_image(
c_variant_image_id int NOT NULL AUTO_INCREMENT,
c_variant_image varchar(500) NOT NULL,
c_variant_image_view ENUM('EXTERIOR', 'INTERIOR')  NOT NULL, 
c_variant_image_description varchar(500)  DEFAULT 'Desc',
c_variant_image_status tinyint(1) NOT NULL DEFAULT '1',
c_variant_id int NOT NULL,
PRIMARY KEY (c_variant_image_id),
FOREIGN KEY (c_variant_id) REFERENCES tbl_variant(c_variant_id)
);

insert into tbl_variant_image
(c_variant_image,c_variant_image_view,c_variant_image_status,c_variant_id)
values
(".//assets//Dump//Volkswagen//Polo//white.jpg","EXTERIOR",1,1),
(".//assets//Dump//Volkswagen//Polo//white.jpg","EXTERIOR",1,2),
(".//assets//Dump//Volkswagen//Polo//white.jpg","EXTERIOR",1,3),

(".//assets//Dump//Volkswagen//Polo//red.jpg","EXTERIOR",1,4),
(".//assets//Dump//Volkswagen//Polo//red.jpg","EXTERIOR",1,5),
(".//assets//Dump//Volkswagen//Polo//red.jpg","EXTERIOR",1,6),

(".//assets//Dump//Volkswagen//Polo//silver.jpg","EXTERIOR",1,7),
(".//assets//Dump//Volkswagen//Polo//silver.jpg","EXTERIOR",1,8),
(".//assets//Dump//Volkswagen//Polo//silver.jpg","EXTERIOR",1,9),

(".//assets//Dump//Volkswagen//Polo//white.jpg","EXTERIOR",1,10),
(".//assets//Dump//Volkswagen//Polo//white.jpg","EXTERIOR",1,11),
(".//assets//Dump//Volkswagen//Polo//white.jpg","EXTERIOR",1,12),

(".//assets//Dump//Volkswagen//Polo//red.jpg","EXTERIOR",1,13),
(".//assets//Dump//Volkswagen//Polo//red.jpg","EXTERIOR",1,14),
(".//assets//Dump//Volkswagen//Polo//red.jpg","EXTERIOR",1,15),

(".//assets//Dump//Volkswagen//Polo//silver.jpg","EXTERIOR",1,16),
(".//assets//Dump//Volkswagen//Polo//silver.jpg","EXTERIOR",1,17),
(".//assets//Dump//Volkswagen//Polo//silver.jpg","EXTERIOR",1,18);


create table tbl_location(
c_location_id int NOT NULL AUTO_INCREMENT,
c_location_name varchar(30) NOT NULL, 
c_location_gps varchar(500)  DEFAULT NULL,
c_location_state_name varchar(30) NOT NULL, 
c_location_country_name varchar(30) NOT NULL, 
c_location_status tinyint(1) NOT NULL DEFAULT '1',
PRIMARY KEY (c_location_id),
UNIQUE KEY (c_location_name)
);

insert into tbl_location
(c_location_name,c_location_state_name,c_location_country_name,c_location_status)
values
("Amsterdam","New York","USA",1),
("New York City","New York","USA",1),
("Albany","New York","USA",0),
("Buffalo","New York","USA",0),
("Geneva","New York","USA",0),
("Hornell","New York","USA",0),
("Jamestown","New York","USA",0),
("Olean","New York","USA",0);

create table tbl_variant_location_history(
c_variant_location_id int NOT NULL AUTO_INCREMENT,
c_location_id int NOT NULL,
c_variant_id int NOT NULL,
c_is_available tinyint(1) NOT NULL DEFAULT '1',
PRIMARY KEY (c_variant_location_id),
FOREIGN KEY (c_location_id) REFERENCES tbl_location(c_location_id),
FOREIGN KEY (c_variant_id) REFERENCES tbl_variant(c_variant_id)
);

insert into tbl_variant_location_history
(c_location_id,c_variant_id,c_is_available)
values
(1,1,1),(1,2,1),(1,3,1),(1,4,1),(1,5,1),(1,6,1),(1,16,1),(1,17,1),(1,18,1),
(2,7,1),(2,8,1),(2,9,1),(2,10,1),(2,11,1),(2,12,1),(2,13,1),(2,14,1),(2,15,1);


create table tbl_user(
c_user_id int NOT NULL AUTO_INCREMENT,
c_user_name varchar(30) NOT NULL, 
c_user_type ENUM('GUEST', 'REGISTERED','ADMIN')  NOT NULL, 
c_user_verification_type varchar(30) DEFAULT NULL,  
c_user_description varchar(500) DEFAULT NULL,
c_user_email varchar(30) NOT NULL, 
c_user_mobile_number varchar(15) NOT NULL, 
c_user_status tinyint(1) NOT NULL DEFAULT '1',
c_user_password varchar(30) NOT NULL,
PRIMARY KEY (c_user_id),
UNIQUE KEY (c_user_name)
);


create table tbl_auth(
c_auth_id int NOT NULL AUTO_INCREMENT,
c_user_id int NOT NULL,
c_auth_token varchar(30) DEFAULT NULL, 
c_login_status tinyint(1) NOT NULL DEFAULT '1',
PRIMARY KEY (c_auth_id),
FOREIGN KEY (c_user_id) REFERENCES tbl_user(c_user_id)
);



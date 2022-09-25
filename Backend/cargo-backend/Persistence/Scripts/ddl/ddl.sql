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
("Maruti Suzuki","Maruti Suzuki desc",1),
("Hyundai","Hyundai desc",1),
("Ford","Ford desc",0);

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
("SUV","SUV",1),
("MUV","MUV desc",1),;

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
("Ertiga","Ertiga desc",1,1,4),
("Breza","Breza desc",1,1,3),
("Baleno","Baleno desc",1,1,1),
("Dzire","Dzire desc",1,1,2),
("Alto","Alto desc",1,1,1),
("Alcazar","Alcazar desc",1,2,3),
("Venue","Venue desc",1,2,3),
("Elantra","Elantra desc",1,2,2),
("Aura","Aura desc",1,2,2),
("i20","i20 desc",1,2,1),
("Santra","Santra desc",1,2,1);


create table tbl_color(
c_color_id int NOT NULL AUTO_INCREMENT,
c_color_name varchar(30) NOT NULL, 
c_color_description varchar(500) 
PRIMARY KEY (c_color_id),
UNIQUE KEY (c_color_name)
);

insert into tbl_color
(c_color_name,c_color_description)
values
("White","White desc"),
("Red","Red desc"),
("Blue","Blue desc");


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
CONSTRAINT u_variant_fuel_tansmission UNIQUE(c_variant_name,c_fuel_type_id,c_transmission_type_id),
FOREIGN KEY (c_model_id) REFERENCES tbl_model(c_model_id),
FOREIGN KEY (c_color_id) REFERENCES tbl_color(c_color_id),
FOREIGN KEY (c_fuel_type_id) REFERENCES tbl_fuel_type(c_fuel_type_id),
FOREIGN KEY (c_transmission_type_id) REFERENCES tbl_transmission_type(c_transmission_type_id)
);



-- ("Alcazar","Alcazar desc",1,2,3),
-- ("Venue","Venue desc",1,2,3),
-- ("Elantra","Elantra desc",1,2,2),
-- ("Aura","Aura desc",1,2,2),
-- ("i20","i20 desc",1,2,1),
-- ("Santra","Santra desc",1,2,1);


-- insert into tbl_variant
-- (c_variant_name,c_variant_description,c_variant_status,c_model_id,c_color_id,c_fuel_type_id,c_transmission_type_id,
-- c_mileage,c_manufacturing_date,c_price_per_kilometer,c_kilometers_driven,c_number_plate)
-- values
-- ("Manual","Manual desc",1),
-- ("AMT","AMT desc",1),
-- ("Automatic","Automatic desc",1);


create table tbl_variant_image(
c_variant_image_id int NOT NULL AUTO_INCREMENT,
c_variant_image_view ENUM('exterior', 'interior')  NOT NULL, 
c_variant_image_description varchar(500) ,
c_variant_image_status tinyint(1) NOT NULL DEFAULT '1',
c_variant_id int NOT NULL,
PRIMARY KEY (c_variant_image_id),
FOREIGN KEY (c_variant_id) REFERENCES tbl_variant(c_variant_id)
);

-- tbl_car_image
-- c_id 
-- c_car_id
-- c_view :- ENUM :- TOP / LSIDE / RSIDE / REAR / FRONT / CUSTOM
-- c_image :- blob
-- c_description :- 


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



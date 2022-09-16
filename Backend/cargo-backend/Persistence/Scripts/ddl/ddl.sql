create database cargo;
use cargo;
create table tbl_make(
c_make_id int NOT NULL AUTO_INCREMENT,
c_make_name varchar(30) NOT NULL, 
c_make_description varchar(500) ,
c_status tinyint(1) NOT NULL DEFAULT '1',
PRIMARY KEY (c_make_id),
UNIQUE KEY (c_make_name)
);

insert into tbl_make
(c_make_name,c_make_description,c_status)
values
("Maruti Suzuki","Maruti Suzuki desc",1),
("Hyundai","Hyundai desc",1)
("Ford","Ford desc",0);


-- Sample Usage to call Procedures via terminal
-- call proc_get_car_make_v1dot0(0,@a,@b);
-- select @a,@b;
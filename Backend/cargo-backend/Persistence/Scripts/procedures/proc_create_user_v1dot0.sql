delimiter #
USE cargo#
SELECT DATABASE()#
DROP PROCEDURE IF EXISTS proc_create_user_v1dot0#

CREATE PROCEDURE proc_create_user_v1dot0(
        IN  in_user_name  VARCHAR(30),
        IN  in_user_email  VARCHAR(30),
        IN  in_user_password  VARCHAR(30),
        IN  in_user_mobile_number VARCHAR(15),
        IN  in_user_type VARCHAR(30),
        OUT  out_status   INT,
        OUT  out_err_msg  VARCHAR(250)
)
BEGIN

        DECLARE v_user_name VARCHAR(30) DEFAULT NULL;
        DECLARE v_user_id,v_auth_id int DEFAULT NULL;

        DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
                GET DIAGNOSTICS CONDITION 1
				out_err_msg = MESSAGE_TEXT;
                SET out_err_msg = CONCAT('proc_create_user_v1dot0',out_err_msg);
                SET out_status = 500;
        ROLLBACK;
        END;


        SET out_status = 500;
        SET out_err_msg = '';


        START TRANSACTION;
        BEGIN
                select c_user_name into v_user_name from tbl_user where c_user_name = in_user_name;

                IF(v_user_name is not null) THEN
                        SET out_status = 409;
                        SET out_err_msg = CONCAT('User Name already exists',out_err_msg);
                ELSE
                       insert into tbl_user(c_user_name,c_user_email,c_user_mobile_number,c_user_type,c_user_password)
                       values(in_user_name,in_user_email,in_user_mobile_number,in_user_type,in_user_password);

                       select LAST_INSERT_ID() into v_user_id;

                       insert into tbl_auth(c_user_id,c_login_status)
                       values(v_user_id,1);

                       select LAST_INSERT_ID() into v_auth_id;

                       SET out_status = 200;
                END IF;

                select v_auth_id;
          
        END;

        IF(out_status = 200) THEN 
		    COMMIT;
	    END IF;

END #
delimiter ;

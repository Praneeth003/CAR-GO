delimiter #
USE cargo#
SELECT DATABASE()#
DROP PROCEDURE IF EXISTS proc_create_user_profile_v1dot0#

CREATE PROCEDURE proc_create_user_profile_v1dot0(
        IN  in_user_id  INT,
        IN  in_user_profile_name  VARCHAR(30),
        IN  in_licence_number  VARCHAR(30),
        IN  in_licence_image blob,
        IN  in_is_active  tinyint(1),
        IN  in_is_primary tinyint(1),
        OUT  out_status   INT,
        OUT  out_err_msg  VARCHAR(250)
)
BEGIN

        DECLARE v_user_profile_id int DEFAULT NULL;
        DECLARE v_user_profile_name VARCHAR(30) DEFAULT NULL;
        

        DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
                GET DIAGNOSTICS CONDITION 1
				out_err_msg = MESSAGE_TEXT;
                SET out_err_msg = CONCAT('proc_create_user_profile_v1dot0',out_err_msg);
                SET out_status = 500;
        ROLLBACK;
        END;


        SET out_status = 500;
        SET out_err_msg = '';


        START TRANSACTION;
        BEGIN
                select c_user_profile_name into v_user_profile_name from tbl_user_profile where c_licence_number = in_licence_number and c_user_id = in_user_id;

                IF(v_user_profile_name is not null) THEN
                        SET out_status = 409;
                        SET out_err_msg = CONCAT('User Profile already exists, Please Add New Licence Number',out_err_msg);
                ELSE
                       insert into tbl_user_profile(c_user_id,c_user_profile_name,c_licence_number,c_licence_image,c_is_active,c_is_primary)
                       values(in_user_id,in_user_profile_name,in_licence_number,in_licence_image,in_is_active,in_is_primary);

                       select LAST_INSERT_ID() into v_user_profile_id;

                       SET out_status = 200;
                END IF;

                select v_user_profile_id;
          
        END;

        IF(out_status = 200) THEN 
		    COMMIT;
	    END IF;

END #
delimiter ;

delimiter #
USE cargo#
SELECT DATABASE()#
DROP PROCEDURE IF EXISTS proc_authenticate_user_details_v1dot0#

CREATE PROCEDURE proc_authenticate_user_details_v1dot0(
        IN  in_user_name  VARCHAR(30),
        IN  in_user_password  VARCHAR(30),
        OUT  out_status   INT,
        OUT  out_err_msg  VARCHAR(250)
)
BEGIN

        DECLARE v_user_id,v_auth_id int DEFAULT NULL;
        DECLARE v_user_name,v_user_password VARCHAR(30) DEFAULT NULL;

        DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
                GET DIAGNOSTICS CONDITION 1
				out_err_msg = MESSAGE_TEXT;
                SET out_err_msg = CONCAT('proc_authenticate_user_details_v1dot0',out_err_msg);
                SET out_status = 500;
        END;

        SET out_status = 500;
        SET out_err_msg = '';

        BEGIN

            select c_user_id,c_user_password 
            into   v_user_id,v_user_password 
            from tbl_user where c_user_name = in_user_name;

            if(v_user_id is null) THEN
                SET out_status = 404;
                SET out_err_msg = CONCAT('User not found',out_err_msg);
            else 
                if(v_user_password = in_user_password) THEN
                   
                    insert into tbl_auth(c_user_id,c_login_status)
                    values(v_user_id,1);

                    select LAST_INSERT_ID() into v_auth_id;

                    SET out_status = 200;

                else
                    SET out_status = 406;
                    SET out_err_msg = CONCAT('Incorrect Password',out_err_msg);
                end if;

            end if ;

            select v_auth_id;

        END;


END #
delimiter ;

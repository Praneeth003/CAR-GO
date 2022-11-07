delimiter #
USE cargo#
SELECT DATABASE()#
DROP PROCEDURE IF EXISTS proc_logout_user_v1dot0#

CREATE PROCEDURE proc_logout_user_v1dot0(
	    IN  in_user_id  INT,
        IN  in_auth_id  INT,
        IN  in_user_name  VARCHAR(30),
        OUT  out_status   INT,
        OUT  out_err_msg  VARCHAR(250)
)
BEGIN
        DECLARE v_user_id INT DEFAULT NULL;
        DECLARE v_user_name,v_user_email VARCHAR(30) DEFAULT NULL;
        DECLARE v_user_mobile_number VARCHAR(15) DEFAULT NULL;
        DECLARE v_login_status tinyint(1) DEFAULT '0';

        DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
                GET DIAGNOSTICS CONDITION 1
				out_err_msg = MESSAGE_TEXT;
                SET out_err_msg = CONCAT('proc_logout_user_v1dot0',out_err_msg);
                SET out_status = 500;
        END;

        SET out_status = 500;
        SET out_err_msg = '';

        BEGIN
                if(in_auth_id is null) then
                
                        select user.c_user_id,c_user_name,c_user_email,c_user_mobile_number
                        into  v_user_id,v_user_name,v_user_email,v_user_mobile_number
                        from tbl_user user 
                        where user.c_user_name = IF(in_user_name is not null,in_user_name,c_user_name) and
                        user.c_user_id = IF(in_user_id is not null,in_user_id, user.c_user_id) ;

                        if(v_user_id is not null) then   
                                update tbl_auth set c_login_status =0
                                where c_user_id = v_user_id;
                                SET out_status = 200;
                        else
                                SET out_status = 404;
                                SET out_err_msg = CONCAT('User not found',out_err_msg);
                        end if;

                else 
                        select user.c_user_id,c_user_name,c_user_email,c_user_mobile_number,auth.c_login_status
                        into  v_user_id,v_user_name,v_user_email,v_user_mobile_number,v_login_status
                        from tbl_user user inner join tbl_auth auth on auth.c_user_id = user.c_user_id
                        where auth.c_auth_id = IF(in_auth_id is not null,in_auth_id,auth.c_auth_id);

                        if(v_user_id is null) then
                                SET out_status = 404;
                                SET out_err_msg = CONCAT('User not found',out_err_msg);
                        else 
                                update tbl_auth set c_login_status =0
                                where c_auth_id = in_auth_id;
                                SET out_status = 200;    
                        end if;

                end if;
        END;


END #
delimiter ;

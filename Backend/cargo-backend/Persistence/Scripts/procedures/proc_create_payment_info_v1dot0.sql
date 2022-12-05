delimiter #
USE cargo#
SELECT DATABASE()#
DROP PROCEDURE IF EXISTS proc_create_payment_info_v1dot0#

CREATE PROCEDURE proc_create_payment_info_v1dot0(
        IN  in_user_id  INT,
        IN  in_payment_method_info  text,
        IN  in_is_active  tinyint(1),
        OUT  out_status   INT,
        OUT  out_err_msg  VARCHAR(250)
)
BEGIN

        DECLARE v_payment_info_id int DEFAULT NULL;
        

        DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
                GET DIAGNOSTICS CONDITION 1
				out_err_msg = MESSAGE_TEXT;
                SET out_err_msg = CONCAT('proc_create_payment_info_v1dot0',out_err_msg);
                SET out_status = 500;
        ROLLBACK;
        END;


        SET out_status = 500;
        SET out_err_msg = '';


        START TRANSACTION;
        BEGIN
                insert into tbl_payment_info(c_user_id,c_payment_method_info,c_is_active)
                values (in_user_id,in_payment_method_info,in_is_active);

                select LAST_INSERT_ID() into v_payment_info_id;

                SET out_status = 200;

                select v_payment_info_id;
          
        END;

        IF(out_status = 200) THEN 
		    COMMIT;
	    END IF;

END #
delimiter ;

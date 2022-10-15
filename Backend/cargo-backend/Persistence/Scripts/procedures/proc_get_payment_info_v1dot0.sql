USE cargo;
SELECT DATABASE();

DELIMITER #

DROP PROCEDURE IF EXISTS proc_get_payment_info_v1dot0 #

CREATE PROCEDURE proc_get_payment_info_v1dot0(
    IN  in_id 								 INT,
	OUT out_status 								 INT,
	OUT	out_err_msg								 VARCHAR(250)
)
BEGIN

    DECLARE v_query, v_sub_query1 LONGTEXT default "";

	DECLARE EXIT HANDLER FOR SQLEXCEPTION BEGIN
            SET out_err_msg = CONCAT('proc_get_payment_info_v1dot0',out_err_msg);
            SET out_status = 500;
	END;

    SET out_status = 200;
    SET out_err_msg = '';

	
    SET v_sub_query1 =CONCAT("c_user_id = '",in_id,"'");

	SET v_query = CONCAT("SELECT * FROM tbl_payment_info where ",v_sub_query1);	
	
	SET @t2  = v_query;
	
	PREPARE stmt3 FROM @t2;
	EXECUTE stmt3;	
	DEALLOCATE PREPARE stmt3;


END #
delimiter ;

USE cargo;
SELECT DATABASE();

DELIMITER #

DROP PROCEDURE IF EXISTS proc_get_location_v1dot0 #

CREATE PROCEDURE proc_get_location_v1dot0(
	OUT out_status 								 INT,
	OUT	out_err_msg								 VARCHAR(250)
)
BEGIN

    DECLARE v_query LONGTEXT default "";

	DECLARE EXIT HANDLER FOR SQLEXCEPTION BEGIN
            SET out_err_msg = CONCAT('proc_get_location_v1dot0',out_err_msg);
            SET out_status = 500;
	END;

    SET out_status = 200;
    SET out_err_msg = '';

	SET v_query ="SELECT * FROM tbl_location";	
	
	SET @t2  = v_query;
	
	PREPARE stmt3 FROM @t2;
	EXECUTE stmt3;	
	DEALLOCATE PREPARE stmt3;


END #
delimiter ;

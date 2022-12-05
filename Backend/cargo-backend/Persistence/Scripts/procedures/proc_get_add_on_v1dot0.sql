USE cargo;
SELECT DATABASE();

DELIMITER #

DROP PROCEDURE IF EXISTS proc_get_add_on_v1dot0 #

CREATE PROCEDURE proc_get_add_on_v1dot0(
	IN in_add_on_delimitted_string               TEXT,
	OUT out_status 								 INT,
	OUT	out_err_msg								 VARCHAR(250)
)
BEGIN

    DECLARE v_query LONGTEXT default "";

	DECLARE EXIT HANDLER FOR SQLEXCEPTION BEGIN
            SET out_err_msg = CONCAT('proc_get_add_on_v1dot0',out_err_msg);
            SET out_status = 500;
	END;

    SET out_status = 200;
    SET out_err_msg = '';

	
	SET v_query ="SELECT * FROM tbl_add_on";

	IF(in_add_on_delimitted_string <> "ALL") THEN
          SET in_add_on_delimitted_string = REPLACE(in_add_on_delimitted_string,',','\',\'');	
          SET v_query =CONCAT(v_query, " where c_add_on_id in ('",in_add_on_delimitted_string,"')");
    END IF;	
	
	SET @t2  = v_query;
	
	PREPARE stmt3 FROM @t2;
	EXECUTE stmt3;	
	DEALLOCATE PREPARE stmt3;


END #
delimiter ;

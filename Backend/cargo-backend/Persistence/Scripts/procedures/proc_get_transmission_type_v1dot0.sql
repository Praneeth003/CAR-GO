USE cargo;
SELECT DATABASE();

DELIMITER #

DROP PROCEDURE IF EXISTS proc_get_transmission_type_v1dot0 #

CREATE PROCEDURE proc_get_transmission_type_v1dot0(
  	IN  in_transmission_type_id_delimited_string          VARCHAR(1000),
    IN  in_transmission_type_name_delimited_string        VARCHAR(1000),
	OUT out_status 								 INT,
	OUT	out_err_msg								 VARCHAR(250)
)
BEGIN

	DECLARE v_query, v_sub_query1, v_sub_query2 LONGTEXT default "";
		
	DECLARE EXIT HANDLER FOR SQLEXCEPTION BEGIN
            SET out_err_msg = CONCAT('proc_get_transmission_type_v1dot0',out_err_msg);
            SET out_status = 500;
	END;

    SET out_status = 200;
    SET out_err_msg = '';

    IF(in_transmission_type_id_delimited_string <> "ALL") THEN
          SET in_transmission_type_id_delimited_string = REPLACE(in_transmission_type_id_delimited_string,',','\',\'');	
          SET v_sub_query1 =CONCAT("c_transmission_type_id in ('",in_transmission_type_id_delimited_string,"')");
    ELSE 
         SET v_sub_query1 =CONCAT("c_transmission_type_id =  c_transmission_type_id");
    END IF;

    IF(in_transmission_type_name_delimited_string <> "ALL") THEN
         SET in_transmission_type_name_delimited_string = REPLACE(in_transmission_type_name_delimited_string,',','\',\'');	
         SET v_sub_query2 =CONCAT("c_transmission_type_name in ('",in_transmission_type_name_delimited_string,"')");
    ELSE 
         SET v_sub_query2 =CONCAT("c_transmission_type_name =  c_transmission_type_name");
    END IF;


	SET v_query =CONCAT("SELECT * FROM tbl_transmission_type WHERE ",v_sub_query1 ," AND ",v_sub_query2);	
	
	SET @t2  = v_query;
	
	PREPARE stmt3 FROM @t2;
	EXECUTE stmt3;	
	DEALLOCATE PREPARE stmt3;


END #
delimiter ;

USE cargo;
SELECT DATABASE();

DELIMITER #

DROP PROCEDURE IF EXISTS proc_get_model_v1dot0 #

CREATE PROCEDURE proc_get_model_v1dot0(
  	IN  in_model_id_delimited_string          VARCHAR(1000),
    IN  in_model_name_delimited_string        VARCHAR(1000),
    IN  in_make_id_delimited_string          VARCHAR(1000),
    IN  in_body_type_id_delimited_string          VARCHAR(1000),
	OUT out_status 								 INT,
	OUT	out_err_msg								 VARCHAR(250)
)
BEGIN

	DECLARE v_query, v_sub_query1, v_sub_query2,v_sub_query3,v_sub_query4 LONGTEXT default "";
		
	DECLARE EXIT HANDLER FOR SQLEXCEPTION BEGIN
            SET out_err_msg = CONCAT('proc_get_model_v1dot0',out_err_msg);
            SET out_status = 500;
	END;

    SET out_status = 200;
    SET out_err_msg = '';

    IF(in_model_id_delimited_string <> "ALL") THEN
          SET in_model_id_delimited_string = REPLACE(in_model_id_delimited_string,',','\',\'');	
          SET v_sub_query1 =CONCAT("a.c_model_id in ('",in_model_id_delimited_string,"')");
    ELSE 
         SET v_sub_query1 =CONCAT("a.c_model_id =  a.c_model_id");
    END IF;

    IF(in_model_name_delimited_string <> "ALL") THEN
         SET in_model_name_delimited_string = REPLACE(in_model_name_delimited_string,',','\',\'');	
         SET v_sub_query2 =CONCAT("a.c_model_name in ('",in_model_name_delimited_string,"')");
    ELSE 
         SET v_sub_query2 =CONCAT("a.c_model_name =  a.c_model_name");
    END IF;

    
    IF(in_make_id_delimited_string <> "ALL") THEN
          SET in_make_id_delimited_string = REPLACE(in_make_id_delimited_string,',','\',\'');	
          SET v_sub_query3 =CONCAT("a.c_make_id in ('",in_make_id_delimited_string,"')");
    ELSE 
         SET v_sub_query3 =CONCAT("a.c_make_id =  a.c_make_id");
    END IF;
    
    IF(in_body_type_id_delimited_string <> "ALL") THEN
          SET in_body_type_id_delimited_string = REPLACE(in_body_type_id_delimited_string,',','\',\'');	
          SET v_sub_query4 =CONCAT("a.c_body_type_id in ('",in_body_type_id_delimited_string,"')");
    ELSE 
         SET v_sub_query4 =CONCAT("a.c_body_type_id =  a.c_body_type_id");
    END IF;


	SET v_query =CONCAT("SELECT c.*,a.c_model_id,a.c_model_name,a.c_model_description,a.c_model_status,b.* FROM tbl_model a inner join tbl_body_type b on b.c_body_type_id = a.c_body_type_id
                         inner join tbl_make c on c.c_make_id = a.c_make_id
                        WHERE ",v_sub_query1 ," AND ",v_sub_query2," AND ",v_sub_query3," AND ",v_sub_query4);	
	
	SET @t2  = v_query;
	
	PREPARE stmt3 FROM @t2;
	EXECUTE stmt3;	
	DEALLOCATE PREPARE stmt3;


END #
delimiter ;

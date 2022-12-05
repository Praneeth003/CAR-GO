USE cargo;
SELECT DATABASE();

DELIMITER #

DROP PROCEDURE IF EXISTS proc_get_variant_by_id_v1dot0 #

CREATE PROCEDURE proc_get_variant_by_id_v1dot0(
    IN  in_id 								 INT,
	OUT out_status 								 INT,
	OUT	out_err_msg								 VARCHAR(250)
)
BEGIN

	DECLARE v_query, v_sub_query1 LONGTEXT default "";
		
	DECLARE EXIT HANDLER FOR SQLEXCEPTION BEGIN
            SET out_err_msg = CONCAT('proc_get_variant_v1dot0',out_err_msg);
            SET out_status = 500;
	END;

    SET out_status = 200;
    SET out_err_msg = '';

    
    SET v_sub_query1 =CONCAT("a.c_variant_id = '",in_id,"'");

    -- select v_sub_query1, v_sub_query2,v_sub_query3,v_sub_query4,
    -- v_sub_query5,v_sub_query6,v_sub_query7,v_sub_query8,v_sub_query9;
	SET v_query =CONCAT("select a.c_variant_id,a.c_variant_name,a.c_variant_description,a.c_variant_status,a.c_mileage,
    a.c_manufacturing_date,a.c_price_per_kilometer,a.c_kilometers_driven,a.c_number_plate,
    b.c_model_id,b.c_model_name,b.c_model_description,b.c_model_status,
    c.*,d.*,e.*,f.*,g.*,h.c_variant_image_id,h.c_variant_image,h.c_variant_image_view,h.c_variant_image_description,h.c_variant_image_status,j.*
    from
    tbl_variant a inner join tbl_model b on b.c_model_id = a.c_model_id 
    inner join tbl_make c on c.c_make_id =b.c_make_id 
    inner join tbl_body_type d on d.c_body_type_id =b.c_body_type_id 
    inner join tbl_fuel_type e on e.c_fuel_type_id = a.c_fuel_type_id
    inner join tbl_transmission_type f on f.c_transmission_type_id = a.c_transmission_type_id
    inner join tbl_color g on g.c_color_id =a.c_color_id
    inner join tbl_variant_image h on h.c_variant_id = a.c_variant_id
    inner join tbl_variant_location_history i on i.c_variant_id = a.c_variant_id
    inner join tbl_location j on j.c_location_id = i.c_location_id
    where ",v_sub_query1);	
	
	SET @t2  = v_query;
	
	PREPARE stmt3 FROM @t2;
	EXECUTE stmt3;	
	DEALLOCATE PREPARE stmt3;


END #
delimiter ;

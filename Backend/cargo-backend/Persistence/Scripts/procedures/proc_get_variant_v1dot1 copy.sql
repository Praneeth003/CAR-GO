USE cargo;
SELECT DATABASE();

DELIMITER #

DROP PROCEDURE IF EXISTS proc_get_variant_v1dot1 #

CREATE PROCEDURE proc_get_variant_v1dot1(
    IN in_from_date TimeStamp,
    IN in_to_date TimeStamp,
    IN in_from_location_id VARCHAR(1000),
    IN in_to_location_id VARCHAR(1000),
    IN  in_variant_id_delimited_string          VARCHAR(1000),
    IN  in_variant_name_delimited_string        VARCHAR(1000),
    IN  in_make_id_delimited_string          VARCHAR(1000),
  	IN  in_model_id_delimited_string          VARCHAR(1000),
    IN  in_body_type_id_delimited_string          VARCHAR(1000),
    IN  in_fuel_type_id_delimited_string          VARCHAR(1000),
    IN  in_transmission_type_id_delimited_string          VARCHAR(1000),
    IN  in_color_id_delimited_string          VARCHAR(1000),
    IN  in_offset 								 INT,
    IN  in_limit 								 INT,
	OUT out_status 								 INT,
	OUT	out_err_msg								 VARCHAR(250)
)
BEGIN

	DECLARE v_query, v_sub_query1, v_sub_query2,v_sub_query3,v_sub_query4,
    v_sub_query5,v_sub_query6,v_sub_query7,v_sub_query8,v_sub_query9,v_sub_query10 LONGTEXT default "";
		
	DECLARE EXIT HANDLER FOR SQLEXCEPTION BEGIN
        GET DIAGNOSTICS CONDITION 1
			out_err_msg = MESSAGE_TEXT;
            SET out_err_msg = CONCAT('proc_get_variant_v1dot1',out_err_msg);
            SET out_status = 500;
	END;

    SET out_status = 200;
    SET out_err_msg = '';

    IF(in_variant_id_delimited_string <> "ALL") THEN
          SET in_variant_id_delimited_string = REPLACE(in_variant_id_delimited_string,',','\',\'');	
          SET v_sub_query1 =CONCAT("a.c_variant_id in ('",in_variant_id_delimited_string,"')");
    ELSE 
         SET v_sub_query1 =CONCAT("a.c_variant_id =  a.c_variant_id");
    END IF;

    IF(in_variant_name_delimited_string <> "ALL") THEN
         SET in_variant_name_delimited_string = REPLACE(in_variant_name_delimited_string,',','\',\'');	
         SET v_sub_query2 =CONCAT("a.c_variant_name in ('",in_variant_name_delimited_string,"')");
    ELSE 
         SET v_sub_query2 =CONCAT("a.c_variant_name =  a.c_variant_name");
    END IF;

    IF(in_model_id_delimited_string <> "ALL") THEN
          SET in_model_id_delimited_string = REPLACE(in_model_id_delimited_string,',','\',\'');	
          SET v_sub_query3 =CONCAT("b.c_model_id in ('",in_model_id_delimited_string,"')");
    ELSE 
         SET v_sub_query3 =CONCAT("b.c_model_id =  b.c_model_id");
    END IF;

    IF(in_make_id_delimited_string <> "ALL") THEN
          SET in_make_id_delimited_string = REPLACE(in_make_id_delimited_string,',','\',\'');	
          SET v_sub_query4 =CONCAT("c.c_make_id in ('",in_make_id_delimited_string,"')");
    ELSE 
         SET v_sub_query4 =CONCAT("c.c_make_id =  c.c_make_id");
    END IF;
    
    IF(in_body_type_id_delimited_string <> "ALL") THEN
          SET in_body_type_id_delimited_string = REPLACE(in_body_type_id_delimited_string,',','\',\'');	
          SET v_sub_query5 =CONCAT("d.c_body_type_id in ('",in_body_type_id_delimited_string,"')");
    ELSE 
         SET v_sub_query5 =CONCAT("d.c_body_type_id =  d.c_body_type_id");
    END IF;


    IF(in_color_id_delimited_string <> "ALL") THEN
          SET in_color_id_delimited_string = REPLACE(in_color_id_delimited_string,',','\',\'');	
          SET v_sub_query6 =CONCAT("g.c_color_id in ('",in_color_id_delimited_string,"')");
    ELSE 
         SET v_sub_query6 =CONCAT("g.c_color_id =  g.c_color_id");
    END IF;

    IF(in_fuel_type_id_delimited_string <> "ALL") THEN
          SET in_fuel_type_id_delimited_string = REPLACE(in_fuel_type_id_delimited_string,',','\',\'');	
          SET v_sub_query7 =CONCAT("e.c_fuel_type_id in ('",in_fuel_type_id_delimited_string,"')");
    ELSE 
         SET v_sub_query7 =CONCAT("e.c_fuel_type_id =  e.c_fuel_type_id");
    END IF;

    IF(in_transmission_type_id_delimited_string <> "ALL") THEN
          SET in_transmission_type_id_delimited_string = REPLACE(in_transmission_type_id_delimited_string,',','\',\'');	
          SET v_sub_query8 =CONCAT("f.c_transmission_type_id in ('",in_transmission_type_id_delimited_string,"')");
    ELSE 
         SET v_sub_query8 =CONCAT("f.c_transmission_type_id =  f.c_transmission_type_id");
    END IF;


    IF(in_from_location_id is not null) THEN
          SET v_sub_query9 =CONCAT("i.c_is_available =1 and j.c_location_id in ('",in_from_location_id,"')");
    ELSE 
         SET v_sub_query9 =CONCAT("j.c_location_id =  j.c_location_id");
    END IF;

    -- Test with Booking
    SET v_sub_query10 = CONCAT(" a.c_variant_id not in (select cart.c_variant_id
     from tbl_booking_info bInfo 
     inner join tbl_booking_cart_info bCartInfo on bCartInfo.c_booking_info_id = bInfo.c_booking_info_id
     inner join tbl_cart cart on cart.c_cart_id = bCartInfo.c_cart_id
     where bInfo.c_status = 'SUCCESS' and
     (('",in_from_date,"' between cart.c_from_date  and cart.c_to_date ) or('",in_to_date,"'between cart.c_from_date  and cart.c_to_date)))");

    -- SET v_sub_query10 = CONCAT(" a.c_variant_id not in (select cart.c_variant_id
    --  from tbl_cart cart
    --  where (('",in_from_date,"' between cart.c_from_date  and cart.c_to_date ) or('",in_to_date,"'between cart.c_from_date  and cart.c_to_date)))");

    -- select v_sub_query1, v_sub_query2,v_sub_query3,v_sub_query4,
    -- v_sub_query5,v_sub_query6,v_sub_query7,v_sub_query8,v_sub_query9;
    -- select v_sub_query10;


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
    where ",v_sub_query1 ," AND ",v_sub_query2," AND ",v_sub_query3," AND ",v_sub_query4," AND ",
    v_sub_query5 ," AND ",v_sub_query6," AND ",v_sub_query7," AND ",v_sub_query8," AND ", v_sub_query9," AND ",v_sub_query10, " AND a.c_variant_status = '1'");	
	
    -- select v_query;
    
	SET @t2  = v_query;	
	PREPARE stmt3 FROM @t2;
	EXECUTE stmt3;	
	DEALLOCATE PREPARE stmt3;


END #
delimiter ;

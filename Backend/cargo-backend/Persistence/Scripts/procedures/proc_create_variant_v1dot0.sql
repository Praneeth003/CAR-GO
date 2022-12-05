delimiter #
USE cargo#
SELECT DATABASE()#
DROP PROCEDURE IF EXISTS proc_create_variant_v1dot0#
CREATE PROCEDURE proc_create_variant_v1dot0(
        IN in_variant_name   text ,
        IN in_variant_description   text ,
        IN in_variant_status   text ,
        IN in_model_id   text ,
        IN in_color_id   text ,
        IN in_fuel_type_id   text ,
        IN in_transmission_type_id   text ,
        IN in_mileage   text ,
        IN in_manufacturing_date   text ,
        IN in_price_per_kilometer   text ,
        IN in_kilometers_driven   text ,
        IN in_number_plate text ,
        IN in_location_id text,
        IN in_image_url_delimitted_string LONGTEXT,
        IN in_image_type_delimitted_string LONGTEXT,
        OUT  out_variant_id  INT,
        OUT  out_status   INT,
        OUT  out_err_msg  VARCHAR(250)
)
/**************************************************************************************************************************************************************
 PURPOSE :
  This procedure is for creating a new partner gates and interceptors

 OUTPUT PARAMATERS :
  out_status   - returns the authentication status, if 1 its success, else failure
  out_err_msg   - has error description in case of failures
 VERSION INFO :
  V1.0(v1dot0)
 CHANGE LOG :
  Initial version

**************************************************************************************************************************************************************/
BEGIN

        DECLARE v_variant_id int DEFAULT NULL;
        DECLARE cart_iter_count int DEFAULT 1;
        DECLARE current_image_url text;
        DECLARE current_image_type text;
        

        DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
                GET DIAGNOSTICS CONDITION 1
				out_err_msg = MESSAGE_TEXT;
                SET out_err_msg = CONCAT('proc_create_variant_v1dot0',out_err_msg);
                SET out_status = 500;
        ROLLBACK;
        END;


        SET out_status = 500;
        SET out_err_msg = '';


        -- select in_variant_name,in_variant_description,in_variant_status,in_model_id,in_color_id,in_fuel_type_id,in_transmission_type_id,in_mileage,
        --         in_manufacturing_date,in_price_per_kilometer,in_kilometers_driven,in_number_plate;

        -- select in_image_url_delimitted_string, in_image_type_delimitted_string;

        START TRANSACTION;
        BEGIN

--         insert into tbl_variant
-- (c_variant_name,c_variant_description,c_variant_status,c_model_id,c_color_id,c_fuel_type_id,c_transmission_type_id,c_mileage,
-- c_manufacturing_date,c_price_per_kilometer,c_kilometers_driven,c_number_plate)
-- values
-- ("GTX PLUS","GTX PLUSdesc",1,6,1,1,1,15,"2020-01-01",75,1000,"Cargo"),

-- insert into tbl_variant_image
-- (c_variant_image,c_variant_image_view,c_variant_image_status,c_variant_id)
-- values
-- (".//assets//Dump//Kia//Seltos//white.jpg","EXTERIOR",1,19),
-- (".//assets//Dump//Kia//Seltos//white.jpg","EXTERIOR",1,20);

-- insert into tbl_variant_location_history
-- (c_location_id,c_variant_id,c_is_available)
-- values
-- (1,19,1)
                insert into tbl_variant
                (c_variant_name,c_variant_description,c_variant_status,c_model_id,c_color_id,c_fuel_type_id,c_transmission_type_id,c_mileage,
                c_manufacturing_date,c_price_per_kilometer,c_kilometers_driven,c_number_plate)
                values
                (in_variant_name,in_variant_description,in_variant_status,in_model_id,in_color_id,in_fuel_type_id,in_transmission_type_id,in_mileage,
                in_manufacturing_date,in_price_per_kilometer,in_kilometers_driven,in_number_plate);

                select LAST_INSERT_ID() into v_variant_id;

                -- select v_variant_id,"before";

                insert into tbl_variant_location_history
                (c_location_id,c_variant_id,c_is_available)
                values
                (in_location_id,v_variant_id,1);


                -- select v_variant_id,"after";


                IF(in_image_url_delimitted_string IS NOT NULL) THEN
                        commLoop:Loop

                                SELECT SPLIT_STR(in_image_url_delimitted_string, ',', cart_iter_count) into current_image_url;
                                SELECT SPLIT_STR(in_image_type_delimitted_string, ',', cart_iter_count) into current_image_type;

                                IF current_image_url <> '' AND current_image_url IS NOT NULL THEN

                                        insert into tbl_variant_image
                                        (c_variant_image,c_variant_image_view,c_variant_image_status,c_variant_id)
                                        values
                                        (current_image_url,current_image_type,1,v_variant_id);

                                        SET cart_iter_count = cart_iter_count + 1;
                                ELSE
                                        LEAVE commLoop;
                                        
                                END IF;

                        END LOOP commLoop;
                END IF;	

                
                SET   out_variant_id  = v_variant_id;

                SET out_status = 200;
          
        END;

        IF(out_status = 200) THEN 
		    COMMIT;
	    END IF;

END #
delimiter ;
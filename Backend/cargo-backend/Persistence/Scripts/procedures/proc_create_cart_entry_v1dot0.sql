delimiter #
USE cargo#
SELECT DATABASE()#
DROP PROCEDURE IF EXISTS proc_create_cart_entry_v1dot0#

CREATE PROCEDURE proc_create_cart_entry_v1dot0(
        IN  in_user_id int,
        IN  in_variant_id int,
        IN  in_is_active tinyint(1),
        IN  in_from_date TimeStamp,
        IN  in_to_date TimeStamp,
        IN  in_pickup_location_id int,
        IN  in_drop_location_id int,
        IN  in_price double,
        IN  in_add_on_delimitted_string text,
        OUT  out_status   INT,
        OUT  out_err_msg  VARCHAR(250)
)
BEGIN

        DECLARE v_cart_entry_id int DEFAULT NULL;
        DECLARE add_on_count int DEFAULT 1;
        DECLARE current_add_on_id text;
        

        DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
                GET DIAGNOSTICS CONDITION 1
				out_err_msg = MESSAGE_TEXT;
                SET out_err_msg = CONCAT('proc_create_cart_entry_v1dot0',out_err_msg);
                SET out_status = 500;
        ROLLBACK;
        END;


        SET out_status = 500;
        SET out_err_msg = '';


        START TRANSACTION;
        BEGIN
                insert into tbl_cart(c_user_id,c_variant_id,c_is_active,c_from_date,c_to_date,c_pickup_location_id,c_drop_location_id,c_price)
                values (in_user_id,in_variant_id,in_is_active,in_from_date,in_to_date,in_pickup_location_id,in_drop_location_id,in_price);

                select LAST_INSERT_ID() into v_cart_entry_id;

                IF(in_add_on_delimitted_string <> "ALL") THEN
                        commLoop:Loop

                                SELECT SPLIT_STR(in_add_on_delimitted_string, ',', add_on_count) into current_add_on_id;

                                IF current_add_on_id <> '' AND current_add_on_id IS NOT NULL THEN

                                        insert into tbl_user_cart_add_on(c_cart_id,c_add_on_id,c_is_active)
                                        values (v_cart_entry_id,current_add_on_id,in_is_active);
                                        SET add_on_count = add_on_count + 1;
                                ELSE
                                        LEAVE commLoop;
                                        
                                END IF;

                        END LOOP commLoop;
                END IF;	
                
                SELECT cart.c_cart_id,cart.c_user_id, cart.c_is_active, cart.c_from_date, cart.c_to_date, cart.c_price 
                ,a.c_variant_id,a.c_variant_name,a.c_variant_description,a.c_variant_status,a.c_mileage,
                a.c_manufacturing_date,a.c_price_per_kilometer,a.c_kilometers_driven,a.c_number_plate,
                b.c_model_id,b.c_model_name,b.c_model_description,b.c_model_status,
                c.*,d.*,e.*,f.*,g.*,h.*,
                drop_location.c_location_id as drop_location_id,drop_location.c_location_name as drop_location_name,drop_location.c_location_gps as drop_location_gps,drop_location.c_location_state_name as drop_location_state_name,drop_location.c_location_country_name as drop_location_country_name,drop_location.c_location_status as drop_location_status,
                pickup_location.c_location_id as pickup_location_id,pickup_location.c_location_name as pickup_location_name,pickup_location.c_location_gps as pickup_location_gps,pickup_location.c_location_state_name as pickup_location_state_name,pickup_location.c_location_country_name as pickup_location_country_name,pickup_location.c_location_status as pickup_location_status
                FROM tbl_cart cart
                inner join tbl_variant a on cart.c_variant_id = a.c_variant_id
                inner join tbl_model b on b.c_model_id = a.c_model_id 
                inner join tbl_make c on c.c_make_id =b.c_make_id 
                inner join tbl_body_type d on d.c_body_type_id =b.c_body_type_id 
                inner join tbl_fuel_type e on e.c_fuel_type_id = a.c_fuel_type_id
                inner join tbl_transmission_type f on f.c_transmission_type_id = a.c_transmission_type_id
                inner join tbl_color g on g.c_color_id =a.c_color_id
                inner join tbl_variant_image h on h.c_variant_id = a.c_variant_id
                inner join tbl_location drop_location on drop_location.c_location_id = cart.c_pickup_location_id
                inner join tbl_location pickup_location on pickup_location.c_location_id = cart.c_pickup_location_id
                where cart.c_cart_id = v_cart_entry_id;

                SELECT a.*
                FROM tbl_user_cart_add_on cart
                inner join tbl_add_on a on cart.c_add_on_id = a.c_add_on_id
                where cart.c_cart_id = v_cart_entry_id;

                SET out_status = 200;
          
        END;

        IF(out_status = 200) THEN 
		    COMMIT;
	    END IF;

END #
delimiter ;

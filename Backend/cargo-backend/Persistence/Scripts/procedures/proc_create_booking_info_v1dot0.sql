delimiter #
USE cargo#
SELECT DATABASE()#
DROP PROCEDURE IF EXISTS proc_create_booking_info_v1dot0#

CREATE PROCEDURE proc_create_booking_info_v1dot0(
        IN  in_user_id int,
        IN  in_payment_info_id int,
        IN  in_payment_info_reference_id varchar(500),
        IN  in_cart_id_delimitted_string text,
        IN  in_user_profile_id_delimitted_string text,
        IN  in_promo_code_id_delimitted_string text,
        IN  in_status text,
        OUT  out_status   INT,
        OUT  out_err_msg  VARCHAR(250)
)
BEGIN

        DECLARE v_booking_info_id int DEFAULT NULL;
        DECLARE cart_iter_count int DEFAULT 1;
        DECLARE promo_iter_count int DEFAULT 1;
        DECLARE current_cart_id text;
        DECLARE current_promo_id text;
        DECLARE current_user_profile_id text;
        

        DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
                GET DIAGNOSTICS CONDITION 1
				out_err_msg = MESSAGE_TEXT;
                SET out_err_msg = CONCAT('proc_create_booking_info_v1dot0',out_err_msg);
                SET out_status = 500;
        ROLLBACK;
        END;


        SET out_status = 500;
        SET out_err_msg = '';


        START TRANSACTION;
        BEGIN
                insert into tbl_booking_info(c_user_id,c_payment_info_id,c_payment_info_reference_id,c_status)
                values (in_user_id,in_payment_info_id,in_payment_info_reference_id,in_status);

                select LAST_INSERT_ID() into v_booking_info_id;

                IF(in_cart_id_delimitted_string <> "EMPTY") THEN
                        commLoop:Loop

                                SELECT SPLIT_STR(in_cart_id_delimitted_string, ',', cart_iter_count) into current_cart_id;
                                SELECT SPLIT_STR(in_user_profile_id_delimitted_string, ',', cart_iter_count) into current_user_profile_id;

                                IF current_cart_id <> '' AND current_cart_id IS NOT NULL THEN

                                        insert into tbl_booking_cart_info(c_cart_id,c_user_profile_id,c_booking_info_id)
                                        values (current_cart_id,current_user_profile_id,v_booking_info_id);
                                        SET cart_iter_count = cart_iter_count + 1;
                                ELSE
                                        LEAVE commLoop;
                                        
                                END IF;

                        END LOOP commLoop;
                END IF;	

                IF(in_promo_code_id_delimitted_string <> "EMPTY") THEN
                        commLoop:Loop

                                SELECT SPLIT_STR(in_promo_code_id_delimitted_string, ',', promo_iter_count) into current_promo_id;

                                IF current_promo_id <> '' AND current_promo_id IS NOT NULL THEN

                                        insert into tbl_booking_promo_code(c_promo_code_id,c_booking_info_id)
                                        values (current_promo_id,v_booking_info_id);
                                        SET promo_iter_count = promo_iter_count + 1;
                                ELSE
                                        LEAVE commLoop;
                                        
                                END IF;

                        END LOOP commLoop;
                END IF;	

                SELECT booking_info.c_booking_info_id, user.*, payment_info.*, booking_info.c_payment_info_reference_id, booking_info.c_status
                FROM tbl_booking_info booking_info
                inner join tbl_user user on user.c_user_id = booking_info.c_user_id
                inner join tbl_payment_info payment_info on payment_info.c_payment_info_id = booking_info.c_payment_info_id
                where booking_info.c_booking_info_id = v_booking_info_id;
                
                SELECT cart.c_cart_id,cart.c_user_id, cart.c_is_active, cart.c_from_date, cart.c_to_date, cart.c_price 
                ,a.c_variant_id,a.c_variant_name,a.c_variant_description,a.c_variant_status,a.c_mileage,
                a.c_manufacturing_date,a.c_price_per_kilometer,a.c_kilometers_driven,a.c_number_plate,
                b.c_model_id,b.c_model_name,b.c_model_description,b.c_model_status,
                c.*,d.*,e.*,f.*,g.*,h.*,u.*,
                drop_location.c_location_id as drop_location_id,drop_location.c_location_name as drop_location_name,drop_location.c_location_gps as drop_location_gps,drop_location.c_location_state_name as drop_location_state_name,drop_location.c_location_country_name as drop_location_country_name,drop_location.c_location_status as drop_location_status,
                pickup_location.c_location_id as pickup_location_id,pickup_location.c_location_name as pickup_location_name,pickup_location.c_location_gps as pickup_location_gps,pickup_location.c_location_state_name as pickup_location_state_name,pickup_location.c_location_country_name as pickup_location_country_name,pickup_location.c_location_status as pickup_location_status
                FROM tbl_booking_cart_info booking_cart
                inner join tbl_cart cart on cart.c_cart_id = booking_cart.c_cart_id
                inner join tbl_user_profile u on u.c_user_profile_id = booking_cart.c_user_profile_id
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
                where booking_cart.c_booking_info_id = v_booking_info_id;

                SELECT a.*, cart.c_cart_id
                FROM tbl_booking_cart_info booking_cart
                inner join tbl_user_cart_add_on cart on cart.c_cart_id = booking_cart.c_cart_id
                inner join tbl_add_on a on cart.c_add_on_id = a.c_add_on_id
                where booking_cart.c_booking_info_id = v_booking_info_id;

                SELECT promo.*
                FROM tbl_booking_promo_code booking_promo
                inner join tbl_promo_code promo on promo.c_promo_code_id = booking_promo.c_promo_code_id
                where booking_promo.c_booking_info_id = v_booking_info_id;

                SET out_status = 200;
          
        END;

        IF(out_status = 200) THEN 
		    COMMIT;
	    END IF;

END #
delimiter ;

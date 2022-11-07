USE cargo;
SELECT DATABASE();

DELIMITER #

DROP PROCEDURE IF EXISTS proc_update_cart_date_info_v1dot #

CREATE PROCEDURE proc_update_cart_date_info_v1dot(
    IN  in_cart_id_delimitted_string 			TEXT,
    IN  in_price_delimitted_string 		 	TEXT,
    IN  in_from_date TimeStamp,
    IN  in_to_date TimeStamp,
	OUT out_status 								INT,
	OUT	out_err_msg								VARCHAR(250)
)
BEGIN
        DECLARE v_price text DEFAULT NULL;
        DECLARE v_cart text DEFAULT NULL;
        DECLARE iter_count int DEFAULT 1;

        DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
                GET DIAGNOSTICS CONDITION 1
				out_err_msg = MESSAGE_TEXT;
                SET out_err_msg = CONCAT('proc_update_cart_date_info_v1dot',out_err_msg);
                SET out_status = 500;
        ROLLBACK;
        END;


        SET out_status = 500;
        SET out_err_msg = '';


        START TRANSACTION;
        BEGIN
                IF(in_cart_id_delimitted_string <> "EMPTY") THEN
                        commLoop:Loop

                                SELECT SPLIT_STR(in_cart_id_delimitted_string, ',', iter_count) into v_cart;
                                SELECT SPLIT_STR(in_price_delimitted_string, ',', iter_count) into v_price;

                                IF v_cart <> '' AND v_cart IS NOT NULL THEN

                                        update tbl_cart set c_from_date = in_from_date, c_to_date = in_to_date, c_price = v_price
				                        where c_cart_id = v_cart;
                                        SET iter_count = iter_count + 1;
                                ELSE
                                        LEAVE commLoop;
                                        
                                END IF;

                        END LOOP commLoop;
                END IF;	
                
                

                SET out_status = 200;
          
        END;

        IF(out_status = 200) THEN 
		    COMMIT;
	    END IF;


END #
delimiter ;

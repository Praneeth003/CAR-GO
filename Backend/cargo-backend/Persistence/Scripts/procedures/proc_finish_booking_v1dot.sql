USE cargo;
SELECT DATABASE();

DELIMITER #

DROP PROCEDURE IF EXISTS proc_finish_booking_v1dot #

CREATE PROCEDURE proc_finish_booking_v1dot(
    IN  in_booking_id  TEXT,
    IN  in_variant_id_delimitted_string 			TEXT,
    IN  in_kilometers_driven_delimitted_string 		 	TEXT,
	OUT out_status 								INT,
	OUT	out_err_msg								VARCHAR(250)
)
BEGIN
        DECLARE v_kilometers_driven text DEFAULT NULL;
        DECLARE v_variant text DEFAULT NULL;
        DECLARE iter_count int DEFAULT 1;

        DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
                GET DIAGNOSTICS CONDITION 1
				out_err_msg = MESSAGE_TEXT;
                SET out_err_msg = CONCAT('proc_finish_booking_v1dot',out_err_msg);
                SET out_status = 500;
        ROLLBACK;
        END;


        SET out_status = 500;
        SET out_err_msg = '';


        START TRANSACTION;
        BEGIN
                IF(in_variant_id_delimitted_string <> "EMPTY") THEN
                        commLoop:Loop

                                SELECT SPLIT_STR(in_variant_id_delimitted_string, ',', iter_count) into v_variant;
                                SELECT SPLIT_STR(in_kilometers_driven_delimitted_string, ',', iter_count) into v_kilometers_driven;

                                IF v_variant <> '' AND v_variant IS NOT NULL THEN

                                        update tbl_variant set c_kilometers_driven = v_kilometers_driven
				                        where c_variant_id = v_variant;
                                        SET iter_count = iter_count + 1;
                                ELSE
                                        LEAVE commLoop;
                                        
                                END IF;

                        END LOOP commLoop;
                END IF;	


                update tbl_booking_info set c_status = "COMPLETED" where c_booking_info_id = in_booking_id;

                SET out_status = 200;
          
        END;

        IF(out_status = 200) THEN 
		    COMMIT;
	    END IF;


END #
delimiter ;

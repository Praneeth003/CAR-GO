USE cargo;
SELECT DATABASE();

DELIMITER #

DROP PROCEDURE IF EXISTS proc_update_booking_info_v1dot #

CREATE PROCEDURE proc_update_booking_info_v1dot(
    IN  in_booking_info_id 						INT,
    IN  in_payment_info_reference_id 		 	VARCHAR(250),
    IN  in_status 					 	VARCHAR(250),
	OUT out_status 								INT,
	OUT	out_err_msg								VARCHAR(250)
)
BEGIN
        

        DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
                GET DIAGNOSTICS CONDITION 1
				out_err_msg = MESSAGE_TEXT;
                SET out_err_msg = CONCAT('proc_update_booking_info_v1dot',out_err_msg);
                SET out_status = 500;
        ROLLBACK;
        END;


        SET out_status = 500;
        SET out_err_msg = '';


        START TRANSACTION;
        BEGIN
                update tbl_booking_info set c_payment_info_reference_id = in_payment_info_reference_id, c_status = in_status 
				where c_booking_info_id = in_booking_info_id;

                SET out_status = 200;
          
        END;

        IF(out_status = 200) THEN 
		    COMMIT;
	    END IF;


END #
delimiter ;

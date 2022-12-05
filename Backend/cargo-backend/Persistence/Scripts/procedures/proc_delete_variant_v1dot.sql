USE cargo;
SELECT DATABASE();

DELIMITER #

DROP PROCEDURE IF EXISTS proc_delete_variant_v1dot #

CREATE PROCEDURE proc_delete_variant_v1dot(
    IN  in_variant_id  TEXT,
	OUT out_status 								INT,
	OUT	out_err_msg								VARCHAR(250)
)
BEGIN
        DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
                GET DIAGNOSTICS CONDITION 1
				out_err_msg = MESSAGE_TEXT;
                SET out_err_msg = CONCAT('proc_delete_variant_v1dot',out_err_msg);
                SET out_status = 500;
        ROLLBACK;
        END;


        SET out_status = 500;
        SET out_err_msg = '';


        START TRANSACTION;
        BEGIN
                
                update tbl_variant set c_variant_status = '0'
				                        where c_variant_id = in_variant_id;

                SET out_status = 200;
          
        END;

        IF(out_status = 200) THEN 
		    COMMIT;
	    END IF;


END #
delimiter ;

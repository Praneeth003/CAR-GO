delimiter #
USE cargo#
SELECT DATABASE()#
DROP PROCEDURE IF EXISTS proc_get_car_make_v1dot0#

CREATE PROCEDURE proc_get_car_make_v1dot0(
		IN   in_status  tinyint(1),
        OUT  out_status   INT,
        OUT  out_err_msg  VARCHAR(250)
)
BEGIN

        DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
                GET DIAGNOSTICS CONDITION 1
				out_err_msg = MESSAGE_TEXT;
                SET out_err_msg = CONCAT('proc_get_car_make_v1dot0',out_err_msg);
                SET out_status = 0;
        -- ROLLBACK;
        END;

        SET out_status = 0;
        SET out_err_msg = '';

        BEGIN
        -- START TRANSACTION;
            select c_make_id,c_make_name,c_make_description,c_make_status
            from tbl_make
            where c_make_status = IF(in_status is not null,in_status,c_make_status);
        -- COMMIT;
             SET out_status = 1;
        END;


END #
delimiter ;

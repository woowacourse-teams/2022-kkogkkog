ALTER TABLE `coupon`
    ADD `meeting_date` DATETIME;

UPDATE coupon
    INNER JOIN reservation
    ON reservation.coupon_id = coupon.id
SET coupon.meeting_date = reservation.meeting_date;

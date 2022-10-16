CREATE TABLE IF NOT EXISTS `coupon_unregistered_coupon` (
     `id` BIGINT NOT NULL AUTO_INCREMENT
     ,`coupon_id` BIGINT NOT NULL;
     ,`unregistered_coupon_id` BIGINT NOT NULL;
     ,PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `unregistered_coupon`
    DROP `coupon_id`;

CREATE TABLE IF NOT EXISTS `coupon_lazy_coupon` (
     `id` BIGINT NOT NULL AUTO_INCREMENT
     ,`coupon_id` BIGINT NOT NULL;
     ,`lazy_coupon_id` BIGINT NOT NULL;
     ,PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE unregistered_coupon
RENAME lazy_coupon;

ALTER TABLE `lazy_coupon`
DROP `coupon_id`;

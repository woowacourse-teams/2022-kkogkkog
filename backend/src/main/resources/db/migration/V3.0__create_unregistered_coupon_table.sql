CREATE TABLE IF NOT EXISTS `unregistered_coupon` (
    `id` BIGINT NOT NULL AUTO_INCREMENT
    ,`sender_member_id` BIGINT NOT NULL
    ,`coupon_code` VARCHAR(255) NOT NULL
    ,`coupon_message` VARCHAR(255) NOT NULL
    ,`coupon_tag` VARCHAR(255) NOT NULL
    ,`coupon_type` VARCHAR(255) NOT NULL
    ,`unregistered_coupon_status` VARCHAR(255) NOT NULL
    ,`deleted` BOOLEAN NOT NULL
    ,`created_time` DATETIME NOT NULL
    ,`updated_time` DATETIME NOT NULL
    ,PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

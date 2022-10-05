CREATE TABLE IF NOT EXISTS `unregistered_coupon` (
    `id` BIGINT NOT NULL AUTO_INCREMENT
    ,`sender_member_id` BIGINT NOT NULL
    ,`description` VARCHAR(255) NOT NULL
    ,`hashtag` VARCHAR(255) NOT NULL
    , `deleted` BOOLEAN NOT NULL
    ,`created_time` DATETIME NOT NULL
    ,`updated_time` DATETIME NOT NULL
    ,PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

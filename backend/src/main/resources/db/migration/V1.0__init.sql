CREATE TABLE IF NOT EXISTS `coupon` (
    `id` BIGINT NOT NULL AUTO_INCREMENT
    ,`sender_member_id` BIGINT NOT NULL
    ,`receiver_member_id` BIGINT NOT NULL
    ,`description` VARCHAR(255) NOT NULL
    ,`hashtag` VARCHAR(255) NOT NULL
    ,`coupon_type` VARCHAR(255) NOT NULL
    ,`coupon_status` VARCHAR(255) NOT NULL
    ,`created_time` DATETIME NOT NULL
    ,`updated_time` DATETIME NOT NULL
    ,PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `member` (
    `id` BIGINT NOT NULL AUTO_INCREMENT
    ,`email` VARCHAR(255) NOT NULL UNIQUE
    ,`user_id` VARCHAR(255) NOT NULL
    ,`workspace_id` VARCHAR(255) NOT NULL
    ,`nickname` VARCHAR(255) NOT NULL
    ,`image_url` VARCHAR(255) NOT NULL
    ,PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `member_history` (
    `id` BIGINT NOT NULL AUTO_INCREMENT
    ,`host_member_id` BIGINT NOT NULL
    ,`target_member_id` BIGINT
    ,`coupon_id` BIGINT
    ,`coupon_type` VARCHAR(255)
    ,`coupon_event` VARCHAR(255)
    ,`meeting_date` DATETIME
    ,`message` VARCHAR(255)
    ,`is_read` BOOLEAN NOT NULL
    ,`created_time` DATETIME NOT NULL
    ,PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `reservation` (
    `id` BIGINT NOT NULL AUTO_INCREMENT
    ,`meeting_date` DATETIME NOT NULL
    ,`message` VARCHAR(255) NOT NULL
    ,`coupon_id` BIGINT NOT NULL
    ,`created_time` DATETIME NOT NULL
    ,`updated_time` DATETIME NOT NULL
    ,PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `workspace` (
    `id` BIGINT NOT NULL AUTO_INCREMENT
    ,`workspace_id` VARCHAR(255) NOT NULL UNIQUE
    ,`name` VARCHAR(255) NOT NULL
    ,`access_token` VARCHAR(255)
    ,PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `workspace_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT
    ,`user_id` VARCHAR(255) NOT NULL UNIQUE
    ,`workspace_id` VARCHAR(255) NOT NULL
    ,`display_name` VARCHAR(255) NOT NULL
    ,`email` VARCHAR(255) NOT NULL
    ,`image_url` VARCHAR(255) NOT NULL
    ,`master_member_id` BIGINT NOT NULL
    ,PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

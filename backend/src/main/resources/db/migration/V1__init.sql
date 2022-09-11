CREATE TABLE IF NOT EXISTS `coupon` (
    `id` bigint NOT NULL AUTO_INCREMENT
    ,`sender_member_id` bigint NOT NULL
    ,`receiver_member_id` bigint NOT NULL
    ,`description` varchar(255) NOT NULL
    ,`hashtag` varchar(255) NOT NULL
    ,`coupon_type` varchar(255) NOT NULL
    ,`coupon_status` varchar(255) NOT NULL
    ,`created_time` datetime NOT NULL
    ,`updated_time` datetime NOT NULL
    ,PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `member` (
    `id` bigint NOT NULL AUTO_INCREMENT
    ,`email` varchar(255) NOT NULL
    ,`user_id` varchar(255) NOT NULL
    ,`workspace_id` varchar(255) NOT NULL
    ,`nickname` varchar(255) NOT NULL
    ,`image_url` varchar(255) NOT NULL
    ,PRIMARY KEY (`id`)
    ,UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `member_history` (
    `id` bigint NOT NULL AUTO_INCREMENT
    ,`host_member_id` bigint NOT NULL
    ,`target_member_id` bigint DEFAULT NULL
    ,`coupon_id` bigint DEFAULT NULL
    ,`coupon_type` varchar(255) DEFAULT NULL
    ,`coupon_event` varchar(255) DEFAULT NULL
    ,`meeting_date` datetime DEFAULT NULL
    ,`message` varchar(255) DEFAULT NULL
    ,`is_read` tinyint(1) NOT NULL
    ,`created_time` datetime NOT NULL
    ,PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `reservation` (
    `id` bigint NOT NULL AUTO_INCREMENT
    ,`meeting_date` datetime NOT NULL
    ,`message` varchar(255) NOT NULL
    ,`coupon_id` bigint NOT NULL
    ,`created_time` datetime NOT NULL
    ,`updated_time` datetime NOT NULL
    ,PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `workspace` (
    `id` bigint NOT NULL AUTO_INCREMENT
    ,`workspace_id` varchar(255) NOT NULL
    ,`name` varchar(255) NOT NULL
    ,`access_token` varchar(255) DEFAULT NULL
    ,PRIMARY KEY (`id`)
    ,UNIQUE KEY `workspace_id` (`workspace_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `workspace_user` (
    `id` bigint NOT NULL AUTO_INCREMENT
    ,`user_id` varchar(255) NOT NULL
    ,`workspace_id` varchar(255) NOT NULL
    ,`display_name` varchar(255) NOT NULL
    ,`email` varchar(255) NOT NULL
    ,`image_url` varchar(255) NOT NULL
    ,`master_member_id` bigint NOT NULL
    ,PRIMARY KEY (`id`)
    ,UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

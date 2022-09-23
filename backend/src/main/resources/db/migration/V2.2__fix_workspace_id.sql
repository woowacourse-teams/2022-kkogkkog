ALTER TABLE `member`
    MODIFY COLUMN `workspace_id` BIGINT;
ALTER TABLE `workspace_user`
    MODIFY COLUMN `workspace_id` BIGINT;

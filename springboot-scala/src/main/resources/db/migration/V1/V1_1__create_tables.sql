USE test;

CREATE TABLE `article`
(
    `id`              bigint(20)   NOT NULL AUTO_INCREMENT,
    `subject`         varchar(128) NOT NULL,
    `content`         text,
    `updated_at`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`      bigint(20)   NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

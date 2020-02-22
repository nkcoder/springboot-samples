CREATE TABLE IF NOT EXISTS `player`
(
    `id`      INT AUTO_INCREMENT,
    `name`    VARCHAR(64) NOT NULL,
    `team`    VARCHAR(32) NOT NULL,
    `join_at` DATE,
    PRIMARY KEY (`id`)
);


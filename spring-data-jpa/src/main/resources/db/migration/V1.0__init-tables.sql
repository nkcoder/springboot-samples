CREATE TABLE IF NOT EXISTS player
(
    id      INT AUTO_INCREMENT,
    name    VARCHAR(64) NOT NULL,
    team_id INT         NOT NULL,
    join_at DATE,
    PRIMARY KEY (id)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS team
(
    id         INT AUTO_INCREMENT,
    name       VARCHAR(64) NOT NULL,
    location   varchar(128) DEFAULT '',
    founded_at DATE ,
    PRIMARY KEY (id)
) ENGINE = INNODB;

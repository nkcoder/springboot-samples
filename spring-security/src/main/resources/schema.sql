create table if not exists user (
    id identity ,
    username varchar(16),
    password varchar(64),
    enabled smallint(1)
);

create table if not exists user_authority (
    id identity ,
    username varchar(16),
    authority varchar(64)
);
create database user_db;

use user_db;

create table user
(
    id         bigint auto_increment
        primary key,
    created_at timestamp default CURRENT_TIMESTAMP null,
    created_by varchar(255)                        null,
    updated_at timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    updated_by varchar(255)                        null,
    password   varchar(255)                        null,
    role       varchar(255)                        null,
    username   varchar(64)                         null
);

create table user_info
(
    id         bigint auto_increment
        primary key,
    created_at timestamp default CURRENT_TIMESTAMP null,
    created_by varchar(255)                        null,
    updated_at timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    updated_by varchar(255)                        null,
    dob        date                                null,
    email      varchar(255)                        null,
    gender     varchar(32)                         null,
    name       varchar(255)                        null,
    photo      longblob                            null,
    user_id    bigint                              null,
    constraint FKn8pl63y4abe7n0ls6topbqjh2
        foreign key (user_id) references user (id)
);


create table if not exists `user`
(
    `id`       bigint auto_increment primary key,
    `email`    varchar(255) null,
    `name`     varchar(255) null,
    `password` varchar(255) null,
    `surname`  varchar(255) null
) engine = InnoDB
  DEFAULT CHARSET = UTF8;


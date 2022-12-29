create table if not exists `device`
(
    `code`   bigint auto_increment primary key,
    `name` varchar(255) not null
) engine = InnoDB
  DEFAULT CHARSET = UTF8;


create table if not exists `device_user`
(
    `id`   bigint auto_increment primary key,
    `device_code` bigint,
    `user_email`  varchar(255) not null,
    FOREIGN KEY (`device_code`) REFERENCES device (`code`)
) engine = InnoDB
  DEFAULT CHARSET = UTF8;

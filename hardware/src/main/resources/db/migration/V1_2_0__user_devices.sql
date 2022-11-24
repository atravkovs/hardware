create table if not exists `device_user`
(
    `device_code` bigint,
    `user_email`  varchar(255) not null,
    CONSTRAINT PK_Device_User PRIMARY KEY (`device_code`, `user_email`),
    FOREIGN KEY (`device_code`) REFERENCES device (`code`)
) engine = InnoDB
  DEFAULT CHARSET = UTF8;

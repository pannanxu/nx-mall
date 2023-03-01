create table nx_mall.t_identity
(
    id                 bigint auto_increment comment '用户凭证ID'
        primary key,
    user_id            bigint        not null comment '用户ID',
    identity_type      int default 0 not null comment '账户类型(0:基本账户(用户名密码);10:手机号账户;20:微信账户;30:支付宝账户;40:邮箱账户;)',
    identifier         varchar(100)  not null comment '账户标识',
    credential         varchar(255)  null comment '账户凭证',
    nickname           varchar(50)   null comment '授权名称',
    avatar             varchar(255)  null comment '授权头像',
    created_by         bigint        not null comment '创建人',
    created_time       datetime      not null comment '创建时间',
    last_modified_time datetime      null comment '最后一次修改时间',
    last_modified_by   bigint        null comment '最后一次修改人',
    extra              json          null comment '扩展字段',
    constraint identity_type
        unique (identity_type, identifier)
)
    comment '用户授权凭证';

create index user_id
    on nx_mall.t_identity (user_id);

create table nx_mall.t_user
(
    id                 bigint auto_increment comment '用户ID'
        primary key,
    nickname           varchar(50)  not null comment '用户昵称',
    avatar             varchar(255) null comment '用户头像',
    created_by         bigint       not null comment '创建人',
    created_time       datetime     not null comment '创建时间',
    last_modified_time datetime     null comment '最后一次修改时间',
    last_modified_by   bigint       null comment '最后一次修改人',
    extra              json         null comment '扩展字段',
    version            int          not null comment '版本号'
)
    comment '用户基础信息';


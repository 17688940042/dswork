/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014-8-21 14:46:08                           */
/*==============================================================*/


drop table if exists DS_CMS_CATEGORY;

drop table if exists DS_CMS_PAGE;

drop table if exists DS_EP_ENTERPRISE;

drop table if exists DS_EP_TYPE;

drop table if exists DS_EP_USER;

/*==============================================================*/
/* Table: DS_CMS_CATEGORY                                       */
/*==============================================================*/
create table DS_CMS_CATEGORY
(
   ID                   bigint not null auto_increment comment '����',
   PID                  bigint comment '��ID',
   QYBM                 varchar(300) comment '��ҵ����',
   NAME                 varchar(300) comment '��Ŀ����',
   FOLDER               varchar(300) comment 'Ŀ¼����',
   STATUS               int comment '״̬(0�б�1��ҳ��2����)',
   URL                  varchar(300) comment '����',
   IMG                  varchar(300) comment 'ͼƬ',
   VIEWSITE             varchar(300) comment '��վģ��',
   VIEWAPP              varchar(300) comment 'APPģ��',
   SEQ                  varchar(300) comment '����',
   primary key (ID)
);

alter table DS_CMS_CATEGORY comment '��Ŀ';

/*==============================================================*/
/* Table: DS_CMS_PAGE                                           */
/*==============================================================*/
create table DS_CMS_PAGE
(
   ID                   bigint not null auto_increment comment '����',
   QYBM                 varchar(300) comment '��ҵ����',
   CATEGORYID           bigint comment '��ĿID',
   TITLE                VARCHAR(300) comment '����',
   KEYWORDS             VARCHAR(300) comment '�ؼ���',
   SUMMARY              VARCHAR(300) comment 'ժҪ',
   CONTENT              text comment '����',
   CREATETIME           VARCHAR(19) comment '����ʱ��',
   IMG                  VARCHAR(300) comment 'ͼƬ',
   IMGTOP               int comment '����ͼ(0��1��)',
   PAGETOP              int comment '��ҳ�Ƽ�(0��1��)',
   VIEWSITE             varchar(300) comment '��վģ��',
   VIEWAPP              varchar(300) comment 'APPģ��',
   primary key (ID)
);

alter table DS_CMS_PAGE comment '��ҳ����';

/*==============================================================*/
/* Table: DS_EP_ENTERPRISE                                      */
/*==============================================================*/
create table DS_EP_ENTERPRISE
(
   ID                   bigint not null comment 'ID',
   NAME                 varchar(300) comment '��ҵ����',
   SSXQ                 varchar(100) comment '����Ͻ��',
   QYBM                 varchar(64) comment '��ҵ����',
   STATUS               int comment '��ҵ״̬',
   TYPEID               bigint comment '����ID',
   primary key (ID)
);

alter table DS_EP_ENTERPRISE comment '��ҵ';

/*==============================================================*/
/* Table: DS_EP_TYPE                                            */
/*==============================================================*/
create table DS_EP_TYPE
(
   ID                   bigint not null auto_increment comment 'ID',
   NAME                 varchar(300) comment '������',
   MEMO                 varchar(3000) comment '��ע',
   primary key (ID)
);

alter table DS_EP_TYPE comment '��ҵ����';

/*==============================================================*/
/* Table: DS_EP_USER                                            */
/*==============================================================*/
create table DS_EP_USER
(
   ID                   bigint not null comment '����',
   QYBM                 varchar(64) comment '��ҵ����',
   ACCOUNT              varchar(64) comment '�˺�',
   PASSWORD             varchar(256) comment '����',
   NAME                 varchar(30) comment '����',
   IDCARD               varchar(64) comment '���֤��',
   STATUS               int comment '״̬',
   EMAIL                varchar(300) comment '�����ʼ�',
   MOBILE               varchar(30) comment '�ֻ�',
   PHONE                varchar(30) comment '�绰',
   WORKCARD             varchar(64) comment '����֤��',
   CAKEY                varchar(1024) comment 'CA֤���KEY',
   CREATETIME           varchar(19) comment '����ʱ��',
   SSDW                 varchar(300) comment '������λ',
   SSBM                 varchar(300) comment '��������',
   FAX                  varchar(30) comment '����',
   primary key (ID)
);

alter table DS_EP_USER comment '��ҵ�û�';

alter table DS_CMS_CATEGORY add constraint FK_DS_CMS_CATEGORY foreign key (PID)
      references DS_CMS_CATEGORY (ID) on delete cascade on update cascade;

alter table DS_EP_ENTERPRISE add constraint FK_DS_EP_ENTERPRISE_T foreign key (TYPEID)
      references DS_EP_TYPE (ID) on delete restrict on update restrict;


drop table if exists u_luopan;

CREATE TABLE `u_luopan` (
  `id` varchar(100) NOT NULL COMMENT 'id',
  `parentId` varchar(100) DEFAULT NULL COMMENT '父id 上一级id',
  `name` varchar(100) DEFAULT NULL COMMENT '名称，描述',
  `value` bigint(20) DEFAULT NULL COMMENT '对应值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 等级一
INSERT INTO u_luopan VALUES ('0.0',null,'The World',null);


-- 等级二
INSERT INTO u_luopan VALUES ('1.1','0.0','非洲',null);

INSERT INTO u_luopan VALUES ('2.1','1.1','东非',null);
INSERT INTO u_luopan VALUES ('2.10','2.1','埃塞俄比亚',104957438),
                            ('2.11','2.1','坦桑尼亚',57310019),
                            ('2.12','2.1','肯尼亚',49699862),
                            ('2.13','2.1','乌干达',42862958),
                            ('2.14','2.1','莫桑比克',29668834);

INSERT INTO u_luopan VALUES ('2.2','1.1','Central Africa',null);
INSERT INTO u_luopan VALUES ('2.20','2.2','Democratic',81339988),
                            ('2.21','2.2','Angola',29784193),
                            ('2.22','2.2','Chad',14899994);

INSERT INTO u_luopan VALUES ('2.3','1.1','北非',null);
INSERT INTO u_luopan VALUES ('2.30','2.3','Egypt',97553151),
                            ('2.31','2.3','Algeria',41318142),
                            ('2.32','2.3','Sudan',40533330);

INSERT INTO u_luopan VALUES ('2.4','1.1','南美洲',null);
INSERT INTO u_luopan VALUES ('2.40','2.4','南非',56717156),
                            ('2.41','2.4','Namibia',46717156);

INSERT INTO u_luopan VALUES ('2.5','1.1','西非',null);
INSERT INTO u_luopan VALUES ('2.50','2.5','尼日利亚',190886311),
                            ('2.51','2.5','加纳',28833629),
                            ('2.52','2.5','科特迪瓦共和国',24294750),
                            ('2.53','2.5','尼日尔',21477348),
                            ('2.54','2.5','布基纳法索',19193382);


-- 等级二
INSERT INTO u_luopan VALUES ('1.2','0.0','美洲',null);

INSERT INTO u_luopan VALUES ('3.1','1.2','南美洲',null);
INSERT INTO u_luopan VALUES ('3.10','3.1','Brazil',209288278),
                            ('3.11','3.1','Colombia',49065615),
                            ('3.12','3.1','Argentina',44271041);

INSERT INTO u_luopan VALUES ('3.2','1.2','北美洲',null);
INSERT INTO u_luopan VALUES ('3.20','3.2','美国',324459463),
                            ('3.21','3.2','加拿大',36624199),
                            ('3.22','3.2','百慕大群岛',61349),
                            ('3.23','3.2','格陵兰',56480),
                            ('3.24','3.2','Miquelon',6320);

INSERT INTO u_luopan VALUES ('3.3','1.2','中美洲',null);
INSERT INTO u_luopan VALUES ('3.30','3.3','墨西哥',129163276),
                            ('3.31','3.3','危地马拉',16913503);


-- 等级二
INSERT INTO u_luopan VALUES ('1.3','0.0','亚洲',null);

INSERT INTO u_luopan VALUES ('4.1','1.3','南亚',null);
INSERT INTO u_luopan VALUES ('4.10','4.1','印度',183918012),
                            ('4.11','4.1','巴基斯坦',197015955),
                            ('4.12','4.1','孟加拉国',164669751),
                            ('4.13','4.1','伊朗',81162788);

INSERT INTO u_luopan VALUES ('4.2','1.3','东亚',null);
INSERT INTO u_luopan VALUES ('4.20','4.2','中国',290951739),
                            ('4.21','4.2','日本',127484450),
                            ('4.22','4.2','南韩',50982212),
                            ('4.23','4.2','朝鲜',25490965),
                            ('4.24','4.2','台湾省（中国）',23626456),
                            ('4.25','4.2','香港特别行政区（中国）',7364883);

INSERT INTO u_luopan VALUES ('4.3','1.3','东南亚',null);
INSERT INTO u_luopan VALUES ('4.30','4.3','印尼',263991379),
                            ('4.31','4.3','菲律宾',104918090),
                            ('4.32','4.3','越南',95540800),
                            ('4.33','4.3','泰国',69037513);

INSERT INTO u_luopan VALUES ('4.4','1.3','西亚',null);
INSERT INTO u_luopan VALUES ('4.40','4.4','土耳其',80745020),
                            ('4.41','4.4','伊拉克',38274618),
                            ('4.42','4.4','沙特阿拉伯',32938213);


-- 等级二
INSERT INTO u_luopan VALUES ('1.4','0.0','欧洲',null);

INSERT INTO u_luopan VALUES ('5.1','1.4','东欧',null);
INSERT INTO u_luopan VALUES ('5.10','5.1','俄罗斯',143989754),
                            ('5.11','5.1','乌克兰',44222947),
                            ('5.12','5.1','波兰',38170712);

INSERT INTO u_luopan VALUES ('5.2','1.4','北欧',null);
INSERT INTO u_luopan VALUES ('5.20','5.2','英国',66181585),
                            ('5.21','5.2','瑞典',49107011);

INSERT INTO u_luopan VALUES ('5.3','1.4','Southern',null);
INSERT INTO u_luopan VALUES ('5.30','5.3','Italy',59359900),
                            ('5.31','5.3','Spain',46354321),
                            ('5.32','5.3','Greece',41159773);

INSERT INTO u_luopan VALUES ('5.4','1.4','Southern',null);
INSERT INTO u_luopan VALUES ('5.40','5.4','Germany',82114224),
                            ('5.41','5.4','France',64979548),
                            ('5.42','5.4','Netherlands',17035938);


-- 等级二
INSERT INTO u_luopan VALUES ('1.5','0.0','测试洲',null);

INSERT INTO u_luopan VALUES ('6.1','1.5','测试洲A',null);
INSERT INTO u_luopan VALUES ('6.10','6.1','测试洲AA',66181585),
                            ('6.11','6.1','测试洲BB',49107011);

INSERT INTO u_luopan VALUES ('6.2','1.5','测试洲B',null);
INSERT INTO u_luopan VALUES ('6.20','6.2','测试洲CC',66181585),
                            ('6.21','6.2','测试洲DD',49107011);

COMMIT ;
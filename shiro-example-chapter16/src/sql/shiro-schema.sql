drop table if exists sys_user;
drop table if exists sys_organization;
drop table if exists sys_resource;
drop table if exists sys_role;

-- 用户：username表示用户名；
-- password表示密码；
-- salt表示加密密码的盐；
-- role_ids表示用户拥有的角色列表，可以通过角色再获取其权限字符串列表；
-- locked表示用户是否锁定。
create table sys_user (
  id bigint auto_increment,
  organization_id bigint,
  username varchar(100),
  password varchar(100),
  salt varchar(100),
  role_ids varchar(100),
  locked bool default false,
  constraint pk_sys_user primary key(id)
) charset=utf8 ENGINE=InnoDB;
create unique index idx_sys_user_username on sys_user(username);
create index idx_sys_user_organization_id on sys_user(organization_id);

-- 组织机构：name表示组织机构名称，priority是组织机构的排序，即显示顺序；
-- available表示组织机构是否可用。
create table sys_organization (
  id bigint auto_increment,
  name varchar(100),
  parent_id bigint,
  parent_ids varchar(100),
  available bool default false,
  constraint pk_sys_organization primary key(id)
) charset=utf8 ENGINE=InnoDB;
create index idx_sys_organization_parent_id on sys_organization(parent_id);
create index idx_sys_organization_parent_ids on sys_organization(parent_ids);

-- 资源表：表示菜单元素、页面按钮元素等；
-- 菜单元素用来显示界面菜单的，页面按钮是每个页面可进行的操作，如新增、修改、删除按钮；
-- 使用type来区分元素类型（如menu表示菜单，button代表按钮），priority是元素的排序，如菜单显示顺序；
-- permission表示权限；如用户菜单使用user:*；也就是把菜单授权给用户后，用户就拥有了user:*权限；
-- 如用户新增按钮使用user:create，也就是把用户新增按钮授权给用户后，用户就拥有了user:create权限了；
-- available表示资源是否可用，如菜单显示/不显示。
create table sys_resource (
  id bigint auto_increment,
  name varchar(100),
  type varchar(50),
  url varchar(200),
  parent_id bigint,
  parent_ids varchar(100),
  permission varchar(100),
  available bool default false,
  constraint pk_sys_resource primary key(id)
) charset=utf8 ENGINE=InnoDB;
create index idx_sys_resource_parent_id on sys_resource(parent_id);
create index idx_sys_resource_parent_ids on sys_resource(parent_ids);

-- 角色表：role表示角色标识符，如admin，用于后台判断使用；
-- description表示角色描述，如超级管理员，用于前端显示给用户使用；
-- resource_ids表示该角色拥有的资源列表，即该角色拥有的权限列表（显示角色），即角色是权限字符串集合；
-- available表示角色是否可用。
create table sys_role (
  id bigint auto_increment,
  role varchar(100),
  description varchar(100),
  resource_ids varchar(100),
  available bool default false,
  constraint pk_sys_role primary key(id)
) charset=utf8 ENGINE=InnoDB;
create index idx_sys_role_resource_ids on sys_role(resource_ids);

COMMIT ;
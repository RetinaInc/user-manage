insert into account (id, username, email, birthday, password, salt, register_date)
values (1, 'superuser', 'superuser@yigwoo.com', '1970-01-02 00:00:00', '61d3e991c81154ba3056705e0fe16e88d94e95c5', '6c9c5d5a2b0c5b90', '2013-12-10 11:03:03');

insert into role(id, rolename) values (1, 'super admin');
insert into role(id, rolename) values (2, 'admin');
insert into role(id, rolename) values (3, 'common user');

insert into account_role (account_id, role_id) values (1, 1);
insert into account_role (account_id, role_id) values (1, 2);
insert into account_role (account_id, role_id) values (1, 3);

insert into account (id, username, email, password, salt, register_date)
value (2,'admin','admin@admin.com','824f706c8ea46cc37d195298932fd004162af90b','0cbc2cea319be69b','2013-12-13 06:42:05');
insert into account_role (account_id, role_id) values (2, 2);
insert into account_role (account_id, role_id) values (2, 3);
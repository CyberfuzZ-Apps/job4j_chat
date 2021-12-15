create table roles
(
	id serial primary key,
	authority varchar(255)
);

create table persons
(
	id serial primary key,
	nickname varchar(255),
	password varchar(255),
	username varchar(255),
	authority_id integer references roles(id)
);

create table rooms
(
	id serial primary key,
	name varchar(255),
	person_id integer references persons(id)
);

create table messages
(
	id serial primary key,
	created timestamp,
	message varchar(2000),
	person_id integer references persons (id),
	room_id integer references rooms (id)
);


insert into roles (authority) values ('ROLE_ADMIN');
insert into roles (authority) values ('ROLE_USER');


insert into persons (nickname, password, username, authority_id)
values ('Admin', 'root', 'root@local', 1);


insert into messages (created, message, person_id, room_id) values ('2021-12-16 13:33:18.000000', 'Message 1', 1, 1);
insert into messages (created, message, person_id, room_id) values ('2021-12-16 21:45:17.276481', 'Message 2', 1, 1);
insert into messages (created, message, person_id, room_id) values ('2021-12-16 21:57:35.321509', 'Message 3', 1, 1);


insert into rooms (name, person_id) values ('First room', 1);
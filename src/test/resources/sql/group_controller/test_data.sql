insert into users(id, username, password, email)
values ('1', 'user', '$2a$05$b/Pagl2rctd8S.4aQ5EbgOJalUHkGI.2metpiQ9sR8USuDGzE0wRG', 'user@example.com'),
        ('2', 'user2', '$2a$05$b/Pagl2rctd8S.4aQ5EbgOJalUHkGI.2metpiQ9sR8USuDGzE0wRG', 'user2@example.com');

insert into groups(id, name)
values ('1', 'group1'),
        ('2', 'group2');

insert into users_groups(user_id, group_id)
values ('1', '1'),
        ('1', '2');
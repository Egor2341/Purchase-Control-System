insert into users(id, username, password, email)
values ('1', 'user', '$2a$05$b/Pagl2rctd8S.4aQ5EbgOJalUHkGI.2metpiQ9sR8USuDGzE0wRG', 'user@example.com'),
        ('2', 'user2', '$2a$05$b/Pagl2rctd8S.4aQ5EbgOJalUHkGI.2metpiQ9sR8USuDGzE0wRG', 'user2@example.com'),
        ('3', 'user3', '$2a$05$b/Pagl2rctd8S.4aQ5EbgOJalUHkGI.2metpiQ9sR8USuDGzE0wRG', 'user3@example.com');

insert into groups(id, name, author)
values ('1', 'group1', '1'),
        ('2', 'group2', '1'),
        ('3', 'group3', '2');

insert into users_groups(user_id, group_id)
values ('1', '1'),
        ('1', '2'),
        ('2', '3');
insert into jpa.users(id,activation_code,active, email, password, username)
values (1,null,true,'testmail@gmail.com', '$2a$10$GI9SsCyQ97lThO3w7aHHXeNkIHK07GH.sqppOP85QCEQesrIsyo/6', 'testUser');
insert into jpa.user_role(user_id, roles) values
                                          (1, 'ROLE_USER');
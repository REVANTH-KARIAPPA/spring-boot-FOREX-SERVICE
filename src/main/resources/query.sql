--Insert in Roles Table
INSERT INTO role (id, description, name) VALUES (1, 'Admin role', 'ADMIN');
INSERT INTO role (id, description, name) VALUES (2, 'User role', 'USER');

--Insert in User Table
INSERT INTO public.users(id, email, password, phone, username)
VALUES (4, 'admin@email.com', 'admin', 9280675282, 'admin');

--Insert in user_roles Table
INSERT INTO public.user_roles(user_id, role_id)
VALUES (4, 1);



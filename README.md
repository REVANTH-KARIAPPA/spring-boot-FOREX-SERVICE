# spring-boot-FOREX-SERVICE

Intiall setup that should be done. 


--Insert in Roles Table.
INSERT INTO role (id, description, name) VALUES (1, 'Admin role', 'ADMIN');
INSERT INTO role (id, description, name) VALUES (2, 'User role', 'USER');


--Steps to create an ADMIN. (if user is directely created in database it give error since password is not encrypted by ByEcrypt)
Sign up a user from frontend.
Frontend in angular :- https://github.com/REVANTH-KARIAPPA/Angular-Forex-Service.git (can also be done with postman or swagger)


-- In user_roles Table give user role as ADMIN.
eg:-
INSERT INTO public.user_roles(user_id, role_id)
VALUES (1, 1);


-- In user_roles Table remove same users role as USER.
eg:-
DELETE FROM public.user_roles
WHERE user_id=1 and role_id=2;

Follow above steps for the application to work as intended.



SET FOREIGN_KEY_CHECKS = 0; 
drop table roles;
drop table addresses;
drop table location_types;
drop table object_types;
drop table objects;
drop table locations;
drop table users;


SET FOREIGN_KEY_CHECKS = 0; 
delete from roles where 1;
delete from addresses where 1;
delete from location_types where 1;
delete from object_types where 1;
delete from objects where 1;
delete from locations where 1;
delete from users where 1;
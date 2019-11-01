
INSERT INTO `roles` (`short_name`, `long_name`, `description`) 
VALUES ('admin', 'administrator', NULL);

INSERT INTO `states` (`short_name`, `long_name`) 
VALUES ('WA', "Washington");

INSERT INTO `addresses` (`address1`, `address2`, `city`, `state_id`, `zipcode`, `zipcode_ext`) 
VALUES ('123 fake st', 'apt 1', 'Cheney', 1, '99004', '');

INSERT INTO `location_types` (`short_name`, `long_name`, `description`) 
VALUES ('room', NULL, NULL);

INSERT INTO `object_types` (`short_name`, `long_name`, `description`) 
VALUES ('path', NULL, NULL),
 ('door', NULL, NULL);

INSERT INTO `objects` (`location_id`, `short_name`, `long_name`, `description`, `object_type_id`, `x_coordinate`, `y_coordinate`, `latitude`, `longitude`, `active`) 
VALUES (1, 'object1', NULL, NULL, 1, 0, 0, NULL, NULL, 1),
 (1, 'object1', NULL, NULL, 1, 10, 10, NULL, NULL, 1),
 (1, 'object1', NULL, NULL, 1, 40, -20, NULL, NULL, 1),
 (1, 'object1', NULL, NULL, 1, 100, 50, NULL, NULL, 1);

INSERT INTO `locations` (`short_name`, `long_name`, `description`, `location_type_id`, `address_id`, `primary_object_id`, `scale_ft`, `min_x_coordinate`, `min_y_coordinate`, `max_x_coordinate`, `max_y_coordinate`, `latitude`, `longitude`, `active`) 
VALUES ('CEB101', NULL, 'the cave', 1, 1, 1, 1, '0', '0', '100', '100', NULL, NULL, 1);

INSERT INTO `users` (`username`, `salt`, `password`, `first_name`, `last_name`, `email`, `role_id`, `active`) 
VALUES ('david', 'abc123', 'passowrd', 'david', 'lastname', 'email@eagles.ewu.edu', 1, 1),
 ('naji', 'abc123', 'passowrd', 'naji', 'lastname', 'email@eagles.ewu.edu', 1, 1),
 ('valdyn', 'abc123', 'passowrd', 'valdyn', 'lastname', 'email@eagles.ewu.edu', 1, 1),
 ('jason', 'abc123', 'passowrd', 'jason', 'lastname', 'email@eagles.ewu.edu', 1, 1);



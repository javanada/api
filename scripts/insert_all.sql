
INSERT INTO `roles` (`short_name`, `long_name`, `description`) 
VALUES 
 ('admin', 'administrator', 'godlike'),
 ('normal', 'standard normal', 'plebian');

INSERT INTO `addresses` (`address1`, `address2`, `city`, `state`, `zipcode`, `zipcode_ext`) 
VALUES 
 ('123 fake st', '', 'Cheney', 'WA', '99004', '');

INSERT INTO `location_types` (`short_name`, `long_name`, `description`) 
VALUES 
 ('room', 'room', 'a generic room, may have children and probably a parent'),
 ('building', 'building', 'location that contains child locations and no parents'),
 ('hallway', 'hallway', 'a generic hallway');

INSERT INTO `object_types` (`short_name`, `long_name`, `description`) 
VALUES 
 ('path', 'path', 'a point through which one can travel'),
 ('door', 'door', 'a door leading from one room to another'),
 ('generic', 'generic', 'whatever'),
 ('location_primary', 'location_primary', 'location_primary'),
 ('location_secondary', 'location_secondary', 'location_secondary');


INSERT INTO `locations` (`short_name`, `long_name`, `description`, `image`, `location_type_id`, `address_id`, `active`) 
VALUES 
 ('CEB', 'EWU Computer Engineering Building', 'Computer Science Stuff', 'https://inav-2761e89d-6096-43cc-b585-1c07a19a140d.s3-us-west-2.amazonaws.com/ceb2.png', 2, 1, 1),
 ('CEB0', 'EWU Computer Engineering Building Lower Level', 'Computer Science Stuff', 'https://inav-2761e89d-6096-43cc-b585-1c07a19a140d.s3-us-west-2.amazonaws.com/location1floor0.png', 2, 1, 1),
 ('CEB1', 'EWU Computer Engineering Building First Level', 'Computer Science Stuff', 'https://inav-2761e89d-6096-43cc-b585-1c07a19a140d.s3-us-west-2.amazonaws.com/location1floor1.png', 2, 1, 1),
 ('CEB2', 'EWU Computer Engineering Building Second Level', 'Computer Science Stuff', 'https://inav-2761e89d-6096-43cc-b585-1c07a19a140d.s3-us-west-2.amazonaws.com/location1floor2.png', 2, 1, 1),
 ('CEB3', 'EWU Computer Engineering Building Third Level', 'Computer Science Stuff', 'https://inav-2761e89d-6096-43cc-b585-1c07a19a140d.s3-us-west-2.amazonaws.com/location1floor3.png', 2, 1, 1),
 ('CEB101', 'EWU Computer Engineering Building Room 101', 'Computer Science Stuff', '', 1, 1, 1);


INSERT INTO `location_relations` (`parent_id`, `child_id`, `active`) 
VALUES 
 (1, 2, 1),
 (1, 3, 1),
 (1, 4, 1),
 (1, 5, 1),
 (1, 6, 1),
 (3, 6, 1);



-- These tables have special operations required for insertions. objects requires a graph vertex and users requires crypto

-- INSERT INTO `objects` (`location_id`, `short_name`, `long_name`, `description`, `object_type_id`, `x_coordinate`, `y_coordinate`, `latitude`, `longitude`, `active`) 
-- VALUES 
--  (2, 'power outlet', NULL, NULL, 3, 0, 0, NULL, NULL, 1),
--  (2, 'garbage can', NULL, NULL, 3, 10, 10, NULL, NULL, 1),
--  (2, 'door', NULL, NULL, 2, 40, -20, NULL, NULL, 1),
--  (2, 'path', NULL, NULL, 1, 100, 50, NULL, NULL, 1);



-- INSERT INTO `users` (`username`, `salt`, `password`, `first_name`, `last_name`, `email`, `role_id`, `active`) 
-- VALUES ('david', 'abc123', 'passowrd', 'david', 'lastname', 'email@eagles.ewu.edu', 1, 1),
--  ('naji', 'abc123', 'passowrd', 'naji', 'lastname', 'email@eagles.ewu.edu', 1, 1),
--  ('valdyn', 'abc123', 'passowrd', 'valdyn', 'lastname', 'email@eagles.ewu.edu', 1, 1),
--  ('jason', 'abc123', 'passowrd', 'jason', 'lastname', 'email@eagles.ewu.edu', 1, 1);



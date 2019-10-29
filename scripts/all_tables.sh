

cat states.sql roles.sql location_types.sql object_types.sql addresses.sql locations.sql objects.sql users.sql > all_tables.sql

mysql -hlocalhost -uroot -p i_nav < all_tables.sql


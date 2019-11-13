

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE IF NOT EXISTS `i_nav`.`roles` (
  `role_id` int(11) NOT NULL,
  `short_name` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `long_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`role_id`);

-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT;

COMMIT;




SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;

-- --------------------------------------------------------

--
-- Table structure for table `location_types`
--

CREATE TABLE IF NOT EXISTS `i_nav`.`location_types` (
  `location_type_id` int(11) NOT NULL,
  `short_name` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `long_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for table `location_types`
--

ALTER TABLE `location_types`
  ADD PRIMARY KEY (`location_type_id`);

-- AUTO_INCREMENT for table `location_types`
--
ALTER TABLE `location_types`
  MODIFY `location_type_id` int(11) NOT NULL AUTO_INCREMENT;


COMMIT;




SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;

-- --------------------------------------------------------

--
-- Table structure for table `object_types`
--

CREATE TABLE IF NOT EXISTS `i_nav`.`object_types` (
  `object_type_id` int(11) NOT NULL,
  `short_name` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `long_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for table `object_types`
--
ALTER TABLE `object_types`
  ADD PRIMARY KEY (`object_type_id`);

-- AUTO_INCREMENT for table `object_types`
--
ALTER TABLE `object_types`
  MODIFY `object_type_id` int(11) NOT NULL AUTO_INCREMENT;

COMMIT;




SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;

-- --------------------------------------------------------

--
-- Table structure for table `addresses`
--

CREATE TABLE IF NOT EXISTS `i_nav`.`addresses` (
  `address_id` int(11) NOT NULL,
  `address1` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `address2` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `city` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `state` varchar(2) COLLATE utf8_unicode_ci NOT NULL,
  `zipcode` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `zipcode_ext` varchar(4) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for table `addresses`
--
ALTER TABLE `addresses`
  ADD PRIMARY KEY (`address_id`);

-- AUTO_INCREMENT for table `addresses`
--
ALTER TABLE `addresses`
  MODIFY `address_id` int(11) NOT NULL AUTO_INCREMENT;

COMMIT;




SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;

-- --------------------------------------------------------

--
-- Table structure for table `locations`
--

CREATE TABLE IF NOT EXISTS `i_nav`.`locations` (
  `location_id` int(11) NOT NULL,
  `short_name` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `long_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `location_type_id` int(11) NOT NULL,
  `address_id` int(11) NOT NULL,
  `primary_object_id` int(11) DEFAULT NULL,
  `scale_ft` float(10) NOT NULL,
  `min_x_coordinate` int(8) NOT NULL,
  `min_y_coordinate` int(8) NOT NULL,
  `max_x_coordinate` int(8) NOT NULL,
  `max_y_coordinate` int(8) NOT NULL,
  `latitude` decimal(9,6) DEFAULT NULL,
  `longitude` decimal(9,6) DEFAULT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for table `locations`
--
ALTER TABLE `locations`
  ADD PRIMARY KEY (`location_id`);
ALTER TABLE `locations`
  ADD FOREIGN KEY (`location_type_id`) REFERENCES `location_types`(`location_type_id`);
ALTER TABLE `locations`
  ADD FOREIGN KEY (`address_id`) REFERENCES `addresses`(`address_id`);
ALTER TABLE `locations`
  ADD FOREIGN KEY (`primary_object_id`) REFERENCES `objects`(`object_id`);

-- AUTO_INCREMENT for table `locations`
--
ALTER TABLE `locations`
  MODIFY `location_id` int(11) NOT NULL AUTO_INCREMENT;

COMMIT;




SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;

-- --------------------------------------------------------

--
-- Table structure for table `objects`
--

CREATE TABLE IF NOT EXISTS `i_nav`.`objects` (
  `object_id` int(11) NOT NULL,
  `location_id` int(11) NOT NULL,
  `short_name` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `long_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `object_type_id` int(11) NOT NULL,
  `x_coordinate` int(8) NOT NULL,
  `y_coordinate` int(8) NOT NULL,
  `image_x` int(11) DEFAULT NULL,
  `image_y` int(11) DEFAULT NULL,
  `latitude` decimal(9,6) DEFAULT NULL,
  `longitude` decimal(9,6) DEFAULT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for table `objects`
--
ALTER TABLE `objects`
  ADD PRIMARY KEY (`object_id`);
-- ALTER TABLE `objects`
--   ADD FOREIGN KEY (`location_id`) REFERENCES `locations`(`location_id`);
ALTER TABLE `objects`
  ADD FOREIGN KEY (`object_type_id`) REFERENCES `object_types`(`object_type_id`);

-- AUTO_INCREMENT for table `objects`
--
ALTER TABLE `objects`
  MODIFY `object_id` int(11) NOT NULL AUTO_INCREMENT;

COMMIT;




SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `i_nav`.`users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `salt` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_name` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_name` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);
ALTER TABLE `users`
  ADD FOREIGN KEY (`role_id`) REFERENCES `roles`(`role_id`);

-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT;


COMMIT;




SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;

-- --------------------------------------------------------

--
-- Table structure for table `location_relations`
--

CREATE TABLE IF NOT EXISTS `i_nav`.`location_relations` (
  `parent_id` int(11) NOT NULL,
  `child_id` int(11) NOT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for table `location_relations`
--
ALTER TABLE `location_relations`
  ADD FOREIGN KEY (`parent_id`) REFERENCES `locations`(`location_id`);
ALTER TABLE `location_relations`
  ADD FOREIGN KEY (`child_id`) REFERENCES `locations`(`location_id`);

COMMIT;



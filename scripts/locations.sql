

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



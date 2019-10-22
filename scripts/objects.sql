

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
  `latitude` decimal(9,6) DEFAULT NULL,
  `longitude` decimal(9,6) DEFAULT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for table `objects`
--
ALTER TABLE `objects`
  ADD PRIMARY KEY (`object_id`);
ALTER TABLE `objects`
  ADD FOREIGN KEY (`location_id`) REFERENCES `locations`(`location_id`);
ALTER TABLE `objects`
  ADD FOREIGN KEY (`object_type_id`) REFERENCES `object_types`(`object_type_id`);

-- AUTO_INCREMENT for table `objects`
--
ALTER TABLE `objects`
  MODIFY `object_id` int(11) NOT NULL AUTO_INCREMENT;

COMMIT;





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



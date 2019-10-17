

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;

-- --------------------------------------------------------

--
-- Table structure for table `location_points`
--

CREATE TABLE IF NOT EXISTS `i_nav`.`location_points` (
  `location_id` int(11) NOT NULL,
  `x_coordinate` int(8) NOT NULL,
  `y_coordinate` int(8) NOT NULL,
  `latitude` decimal(9,6) DEFAULT NULL,
  `longitude` decimal(9,6) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for table `location_points`
--
ALTER TABLE `location_points`
  ADD FOREIGN KEY (`location_id`) REFERENCES `locations`(`location_id`);


COMMIT;



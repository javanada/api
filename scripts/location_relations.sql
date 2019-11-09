

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



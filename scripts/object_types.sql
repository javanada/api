

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;

-- --------------------------------------------------------

--
-- Table structure for table `objects`
--

CREATE TABLE IF NOT EXISTS `i_nav`.`object_types` (
  `object_type_id` int(11) NOT NULL,
  `short_name` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `long_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for table `objects`
--

ALTER TABLE `objects`
  ADD FOREIGN KEY (`object_type_id`) REFERENCES `objects`(`object_type_id`);

COMMIT;



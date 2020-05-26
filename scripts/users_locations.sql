

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;

-- --------------------------------------------------------

--
-- Table structure for table `users_locations`
--

CREATE TABLE IF NOT EXISTS `i_nav`.`users_locations` (
  `user_id` int(11) NOT NULL,
  `location_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for table `users_locations`
--
ALTER TABLE `users_locations`
  ADD FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`);
ALTER TABLE `users_locations`
  ADD FOREIGN KEY (`location_id`) REFERENCES `locations`(`location_id`);




COMMIT;



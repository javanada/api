

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;

-- --------------------------------------------------------

--
-- Table structure for table `street_view_media`
--

CREATE TABLE IF NOT EXISTS `i_nav`.`street_view_media` (
  `media_id` int(11) NOT NULL,
  `video_id` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `video_progress` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `image_id` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `location_id` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `x_coordinate` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `y_coordinate` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for table `street_view_media`
--
ALTER TABLE `street_view_media`
  ADD PRIMARY KEY (`media_id`);
ALTER TABLE `street_view_media`
  ADD FOREIGN KEY (`video_id`) REFERENCES `videos`(`video_id`);
ALTER TABLE `street_view_media`
  ADD FOREIGN KEY (`location_id`) REFERENCES `locations`(`location_id`);

COMMIT;




--
-- Table structure for table `association_value_entry`
--


DROP TABLE IF EXISTS `association_value_entry`;

CREATE TABLE `association_value_entry` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `association_key` varchar(255) NOT NULL,
  `association_value` varchar(255) DEFAULT NULL,
  `saga_id` varchar(255) NOT NULL,
  `saga_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDXs2yi8bobx8dd4ee6t63dufs6d` (`saga_id`,`association_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


LOCK TABLES `association_value_entry` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `blog_post`
--

DROP TABLE IF EXISTS `blog_post`;

CREATE TABLE `blog_post` (
  `id` varchar(255) NOT NULL,
  `aggregate_version` bigint(20) DEFAULT NULL,
  `author_id` varchar(255) DEFAULT NULL,
  `broadcast` bit(1) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `draft` bit(1) DEFAULT NULL,
  `public_slug` varchar(255) DEFAULT NULL,
  `publish_at` datetime DEFAULT NULL,
  `raw_content` text(102555) DEFAULT NULL,
  `render_content` text(102555) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


LOCK TABLES `blog_post` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `domain_event_entry`
--

DROP TABLE IF EXISTS `domain_event_entry`;

CREATE TABLE `domain_event_entry` (
  `global_index` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_identifier` varchar(255) NOT NULL,
  `meta_data` longblob,
  `payload` longblob NOT NULL,
  `payload_revision` varchar(255) DEFAULT NULL,
  `payload_type` varchar(255) NOT NULL,
  `time_stamp` varchar(255) NOT NULL,
  `aggregate_identifier` varchar(255) NOT NULL,
  `sequence_number` bigint(20) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`global_index`),
  UNIQUE KEY `UK8s1f994p4la2ipb13me2xqm1w` (`aggregate_identifier`,`sequence_number`),
  UNIQUE KEY `UK_fwe6lsa8bfo6hyas6ud3m8c7x` (`event_identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


LOCK TABLES `domain_event_entry` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` varchar(255) NOT NULL,
  `aggregate_version` bigint(20) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `description` text(2555) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `repo_url` varchar(255) DEFAULT NULL,
  `site_url` varchar(255) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


LOCK TABLES `project` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `team`
--
DROP TABLE IF EXISTS `team`;

CREATE TABLE `team` (
  `id` varchar(255) NOT NULL,
  `aggregate_version` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `project_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp6ovpc4soflfcjbafch33w2ky` (`project_id`),
  CONSTRAINT `FKp6ovpc4soflfcjbafch33w2ky` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `team` WRITE;
UNLOCK TABLES;


DROP TABLE IF EXISTS `team_member`;
CREATE TABLE `team_member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `weekly_hours` bigint(20) DEFAULT NULL,
  `team_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp6ovpc4soflfcjbafch33w2kz` (`team_id`),
  CONSTRAINT `FKp6ovpc4soflfcjbafch33w2kz` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;



--
-- Table structure for table `saga_entry`
--

DROP TABLE IF EXISTS `saga_entry`;

CREATE TABLE `saga_entry` (
  `saga_id` varchar(255) NOT NULL,
  `revision` varchar(255) DEFAULT NULL,
  `saga_type` varchar(255) DEFAULT NULL,
  `serialized_saga` longblob,
  PRIMARY KEY (`saga_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


LOCK TABLES `saga_entry` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `snapshot_event_entry`
--

DROP TABLE IF EXISTS `snapshot_event_entry`;

CREATE TABLE `snapshot_event_entry` (
  `aggregate_identifier` varchar(255) NOT NULL,
  `sequence_number` bigint(20) NOT NULL,
  `type` varchar(255) NOT NULL,
  `event_identifier` varchar(255) NOT NULL,
  `meta_data` longblob,
  `payload` longblob NOT NULL,
  `payload_revision` varchar(255) DEFAULT NULL,
  `payload_type` varchar(255) NOT NULL,
  `time_stamp` varchar(255) NOT NULL,
  PRIMARY KEY (`aggregate_identifier`,`sequence_number`,`type`),
  UNIQUE KEY `UK_e1uucjseo68gopmnd0vgdl44h` (`event_identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


LOCK TABLES `snapshot_event_entry` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `token_entry`
--

DROP TABLE IF EXISTS `token_entry`;

CREATE TABLE `token_entry` (
  `processor_name` varchar(255) NOT NULL,
  `segment` int(11) NOT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `timestamp` varchar(255) NOT NULL,
  `token` longblob,
  `token_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`processor_name`,`segment`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


LOCK TABLES `token_entry` WRITE;
UNLOCK TABLES;


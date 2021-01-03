CREATE DATABASE  IF NOT EXISTS `cmsshoppingcart`;
USE `cmsshoppingcart`;

--
-- Table structure for table `pages`
--

DROP TABLE IF EXISTS `pages`;

CREATE TABLE `pages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `slug` varchar(45) NOT NULL,
  `content` text NOT NULL,
  `sorting` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;

CREATE TABLE `cmsshoppingcart`.`categories` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `slug` VARCHAR(45) NOT NULL,
  `sorting` INT(3) NOT NULL,
  PRIMARY KEY (`id`)
  )ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
  
--
-- Dumping data for table `pages`
--

LOCK TABLES `pages` WRITE;

INSERT INTO `pages` VALUES 
	(1,'Home','home','Home Page', 0),
	(2,'Services','services','Services Page', 1),
	(3,'About Us','about-us','About Page', 2);
	
UNLOCK TABLES;
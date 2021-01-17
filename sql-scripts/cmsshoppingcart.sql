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
-- Table structure for table `products`
--

 DROP TABLE IF EXISTS `products`;
 
 CREATE TABLE `cmsshoppingcart`.`products` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `slug` VARCHAR(45) NOT NULL,
  `description` TEXT NOT NULL,
  `image` VARCHAR(45) NOT NULL,
  `price` DECIMAL(8,2) NOT NULL,
  `category_id` INT(11) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`)
  )ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
 
--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;

CREATE TABLE `cmsshoppingcart`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
  )ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
 
--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;

CREATE TABLE `cmsshoppingcart`.`admins` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
  )ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
 
--
-- Dumping data for table `pages`
--

LOCK TABLES `pages` WRITE;

INSERT INTO `pages` VALUES 
	(1,'Home','home','Home Page', 0),
	(2,'Services','services','Services Page', 1),
	(3,'About Us','about-us','About Page', 2),
  (4,'Contact','contact','Contact Us', 3);
	
UNLOCK TABLES;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;

INSERT INTO  `categories` (`id`, `name`, `slug`, `sorting`) VALUES
	('1', 'T-Shirts', 't-shirts', '1'),
  ('2', 'Fruits', 'fruits', '2');

UNLOCK TABLES;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;

INSERT INTO `products` VALUES 
  (1,'Apples','apples','Nice apples','apples.jpg',1.99,2,'2019-07-02 08:52:17','2019-07-02 09:10:03'),
  (2,'Bananas','bananas','Tasty bananas','bananas.jpg',3.99,2,'2019-07-02 08:52:48','2019-07-02 09:01:31'),
  (3,'Black shirt','black-shirt','A black shirt','black shirt.jpg',5.99,1,'2019-07-02 08:53:08','2019-07-02 08:53:08'),
  (4,'Blue shirt','blue-shirt','A blue shirt','blue shirt.jpg',6.99,1,'2019-07-02 08:53:26','2019-07-02 08:53:26'),
  (5,'Graperuit','graperuit','Juicy grapefruit','grapefruit.jpg',3.99,2,'2019-07-02 08:53:57','2019-07-02 08:53:57'),
  (6,'Grapes','grapes','Great grapes','grapes.jpg',2.50,2,'2019-07-02 08:54:19','2019-07-02 08:54:19'),
  (7,'Grey shirt','grey-shirt','A grey shirt','grey shirt.jpg',3.99,1,'2019-07-02 08:54:35','2019-07-02 08:54:35'),
  (8,'Kiwi','kiwi','Fresh kiwi','kiwi.jpg',4.99,2,'2019-07-02 08:54:57','2019-07-02 08:54:57'),
  (9,'Pink shirt','pink-shirt','A pink shirt','pink shirt.jpg',7.99,1,'2019-07-02 08:55:21','2019-07-02 08:55:21'),
  (10,'Watermelon','watermelon','Juicy watermelon','watermelon.jpg',1.50,2,'2019-07-02 08:55:53','2019-07-02 08:55:53'),
  (11,'White shirt','white-shirt','A white shirt','white shirt.jpg',4.50,1,'2019-07-02 08:56:11','2019-07-02 08:56:11'),
  (12,'Yellow shirt','yellow-shirt','A yellow shirt','yellow shirt.jpg',3.50,1,'2019-07-02 08:56:31','2019-07-02 08:56:31');

UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;

INSERT INTO `cmsshoppingcart`.`users` (`id`, `username`, `password`, `email`, `phone_number`) VALUES 
	('1', 'john', '$2y$12$bTHCyzO4NTnEyEafPLX8GuFPPhsLds/Zq/Vn8Fhrkrc9IhVnc1FMS', 'john@festudent.com', '111111'),
	('2', 'bill', '$2y$12$bTHCyzO4NTnEyEafPLX8GuFPPhsLds/Zq/Vn8Fhrkrc9IhVnc1FMS', 'bil@festudent.com', '222222'),
	('3', 'jane', '$2y$12$bTHCyzO4NTnEyEafPLX8GuFPPhsLds/Zq/Vn8Fhrkrc9IhVnc1FMS', 'jane@festudent.com', '333333');

UNLOCK TABLES;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;

INSERT INTO `cmsshoppingcart`.`admins` (`id`, `username`, `password`) VALUES
 ('1', 'admin', '$2y$12$n7v4IYAd/HwnlQA4uVeSXeK7hIIrgpO9joXlGBhjDj8iBYrXFvdVG');

UNLOCK TABLES;

--
-- Create a foreign key constraint for table `products`
-- This way, if a category is deleted, all of its 
-- products will be deleted as well.
--
ALTER TABLE `cmsshoppingcart`.`products` 
ADD INDEX `category_id` (`category_id` ASC) VISIBLE;
;

ALTER TABLE `cmsshoppingcart`.`products` 
ADD CONSTRAINT `category_id_fk`
  FOREIGN KEY (`category_id`)
  REFERENCES `cmsshoppingcart`.`categories` (`id`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;
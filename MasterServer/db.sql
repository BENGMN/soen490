-- MySQL dump 10.13  Distrib 5.1.58, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: soen490
-- ------------------------------------------------------
-- Server version	5.1.58-1ubuntu1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Message`
--

DROP TABLE IF EXISTS `Message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Message` (
  `mid` decimal(39,0) NOT NULL,
  `uid` decimal(39,0) NOT NULL,
  `message` blob NOT NULL,
  `speed` float DEFAULT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_rating` int(11) NOT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Message`
--

LOCK TABLES `Message` WRITE;
/*!40000 ALTER TABLE `Message` DISABLE KEYS */;
INSERT INTO `Message` VALUES ('17682855922359940342473900963411213882','19521065307543938668775565691227658539','\0	',20,30,10,'2012-03-19 22:28:42',2);
/*!40000 ALTER TABLE `Message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServerList`
--

DROP TABLE IF EXISTS `ServerList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ServerList` (
  `hostname` varchar(255) NOT NULL,
  `port` int(11) NOT NULL,
  PRIMARY KEY (`hostname`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServerList`
--

LOCK TABLES `ServerList` WRITE;
/*!40000 ALTER TABLE `ServerList` DISABLE KEYS */;
/*!40000 ALTER TABLE `ServerList` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServerParameters`
--

DROP TABLE IF EXISTS `ServerParameters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ServerParameters` (
  `paramName` varchar(64) NOT NULL DEFAULT '',
  `description` varchar(256) DEFAULT NULL,
  `value` varchar(32) NOT NULL,
  PRIMARY KEY (`paramName`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServerParameters`
--

LOCK TABLES `ServerParameters` WRITE;
/*!40000 ALTER TABLE `ServerParameters` DISABLE KEYS */;
INSERT INTO `ServerParameters` VALUES ('minMessageSizeBytes','The minimum size of uploaded audio files that should be accepted, in bytes.','2000'),('maxMessageSizeBytes','The maximum size of uploaded audio files that should be accepted, in bytes.','50000'),('messageLifeDays','The time to live of regular messages. If a message is older than this amount, in days, it should be deleted.','7'),('advertiserMessageLifeDays','The time to live of advertiser messages.','30'),('minEmailLength','The minimum character length of email addresses when creating user accounts.','15'),('maxEmailLength','The maximum character length of email addresses when creating user accounts.','50'),('minPasswordLength','The minimum character length of password when creating user accounts.','6'),('maxPasswordLength','The maximum character length of password when creating user accounts.','20'),('speedThreshold','The speed threshold to compare against a user requesting messages.','15'),('defaultMessageRadiusMeters','The default radius, in meters, in which to check if there are any messages.','100'),('minMessages','The minimum amount of messages fetched to the user.','10'),('maxMessages','The maximum amount of messages fetched to the user.','50');
/*!40000 ALTER TABLE `ServerParameters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `uid` decimal(39,0) NOT NULL,
  `email` varchar(64) NOT NULL,
  `password` varchar(256) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('19521065307543938668775565691227658539','example@example.com','capstone',0,1);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-03-19 15:31:52

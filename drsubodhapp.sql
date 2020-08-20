-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: drsubodhapp
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `patient_masterdata`
--

DROP TABLE IF EXISTS `patient_masterdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_masterdata` (
  `patient_id` int NOT NULL AUTO_INCREMENT,
  `mobileNumber` varchar(50) DEFAULT NULL,
  `patient_name` varchar(50) DEFAULT NULL,
  `gender` varchar(6) DEFAULT NULL,
  `emailId` varchar(50) DEFAULT NULL,
  `dob` varchar(15) DEFAULT NULL,
  `age` varchar(10) DEFAULT NULL,
  `active` enum('Y','N') DEFAULT NULL,
  `created_timestamp` varchar(45) DEFAULT NULL,
  `modified_timestamp` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_masterdata`
--

LOCK TABLES `patient_masterdata` WRITE;
/*!40000 ALTER TABLE `patient_masterdata` DISABLE KEYS */;
INSERT INTO `patient_masterdata` VALUES (1,'9658742150','Arun','Male','arun@gmail.com','12-05-2000','20',NULL,NULL,NULL),(2,'9667854222','Lavanya','Female','lavanya@gmail.com','21-03-1999','21',NULL,NULL,NULL),(3,'8541235100','Sujatha','Female','sujatha@gmail.com','2/10/1997','23','Y',NULL,NULL),(4,'7785695123','Inchara','Female','inchara@gmail.com','15/4/2009','11','Y',NULL,NULL),(5,'7795864125','Arnaz','Male','arnav@gmail.com','12/10/2005','15','Y',NULL,NULL),(6,'7965842103','Shravan','Male','shravan@gmail.com','25/6/2020','20','Y',NULL,NULL),(7,'7215980100','Akshay','Male','akshay@gmail.com','29/8/2001','30','Y',NULL,NULL),(8,'9235410001','Cynthia','Female','cynthia@gmail.com','2/9/2020','35','Y',NULL,NULL),(9,'8521346790','Ram','Male','','12/8/2020','5','Y',NULL,NULL),(10,'7632514901','Vini','Female','vini@gmail.com','5/10/1982','38','Y',NULL,NULL),(11,'7958642311','Meena','Female','meena@gmail.com','6/8/2015','5','Y',NULL,NULL),(12,'9658444123','aaaa','Male','aaa@gmail.com','9/8/2000','20','Y',NULL,NULL),(13,'7625895622','bbbbb','Male','bbbb@gmail.com','9/9/2010','10','Y',NULL,NULL),(14,'7666854520','ccccc','Female','cccc@gmail.com','11/8/2006','14','Y',NULL,NULL),(15,'7775558540','deena','Female','deena@gmail.com','31/1/2007','13','Y',NULL,NULL),(16,'7588564410','veena','Female','veena@gmail.com','16/2/2006','14','Y',NULL,NULL),(17,'7788566410','reena','Female','reena@gmail.com','28/11/2007','13','Y','null',NULL),(18,'8699856120','soma','Male','soma@gmail.com','26/10/2012','8','Y','19.08.2020.13.21.05',NULL);
/*!40000 ALTER TABLE `patient_masterdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_reportmasterdata`
--

DROP TABLE IF EXISTS `patient_reportmasterdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_reportmasterdata` (
  `id` int NOT NULL AUTO_INCREMENT,
  `regNumber` int DEFAULT NULL,
  `ref_doctor` varchar(45) DEFAULT NULL,
  `testID` int DEFAULT NULL,
  `folderPath` varchar(50) DEFAULT NULL,
  `active` enum('Y','N') DEFAULT NULL,
  `created_timestamp` varchar(45) DEFAULT NULL,
  `modified_timestamp` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk2_regID_idx` (`regNumber`),
  KEY `fk1_testID_idx` (`testID`),
  CONSTRAINT `fk1_testID` FOREIGN KEY (`testID`) REFERENCES `testdetails` (`tID`),
  CONSTRAINT `fk2_regID` FOREIGN KEY (`regNumber`) REFERENCES `patient_masterdata` (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_reportmasterdata`
--

LOCK TABLES `patient_reportmasterdata` WRITE;
/*!40000 ALTER TABLE `patient_reportmasterdata` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient_reportmasterdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testdetails`
--

DROP TABLE IF EXISTS `testdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `testdetails` (
  `tID` int NOT NULL,
  `TestName` varchar(50) DEFAULT NULL,
  `active` enum('Y','N') DEFAULT NULL,
  PRIMARY KEY (`tID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testdetails`
--

LOCK TABLES `testdetails` WRITE;
/*!40000 ALTER TABLE `testdetails` DISABLE KEYS */;
INSERT INTO `testdetails` VALUES (1,'COLOUR DOPPLER OF CAROTID','Y'),(2,'FACIAL AND CARDIAC SCAN','Y'),(3,'Follicular Study','Y'),(4,'Missed abortion','Y'),(5,'PCOS.','Y'),(6,'PORTAL VEIN DOPPLER','Y'),(7,'USG BREAST','Y'),(8,'USG ABDOMEN AND PELVIS','Y'),(9,'USG OF ABDOMEN &amp; PELVIS(Female)','Y'),(10,'USG OF ABDOMEN &amp; PELVIS(Male BPH)','Y'),(11,'USG OF ABDOMEN &amp; PELVIS(Male)','Y'),(12,'USG OF ABDOMEN','Y'),(13,'USG OF CHEST','Y'),(14,'USG OF KUB','Y'),(15,'USG OF KUB(BKS)','Y'),(16,'USG OF KUB M','Y'),(17,'USG OBSTETRIC(4-9 MONTHS)','Y'),(18,'USG OBSTETRIC ANOMALY','Y'),(19,'USG PELVIS OBSTETRIC (NT SCAN)','Y'),(20,'USG OBSTETRIC TWIN PREGNANCY','Y'),(21,'USG OBSTETRIC ( VERY EARLY PREGNANCY)','Y'),(22,'USG OF PELVIS','Y'),(23,'USG OF SCROTUM','Y'),(24,'USG SHOULDER','Y'),(25,'USG SMALL PART','Y'),(26,'USG OF THYROID','Y'),(27,'VENOUS DOPPLER OF BOTH LOWER LIMBS','Y');
/*!40000 ALTER TABLE `testdetails` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-19 23:27:01

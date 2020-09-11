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
  `dob` varchar(20) DEFAULT NULL,
  `age` varchar(30) DEFAULT NULL,
  `active` enum('Y','N') DEFAULT NULL,
  `created_timestamp` varchar(45) DEFAULT NULL,
  `modified_timestamp` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_masterdata`
--

LOCK TABLES `patient_masterdata` WRITE;
/*!40000 ALTER TABLE `patient_masterdata` DISABLE KEYS */;
INSERT INTO `patient_masterdata` VALUES (1,'9658742150','Arun','Male','arun@gmail.com','12-05-2000','20',NULL,NULL,NULL),(2,'9667854222','Lavanya','Female','lavanya@gmail.com','21-03-1999','21',NULL,NULL,NULL),(3,'8541235100','Sujatha','Female','sujatha@gmail.com','2/10/1997','23','Y',NULL,NULL),(4,'7785695123','Inchara','Female','inchara@gmail.com','15/4/2009','11','Y',NULL,NULL),(5,'7795864125','Arnaz','Male','arnav@gmail.com','12/10/2005','15','Y',NULL,NULL),(6,'7965842103','Shravan','Male','shravan@gmail.com','25/6/2020','20','Y',NULL,NULL),(7,'7215980100','Akshay','Male','akshay@gmail.com','29/8/2001','30','Y',NULL,NULL),(8,'9235410001','Cynthia','Female','cynthia@gmail.com','2/9/2020','35','Y',NULL,NULL),(9,'8521346790','Ram','Male','','12/8/2020','5','Y',NULL,NULL),(10,'7632514901','Vini','Female','vini@gmail.com','5/10/1982','38','Y',NULL,NULL),(11,'7958642311','Meena','Female','meena@gmail.com','6/8/2015','5','Y',NULL,NULL),(12,'9658444123','aaaa','Male','aaa@gmail.com','9/8/2000','20','Y',NULL,NULL),(13,'7625895622','bbbbb','Male','bbbb@gmail.com','9/9/2010','10','Y',NULL,NULL),(14,'7666854520','ccccc','Female','cccc@gmail.com','11/8/2006','14','Y',NULL,NULL),(15,'7775558540','deena','Female','deena@gmail.com','31/1/2007','13','Y',NULL,NULL),(16,'7588564410','veena','Female','veena@gmail.com','16/2/2006','14','Y',NULL,NULL),(17,'7788566410','reena','Female','reena@gmail.com','28/11/2007','13','Y','null',NULL),(18,'8699856120','soma','Male','soma@gmail.com','26/10/2012','8','Y','19.08.2020.13.21.05',NULL),(19,'7854596854','sona','Female','sona@gmail.com','5/8/1994','26 years','Y','20.08.2020.15.14.16','20.08.2020.15.14.16'),(20,'9968852130','Varun','Male','varun@gmail.com','23/8/1985','34 years','Y','20.08.2020.15.19.59','20.08.2020.15.19.59'),(21,'9846568465','gjdgfj','Female','agrghai@mil.com','8/7/2015','5 years','Y','20.08.2020.23.13.59','20.08.2020.23.13.59'),(22,'6547896541','vvvvv','Male','rytjf@gmail.com','30/7/2014','6 years','Y','20.08.2020.23.36.08','20.08.2020.23.36.08'),(23,'8856542100','Reema','Female','reema@email.com','3/9/2009','10 years','Y','21.08.2020.10.09.30','21.08.2020.10.09.30'),(24,'9526766846','fhgfdj','Female','','10/8/2011','9 years','Y','21.08.2020.16.03.56','21.08.2020.16.03.56'),(25,'9658475210','hjfytdyt','Male','djhg@gmail.com','5/8/2009','11 years','Y','21.08.2020.16.05.51','21.08.2020.16.05.51'),(26,'9559666565','djgdg','Female','fss@gmil.cm','1/8/2012','8 years','Y','21.08.2020.16.46.12','21.08.2020.16.46.12'),(27,'9966322500','test name','Male','test@mail.com','7/5/2014','6 years 3 months','Y','27.08.2020.14.53.15','27.08.2020.14.53.15'),(28,'9685525200','test success','Female','test@mail.com','8/6/2016','4 years 2 months','Y','27.08.2020.17.35.36','27.08.2020.17.35.36'),(29,'8866553320','amar','Male','amar@mail.com','21/9/2011','8 years 11 months','Y','27.08.2020.17.37.15','27.08.2020.17.37.15'),(30,'7584521000','suresh','Female','suresh@mail.com','14/11/2012','7 years 9 months','Y','27.08.2020.23.06.05','27.08.2020.23.06.05'),(31,'9636938520','new Name','Male','new@mil.com','29/9/2004','15 years 10 months','Y','28.08.2020.10.24.48','28.08.2020.10.24.48'),(32,'6853253200','navi','Male','navi@mail.com','6/8/2014','6 years 0 months','Y','28.08.2020.10.27.11','28.08.2020.10.27.11'),(33,'8632595201','bhuvi','Female','bhuvi@mail.com','25/9/1996','23 years 11 months','Y','28.08.2020.10.29.15','28.08.2020.10.29.15'),(34,'7788556620','Reka','Female','reka@mail.com','12/8/1987','33 years 0 months','Y','28.08.2020.10.31.09','28.08.2020.10.31.09'),(35,'8856552620','Ravan','Male','ravan@mail.com','16/8/2006','14 years 0 months','Y','28.08.2020.10.33.13','10.09.2020.15.22.03'),(104,'7829948925','Shaikh Nafeesa','Female','sknafe2610@gmail.com','6/8/2020','22 days','Y','2028-08-20 20:17:11','10.09.2020.15.16.26'),(105,'7829948926','Nafeesa','Female','','4/8/2016','4 years 0 months','Y','2028-08-20 20:17:19','2028-08-20 20:17:19'),(106,'7828282802','Afreen','Female','af@gmail.com','4/10/2001','18 years 10 months','Y','2031-08-20 20:17:28','10.09.2020.15.38.50'),(107,'9685666522','Roopa A','Female','roopa@gmail.com','7/9/2006','14 years 0 months','Y','10.09.2020.15.41.14','10.09.2020.15.59.24');
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
  `ref_doctor` varchar(500) DEFAULT NULL,
  `testName` varchar(500) DEFAULT NULL,
  `testDate` varchar(50) DEFAULT NULL,
  `reportDate` varchar(50) DEFAULT NULL,
  `patientHistory` varchar(3000) DEFAULT NULL,
  `testDescription` varchar(5000) DEFAULT NULL,
  `impression` varchar(3000) DEFAULT NULL,
  `note` varchar(3000) DEFAULT NULL,
  `active` enum('Y','N') DEFAULT NULL,
  `folderPath` varchar(1000) DEFAULT NULL,
  `created_timestamp` varchar(50) DEFAULT NULL,
  `modified_timestamp` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk2_regID_idx` (`regNumber`),
  CONSTRAINT `fk2_regID` FOREIGN KEY (`regNumber`) REFERENCES `patient_masterdata` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_reportmasterdata`
--

LOCK TABLES `patient_reportmasterdata` WRITE;
/*!40000 ALTER TABLE `patient_reportmasterdata` DISABLE KEYS */;
INSERT INTO `patient_reportmasterdata` VALUES (1,105,'Babita','PCOS.','24/08/2020','18/08/2020','no','good','N.shaikh','xs','Y',NULL,'2031-08-20 20:12:07','2031-08-20 20:12:07'),(2,105,'Dr Sauda','Follicular Study','31/08/2020','31/08/2020','no','good','N.Shaikh','Good','Y',NULL,'2031-08-20 20:12:13','07.09.2020.10.29.34'),(5,104,'Nafeesaaaa','Missed abortion','30/08/2020','31/08/2020','Good','Good','Good','Good','Y',NULL,'2031-08-20 20:15:38','2031-08-20 20:15:38'),(7,105,'Sauda','PCOS.','30/08/2020','31/08/2020','No','Good hdjhggggggggggggg gggggggggggg jjjjjjjjjjjjjjjjjjjjjjj fdsaafsg gfgdakg bghjg shsgahs fsdadgfshfg fhjsdfdgajhsgffjv sdhsfgsaf dfsdshjgfsadhf hjgfgf hgfjas hjgfajh fsfgsahfsadgf\nfjgfjs hsdgdfgf shghghi shgiug kjsdhgksag khgfioa salsgfouaigr sgsowyrtgwb fwiygye iywegfwe efgweiw gfyfgiygref hfgfyugf sefgyee efwewyt gt ttueyr ewetgoyegy g rtg yerewygfe f geyr\nfqwhwqfWYFRGYU sgsakaurhg hgsfjguertghkauehgkurg utiuetv twtvi4t ytibeaty istb7ewytb sguytet j gfa ktustb fshvjtruyt fsvrtiset srgyee\nsjhgjsgvf sgsiutzh sughsizk jsghrk hgsirhts fabjkaz','Good','Good','Y','C:UsersPersonalDesktopPatientReports2020/105_Nafeesa_PCOS..pdf','2031-08-20 20:15:45','09.09.2020.16.49.25'),(9,104,'vdv','PCOS.','01/09/2020','02/09/2020','vdsddd','dvssss','vd','ds','Y',NULL,'2031-08-20 20:15:49','03.09.2020.16.22.49'),(10,104,'Dr. Nafeesa','USG OBSTETRIC ANOMALY','30/08/2020','31/08/2020','no','Good','Good','Good','N',NULL,'2031-08-20 20:15:53','2031-08-20 20:15:53'),(11,104,'Doctor','USG ABDOMEN AND PELVIS','30/08/2020','31/08/2020','Good','Good','Good','Good','Y',NULL,'2031-08-20 20:15:55','2031-08-20 20:15:55'),(12,105,'Doctor','USG BREAST','30/08/2020','31/08/2020','sa','xsxz','cs','cds','Y',NULL,'2031-08-20 20:16:10','2031-08-20 20:16:10'),(13,104,'Nafeesa','PCOS.','30/08/2020','31/08/2020','Good','Good','Good','Good','Y',NULL,'2031-08-20 20:16:30','2031-08-20 20:16:30'),(14,106,'Dr. Astha','PCOS.','30/08/2020','31/08/2020','No','Good','Good','Good','Y',NULL,'2031-08-20 20:17:28','2031-08-20 20:17:28'),(15,106,'Dr Subodh','Missed abortion','30/08/2020','30/08/2020','mo','Good','Good','Good','Y','C:UsersPersonalDesktopPatientReports2020/106_Afreen_Missed abortion.pdf','2031-08-20 20:17:32','2031-08-20 20:17:32'),(16,106,'Subodh','Follicular Study','30/08/2020','30/08/2020','Good','Good','Good','Good','Y','C:UsersPersonalDesktopPatientReports2020/106_Afreen_Follicular Study.pdf','2031-08-20 20:17:34','2031-08-20 20:17:34'),(17,106,'Subodh','PORTAL VEIN DOPPLER','31/08/2020','31/08/2020','Good','Good','Good','Good','Y',NULL,'2031-08-20 20:17:35','2031-08-20 20:17:35'),(18,106,'Subosd','USG BREAST','30/08/2020','31/08/2020','No','Good','Good','Good','Y','C:UsersPersonalDesktopPatientReports2020/106_Afreen_USG BREAST.pdf','2031-08-20 20:17:37','2031-08-20 20:17:37'),(19,106,'Subosdh','COLOUR DOPPLER OF CAROTID','30/08/2020','31/08/2020','No','Good','Good','Good','Y',NULL,'2031-08-20 20:18:32','2031-08-20 20:18:32'),(20,106,'Subodh','PCOS.','30/08/2020','31/08/2020','No','Good','Good','Good','Y',NULL,'2031-08-20 20:18:44','2031-08-20 20:18:44'),(21,106,'Subodh','Follicular Study','30/08/2020','31/08/2020','Good','Good','Good','Good','Y','null','2031-08-20 20:18:47','2031-08-20 20:18:47'),(22,106,'Subodh','USG OBSTETRIC ANOMALY','30/08/2020','31/08/2020','Hey','Hey','Hey','Hey','Y','E://Users//neebal//Desktop//PatientReports//2020//','2031-08-20 20:18:50','2031-08-20 20:18:50'),(23,106,'Subodh','USG OBSTETRIC ANOMALY','30/08/2020','31/08/2020','Good','Good','Good','Good','Y','E://Users//neebal//Desktop//PatientReports//2020//','2031-08-20 20:18:55','2031-08-20 20:18:55'),(24,106,'Subodh','PORTAL VEIN DOPPLER','30/08/2020','31/08/2020','Good','Good','Good','Good','Y','E://Users//neebal//Desktop//PatientReports//2020//','2031-08-20 20:18:56','2031-08-20 20:18:56'),(25,23,'Dr Roopa','USG OF ABDOMEN &amp; PELVIS(Female)','30/08/2020','01/09/2020','good','tested','checked','good','Y','null','01.09.2020.13.12.27','05.09.2020.14.27.56'),(26,34,'Dr govind','USG OBSTETRIC ANOMALY','04/09/2020','04/09/2020','no history','tested','good','nothing','Y','null','01.09.2020.15.56.51','05.09.2020.14.21.33'),(27,33,'Dr Teena','USG OBSTETRIC ANOMALY','01/09/2020','02/09/2020','Good','Good tested correct','good','Nothing','Y','C:UsersPersonalDesktopPatientReports2020/33_bhuvi_USG OBSTETRIC ANOMALY.pdf','03.09.2020.09.51.43','03.09.2020.09.51.43'),(28,32,'Dr Rama','USG ABDOMEN AND PELVIS','31/08/2020','03/09/2020','good','correct','Bad','Nothing','Y','C:UsersPersonalDesktopPatientReports202032_navi_USG ABDOMEN AND PELVIS.pdf','03.09.2020.10.17.42','03.09.2020.10.17.42'),(30,34,'Dr Suhas','Follicular Study','05/09/2020','05/09/2020','good','good health yes','good health','nill nothing','Y','C:UsersPersonalDesktopPatientReports2020/34_Reka_Follicular Study.pdf','03.09.2020.10.39.08','05.09.2020.14.33.48'),(31,16,'Dr Ropa','PORTAL VEIN DOPPLER','02/09/2020','03/09/2020','good','better','best','nill','Y','null','03.09.2020.20.52.17','03.09.2020.20.52.17'),(32,35,'Dr Rama','PCOS.','03/09/2020','04/09/2020','good health','good','not bad','good','Y','C:UsersPersonalDesktopPatientReports2020/35_Ravan_PCOS..pdf','04.09.2020.13.31.04','04.09.2020.23.16.00'),(33,32,'DR reka','PCOS.','03/09/2020','04/09/2020','tested','correct','good','nill','Y','null','04.09.2020.13.33.05','04.09.2020.13.33.05'),(34,35,'Dr Khare','PORTAL VEIN DOPPLER','04/09/2020','05/09/2020','not so good','good','better','best','Y','C:UsersPersonalDesktopPatientReports2020/35_Ravan_PORTAL VEIN DOPPLER.pdf','05.09.2020.12.39.36','05.09.2020.13.53.12'),(37,35,'Dr Reka','PORTAL VEIN DOPPLER','07/09/2020','07/09/2020','Bad','recovering','good','better','Y','C:UsersPersonalDesktopPatientReports2020/35_Ravan_PORTAL VEIN DOPPLER.pdf','07.09.2020.23.08.16','07.09.2020.23.08.16');
/*!40000 ALTER TABLE `patient_reportmasterdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportdata`
--

DROP TABLE IF EXISTS `reportdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reportdata` (
  `rid` int NOT NULL AUTO_INCREMENT,
  `rm_id` int DEFAULT NULL,
  `p_id` int DEFAULT NULL,
  `testData` varchar(50) DEFAULT NULL,
  `RI` varchar(45) DEFAULT NULL,
  `PI` varchar(45) DEFAULT NULL,
  `S/D` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`rid`),
  KEY `fk_rm_idx` (`rm_id`) /*!80000 INVISIBLE */,
  KEY `fk_pid` (`p_id`),
  CONSTRAINT `fk_pid` FOREIGN KEY (`p_id`) REFERENCES `patient_masterdata` (`patient_id`),
  CONSTRAINT `fk_rm` FOREIGN KEY (`rm_id`) REFERENCES `patient_reportmasterdata` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reportdata`
--

LOCK TABLES `reportdata` WRITE;
/*!40000 ALTER TABLE `reportdata` DISABLE KEYS */;
INSERT INTO `reportdata` VALUES (1,NULL,NULL,'LT UTERINE ARTERY',NULL,NULL,NULL),(2,NULL,NULL,'RT UTERINE ARTERY',NULL,NULL,NULL),(3,NULL,NULL,'MCA',NULL,NULL,NULL),(4,NULL,NULL,'AORTA',NULL,NULL,NULL),(5,NULL,NULL,'UMBLICAL ARTERY',NULL,NULL,NULL),(6,NULL,NULL,'CP RATIO',NULL,NULL,NULL),(7,NULL,NULL,'DUCTUS VENOSUS',NULL,NULL,NULL);
/*!40000 ALTER TABLE `reportdata` ENABLE KEYS */;
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
  PRIMARY KEY (`tID`),
  KEY `idx_testdetails_TestName` (`TestName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testdetails`
--

LOCK TABLES `testdetails` WRITE;
/*!40000 ALTER TABLE `testdetails` DISABLE KEYS */;
INSERT INTO `testdetails` VALUES (1,'COLOUR DOPPLER OF CAROTID','Y'),(2,'FACIAL AND CARDIAC SCAN','Y'),(3,'Follicular Study','Y'),(4,'Missed abortion','Y'),(5,'PCOS','Y'),(6,'PORTAL VEIN DOPPLER','Y'),(7,'USG BREAST','Y'),(8,'USG ABDOMEN AND PELVIS','Y'),(9,'USG OF ABDOMEN &amp; PELVIS(Female)','Y'),(10,'USG OF ABDOMEN &amp; PELVIS(Male BPH)','Y'),(11,'USG OF ABDOMEN &amp; PELVIS(Male)','Y'),(12,'USG OF ABDOMEN','Y'),(13,'USG OF CHEST','Y'),(14,'USG OF KUB','Y'),(15,'USG OF KUB(BKS)','Y'),(16,'USG OF KUB M','Y'),(17,'USG OBSTETRIC(4-9 MONTHS)','Y'),(18,'USG OBSTETRIC ANOMALY','Y'),(19,'USG PELVIS OBSTETRIC (NT SCAN)','Y'),(20,'USG OBSTETRIC TWIN PREGNANCY','Y'),(21,'USG OBSTETRIC ( VERY EARLY PREGNANCY)','Y'),(22,'USG OF PELVIS','Y'),(23,'USG OF SCROTUM','Y'),(24,'USG SHOULDER','Y'),(25,'USG SMALL PART','Y'),(26,'USG OF THYROID','Y'),(27,'VENOUS DOPPLER OF BOTH LOWER LIMBS','Y');
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

-- Dump completed on 2020-09-11 11:05:22

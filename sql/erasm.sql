-- MySQL dump 10.13  Distrib 8.0.46, for macos15 (arm64)
--
-- Host: localhost    Database: erasm_db
-- ------------------------------------------------------
-- Server version	9.7.0

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ 'ace28942-40a3-11f1-8b7b-c5182679ea2a:1-611';

--
-- Table structure for table `allocations`
--

DROP TABLE IF EXISTS `allocations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allocations` (
  `allocation_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `allocation_percentage` int NOT NULL,
  `end_date` date NOT NULL,
  `project_role` varchar(100) NOT NULL,
  `start_date` date NOT NULL,
  `status` enum('ACTIVE','CANCELLED','COMPLETED') NOT NULL,
  `employee_id` bigint NOT NULL,
  `project_id` bigint NOT NULL,
  PRIMARY KEY (`allocation_id`),
  KEY `FKlidedpimhddtisgg66kca1to7` (`employee_id`),
  KEY `FKlgyvi1ifhphug0govaqwnsqyf` (`project_id`),
  CONSTRAINT `FKlgyvi1ifhphug0govaqwnsqyf` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`),
  CONSTRAINT `FKlidedpimhddtisgg66kca1to7` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allocations`
--

LOCK TABLES `allocations` WRITE;
/*!40000 ALTER TABLE `allocations` DISABLE KEYS */;
INSERT INTO `allocations` VALUES (1,'2026-07-04 23:02:03.291524','2026-07-04 23:09:22.273450',60,'2026-12-31','Solution Architect','2026-07-01','COMPLETED',1,1),(2,'2026-07-04 23:03:19.741718','2026-07-04 23:03:19.741783',100,'2026-12-31','Resource Manager','2026-07-01','ACTIVE',2,1),(3,'2026-07-04 23:03:30.110964','2026-07-04 23:03:30.111010',80,'2026-11-30','Delivery Manager','2026-07-01','ACTIVE',3,2),(4,'2026-07-04 23:03:38.900518','2026-07-04 23:03:38.900562',100,'2026-12-31','Spring Boot Developer','2026-07-01','ACTIVE',4,1);
/*!40000 ALTER TABLE `allocations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_logs`
--

DROP TABLE IF EXISTS `audit_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_logs` (
  `audit_log_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `action` enum('CREATE','DELETE','LOGIN','LOGOUT','UPDATE') NOT NULL,
  `action_time` datetime(6) NOT NULL,
  `description` varchar(500) NOT NULL,
  `entity_id` bigint NOT NULL,
  `entity_name` varchar(100) NOT NULL,
  `employee_id` bigint NOT NULL,
  PRIMARY KEY (`audit_log_id`),
  KEY `FK6geiy0g1j2se38krkm52jvavh` (`employee_id`),
  CONSTRAINT `FK6geiy0g1j2se38krkm52jvavh` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_logs`
--

LOCK TABLES `audit_logs` WRITE;
/*!40000 ALTER TABLE `audit_logs` DISABLE KEYS */;
INSERT INTO `audit_logs` VALUES (1,'2026-07-04 21:35:33.398527','2026-07-04 21:35:33.398561','UPDATE','2026-07-04 21:35:33.336828','Password changed',2,'User',2),(2,'2026-07-04 22:49:54.215296','2026-07-04 22:49:54.215328','UPDATE','2026-07-04 22:49:54.156621','Resource request submitted',1,'Resource Request',3),(3,'2026-07-04 22:53:55.994906','2026-07-04 22:53:55.995146','UPDATE','2026-07-04 22:53:55.993662','Resource request moved to review',1,'Resource Request',3),(4,'2026-07-04 22:54:22.825499','2026-07-04 22:54:22.825506','UPDATE','2026-07-04 22:54:22.824845','Resource request approved',1,'Resource Request',3),(5,'2026-07-04 22:54:51.628489','2026-07-04 22:54:51.628497','UPDATE','2026-07-04 22:54:51.627611','Resource request allocated',1,'Resource Request',3),(6,'2026-07-04 22:55:21.555479','2026-07-04 22:55:21.555488','UPDATE','2026-07-04 22:55:21.554102','Resource request completed',1,'Resource Request',3),(7,'2026-07-04 23:02:03.329377','2026-07-04 23:02:03.329384','CREATE','2026-07-04 23:02:03.326659','Employee allocated to project ERASM Resource Management System',1,'Allocation',1),(8,'2026-07-04 23:03:19.755827','2026-07-04 23:03:19.755838','CREATE','2026-07-04 23:03:19.754751','Employee allocated to project ERASM Resource Management System',2,'Allocation',2),(9,'2026-07-04 23:03:30.118864','2026-07-04 23:03:30.118877','CREATE','2026-07-04 23:03:30.118078','Employee allocated to project Inventory Management Platform',3,'Allocation',3),(10,'2026-07-04 23:03:38.915461','2026-07-04 23:03:38.915469','CREATE','2026-07-04 23:03:38.914058','Employee allocated to project ERASM Resource Management System',4,'Allocation',4),(11,'2026-07-04 23:08:23.353080','2026-07-04 23:08:23.353098','UPDATE','2026-07-04 23:08:23.352198','Allocation updated',1,'Allocation',1),(12,'2026-07-04 23:09:22.280262','2026-07-04 23:09:22.280272','UPDATE','2026-07-04 23:09:22.278930','Allocation released',1,'Allocation',1);
/*!40000 ALTER TABLE `audit_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `certifications`
--

DROP TABLE IF EXISTS `certifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `certifications` (
  `certification_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `certificate_number` varchar(100) DEFAULT NULL,
  `certificate_url` varchar(255) DEFAULT NULL,
  `certification_name` varchar(150) NOT NULL,
  `expiry_date` date DEFAULT NULL,
  `issue_date` date NOT NULL,
  `issuing_organization` varchar(100) NOT NULL,
  `employee_id` bigint NOT NULL,
  PRIMARY KEY (`certification_id`),
  UNIQUE KEY `UK2d1ckh2kion1hs08pluprue1s` (`certificate_number`),
  KEY `FK8jk3p8d9bytcopb1mb9yb3to9` (`employee_id`),
  CONSTRAINT `FK8jk3p8d9bytcopb1mb9yb3to9` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `certifications`
--

LOCK TABLES `certifications` WRITE;
/*!40000 ALTER TABLE `certifications` DISABLE KEYS */;
INSERT INTO `certifications` VALUES (1,'2026-07-04 22:24:57.383406','2026-07-04 22:24:57.383439','OCP-2024-001','https://oracle.com/certificates/OCP001','Oracle Certified Java Programmer','2027-01-15','2024-01-15','Oracle',1),(2,'2026-07-04 22:25:51.196131','2026-07-04 22:25:51.196232','AWS-2023-101','https://aws.amazon.com/certificates/AWS101','AWS Certified Solutions Architect','2026-06-10','2023-06-10','Amazon',2),(3,'2026-07-04 22:26:03.062105','2026-07-04 22:26:03.062128','PMP-2022-201','https://pmi.org/certificates/PMP201','Project Management Professional','2025-09-20','2022-09-20','PMI',3),(4,'2026-07-04 22:26:12.508312','2026-07-04 22:30:05.088802','AZ104-2025-301','https://learn.microsoft.com/certificates/AZ104301','Microsoft Azure Administrator','2028-02-01','2025-02-01','Microsoft',4);
/*!40000 ALTER TABLE `certifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_skills`
--

DROP TABLE IF EXISTS `employee_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_skills` (
  `employee_skill_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `skill_level` enum('ADVANCED','BEGINNER','EXPERT','INTERMEDIATE') NOT NULL,
  `years_of_experience` double NOT NULL,
  `employee_id` bigint NOT NULL,
  `skill_id` bigint NOT NULL,
  PRIMARY KEY (`employee_skill_id`),
  UNIQUE KEY `UKfe5ma5xs8n2ekumehtvg4sn1w` (`employee_id`,`skill_id`),
  KEY `FK8anwsnenk9d8nirjuov0ywinb` (`skill_id`),
  CONSTRAINT `FK8anwsnenk9d8nirjuov0ywinb` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`skill_id`),
  CONSTRAINT `FKf1412wfxc2xjq9gbfm6v0op0` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_skills`
--

LOCK TABLES `employee_skills` WRITE;
/*!40000 ALTER TABLE `employee_skills` DISABLE KEYS */;
INSERT INTO `employee_skills` VALUES (1,'2026-07-04 22:12:52.083224','2026-07-04 22:12:52.083281','EXPERT',5,1,1),(2,'2026-07-04 22:14:00.137063','2026-07-04 22:14:00.137129','ADVANCED',8,2,1),(3,'2026-07-04 22:14:41.120980','2026-07-04 22:14:41.121067','INTERMEDIATE',6,3,4),(4,'2026-07-04 22:15:08.429782','2026-07-04 22:19:37.880443','INTERMEDIATE',3,4,3);
/*!40000 ALTER TABLE `employee_skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `employee_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `department` varchar(100) NOT NULL,
  `designation` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `employee_code` varchar(20) NOT NULL,
  `experience` double NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `joining_date` date NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `UKj9xgmd0ya5jmus09o0b8pqrpb` (`email`),
  UNIQUE KEY `UKetqhw9qqnad1kyjq3ks1glw8x` (`employee_code`),
  UNIQUE KEY `UKgnponadwwxr5nm2tqe5b905hs` (`phone`),
  KEY `FKah490190ww1q2a4piuv41hk6e` (`role_id`),
  CONSTRAINT `FKah490190ww1q2a4piuv41hk6e` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'2026-07-04 20:57:10.000000','2026-07-04 20:57:10.000000','Engineering','Software Engineer','vikas.admin@erasm.com','EMP001',3,'Vikas','2026-07-04','Chandrappa','7019120295',1),(2,'2026-07-04 20:57:22.000000','2026-07-04 20:57:22.000000','Engineering','Resource Manager','rahul.rm@erasm.com','EMP002',8,'Rahul','2026-07-04','Sharma','9876543211',2),(3,'2026-07-04 20:57:22.000000','2026-07-04 20:57:22.000000','Engineering','Delivery Manager','priya.dm@erasm.com','EMP003',10,'Priya','2026-07-04','Nair','9876543212',3),(4,'2026-07-04 20:57:22.000000','2026-07-04 20:57:22.000000','Engineering','Software Engineer','arjun.employee@erasm.com','EMP004',2,'Arjun','2026-07-04','Kumar','9876543213',4),(5,'2026-07-04 20:57:22.000000','2026-07-04 20:57:22.000000','Audit','Auditor','neha.auditor@erasm.com','EMP005',6,'Neha','2026-07-04','Patel','9876543214',5);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projects` (
  `project_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `budget` decimal(15,2) NOT NULL,
  `client_name` varchar(100) NOT NULL,
  `end_date` date NOT NULL,
  `project_code` varchar(20) NOT NULL,
  `project_name` varchar(100) NOT NULL,
  `start_date` date NOT NULL,
  `status` enum('ACTIVE','COMPLETED','ON_HOLD','PLANNED') NOT NULL,
  `technology_stack` varchar(500) NOT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `UK1batb7mq0elcfcs3d6maqo6sg` (`project_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (1,'2026-07-04 22:33:59.954595','2026-07-04 22:33:59.954773',2500000.00,'Infosys','2026-12-31','PRJ001','ERASM Resource Management System','2026-01-01','ACTIVE','Java, Spring Boot, MySQL, React'),(2,'2026-07-04 22:34:40.335693','2026-07-04 22:39:50.923327',2100000.00,'TCS','2026-11-30','PRJ002','Inventory Management Platform','2026-02-01','ACTIVE','Java, Spring Boot, PostgreSQL, Docker'),(3,'2026-07-04 22:34:48.449924','2026-07-04 22:34:48.449950',4200000.00,'HDFC Bank','2026-06-30','PRJ003','Banking Portal','2025-06-01','ON_HOLD','Spring Boot, Angular, Oracle'),(4,'2026-07-04 22:34:57.733760','2026-07-04 22:34:57.733792',3200000.00,'Wipro','2025-12-31','PRJ004','AI Recruitment Portal','2025-01-01','COMPLETED','Python, FastAPI, React');
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource_requests`
--

DROP TABLE IF EXISTS `resource_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resource_requests` (
  `request_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `experience_required` double NOT NULL,
  `number_of_resources` int NOT NULL,
  `priority` enum('CRITICAL','HIGH','LOW','MEDIUM') NOT NULL,
  `request_date` date NOT NULL,
  `requested_role` varchar(100) NOT NULL,
  `status` enum('ALLOCATED','APPROVED','COMPLETED','DRAFT','REJECTED','REVIEW','SUBMITTED') NOT NULL,
  `project_id` bigint NOT NULL,
  `requested_by` bigint NOT NULL,
  `skill_id` bigint NOT NULL,
  PRIMARY KEY (`request_id`),
  KEY `FKtghqmt481tvabqx37bfp8ueoh` (`project_id`),
  KEY `FKhovsc58v8c9ueqb0shb48erss` (`requested_by`),
  KEY `FK6uh2r2dxvk0d56vu02tbusl7p` (`skill_id`),
  CONSTRAINT `FK6uh2r2dxvk0d56vu02tbusl7p` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`skill_id`),
  CONSTRAINT `FKhovsc58v8c9ueqb0shb48erss` FOREIGN KEY (`requested_by`) REFERENCES `employees` (`employee_id`),
  CONSTRAINT `FKtghqmt481tvabqx37bfp8ueoh` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource_requests`
--

LOCK TABLES `resource_requests` WRITE;
/*!40000 ALTER TABLE `resource_requests` DISABLE KEYS */;
INSERT INTO `resource_requests` VALUES (1,'2026-07-04 22:44:19.714212','2026-07-04 22:55:21.542180',3,2,'HIGH','2026-07-04','Spring Boot Developer','COMPLETED',1,3,2),(2,'2026-07-04 22:44:47.116689','2026-07-04 22:49:11.784252',4,2,'HIGH','2026-07-04','Senior React Developer','DRAFT',2,3,4),(3,'2026-07-04 22:45:12.600720','2026-07-04 22:45:12.600749',5,3,'CRITICAL','2026-07-04','Java Developer','DRAFT',3,2,1);
/*!40000 ALTER TABLE `resource_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `role_name` enum('ADMIN','AUDITOR','DELIVERY_MANAGER','EMPLOYEE','RESOURCE_MANAGER') NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK716hgxp60ym1lifrdgp67xt5k` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'2026-07-04 20:57:02.000000','2026-07-04 20:57:02.000000','ADMIN'),(2,'2026-07-04 20:57:02.000000','2026-07-04 20:57:02.000000','RESOURCE_MANAGER'),(3,'2026-07-04 20:57:02.000000','2026-07-04 20:57:02.000000','DELIVERY_MANAGER'),(4,'2026-07-04 20:57:02.000000','2026-07-04 20:57:02.000000','EMPLOYEE'),(5,'2026-07-04 20:57:02.000000','2026-07-04 20:57:02.000000','AUDITOR');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skills`
--

DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills` (
  `skill_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `category` varchar(50) NOT NULL,
  `skill_name` varchar(100) NOT NULL,
  PRIMARY KEY (`skill_id`),
  UNIQUE KEY `UKqdkr64bxreqdbn6b4k9vumvp3` (`skill_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skills`
--

LOCK TABLES `skills` WRITE;
/*!40000 ALTER TABLE `skills` DISABLE KEYS */;
INSERT INTO `skills` VALUES (1,'2026-07-04 21:56:38.112999','2026-07-04 21:56:38.113088','Programming Language','Java'),(2,'2026-07-04 21:57:24.746831','2026-07-04 21:57:24.746858','Framework','Spring Boot'),(3,'2026-07-04 21:58:13.075551','2026-07-04 21:58:13.075577','Database','MySQL'),(4,'2026-07-04 21:59:03.242038','2026-07-04 21:59:03.242068','Frontend','React'),(5,'2026-07-04 22:00:22.346831','2026-07-04 22:00:22.346868','DevOps','Docker');
/*!40000 ALTER TABLE `skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(50) NOT NULL,
  `employee_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UKd1s31g1a7ilra77m65xmka3ei` (`employee_id`),
  CONSTRAINT `FK6p2ib82uai0pj9yk1iassppgq` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2026-07-04 20:57:31.000000','2026-07-04 20:57:31.000000','vikas.admin@erasm.com',_binary '','$2a$10$uvCl7jCroOGZUe4tl6PZiOS9jDzI8npH7KlOsvXBH6VPAnVqMWLGu','vikas.admin',1),(2,'2026-07-04 21:28:22.028937','2026-07-04 21:35:33.319325','rahul.rm@erasm.com',_binary '','$2a$10$bxxE8RZbXVJOsNt/jPHcEec9cmx8kbfUioPrbVkIx3ZGjJJqmvN8y','rahul.manager',2),(3,'2026-07-04 21:29:06.959301','2026-07-04 21:29:06.959329','priya.dm@erasm.com',_binary '','$2a$10$IBtQploZAIh6x31KEBhzxemtsRZGBw9yWvZHoOgdGmgCf3rpGn0BC','priya.dm',3),(4,'2026-07-04 21:29:51.379837','2026-07-04 21:29:51.379865','arjun.employee@erasm.com',_binary '','$2a$10$Rd2lha0DPvkd3DyJX6Rixel7X59i4dROc1ZuyrAqMml0uJDma773W','arjun.emp',4),(5,'2026-07-04 21:30:52.240934','2026-07-04 21:30:52.240964','neha.auditor@erasm.com',_binary '','$2a$10$tpeq3wuZCkc8u4Gc0n9FfOJ5Fs/W.nnj0nR5pC7HhwpbLZzkNoxFK','neha.audit',5);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-04 23:48:37

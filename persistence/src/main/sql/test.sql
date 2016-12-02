USE test;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.7.10-log

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
-- Table structure for table `hibernate_id_generation`
--

DROP TABLE IF EXISTS `hibernate_id_generation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_id_generation` (
  `next_hi` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_id_generation`
--

LOCK TABLES `hibernate_id_generation` WRITE;
/*!40000 ALTER TABLE `hibernate_id_generation` DISABLE KEYS */;
INSERT INTO `hibernate_id_generation` VALUES (703);
/*!40000 ALTER TABLE `hibernate_id_generation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_element`
--

DROP TABLE IF EXISTS `t_element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_element` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `uuid` varchar(100) NOT NULL,
  `height` int(11) NOT NULL COMMENT '高',
  `width` int(11) NOT NULL COMMENT '宽',
  `positionx` double NOT NULL COMMENT 'x坐标',
  `positiony` double NOT NULL COMMENT 'y坐标',
  `ml_flow_id` bigint(20) NOT NULL,
  `element_tpl_id` bigint(20) NOT NULL,
  `status` varchar(100) DEFAULT NULL,
  `latest_runtime_id` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=22315012 DEFAULT CHARSET=utf8 COMMENT='GUI 拖拽的元素';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_element`
--

LOCK TABLES `t_element` WRITE;
/*!40000 ALTER TABLE `t_element` DISABLE KEYS */;
INSERT INTO `t_element` VALUES (22315008,'2016-04-19 15:40:39','2016-04-20 15:58:13',2,'d72d7fb2-88bf-41c0-8282-95510667d753',45,90,130,130,22282240,1,'STOP','c95a991c-ef49-4662-aee1-faceff7285bd'),(22315009,'2016-04-19 15:40:39','2016-04-20 15:49:24',2,'fd8466d2-f729-43b2-9d58-167f9ed1dfb9',45,90,130,260,22282240,5,'STOP','45437313-1697-48ec-8071-a63abbdae3ca'),(22315010,'2016-04-19 15:40:39','2016-04-20 15:49:24',1,'3a75e1ff-8d90-40a9-902c-1ed5397d00cd',45,90,130,410,22282240,22216704,'STOP','3a5e74ec-3ca0-4908-85f0-4fbd23230634'),(22315011,'2016-04-19 15:42:09','2016-04-20 15:49:24',1,'d64502c0-9240-4236-b314-a8f2aba59ce7',45,90,130,540,22282240,5,'STOP','fb200db2-c65b-4902-9e07-a97de3a11c3b');
/*!40000 ALTER TABLE `t_element` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_element_history`
--

DROP TABLE IF EXISTS `t_element_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_element_history` (
  `id` bigint(20) NOT NULL,
  `runtime_id` varchar(60) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `height` int(11) NOT NULL COMMENT '高',
  `width` int(11) NOT NULL COMMENT '宽',
  `positionx` double NOT NULL COMMENT 'x坐标',
  `positiony` double NOT NULL COMMENT 'y坐标',
  `ml_flow_id` bigint(20) NOT NULL,
  `element_id` bigint(20) NOT NULL,
  `element_tpl_id` bigint(20) NOT NULL,
  `uuid` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_element_history`
--

LOCK TABLES `t_element_history` WRITE;
/*!40000 ALTER TABLE `t_element_history` DISABLE KEYS */;
INSERT INTO `t_element_history` VALUES (22708224,'3bdcbf26-756c-43c3-917a-f18f06011662','2016-04-19 15:54:15','2016-04-19 15:54:15',0,45,90,130,130,22675456,0,1,'d72d7fb2-88bf-41c0-8282-95510667d753'),(22708225,'1359143d-609a-45e2-a28a-3c7c0ad8f39d','2016-04-19 15:54:15','2016-04-19 15:54:15',0,45,90,130,260,22675456,0,5,'fd8466d2-f729-43b2-9d58-167f9ed1dfb9'),(22708226,'a12448ab-f084-4dcd-86f1-e350732c93e4','2016-04-19 15:54:15','2016-04-19 15:54:15',0,45,90,130,410,22675456,0,22216704,'3a75e1ff-8d90-40a9-902c-1ed5397d00cd'),(22708227,'8f70c0e3-da2d-4fc4-8ed2-3b50feca78f8','2016-04-19 15:54:15','2016-04-19 15:54:15',0,45,90,130,540,22675456,0,5,'d64502c0-9240-4236-b314-a8f2aba59ce7'),(22839296,'099758e5-25ef-4807-942e-0e0dd31a4171','2016-04-20 15:15:53','2016-04-20 15:15:53',0,45,90,130,130,22806528,0,1,'d72d7fb2-88bf-41c0-8282-95510667d753'),(22839297,'5b13f20f-784d-4f6e-a818-99cbf7296b7f','2016-04-20 15:16:05','2016-04-20 15:16:05',0,45,90,130,130,22806529,0,1,'d72d7fb2-88bf-41c0-8282-95510667d753'),(22904832,'a75e3be1-e71f-457b-b760-08aa632bbd47','2016-04-20 15:49:24','2016-04-20 15:49:24',0,45,90,130,130,22872064,0,1,'d72d7fb2-88bf-41c0-8282-95510667d753'),(22904833,'45437313-1697-48ec-8071-a63abbdae3ca','2016-04-20 15:49:24','2016-04-20 15:49:24',0,45,90,130,260,22872064,0,5,'fd8466d2-f729-43b2-9d58-167f9ed1dfb9'),(22904834,'3a5e74ec-3ca0-4908-85f0-4fbd23230634','2016-04-20 15:49:24','2016-04-20 15:49:24',0,45,90,130,410,22872064,0,22216704,'3a75e1ff-8d90-40a9-902c-1ed5397d00cd'),(22904835,'fb200db2-c65b-4902-9e07-a97de3a11c3b','2016-04-20 15:49:24','2016-04-20 15:49:24',0,45,90,130,540,22872064,0,5,'d64502c0-9240-4236-b314-a8f2aba59ce7'),(23003136,'c95a991c-ef49-4662-aee1-faceff7285bd','2016-04-20 15:58:13','2016-04-20 15:58:13',0,45,90,130,130,22970368,0,1,'d72d7fb2-88bf-41c0-8282-95510667d753');
/*!40000 ALTER TABLE `t_element_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_element_param`
--

DROP TABLE IF EXISTS `t_element_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_element_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `element_id` bigint(20) NOT NULL,
  `value` text NOT NULL,
  `element_tpl_param_id` bigint(20) NOT NULL,
  `ml_flow_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_element_param`
--

LOCK TABLES `t_element_param` WRITE;
/*!40000 ALTER TABLE `t_element_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_element_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_element_param_history`
--

DROP TABLE IF EXISTS `t_element_param_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_element_param_history` (
  `id` bigint(20) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `element_id` bigint(20) NOT NULL,
  `value` text NOT NULL,
  `element_tpl_param_id` bigint(20) NOT NULL,
  `ml_flow_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_element_param_history`
--

LOCK TABLES `t_element_param_history` WRITE;
/*!40000 ALTER TABLE `t_element_param_history` DISABLE KEYS */;
INSERT INTO `t_element_param_history` VALUES (22740992,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708224,'atom-mllib_2.10-0.10.jar',2031618,22675456),(22740993,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708224,'com.ctrip.atom.ml.toolimpl.Metadata',2031619,22675456),(22740994,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708224,'',2031620,22675456),(22740995,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708224,'',2031621,22675456),(22740996,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708224,'',2031622,22675456),(22740997,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708224,'',2031623,22675456),(22740998,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708224,'',2031624,22675456),(22740999,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708224,'',2031625,22675456),(22741000,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708224,'',2031626,22675456),(22741001,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708224,'20',2031627,22675456),(22741002,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708224,'4g',2031628,22675456),(22741003,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708224,'4',2031629,22675456),(22741004,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708224,'4g',2031630,22675456),(22741005,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708224,'yarn-client',2031679,22675456),(22741006,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708225,'20',2031658,22675456),(22741007,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708225,'4g',2031659,22675456),(22741008,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708225,'4',2031660,22675456),(22741009,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708225,'4g',2031661,22675456),(22741010,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708225,'',2031662,22675456),(22741011,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708225,'',2031663,22675456),(22741012,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708225,'',2031664,22675456),(22741013,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708225,'',2031665,22675456),(22741014,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708225,'',2031666,22675456),(22741015,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708225,'yarn-client',2031683,22675456),(22741016,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708226,'val textFile = sc.textFile(\"README.md\");\ntextFile.count()',22249472,22675456),(22741017,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708227,'20',2031658,22675456),(22741018,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708227,'4g',2031659,22675456),(22741019,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708227,'4',2031660,22675456),(22741020,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708227,'4g',2031661,22675456),(22741021,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708227,'',2031662,22675456),(22741022,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708227,'',2031663,22675456),(22741023,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708227,'',2031664,22675456),(22741024,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708227,'',2031665,22675456),(22741025,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708227,'',2031666,22675456),(22741026,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,22708227,'yarn-client',2031683,22675456);
/*!40000 ALTER TABLE `t_element_param_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_element_tpl`
--

DROP TABLE IF EXISTS `t_element_tpl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_element_tpl` (
  `id` bigint(20) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `type` varchar(100) NOT NULL COMMENT 'js 组件type',
  `text` varchar(100) NOT NULL COMMENT 'label名称',
  `height` int(11) NOT NULL COMMENT '高',
  `width` int(11) NOT NULL COMMENT '宽',
  `group_id` bigint(20) NOT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_element_tpl`
--

LOCK TABLES `t_element_tpl` WRITE;
/*!40000 ALTER TABLE `t_element_tpl` DISABLE KEYS */;
INSERT INTO `t_element_tpl` VALUES (1,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'devs.Atomic','metadata',20,20,1966081,NULL),(2,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'devs.Atomic','beforedummy',20,20,1966081,NULL),(3,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'devs.Atomic','dummy',20,20,1966081,NULL),(4,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'devs.Atomic','eda',20,20,1966081,NULL),(5,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'devs.Atomic','regression',20,20,1966081,NULL),(21102593,'2016-04-18 19:29:15','2016-04-18 19:29:15',0,'basic.Circle','新建组件sfs',20,20,2785280,884736),(22216704,'2016-04-19 15:20:20','2016-04-19 15:20:20',0,'basic.Circle','新建组件',20,20,2785280,884737);
/*!40000 ALTER TABLE `t_element_tpl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_element_tpl_group`
--

DROP TABLE IF EXISTS `t_element_tpl_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_element_tpl_group` (
  `id` bigint(20) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT 'key',
  `value` varchar(100) NOT NULL COMMENT 'label名称',
  `closed` tinyint(1) NOT NULL DEFAULT '0',
  `sort` int(11) NOT NULL COMMENT '顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_element_tpl_group`
--

LOCK TABLES `t_element_tpl_group` WRITE;
/*!40000 ALTER TABLE `t_element_tpl_group` DISABLE KEYS */;
INSERT INTO `t_element_tpl_group` VALUES (1966080,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'base','基础工具',0,1),(1966081,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'feature','特征工具',1,2),(2785280,'2016-02-24 10:16:18','2016-02-24 10:16:18',0,'custom','自定义',1,3);
/*!40000 ALTER TABLE `t_element_tpl_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_element_tpl_param`
--

DROP TABLE IF EXISTS `t_element_tpl_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_element_tpl_param` (
  `id` bigint(20) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `element_tpl_id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `default_value` text NOT NULL,
  `value_type` varchar(100) NOT NULL COMMENT '枚举类型[String,Long,Double]',
  `input_type` varchar(100) NOT NULL COMMENT '枚举类型[text,range,color,select,textarea]',
  `group_id` bigint(20) NOT NULL COMMENT '枚举[module,in,out,spark]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_element_tpl_param`
--

LOCK TABLES `t_element_tpl_param` WRITE;
/*!40000 ALTER TABLE `t_element_tpl_param` DISABLE KEYS */;
INSERT INTO `t_element_tpl_param` VALUES (2031618,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1,'jar','atom-mllib_2.10-0.10.jar','STRING','text',1933312),(2031619,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1,'mainClass','com.ctrip.atom.ml.toolimpl.Metadata','STRING','text',1933312),(2031620,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1,'table_name','','STRING','text',1933313),(2031621,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1,'percentile_columns','','STRING','text',1933313),(2031622,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1,'percentile_items','','STRING','text',1933313),(2031623,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1,'common_columns','','STRING','text',1933313),(2031624,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1,'groups','','STRING','text',1933313),(2031625,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1,'outPath','','STRING','text',1933313),(2031626,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1,'out_db','','STRING','text',1933313),(2031627,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1,'num-executors','20','STRING','text',1933314),(2031628,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1,'executor-memory','4g','STRING','text',1933314),(2031629,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1,'executor-cores','4','STRING','text',1933314),(2031630,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1,'driver-memory','4g','STRING','text',1933314),(2031631,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,3,'dummy_originTable','','STRING','text',1933313),(2031632,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,3,'dummy_metadata_table','','STRING','text',1933313),(2031633,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,3,'outdb','','STRING','text',1933313),(2031634,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,3,'dummy_rateThreshold','','STRING','text',1933313),(2031635,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,3,'dummy_columns','','STRING','text',1933313),(2031636,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,3,'num-executors','20','STRING','text',1933314),(2031637,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,3,'executor-memory','4g','STRING','text',1933314),(2031638,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,3,'executor-cores','4','STRING','text',1933314),(2031639,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,3,'driver-memory','4g','STRING','text',1933314),(2031640,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'conOrCat','','STRING','text',1933313),(2031641,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'eda_table_db','','STRING','text',1933313),(2031642,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'eda_table_name','','STRING','text',1933313),(2031643,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'config_table','','STRING','text',1933313),(2031644,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'eda_transform','','STRING','text',1933313),(2031645,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'meta_db','','STRING','text',1933313),(2031646,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'meta_table','','STRING','text',1933313),(2031647,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'vtype_column','','STRING','text',1933313),(2031648,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'vflat_columns','','STRING','text',1933313),(2031649,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'eda_dv','','STRING','text',1933313),(2031650,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'eda_iv','','STRING','text',1933313),(2031651,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'eda_outPath','','STRING','text',1933313),(2031652,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'eda_model_path','','STRING','text',1933313),(2031653,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'eda_numIterations','','STRING','text',1933313),(2031654,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'num-executors','20','STRING','text',1933314),(2031655,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'executor-memory','4g','STRING','text',1933314),(2031656,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'executor-cores','4','STRING','text',1933314),(2031657,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'driver-memory','4g','STRING','text',1933314),(2031658,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,5,'num-executors','20','STRING','text',1933314),(2031659,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,5,'executor-memory','4g','STRING','text',1933314),(2031660,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,5,'executor-cores','4','STRING','text',1933314),(2031661,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,5,'driver-memory','4g','STRING','text',1933314),(2031662,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,5,'regression_conOrCat','','STRING','text',1933313),(2031663,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,5,'regression_tablename','','STRING','text',1933313),(2031664,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,5,'regression_metatable','','STRING','text',1933313),(2031665,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,5,'edaout_file','','STRING','text',1933313),(2031666,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,5,'regression_outpath','','STRING','text',1933313),(2031667,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,2,'num-executors','20','STRING','text',1933314),(2031668,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,2,'executor-memory','4g','STRING','text',1933314),(2031669,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,2,'executor-cores','4','STRING','text',1933314),(2031670,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,2,'driver-memory','4g','STRING','text',1933314),(2031671,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,2,'metaType','','STRING','text',1933313),(2031672,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,2,'beforeDummy_tablename','','STRING','text',1933313),(2031673,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,2,'beforeDummy_metatable','','STRING','text',1933313),(2031674,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,2,'beforeDummy_outdb','','STRING','text',1933313),(2031675,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,2,'beforeDummy_missingRate','','STRING','text',1933313),(2031676,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,2,'beforeDummy_minlevel','','STRING','text',1933313),(2031677,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,2,'jar','atom-mllib_2.10-0.10.jar','STRING','text',1933312),(2031678,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,2,'mainClass','com.ctrip.atom.ml.toolimpl.BeforeDummyImp','STRING','text',1933312),(2031679,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1,'master','yarn-client','STRING','text',1933314),(2031680,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,2,'master','yarn-client','STRING','text',1933314),(2031681,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,3,'master','yarn-client','STRING','text',1933314),(2031682,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,4,'master','yarn-client','STRING','text',1933314),(2031683,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,5,'master','yarn-client','STRING','text',1933314),(2031684,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,3,'percentile_items','','STRING','text',1933313),(2031685,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,3,'percentile_columns','','STRING','text',1933313),(22249472,'2016-04-19 15:20:20','2016-04-19 15:20:20',0,22216704,'code','val textFile = sc.textFile(\"README.md\");\ntextFile.count()','STRING','textarea',1933315);
/*!40000 ALTER TABLE `t_element_tpl_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_element_tpl_param_group`
--

DROP TABLE IF EXISTS `t_element_tpl_param_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_element_tpl_param_group` (
  `id` bigint(20) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT 'key',
  `value` varchar(100) NOT NULL COMMENT 'label名称',
  `closed` tinyint(1) NOT NULL DEFAULT '0',
  `sort` int(11) NOT NULL COMMENT '顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_element_tpl_param_group`
--

LOCK TABLES `t_element_tpl_param_group` WRITE;
/*!40000 ALTER TABLE `t_element_tpl_param_group` DISABLE KEYS */;
INSERT INTO `t_element_tpl_param_group` VALUES (1933312,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'module','模块参数',0,1),(1933313,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'inout','输入输出参数',0,2),(1933314,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'spark','spark参数',0,3),(1933315,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'other','其他参数',0,4);
/*!40000 ALTER TABLE `t_element_tpl_param_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_link`
--

DROP TABLE IF EXISTS `t_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_link` (
  `id` bigint(20) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `uuid` varchar(100) NOT NULL,
  `source_id` bigint(20) NOT NULL COMMENT '输入id',
  `source_uuid` varchar(100) NOT NULL COMMENT '输入uuid',
  `source_port` varchar(100) DEFAULT NULL COMMENT '输入端口',
  `target_uuid` varchar(100) NOT NULL COMMENT '输出uuid',
  `target_id` bigint(20) NOT NULL COMMENT '输出id',
  `target_port` varchar(100) DEFAULT NULL COMMENT '输出端口',
  `type` varchar(100) NOT NULL COMMENT '连线类型',
  `ml_flow_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_link`
--

LOCK TABLES `t_link` WRITE;
/*!40000 ALTER TABLE `t_link` DISABLE KEYS */;
INSERT INTO `t_link` VALUES (22380544,'2016-04-19 15:40:39','2016-04-19 15:40:39',0,'1db58b5d-078d-49e0-9e16-e550680c9680',22315008,'d72d7fb2-88bf-41c0-8282-95510667d753',NULL,'fd8466d2-f729-43b2-9d58-167f9ed1dfb9',22315009,NULL,'link',22282240),(22380545,'2016-04-19 15:40:39','2016-04-19 15:40:39',0,'641a59f1-3735-4da1-bd8e-a4e4ae719313',22315009,'fd8466d2-f729-43b2-9d58-167f9ed1dfb9',NULL,'3a75e1ff-8d90-40a9-902c-1ed5397d00cd',22315010,NULL,'link',22282240),(22380546,'2016-04-19 15:42:09','2016-04-19 15:42:09',0,'8e098767-e77d-4b18-a8e1-7801bd737cd8',22315010,'3a75e1ff-8d90-40a9-902c-1ed5397d00cd',NULL,'d64502c0-9240-4236-b314-a8f2aba59ce7',22315011,NULL,'link',22282240);
/*!40000 ALTER TABLE `t_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_link_history`
--

DROP TABLE IF EXISTS `t_link_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_link_history` (
  `id` bigint(20) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `uuid` varchar(200) DEFAULT NULL,
  `source_id` bigint(20) NOT NULL COMMENT '输入id',
  `source_uuid` varchar(100) NOT NULL COMMENT '输入uuid',
  `source_port` varchar(100) DEFAULT NULL COMMENT '输入端口',
  `target_uuid` varchar(100) NOT NULL COMMENT '输出uuid',
  `target_id` bigint(20) NOT NULL COMMENT '输出id',
  `target_port` varchar(100) DEFAULT NULL COMMENT '输出端口',
  `type` varchar(100) NOT NULL COMMENT '连线类型',
  `ml_flow_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_link_history`
--

LOCK TABLES `t_link_history` WRITE;
/*!40000 ALTER TABLE `t_link_history` DISABLE KEYS */;
INSERT INTO `t_link_history` VALUES (22773760,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,'1db58b5d-078d-49e0-9e16-e550680c9680',22708224,'d72d7fb2-88bf-41c0-8282-95510667d753',NULL,'fd8466d2-f729-43b2-9d58-167f9ed1dfb9',22708225,NULL,'link',22675456),(22773761,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,'641a59f1-3735-4da1-bd8e-a4e4ae719313',22708225,'fd8466d2-f729-43b2-9d58-167f9ed1dfb9',NULL,'3a75e1ff-8d90-40a9-902c-1ed5397d00cd',22708226,NULL,'link',22675456),(22773762,'2016-04-19 15:54:15','2016-04-19 15:54:15',0,'8e098767-e77d-4b18-a8e1-7801bd737cd8',22708226,'3a75e1ff-8d90-40a9-902c-1ed5397d00cd',NULL,'d64502c0-9240-4236-b314-a8f2aba59ce7',22708227,NULL,'link',22675456),(22937600,'2016-04-20 15:49:24','2016-04-20 15:49:24',0,'1db58b5d-078d-49e0-9e16-e550680c9680',22904832,'d72d7fb2-88bf-41c0-8282-95510667d753',NULL,'fd8466d2-f729-43b2-9d58-167f9ed1dfb9',22904833,NULL,'link',22872064),(22937601,'2016-04-20 15:49:24','2016-04-20 15:49:24',0,'641a59f1-3735-4da1-bd8e-a4e4ae719313',22904833,'fd8466d2-f729-43b2-9d58-167f9ed1dfb9',NULL,'3a75e1ff-8d90-40a9-902c-1ed5397d00cd',22904834,NULL,'link',22872064),(22937602,'2016-04-20 15:49:24','2016-04-20 15:49:24',0,'8e098767-e77d-4b18-a8e1-7801bd737cd8',22904834,'3a75e1ff-8d90-40a9-902c-1ed5397d00cd',NULL,'d64502c0-9240-4236-b314-a8f2aba59ce7',22904835,NULL,'link',22872064);
/*!40000 ALTER TABLE `t_link_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_ml_flow`
--

DROP TABLE IF EXISTS `t_ml_flow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_ml_flow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `owner_id` bigint(20) NOT NULL COMMENT 'user id',
  `name` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL COMMENT '状态 枚举[stop,running,finished]',
  `cronexpression` varchar(60) DEFAULT NULL,
  `latest_runtime_id` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22282241 DEFAULT CHARSET=utf8 COMMENT='算法流程';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_ml_flow`
--

LOCK TABLES `t_ml_flow` WRITE;
/*!40000 ALTER TABLE `t_ml_flow` DISABLE KEYS */;
INSERT INTO `t_ml_flow` VALUES (22282240,'2016-04-19 15:22:00','2016-04-20 15:58:13',5,884737,'tet','STOP','','c0933cd6-8a79-4815-b859-d5b596cc7286');
/*!40000 ALTER TABLE `t_ml_flow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_ml_flow_history`
--

DROP TABLE IF EXISTS `t_ml_flow_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_ml_flow_history` (
  `id` bigint(20) NOT NULL,
  `runtime_id` varchar(60) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `owner_id` bigint(20) NOT NULL COMMENT 'user id',
  `name` varchar(100) NOT NULL,
  `ml_flow_id` bigint(20) NOT NULL,
  `cronexpression` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_ml_flow_history`
--

LOCK TABLES `t_ml_flow_history` WRITE;
/*!40000 ALTER TABLE `t_ml_flow_history` DISABLE KEYS */;
INSERT INTO `t_ml_flow_history` VALUES (22675456,'9ea1378f-36d4-4107-91a2-1dfcd2c879f4','2016-04-19 15:54:15','2016-04-19 15:54:15',1,884737,'tet',22282240,''),(22806528,'c04c7ede-1b67-4a15-85a8-0774446812d9','2016-04-20 15:15:53','2016-04-20 15:15:53',1,884737,'tet',22282240,''),(22806529,'8c2173c7-7eb0-4385-bff6-79ebb3a994bd','2016-04-20 15:16:05','2016-04-20 15:16:05',1,884737,'tet',22282240,''),(22872064,'863e0649-d709-49b3-aac0-1e792487eb3c','2016-04-20 15:49:24','2016-04-20 15:49:24',3,884737,'tet',22282240,''),(22970368,'c0933cd6-8a79-4815-b859-d5b596cc7286','2016-04-20 15:58:13','2016-04-20 15:58:13',4,884737,'tet',22282240,'');
/*!40000 ALTER TABLE `t_ml_flow_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_module_history`
--

DROP TABLE IF EXISTS `t_module_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_module_history` (
  `uuid` int(11) NOT NULL,
  `id` varchar(100) NOT NULL,
  `schedule_times` int(11) NOT NULL,
  `flow_uuid` varchar(100) NOT NULL COMMENT '如果为00000则是单独运行并非完整的flow',
  `owner` varchar(100) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` varchar(20) NOT NULL COMMENT '0:success,1:failed,2:running',
  `log_dir` varchar(300) DEFAULT NULL,
  `worker` varchar(45) DEFAULT NULL,
  `spark_args` varchar(500) DEFAULT NULL,
  `params` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`uuid`,`schedule_times`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_module_history`
--

LOCK TABLES `t_module_history` WRITE;
/*!40000 ALTER TABLE `t_module_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_module_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=884738 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (884736,'2016-02-05 10:22:39','2016-02-05 10:22:39',0,'bi_ttd_bigdata','123456'),(884737,'2016-02-05 10:22:39','2016-02-05 10:22:39',0,'test','1234567');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_zeppelin_paragraph`
--

DROP TABLE IF EXISTS `t_zeppelin_paragraph`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_zeppelin_paragraph` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_date` datetime NOT NULL,
  `last_modified` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `note_id` varchar(100) NOT NULL COMMENT 'zeppelin notebook id',
  `note_name` varchar(100) NOT NULL COMMENT 'zeppelin notebook name',
  `job_name` varchar(100) NOT NULL COMMENT 'zeppelin notebook job name',
  `element_id` bigint(20) DEFAULT NULL,
  `uuid` varchar(100) NOT NULL,
  `code` varchar(100) DEFAULT NULL,
  `msg` text,
  `type` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `text` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_zeppelin_paragraph`
--

LOCK TABLES `t_zeppelin_paragraph` WRITE;
/*!40000 ALTER TABLE `t_zeppelin_paragraph` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_zeppelin_paragraph` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-21 16:34:40

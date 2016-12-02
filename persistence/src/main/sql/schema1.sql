DROP DATABASE test;
CREATE DATABASE test;
USE test;
-- MySQL dump 10.13  Distrib 5.6.23, for osx10.8 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.6.23

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
INSERT INTO `hibernate_id_generation` VALUES (98);
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
  `text` varchar(100) NOT NULL COMMENT 'label名称',
  `height` int(11) NOT NULL COMMENT '高',
  `width` int(11) NOT NULL COMMENT '宽',
  `positionx` double NOT NULL COMMENT 'x坐标',
  `positiony` double NOT NULL COMMENT 'y坐标',
  `comments` varchar(300) DEFAULT NULL,
  `ml_flow_id` bigint(20) NOT NULL,
  `element_tpl_id` bigint(20) NOT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `t_element_t_element_tpl_id_fk` (`element_tpl_id`),
  KEY `t_element_t_ml_flow_id_fk` (`ml_flow_id`),
  CONSTRAINT `t_element_t_element_tpl_id_fk` FOREIGN KEY (`element_tpl_id`) REFERENCES `t_element_tpl` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_element_t_ml_flow_id_fk` FOREIGN KEY (`ml_flow_id`) REFERENCES `t_ml_flow` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3014668 DEFAULT CHARSET=utf8 COMMENT='GUI 拖拽的元素';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_element`
--

LOCK TABLES `t_element` WRITE;
/*!40000 ALTER TABLE `t_element` DISABLE KEYS */;
INSERT INTO `t_element` VALUES (2686984,'2016-02-17 16:02:20','2016-02-17 16:02:20',0,'298e0d4f-ef60-47bb-bf2d-0287c1440805','test',60,60,330,530,NULL,2654209,1998849,NULL),(2686985,'2016-02-17 16:02:20','2016-02-17 16:03:33',1,'f352f7a5-c060-42cc-aa28-d9d1a14cb0eb','test',60,60,180,260,NULL,2654209,1998848,NULL),(2686986,'2016-02-17 16:02:20','2016-02-17 16:03:33',1,'61d69d67-4476-4e97-9144-e83371001a58','test',45,90,270,390,NULL,2654209,1998851,NULL),(2686991,'2016-02-17 16:08:37','2016-02-17 16:08:37',0,'5dced935-6cf7-49cd-97a1-a5dac3a7b789','test',60,60,230,310,NULL,2654210,1998849,NULL),(2686992,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,'c0c31e89-6df5-432a-9ee0-3f30b1b4ed42','test',60,60,170,230,NULL,2654211,1998848,NULL),(2686993,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,'cb8bf057-42d2-4d7c-8228-2dc0d286e845','test',60,60,200,390,NULL,2654211,1998849,NULL),(2686994,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,'40f335a2-f689-442d-a0cd-92a5340584b0','test',45,90,310,340,NULL,2654211,1998851,NULL),(3014659,'2016-02-25 14:46:33','2016-02-25 16:30:10',3,'67a9e9c5-da6f-489d-9a18-0dddfc6e0b54','test',60,60,15,0,NULL,2523136,1998848,'STOP'),(3014660,'2016-02-25 14:46:33','2016-02-25 16:25:03',1,'5fa4af3c-057d-488d-9661-d92ebea88ebd','test',45,90,0,300,NULL,2523136,1998851,'STOP'),(3014661,'2016-02-25 14:46:33','2016-02-25 16:30:10',3,'08cc6184-bcfb-4d84-988d-007ed73e305f','test',60,60,15,395,NULL,2523136,1998849,'STOP'),(3014665,'2016-02-25 15:15:07','2016-02-25 16:28:01',79,'20e53d87-11fe-4e43-baba-b40140996033','test',45,90,0,110,NULL,2523136,2850817,'FINISHED'),(3014667,'2016-02-25 16:29:40','2016-02-25 16:30:10',3,'e983a0ab-5133-4238-b5c1-376dc3652c7a','test',45,90,0,205,NULL,2523136,3145728,'FINISHED');
/*!40000 ALTER TABLE `t_element` ENABLE KEYS */;
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
  `comments` varchar(300) DEFAULT NULL,
  `element_tpl_param_id` bigint(20) NOT NULL,
  `ml_flow_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `t_element_param_t_element_tpl_param_id_fk` (`element_tpl_param_id`),
  KEY `t_element_param_t_element_id_fk` (`element_id`),
  KEY `t_element_param_t_ml_flow_id_fk` (`ml_flow_id`),
  CONSTRAINT `t_element_param_t_element_id_fk` FOREIGN KEY (`element_id`) REFERENCES `t_element` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_element_param_t_element_tpl_param_id_fk` FOREIGN KEY (`element_tpl_param_id`) REFERENCES `t_element_tpl_param` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_element_param_t_ml_flow_id_fk` FOREIGN KEY (`ml_flow_id`) REFERENCES `t_ml_flow` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3047460 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_element_param`
--

LOCK TABLES `t_element_param` WRITE;
/*!40000 ALTER TABLE `t_element_param` DISABLE KEYS */;
INSERT INTO `t_element_param` VALUES (2719800,'2016-02-17 16:02:20','2016-02-17 16:02:20',0,2686984,'无',NULL,2031617,2654209),(2719801,'2016-02-17 16:02:20','2016-02-17 16:02:20',0,2686985,'无',NULL,2031616,2654209),(2719802,'2016-02-17 16:02:20','2016-02-17 16:02:20',0,2686986,'v1',NULL,2031627,2654209),(2719803,'2016-02-17 16:02:20','2016-02-17 16:02:20',0,2686986,'v2',NULL,2031628,2654209),(2719804,'2016-02-17 16:02:20','2016-02-17 16:02:20',0,2686986,'v3',NULL,2031629,2654209),(2719805,'2016-02-17 16:02:20','2016-02-17 16:02:20',0,2686986,'v1',NULL,2031630,2654209),(2719806,'2016-02-17 16:02:20','2016-02-17 16:02:20',0,2686986,'v2',NULL,2031631,2654209),(2719807,'2016-02-17 16:02:20','2016-02-17 16:02:20',0,2686986,'v3',NULL,2031632,2654209),(2719808,'2016-02-17 16:02:20','2016-02-17 16:02:20',0,2686986,'v1',NULL,2031633,2654209),(2719809,'2016-02-17 16:02:20','2016-02-17 16:02:20',0,2686986,'v2',NULL,2031634,2654209),(2719810,'2016-02-17 16:02:20','2016-02-17 16:02:20',0,2686986,'v3',NULL,2031635,2654209),(2719831,'2016-02-17 16:08:37','2016-02-17 16:08:37',0,2686991,'无',NULL,2031617,2654210),(2719832,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,2686992,'无',NULL,2031616,2654211),(2719833,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,2686993,'无',NULL,2031617,2654211),(2719834,'2016-02-17 16:12:01','2016-02-17 16:12:05',1,2686994,'v13',NULL,2031627,2654211),(2719835,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,2686994,'v2',NULL,2031628,2654211),(2719836,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,2686994,'v3',NULL,2031629,2654211),(2719837,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,2686994,'v1',NULL,2031630,2654211),(2719838,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,2686994,'v2',NULL,2031631,2654211),(2719839,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,2686994,'v3',NULL,2031632,2654211),(2719840,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,2686994,'v1',NULL,2031633,2654211),(2719841,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,2686994,'v2',NULL,2031634,2654211),(2719842,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,2686994,'v3',NULL,2031635,2654211),(3047443,'2016-02-25 14:46:33','2016-02-25 14:46:33',0,3014659,'无',NULL,2031616,2523136),(3047444,'2016-02-25 14:46:33','2016-02-25 14:46:33',0,3014660,'v1',NULL,2031627,2523136),(3047445,'2016-02-25 14:46:33','2016-02-25 14:46:33',0,3014660,'v2',NULL,2031628,2523136),(3047446,'2016-02-25 14:46:33','2016-02-25 14:46:33',0,3014660,'v3',NULL,2031629,2523136),(3047447,'2016-02-25 14:46:33','2016-02-25 14:46:33',0,3014660,'v1',NULL,2031630,2523136),(3047448,'2016-02-25 14:46:33','2016-02-25 14:46:33',0,3014660,'v2',NULL,2031631,2523136),(3047449,'2016-02-25 14:46:33','2016-02-25 14:46:33',0,3014660,'v3',NULL,2031632,2523136),(3047450,'2016-02-25 14:46:33','2016-02-25 14:46:33',0,3014660,'v1',NULL,2031633,2523136),(3047451,'2016-02-25 14:46:33','2016-02-25 14:46:33',0,3014660,'v2',NULL,2031634,2523136),(3047452,'2016-02-25 14:46:33','2016-02-25 14:46:33',0,3014660,'v3',NULL,2031635,2523136),(3047453,'2016-02-25 14:46:33','2016-02-25 14:46:33',0,3014661,'无',NULL,2031617,2523136),(3047457,'2016-02-25 15:15:07','2016-02-25 16:27:55',9,3014665,'val textFile = sc.textFile(\"README.md\")\nThread.sleep(3000)\nval wordCounts = textFile.flatMap(line => line.split(\" \")).map(word => (word, 1)).reduceByKey((a, b) => a + b)\ntextFile.count()\nwordCounts.collect()\nwordCounts.collect()',NULL,2883585,2523136),(3047459,'2016-02-25 16:29:40','2016-02-25 16:29:40',0,3014667,'val logData = sc.textFile(\"README.md\", 2).cache()\n    val numAs = logData.filter(line => line.contains(\"a\")).count()\n    val numBs = logData.filter(line => line.contains(\"b\")).count()\n    println(\"Lines with a: %s, Lines with b: %s\".format(numAs, numBs))',NULL,3178496,2523136);
/*!40000 ALTER TABLE `t_element_param` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `t_element_param_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_element_param_history` (

  `id` bigint(20) NOT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;




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
  `in_ports` varchar(100) NOT NULL COMMENT '输入连接点名称,数组","分隔',
  `out_ports` varchar(100) NOT NULL COMMENT '输出连接点名称,数组","分隔',
  `comments` varchar(300) DEFAULT NULL,
  `is_start` tinyint(1) NOT NULL DEFAULT '0',
  `is_end` tinyint(1) NOT NULL DEFAULT '0',
  `group_id` bigint(20) NOT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `t_element_tpl_t_element_tpl_group_id_fk` (`group_id`),
  KEY `t_element_tpl_t_user_id_fk` (`owner_id`),
  CONSTRAINT `t_element_tpl_t_element_tpl_group_id_fk` FOREIGN KEY (`group_id`) REFERENCES `t_element_tpl_group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_element_tpl_t_user_id_fk` FOREIGN KEY (`owner_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_element_tpl`
--

LOCK TABLES `t_element_tpl` WRITE;
/*!40000 ALTER TABLE `t_element_tpl` DISABLE KEYS */;
INSERT INTO `t_element_tpl` VALUES (1998848,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'devs.StartState','开始',20,20,'','','',1,0,1966080,NULL),(1998849,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'devs.EndState','结束',20,20,'','','',0,1,1966080,NULL),(1998850,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'devs.Atomic','数据抽样',20,20,'in1','out1,out2','',0,0,1966081,NULL),(1998851,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'devs.Atomic','Meta数据计算',20,20,'in1','out1','',0,0,1966081,NULL),(1998852,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'devs.Atomic','特征选取',20,20,'in1,in2','out1','',0,0,1966081,NULL),(1998853,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'devs.Atomic','特征规范化',20,20,'in1','out1','',0,0,1966081,NULL),(2850817,'2016-02-24 14:10:47','2016-02-24 14:10:47',0,'devs.Atomic','新建组件',20,20,'in1','out1',NULL,0,0,2785280,884736),(3145728,'2016-02-25 16:29:22','2016-02-25 16:29:22',0,'devs.Atomic','test1',20,20,'in1','out1',NULL,0,0,2785280,884736);
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
INSERT INTO `t_element_tpl_group` VALUES (1966080,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'base','基础工具',0,1),(1966081,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,'feature','特征工具',1,2),(2785280,'2016-02-24 10:16:18','2016-02-24 10:16:18',0,'custom','自定义',1,5);
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
  `comments` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `t_element_param_t_element_tpl_id_fk` (`element_tpl_id`),
  KEY `t_element_tpl_param_t_element_tpl_param_group_id_fk` (`group_id`),
  CONSTRAINT `t_element_param_t_element_tpl_id_fk` FOREIGN KEY (`element_tpl_id`) REFERENCES `t_element_tpl` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_element_tpl_param_t_element_tpl_param_group_id_fk` FOREIGN KEY (`group_id`) REFERENCES `t_element_tpl_param_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_element_tpl_param`
--

LOCK TABLES `t_element_tpl_param` WRITE;
/*!40000 ALTER TABLE `t_element_tpl_param` DISABLE KEYS */;
INSERT INTO `t_element_tpl_param` VALUES (2031616,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998848,'备注','无','STRING','textarea',1933315,''),(2031617,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998849,'备注','无','STRING','textarea',1933315,''),(2031618,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998850,'k1','v1','STRING','text',1933312,'参数1'),(2031619,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998850,'k2','v2','STRING','text',1933312,'参数2'),(2031620,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998850,'k3','v3','STRING','text',1933312,'参数3'),(2031621,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998850,'k1','v1','STRING','text',1933313,'参数1'),(2031622,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998850,'k2','v2','STRING','text',1933313,'参数2'),(2031623,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998850,'k3','v3','STRING','text',1933313,'参数3'),(2031624,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998850,'k1','v1','STRING','text',1933314,'参数1'),(2031625,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998850,'k2','v2','STRING','text',1933314,'参数2'),(2031626,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998850,'k3','v3','STRING','text',1933314,'参数3'),(2031627,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998851,'k1','v1','STRING','text',1933312,'参数1'),(2031628,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998851,'k2','v2','STRING','text',1933312,'参数2'),(2031629,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998851,'k3','v3','STRING','text',1933312,'参数3'),(2031630,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998851,'k1','v1','STRING','text',1933313,'参数1'),(2031631,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998851,'k2','v2','STRING','text',1933313,'参数2'),(2031632,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998851,'k3','v3','STRING','text',1933313,'参数3'),(2031633,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998851,'k1','v1','STRING','text',1933314,'参数1'),(2031634,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998851,'k2','v2','STRING','text',1933314,'参数2'),(2031635,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998851,'k3','v3','STRING','text',1933314,'参数3'),(2031636,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998852,'k1','v1','STRING','text',1933312,'参数1'),(2031637,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998852,'k2','v2','STRING','text',1933312,'参数2'),(2031638,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998852,'k3','v3','STRING','text',1933312,'参数3'),(2031639,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998852,'k1','v1','STRING','text',1933313,'参数1'),(2031640,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998852,'k2','v2','STRING','text',1933313,'参数2'),(2031641,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998852,'k3','v3','STRING','text',1933313,'参数3'),(2031642,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998852,'k1','v1','STRING','text',1933314,'参数1'),(2031643,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998852,'k2','v2','STRING','text',1933314,'参数2'),(2031644,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998852,'k3','v3','STRING','text',1933314,'参数3'),(2031645,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998853,'k1','v1','STRING','text',1933312,'参数1'),(2031646,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998853,'k2','v2','STRING','text',1933312,'参数2'),(2031647,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998853,'k3','v3','STRING','text',1933312,'参数3'),(2031648,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998853,'k1','v1','STRING','text',1933313,'参数1'),(2031649,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998853,'k2','v2','STRING','text',1933313,'参数2'),(2031650,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998853,'k3','v3','STRING','text',1933313,'参数3'),(2031651,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998853,'k1','v1','STRING','text',1933314,'参数1'),(2031652,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998853,'k2','v2','STRING','text',1933314,'参数2'),(2031653,'2016-02-16 09:59:27','2016-02-16 09:59:27',0,1998853,'k3','v3','STRING','text',1933314,'参数3'),(2883585,'2016-02-24 14:10:47','2016-02-24 14:10:47',0,2850817,'code','val textFile = sc.textFile(\"README.md\")\ntextFile.count()','STRING','textarea',1933315,''),(3178496,'2016-02-25 16:29:22','2016-02-25 16:29:22',0,3145728,'code','val logData = sc.textFile(\"README.md\", 2).cache()\n    val numAs = logData.filter(line => line.contains(\"a\")).count()\n    val numBs = logData.filter(line => line.contains(\"b\")).count()\n    println(\"Lines with a: %s, Lines with b: %s\".format(numAs, numBs))','STRING','textarea',1933315,'');
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
  `comments` varchar(300) DEFAULT NULL,
  `ml_flow_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `t_link_t_element_source_uuid_fk` (`source_id`),
  KEY `t_link_t_element_target_uuid_fk` (`target_id`),
  KEY `t_link_t_ml_flow_id_fk` (`ml_flow_id`),
  CONSTRAINT `t_link_t_element_source_uuid_fk` FOREIGN KEY (`source_id`) REFERENCES `t_element` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_link_t_element_target_uuid_fk` FOREIGN KEY (`target_id`) REFERENCES `t_element` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_link_t_ml_flow_id_fk` FOREIGN KEY (`ml_flow_id`) REFERENCES `t_ml_flow` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_link`
--

LOCK TABLES `t_link` WRITE;
/*!40000 ALTER TABLE `t_link` DISABLE KEYS */;
INSERT INTO `t_link` VALUES (2752520,'2016-02-17 16:02:20','2016-02-17 16:02:20',0,'a65d8d94-8855-4d8f-b469-3c3e39e14bf5',2686985,'f352f7a5-c060-42cc-aa28-d9d1a14cb0eb',NULL,'61d69d67-4476-4e97-9144-e83371001a58',2686986,'in1','link',NULL,2654209),(2752521,'2016-02-17 16:02:20','2016-02-17 16:02:20',0,'8f21045a-dbc4-431d-9c2f-c63f7dceb05b',2686986,'61d69d67-4476-4e97-9144-e83371001a58','out1','298e0d4f-ef60-47bb-bf2d-0287c1440805',2686984,NULL,'link',NULL,2654209),(2752526,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,'47c816fe-75d7-41a5-a3ab-e0f1d76d2794',2686992,'c0c31e89-6df5-432a-9ee0-3f30b1b4ed42',NULL,'40f335a2-f689-442d-a0cd-92a5340584b0',2686994,'in1','link',NULL,2654211),(2752527,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,'912f6184-02b3-4c96-ba57-5da0c318753b',2686994,'40f335a2-f689-442d-a0cd-92a5340584b0','out1','cb8bf057-42d2-4d7c-8228-2dc0d286e845',2686993,NULL,'link',NULL,2654211),(3080194,'2016-02-25 14:46:33','2016-02-25 16:25:03',1,'3c7e2e06-76fb-4ce0-99b9-76f030da77cb',3014659,'67a9e9c5-da6f-489d-9a18-0dddfc6e0b54',NULL,'20e53d87-11fe-4e43-baba-b40140996033',3014665,'in1','link',NULL,2523136),(3080195,'2016-02-25 14:46:33','2016-02-25 14:46:33',0,'668afea1-230e-4534-9100-4e561e378156',3014660,'5fa4af3c-057d-488d-9661-d92ebea88ebd','out1','08cc6184-bcfb-4d84-988d-007ed73e305f',3014661,NULL,'link',NULL,2523136),(3080198,'2016-02-25 16:29:40','2016-02-25 16:29:40',0,'e4d48b6f-34b0-4068-b127-39ce1e5516fc',3014665,'20e53d87-11fe-4e43-baba-b40140996033','out1','e983a0ab-5133-4238-b5c1-376dc3652c7a',3014667,'in1','link',NULL,2523136),(3080199,'2016-02-25 16:29:40','2016-02-25 16:29:40',0,'20d6ae19-5db8-4a9d-9a75-6d9faa059505',3014667,'e983a0ab-5133-4238-b5c1-376dc3652c7a','out1','5fa4af3c-057d-488d-9661-d92ebea88ebd',3014660,'in1','link',NULL,2523136);
/*!40000 ALTER TABLE `t_link` ENABLE KEYS */;
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
  `comments` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `t_ml_flow_t_user_id_fk` (`owner_id`),
  CONSTRAINT `t_ml_flow_t_user_id_fk` FOREIGN KEY (`owner_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2654212 DEFAULT CHARSET=utf8 COMMENT='算法流程';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_ml_flow`
--

LOCK TABLES `t_ml_flow` WRITE;
/*!40000 ALTER TABLE `t_ml_flow` DISABLE KEYS */;
INSERT INTO `t_ml_flow` VALUES (2523136,'2016-02-17 15:20:43','2016-02-17 16:12:14',11,884736,'333','STOP',NULL),(2654209,'2016-02-17 15:57:37','2016-02-17 16:02:20',1,884736,'123','STOP',NULL),(2654210,'2016-02-17 16:08:37','2016-02-17 16:08:37',0,884736,'4444','STOP',NULL),(2654211,'2016-02-17 16:12:01','2016-02-17 16:12:01',0,884736,'555','STOP',NULL);
/*!40000 ALTER TABLE `t_ml_flow` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=884737 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (884736,'2016-02-05 10:22:39','2016-02-05 10:22:39',0,'test','test');
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
  PRIMARY KEY (`id`),
  KEY `t_zeppelin_paragraph_t_element_id_fk` (`element_id`),
  CONSTRAINT `t_zeppelin_paragraph_t_element_id_fk` FOREIGN KEY (`element_id`) REFERENCES `t_element` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3112966 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_zeppelin_paragraph`
--

LOCK TABLES `t_zeppelin_paragraph` WRITE;
/*!40000 ALTER TABLE `t_zeppelin_paragraph` DISABLE KEYS */;
INSERT INTO `t_zeppelin_paragraph` VALUES (3112962,'2016-02-25 15:03:15','2016-02-25 16:28:01',120,'2BDQV6Z64','ml-tool','paragraph_1456383794729_-2022282717',3014665,'20160225-150314_300944563','SUCCESS','textFile: org.apache.spark.rdd.RDD[String] = MapPartitionsRDD[223] at textFile at <console>:29\nwordCounts: org.apache.spark.rdd.RDD[(String, Int)] = ShuffledRDD[226] at reduceByKey at <console>:31\nres285: Long = 234\nres286: Array[(String, Int)] = Array((Built-in,1), (package,10), (under,1), (-Pbuild-distr,2), (apache-maven-3.3.3-bin.tar.gz,1), (feature:,1), (MAVEN_OPTS=\"-Xmx2g,1), (SPARK_HOME=...,1), (its,1), (have,2), (libfontconfig,1), (well.,1), (-Pspark-1.4,1), (embedded,1), (_zeppelin-distribution/target_,1), (Setting,1), (browse,1), (correct,1), (`yarn`.,1), (export,7), (read,1), (any,1), (make,1), (-Pcassandra-spark-1.3,1), (git,1), (node.,1), (version,10), (file,1), (handle,1), (are,3), (SPARK_HOME=/path/to/spark_dir,1), (specified,1), (Integration:**,1), (selenium,1), (build,7), (--version`,1), (Apache,1), (###Run,1), (our,1), (as,3), (Ensure,2), (browser.,1), (-Pmapr41,1), (analytics.,1), (repository,1), (./conf/zeppelin-env.sh,4), (./conf/zeppelin-site.xml,1), (final,1), (party,1), (-Psp...res287: Array[(String, Int)] = Array((Built-in,1), (package,10), (under,1), (-Pbuild-distr,2), (apache-maven-3.3.3-bin.tar.gz,1), (feature:,1), (MAVEN_OPTS=\"-Xmx2g,1), (SPARK_HOME=...,1), (its,1), (have,2), (libfontconfig,1), (well.,1), (-Pspark-1.4,1), (embedded,1), (_zeppelin-distribution/target_,1), (Setting,1), (browse,1), (correct,1), (`yarn`.,1), (export,7), (read,1), (any,1), (make,1), (-Pcassandra-spark-1.3,1), (git,1), (node.,1), (version,10), (file,1), (handle,1), (are,3), (SPARK_HOME=/path/to/spark_dir,1), (specified,1), (Integration:**,1), (selenium,1), (build,7), (--version`,1), (Apache,1), (###Run,1), (our,1), (as,3), (Ensure,2), (browser.,1), (-Pmapr41,1), (analytics.,1), (repository,1), (./conf/zeppelin-env.sh,4), (./conf/zeppelin-site.xml,1), (final,1), (party,1), (-Psp...','TEXT','FINISHED','val textFile = sc.textFile(\"README.md\")\nThread.sleep(3000)\nval wordCounts = textFile.flatMap(line => line.split(\" \")).map(word => (word, 1)).reduceByKey((a, b) => a + b)\ntextFile.count()\nwordCounts.collect()\nwordCounts.collect()'),(3112964,'2016-02-25 15:47:36','2016-02-25 16:29:45',4,'2BDQV6Z64','ml-tool','paragraph_1456386456218_1159208630',3014667,'20160225-154736_1731367728','SUCCESS','logData: org.apache.spark.rdd.RDD[String] = MapPartitionsRDD[228] at textFile at <console>:29\nnumAs: Long = 113\nnumBs: Long = 50\nLines with a: 113, Lines with b: 50\n','TEXT','FINISHED','val logData = sc.textFile(\"README.md\", 2).cache()\n    val numAs = logData.filter(line => line.contains(\"a\")).count()\n    val numBs = logData.filter(line => line.contains(\"b\")).count()\n    println(\"Lines with a: %s, Lines with b: %s\".format(numAs, numBs))'),(3112965,'2016-02-25 16:29:45','2016-02-25 16:29:45',0,'2BDQV6Z64','ml-tool','paragraph_1456388984963_-1588483601',NULL,'20160225-162944_1019552702',NULL,NULL,NULL,'READY',NULL);
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

-- Dump completed on 2016-02-25 16:36:29

/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.9-rc-log : Database - supermarket
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`supermarket` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `supermarket`;

/*Table structure for table `sm_admin` */

DROP TABLE IF EXISTS `sm_admin`;

CREATE TABLE `sm_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` char(11) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sm_admin` */

insert  into `sm_admin`(`id`,`username`,`password`,`phone`,`enabled`) values (1,'admin','$2a$10$NKVn7qniw852oKK/w9uk0OhflqInJW5K64NosLPaJ7LcCQk5y5m5.','13812361398',1),(2,'taomeng','$2a$10$fJVRoyM2b/RW.zEQD/DSrOT1J4UvhnsAgT/NSODf6uEPBbYp6Ldqy','18875971675',1),(3,'naqiao','$2a$10$fJVRoyM2b/RW.zEQD/DSrOT1J4UvhnsAgT/NSODf6uEPBbYp6Ldqy','14588110811',1),(4,'leisu','$2a$10$fJVRoyM2b/RW.zEQD/DSrOT1J4UvhnsAgT/NSODf6uEPBbYp6Ldqy','15761248727',1),(5,'hanli','$2a$10$fJVRoyM2b/RW.zEQD/DSrOT1J4UvhnsAgT/NSODf6uEPBbYp6Ldqy','18030710396',1);

/*Table structure for table `sm_admin_role` */

DROP TABLE IF EXISTS `sm_admin_role`;

CREATE TABLE `sm_admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `adminid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `adminid` (`adminid`),
  KEY `rid` (`rid`),
  CONSTRAINT `sm_admin_role_ibfk_1` FOREIGN KEY (`adminid`) REFERENCES `sm_admin` (`id`),
  CONSTRAINT `sm_admin_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `sm_role` (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sm_admin_role` */

insert  into `sm_admin_role`(`id`,`adminid`,`rid`) values (1,1,4),(2,2,2),(3,3,1),(4,4,3),(5,5,5);

/*Table structure for table `sm_adminvo` */

DROP TABLE IF EXISTS `sm_adminvo`;

CREATE TABLE `sm_adminvo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` char(11) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sm_adminvo` */

insert  into `sm_adminvo`(`id`,`username`,`password`,`phone`,`enabled`) values (1,'admin','$2a$10$NKVn7qniw852oKK/w9uk0OhflqInJW5K64NosLPaJ7LcCQk5y5m5.','13812361398',1),(2,'taomeng','123','18875971675',1),(3,'naqiao','456','14588110811',1),(4,'leisu','789','15761248727',1),(5,'hanli','987','18030710396',1);

/*Table structure for table `sm_chainstore` */

DROP TABLE IF EXISTS `sm_chainstore`;

CREATE TABLE `sm_chainstore` (
  `chainstore_id` int(11) NOT NULL AUTO_INCREMENT,
  `chainstore_address` varchar(100) DEFAULT NULL,
  `chainstore_director` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`chainstore_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `sm_chainstore` */

insert  into `sm_chainstore`(`chainstore_id`,`chainstore_address`,`chainstore_director`) values (1,'北京市丰台区育芳园北路','李梅');

/*Table structure for table `sm_goods` */

DROP TABLE IF EXISTS `sm_goods`;

CREATE TABLE `sm_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `purchasing_price` float DEFAULT NULL,
  `selling_price` float DEFAULT NULL,
  `supplierid` int(11) DEFAULT NULL,
  `produceddate` datetime DEFAULT NULL,
  `incomingdate` datetime DEFAULT NULL,
  `baozhiqi` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `supplierid` (`supplierid`),
  CONSTRAINT `sm_goods_ibfk_1` FOREIGN KEY (`supplierid`) REFERENCES `sm_supplier` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `sm_goods` */

insert  into `sm_goods`(`id`,`name`,`purchasing_price`,`selling_price`,`supplierid`,`produceddate`,`incomingdate`,`baozhiqi`) values (1,'陶华碧老干妈香辣脆油辣椒',6.34,8.5,1,'2022-01-01 14:32:40','2022-02-01 14:32:49',12);

/*Table structure for table `sm_menu` */

DROP TABLE IF EXISTS `sm_menu`;

CREATE TABLE `sm_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parentId` (`parentId`),
  CONSTRAINT `sm_menu_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `sm_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `sm_menu` */

insert  into `sm_menu`(`id`,`name`,`url`,`parentId`) values (1,'所有','/',NULL),(2,'管理员管理','/',1),(3,'收银员管理','/',1),(4,'供应商管理','/',1),(5,'商品管理','/',1),(6,'买家管理','/',1),(7,'管理员列表','/admin/**',2),(8,'收银员列表','/cashier/**',3),(9,'供货商列表','/supplier/**',4),(10,'商品列表','/goods/**',5),(11,'买家列表','/customer/**',6);

/*Table structure for table `sm_role` */

DROP TABLE IF EXISTS `sm_role`;

CREATE TABLE `sm_role` (
  `roleid` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(50) DEFAULT NULL,
  `permission` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sm_role` */

insert  into `sm_role`(`roleid`,`rolename`,`permission`) values (1,'普通买家','查看仓库的信息;购买商品'),(2,'会员买家','查看仓库的信息;购买商品'),(3,'供货商','查看对应商品信息;修改自己的相关信息，但是需要管理员同意'),(4,'管理员','添加管理员、收银员、供应商;修改成员信息和商品信息'),(5,'收银员','收款;清点库存;及时更新商品信息');

/*Table structure for table `sm_role_menu` */

DROP TABLE IF EXISTS `sm_role_menu`;

CREATE TABLE `sm_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) DEFAULT NULL,
  `menuid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `roleid` (`roleid`),
  KEY `menuid` (`menuid`),
  CONSTRAINT `sm_role_menu_ibfk_1` FOREIGN KEY (`roleid`) REFERENCES `sm_role` (`roleid`),
  CONSTRAINT `sm_role_menu_ibfk_2` FOREIGN KEY (`menuid`) REFERENCES `sm_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

/*Data for the table `sm_role_menu` */

insert  into `sm_role_menu`(`id`,`roleid`,`menuid`) values (1,1,10),(2,2,10),(3,3,9),(4,3,10),(5,4,7),(6,4,8),(7,4,9),(8,4,10),(9,4,11),(10,5,5);

/*Table structure for table `sm_supplier` */

DROP TABLE IF EXISTS `sm_supplier`;

CREATE TABLE `sm_supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `suppliername` varchar(50) DEFAULT NULL,
  `productname` varchar(50) DEFAULT NULL,
  `supplierid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `supplierid` (`supplierid`),
  CONSTRAINT `sm_supplier_ibfk_1` FOREIGN KEY (`supplierid`) REFERENCES `sm_admin` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `sm_supplier` */

insert  into `sm_supplier`(`id`,`suppliername`,`productname`,`supplierid`) values (1,'贵州省贵阳南明老干妈风味食品有限公司','陶华碧老干妈香辣脆油辣椒',4);

/*Table structure for table `sm_vipcard` */

DROP TABLE IF EXISTS `sm_vipcard`;

CREATE TABLE `sm_vipcard` (
  `vipcardid` int(11) NOT NULL AUTO_INCREMENT,
  `holderid` int(11) DEFAULT NULL,
  `creationdate` datetime DEFAULT NULL,
  `integral` int(11) DEFAULT NULL,
  PRIMARY KEY (`vipcardid`),
  KEY `holderid` (`holderid`),
  CONSTRAINT `sm_vipcard_ibfk_1` FOREIGN KEY (`holderid`) REFERENCES `sm_admin` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `sm_vipcard` */

insert  into `sm_vipcard`(`vipcardid`,`holderid`,`creationdate`,`integral`) values (1,2,'2022-03-10 18:35:51',2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

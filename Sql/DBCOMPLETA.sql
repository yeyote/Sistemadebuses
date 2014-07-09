/*
SQLyog Ultimate v9.02 
MySQL - 5.5.27 : Database - sistemadebuses
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sistemadebuses` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `sistemadebuses`;

/*Table structure for table `backup` */

DROP TABLE IF EXISTS `backup`;

CREATE TABLE `backup` (
  `id_back` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `SQL` text NOT NULL,
  PRIMARY KEY (`id_back`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Table structure for table `boleto` */

DROP TABLE IF EXISTS `boleto`;

CREATE TABLE `boleto` (
  `id_boleto` int(11) NOT NULL AUTO_INCREMENT,
  `ci` varchar(15) DEFAULT NULL,
  `id_viaje` int(11) DEFAULT NULL,
  `costo` float DEFAULT NULL,
  `asiento` int(2) NOT NULL,
  PRIMARY KEY (`id_boleto`),
  KEY `FK_boleto_viaje` (`id_viaje`),
  KEY `FK_boleto_persona` (`ci`),
  CONSTRAINT `FK_boleto_persona` FOREIGN KEY (`ci`) REFERENCES `persona` (`ci`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_boleto_viaje` FOREIGN KEY (`id_viaje`) REFERENCES `viaje` (`id_viaje`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Table structure for table `bus` */

DROP TABLE IF EXISTS `bus`;

CREATE TABLE `bus` (
  `id_bus` int(11) NOT NULL AUTO_INCREMENT,
  `placa` varchar(8) NOT NULL,
  `marca/modelo` varchar(30) NOT NULL,
  `capacidad` int(2) NOT NULL,
  `estado` int(1) NOT NULL,
  PRIMARY KEY (`id_bus`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Table structure for table `chofer` */

DROP TABLE IF EXISTS `chofer`;

CREATE TABLE `chofer` (
  `id_chofer` int(11) NOT NULL AUTO_INCREMENT,
  `ci` varchar(15) NOT NULL,
  `salario` float DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `brevet` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id_chofer`,`ci`),
  KEY `FK_chofer_persona` (`ci`),
  CONSTRAINT `FK_chofer_persona` FOREIGN KEY (`ci`) REFERENCES `persona` (`ci`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Table structure for table `destino` */

DROP TABLE IF EXISTS `destino`;

CREATE TABLE `destino` (
  `id_destino` int(2) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id_destino`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Table structure for table `encomienda` */

DROP TABLE IF EXISTS `encomienda`;

CREATE TABLE `encomienda` (
  `id_encomienda` int(11) NOT NULL AUTO_INCREMENT,
  `remitente` varchar(30) NOT NULL,
  `destinatario` varchar(30) NOT NULL,
  `id_viaje` int(11) DEFAULT NULL,
  `costo` float NOT NULL,
  `tipo` int(2) NOT NULL,
  `estado` int(1) NOT NULL,
  `giro` int(6) DEFAULT NULL,
  PRIMARY KEY (`id_encomienda`),
  KEY `FK_encomienda` (`id_viaje`),
  CONSTRAINT `FK_encomienda` FOREIGN KEY (`id_viaje`) REFERENCES `viaje` (`id_viaje`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Table structure for table `persona` */

DROP TABLE IF EXISTS `persona`;

CREATE TABLE `persona` (
  `ci` varchar(15) NOT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ci`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `usuario` */

DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
  `id_user` varchar(15) NOT NULL,
  `password` varchar(40) NOT NULL,
  `ci` varchar(15) DEFAULT NULL,
  `rango` int(2) DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  KEY `FK_usuario` (`ci`),
  CONSTRAINT `FK_usuario` FOREIGN KEY (`ci`) REFERENCES `persona` (`ci`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `viaje` */

DROP TABLE IF EXISTS `viaje`;

CREATE TABLE `viaje` (
  `id_viaje` int(11) NOT NULL AUTO_INCREMENT,
  `id_destino` int(2) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `observacion` text,
  `id_chofer` int(11) NOT NULL,
  `id_bus` int(11) NOT NULL,
  PRIMARY KEY (`id_viaje`),
  KEY `FK_viaje_destino` (`id_destino`),
  KEY `FK_viaje_bus` (`id_bus`),
  KEY `FK_viaje_chofer` (`id_chofer`),
  CONSTRAINT `FK_viaje_bus` FOREIGN KEY (`id_bus`) REFERENCES `bus` (`id_bus`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_viaje_chofer` FOREIGN KEY (`id_chofer`) REFERENCES `chofer` (`id_chofer`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_viaje_destino` FOREIGN KEY (`id_destino`) REFERENCES `destino` (`id_destino`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem1
-- ------------------------------------------------------
-- Server version	9.3.0

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
-- Current Database: `podsistem1`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `podsistem1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `podsistem1`;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `IdK` int NOT NULL AUTO_INCREMENT,
  `Email` varchar(40) NOT NULL,
  `Godiste` int NOT NULL,
  `Ime` varchar(40) NOT NULL,
  `Pol` varchar(20) NOT NULL,
  `Prezime` varchar(40) NOT NULL,
  `Mesto` int NOT NULL,
  PRIMARY KEY (`IdK`),
  UNIQUE KEY `Email` (`Email`),
  KEY `FK_KORISNIK_Mesto` (`Mesto`),
  CONSTRAINT `FK_KORISNIK_Mesto` FOREIGN KEY (`Mesto`) REFERENCES `mesto` (`IdM`)
) ENGINE=InnoDB AUTO_INCREMENT=256229 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES 
(1,'Irinapetrovic',2004,'Irina','zenski','Petrovic',1),
(2,'mm03@gmail.com',2003,'Milinko','muski','Miletic',2),
(3,'pp00@gmail.com',2000,'Petar','muski','Petrovic',1),
(4,'ii00@gmail.com',2000,'Ivan','muski','Ivanic',3),
(5,'aa06@gmail.com',2006,'Ana','zenski','Anic',1);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesto`
--

DROP TABLE IF EXISTS `mesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesto` (
  `IdM` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(255) NOT NULL,
  PRIMARY KEY (`IdM`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesto`
--

LOCK TABLES `mesto` WRITE;
/*!40000 ALTER TABLE `mesto` DISABLE KEYS */;
INSERT INTO `mesto` VALUES 
(1,'Valjevo'),
(2,'Uzice'),
(3,'Sarajevo'),
(4,'Beograd'),
(5,'Kikinda'),
(6,'Kraljevo'),
(7,'Sarajevo'),
(8,'Kosjeric'),
(9,'Zagreb');
/*!40000 ALTER TABLE `mesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `podsistem2`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `podsistem2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `podsistem2`;

--
-- Table structure for table `audiosnimak`
--

DROP TABLE IF EXISTS `audiosnimak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audiosnimak` (
  `IdSnimka` int NOT NULL AUTO_INCREMENT,
  `DatumPostavljanja` datetime NOT NULL,
  `Naziv` varchar(100) NOT NULL,
  `Trajanje` int NOT NULL,
  `VlasnikId` int NOT NULL,
  PRIMARY KEY (`IdSnimka`),
  KEY `FK_AUDIOSNIMAK_VlasnikId` (`VlasnikId`),
  CONSTRAINT `FK_AUDIOSNIMAK_VlasnikId` FOREIGN KEY (`VlasnikId`) REFERENCES `korisnik` (`IdK`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audiosnimak`
--

LOCK TABLES `audiosnimak` WRITE;
/*!40000 ALTER TABLE `audiosnimak` DISABLE KEYS */;
INSERT INTO `audiosnimak` VALUES 
(1,'2025-07-27 22:00:00','Pesma1',120,1),
(2,'2025-07-27 22:00:00','Pesma2',100,1),
(3,'2025-07-27 22:00:00','Pesma3',110,2),
(4,'2025-07-27 22:00:00','Pesma4',130,2),
(5,'2025-07-27 22:00:00','Pesma5',110,2),
(6,'2025-07-27 22:00:00','Pesma6',130,3);
/*!40000 ALTER TABLE `audiosnimak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audiosnimakkategorija`
--

DROP TABLE IF EXISTS `audiosnimakkategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audiosnimakkategorija` (
  `KategorijaId` int NOT NULL,
  `AudioId` int NOT NULL,
  PRIMARY KEY (`KategorijaId`,`AudioId`),
  KEY `FK_AUDIOSNIMAKKATEGORIJA_AudioId` (`AudioId`),
  CONSTRAINT `FK_AUDIOSNIMAKKATEGORIJA_AudioId` FOREIGN KEY (`AudioId`) REFERENCES `audiosnimak` (`IdSnimka`),
  CONSTRAINT `FK_AUDIOSNIMAKKATEGORIJA_KategorijaId` FOREIGN KEY (`KategorijaId`) REFERENCES `kategorija` (`IdKategorije`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audiosnimakkategorija`
--

LOCK TABLES `audiosnimakkategorija` WRITE;
/*!40000 ALTER TABLE `audiosnimakkategorija` DISABLE KEYS */;
INSERT INTO `audiosnimakkategorija` VALUES 
(2,1),
(4,1),
(1,2),
(2,2),
(1,3),
(2,3),
(3,3),
(2,4);
/*!40000 ALTER TABLE `audiosnimakkategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorija` (
  `IdKategorije` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(50) NOT NULL,
  PRIMARY KEY (`IdKategorije`),
  UNIQUE KEY `Naziv` (`Naziv`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorija`
--

LOCK TABLES `kategorija` WRITE;
/*!40000 ALTER TABLE `kategorija` DISABLE KEYS */;
INSERT INTO `kategorija` VALUES 
(1,'pop'),
(2,'rok'),
(3,'dzez'),
(4,'indi'),
(5,'klasika');
/*!40000 ALTER TABLE `kategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `IdK` int NOT NULL,
  `Email` varchar(40) NOT NULL,
  `Godiste` int NOT NULL,
  `Ime` varchar(40) NOT NULL,
  `Mesto` int NOT NULL,
  `Pol` varchar(20) NOT NULL,
  `Prezime` varchar(40) NOT NULL,
  PRIMARY KEY (`IdK`),
  UNIQUE KEY `IdK` (`IdK`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;

INSERT INTO `korisnik` VALUES 
(1,'Irinapetrovic',2004,'Irina',1,'zenski','Petrovic'),
(2,'mm03@gmail.com',2003,'Milinko',2,'muski','Miletic'),
(3,'pp00@gmail.com',2000,'Petar',1,'muski','Petrovic'),
(4,'ii00@gmail.com',2000,'Ivan',3,'muski','Ivanic'),
(5,'aa06@gmail.com',2006,'Ana',1,'zenski','Anic');
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `podsistem3`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `podsistem3` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `podsistem3`;

--
-- Table structure for table `audiosnimak`
--

DROP TABLE IF EXISTS `audiosnimak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audiosnimak` (
  `IdSnimka` int NOT NULL,
  `DatumPostavljanja` datetime NOT NULL,
  `Naziv` varchar(100) NOT NULL,
  `Trajanje` int NOT NULL,
  `vlasnikId` int NOT NULL,
  PRIMARY KEY (`IdSnimka`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audiosnimak`
--

LOCK TABLES `audiosnimak` WRITE;
/*!40000 ALTER TABLE `audiosnimak` DISABLE KEYS */;
INSERT INTO `audiosnimak` VALUES 
(1,'2025-07-27 22:00:00','Pesma1',120,1),
(2,'2025-07-27 22:00:00','Pesma2',100,1),
(3,'2025-07-27 22:00:00','Pesma3',110,2),
(4,'2025-07-27 22:00:00','Pesma4',130,2),
(5,'2025-07-27 22:00:00','Pesma5',110,2),
(6,'2025-07-27 22:00:00','Pesma6',130,3);
/*!40000 ALTER TABLE `audiosnimak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `IdK` int NOT NULL,
  `Email` varchar(40) NOT NULL,
  `Godiste` int NOT NULL,
  `Ime` varchar(40) NOT NULL,
  `Mesto` int NOT NULL,
  `Pol` varchar(20) NOT NULL,
  `Prezime` varchar(40) NOT NULL,
  PRIMARY KEY (`IdK`),
  UNIQUE KEY `IdK` (`IdK`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES 
(1,'Irinapetrovic',2004,'Irina',1,'zenski','Petrovic'),
(2,'mm03@gmail.com',2003,'Milinko',2,'muski','Miletic'),
(3,'pp00@gmail.com',2000,'Petar',1,'muski','Petrovic'),
(4,'ii00@gmail.com',2000,'Ivan',3,'muski','Ivanic'),
(5,'aa06@gmail.com',2006,'Ana',1,'zenski','Anic');
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocena`
--

DROP TABLE IF EXISTS `ocena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ocena` (
  `DatumVreme` datetime NOT NULL,
  `Ocena` int NOT NULL,
  `AudioId` int NOT NULL,
  `KorisnikId` int NOT NULL,
  PRIMARY KEY (`AudioId`,`KorisnikId`),
  KEY `FK_OCENA_KorisnikId` (`KorisnikId`),
  CONSTRAINT `FK_OCENA_AudioId` FOREIGN KEY (`AudioId`) REFERENCES `audiosnimak` (`IdSnimka`),
  CONSTRAINT `FK_OCENA_KorisnikId` FOREIGN KEY (`KorisnikId`) REFERENCES `korisnik` (`IdK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocena`
--

LOCK TABLES `ocena` WRITE;
/*!40000 ALTER TABLE `ocena` DISABLE KEYS */;
/*!40000 ALTER TABLE `ocena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `omiljeni`
--

DROP TABLE IF EXISTS `omiljeni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `omiljeni` (
  `AudioId` int NOT NULL,
  `KorisnikId` int NOT NULL,
  PRIMARY KEY (`AudioId`,`KorisnikId`),
  KEY `FK_OMILJENI_KorisnikId` (`KorisnikId`),
  CONSTRAINT `FK_OMILJENI_AudioId` FOREIGN KEY (`AudioId`) REFERENCES `audiosnimak` (`IdSnimka`),
  CONSTRAINT `FK_OMILJENI_KorisnikId` FOREIGN KEY (`KorisnikId`) REFERENCES `korisnik` (`IdK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `omiljeni`
--

LOCK TABLES `omiljeni` WRITE;
/*!40000 ALTER TABLE `omiljeni` DISABLE KEYS */;
INSERT INTO `omiljeni` VALUES 
(3,1),
(4,1),
(1,2),
(3,2),
(1,3),
(2,3),
(3,3);

/*!40000 ALTER TABLE `omiljeni` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paket`
--

DROP TABLE IF EXISTS `paket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paket` (
  `IdPaketa` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(50) NOT NULL,
  `TrenutnaCena` double NOT NULL,
  PRIMARY KEY (`IdPaketa`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paket`
--

LOCK TABLES `paket` WRITE;
/*!40000 ALTER TABLE `paket` DISABLE KEYS */;
INSERT INTO `paket` VALUES 
(1,'mts',200),
(2,'vip',100);
/*!40000 ALTER TABLE `paket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pretplata`
--

DROP TABLE IF EXISTS `pretplata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pretplata` (
  `IdPretplate` int NOT NULL AUTO_INCREMENT,
  `Cena` double NOT NULL,
  `DatumPocetka` datetime NOT NULL,
  `KorisnikId` int NOT NULL,
  `PaketId` int NOT NULL,
  PRIMARY KEY (`IdPretplate`),
  KEY `FK_PRETPLATA_PaketId` (`PaketId`),
  KEY `FK_PRETPLATA_KorisnikId` (`KorisnikId`),
  CONSTRAINT `FK_PRETPLATA_KorisnikId` FOREIGN KEY (`KorisnikId`) REFERENCES `korisnik` (`IdK`),
  CONSTRAINT `FK_PRETPLATA_PaketId` FOREIGN KEY (`PaketId`) REFERENCES `paket` (`IdPaketa`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pretplata`
--

LOCK TABLES `pretplata` WRITE;
/*!40000 ALTER TABLE `pretplata` DISABLE KEYS */;
INSERT INTO `pretplata` VALUES 
(1,20000,'2025-07-29 00:24:55',1,1),
(2,20000,'2025-07-29 00:24:55',2,1);
/*!40000 ALTER TABLE `pretplata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slusanje`
--

DROP TABLE IF EXISTS `slusanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slusanje` (
  `IdSlusanja` int NOT NULL AUTO_INCREMENT,
  `PocetakVreme` datetime NOT NULL,
  `SekundiOd` int NOT NULL,
  `SekundiTrajanje` int NOT NULL,
  `AudioId` int NOT NULL,
  `KorisnikId` int NOT NULL,
  PRIMARY KEY (`IdSlusanja`),
  KEY `FK_SLUSANJE_KorisnikId` (`KorisnikId`),
  KEY `FK_SLUSANJE_AudioId` (`AudioId`),
  CONSTRAINT `FK_SLUSANJE_AudioId` FOREIGN KEY (`AudioId`) REFERENCES `audiosnimak` (`IdSnimka`),
  CONSTRAINT `FK_SLUSANJE_KorisnikId` FOREIGN KEY (`KorisnikId`) REFERENCES `korisnik` (`IdK`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slusanje`
--

LOCK TABLES `slusanje` WRITE;
/*!40000 ALTER TABLE `slusanje` DISABLE KEYS */;
INSERT INTO `slusanje` VALUES 
(1,'2025-07-28 15:00:00',5,1000,6,1),
(2,'2025-07-28 18:00:00',55,222,2,1),
(3,'2025-07-28 18:00:00',58,222,1,2);
/*!40000 ALTER TABLE `slusanje` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-05 21:44:28

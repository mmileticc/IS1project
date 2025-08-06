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
(1, 'jovan.petrovic@gmail.com', 1995, 'Jovan', 'muškarac', 'Petrović', 1),
(2, 'marija.jovanovic@yahoo.com', 1998, 'Marija', 'žena', 'Jovanović', 2),
(3, 'nikola.stojanovic@outlook.com', 1990, 'Nikola', 'muškarac', 'Stojanović', 3),
(4, 'ana.markovic@gmail.com', 1992, 'Ana', 'žena', 'Marković', 1),
(5, 'dusan.ilic@gmail.com', 1988, 'Dušan', 'muškarac', 'Ilić', 2),
(6, 'milica.nikolic@gmail.com', 1996, 'Milica', 'žena', 'Nikolić', 3),
(7, 'vladimir.radovic@gmail.com', 1993, 'Vladimir', 'muškarac', 'Radović', 1),
(8, 'jelena.simic@gmail.com', 1999, 'Jelena', 'žena', 'Simić', 2),
(9, 'marko.milosevic@gmail.com', 1997, 'Marko', 'muškarac', 'Milošević', 3),
(10, 'teodora.pavlovic@gmail.com', 2000, 'Teodora', 'žena', 'Pavlović', 1);
  /*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
  UNLOCK TABLES;

--
-- Table structure for table `mesto`
--

DROP TABLE IF EXISTS `mesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;

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
(1, 'Valjevo'),
(2, 'Užice'),
(3, 'Novi Sad'),
(4, 'Niš'),
(5, 'Kragujevac'),
(6, 'Beograd'),
(7, 'Subotica'),
(8, 'Zrenjanin'),
(9, 'Čačak'),
(10, 'Sombor');
/*!40000 ALTER TABLE `mesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `podsistem2`
--
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `podsistem2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE=utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `podsistem2`;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */

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
/*!40101 SET character_set_client = @saved_cs_client */

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */
INSERT INTO `korisnik` VALUES
(1, 'jovan.petrovic@gmail.com', 1995, 'Jovan', 1, 'muškarac', 'Petrović'),
(2, 'marija.jovanovic@yahoo.com', 1998, 'Marija', 2, 'žena', 'Jovanović'),
(3, 'nikola.stojanovic@outlook.com', 1990, 'Nikola', 3, 'muškarac', 'Stojanović'),
(4, 'ana.markovic@gmail.com', 1992, 'Ana', 1, 'žena', 'Marković'),
(5, 'dusan.ilic@gmail.com', 1988, 'Dušan', 2, 'muškarac', 'Ilić'),
(6, 'milica.nikolic@gmail.com', 1996, 'Milica', 3, 'žena', 'Nikolić'),
(7, 'vladimir.radovic@gmail.com', 1993, 'Vladimir', 1, 'muškarac', 'Radović'),
(8, 'jelena.simic@gmail.com', 1999, 'Jelena', 2, 'žena', 'Simić'),
(9, 'marko.milosevic@gmail.com', 1997, 'Marko', 3, 'muškarac', 'Milošević'),
(10, 'teodora.pavlovic@gmail.com', 2000, 'Teodora', 1, 'žena', 'Pavlović');
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */
UNLOCK TABLES;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */

CREATE TABLE `kategorija` (
  `IdKategorije` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(50) NOT NULL,
  PRIMARY KEY (`IdKategorije`),
  UNIQUE KEY `Naziv` (`Naziv`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */

--
-- Dumping data for table `kategorija`
--

LOCK TABLES `kategorija` WRITE;
/*!40000 ALTER TABLE `kategorija` DISABLE KEYS */
INSERT INTO `kategorija` VALUES
(1, 'rok'),
(2, 'pop'),
(3, 'rege'),
(4, 'klasika'),
(5, 'džez');
/*!40000 ALTER TABLE `kategorija` ENABLE KEYS */
UNLOCK TABLES;

--
-- Table structure for table `audiosnimak`
--

DROP TABLE IF EXISTS `audiosnimak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */

CREATE TABLE `audiosnimak` (
  `IdSnimka` int NOT NULL AUTO_INCREMENT,
  `DatumPostavljanja` datetime NOT NULL,
  `Naziv` varchar(100) NOT NULL,
  `Trajanje` int NOT NULL,
  `VlasnikId` int NOT NULL,
  PRIMARY KEY (`IdSnimka`),
  KEY `FK_AUDIOSNIMAK_VlasnikId` (`VlasnikId`),
  CONSTRAINT `FK_AUDIOSNIMAK_VlasnikId` FOREIGN KEY (`VlasnikId`) REFERENCES `korisnik` (`IdK`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */

--
-- Dumping data for table `audiosnimak`
--

LOCK TABLES `audiosnimak` WRITE;
/*!40000 ALTER TABLE `audiosnimak` DISABLE KEYS */
INSERT INTO `audiosnimak` VALUES
(1, '2025-08-01 20:00:00', 'ZvezdaniRok', 180, 1),
(2, '2025-08-02 21:30:00', 'PopBalada', 210, 2),
(3, '2025-08-03 19:45:00', 'RegeRitam', 200, 3),
(4, '2025-08-04 18:15:00', 'KlasikaVeče', 240, 4);
/*!40000 ALTER TABLE `audiosnimak` ENABLE KEYS */
UNLOCK TABLES;

--
-- Table structure for table `audiosnimakkategorija`
--

DROP TABLE IF EXISTS `audiosnimakkategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */

CREATE TABLE `audiosnimakkategorija` (
  `KategorijaId` int NOT NULL,
  `AudioId` int NOT NULL,
  PRIMARY KEY (`KategorijaId`,`AudioId`),
  KEY `FK_AUDIOSNIMAKKATEGORIJA_AudioId` (`AudioId`),
  CONSTRAINT `FK_AUDIOSNIMAKKATEGORIJA_AudioId` FOREIGN KEY (`AudioId`) REFERENCES `audiosnimak` (`IdSnimka`),
  CONSTRAINT `FK_AUDIOSNIMAKKATEGORIJA_KategorijaId` FOREIGN KEY (`KategorijaId`) REFERENCES `kategorija` (`IdKategorije`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */

--
-- Dumping data for table `audiosnimakkategorija`
--

LOCK TABLES `audiosnimakkategorija` WRITE;
/*!40000 ALTER TABLE `audiosnimakkategorija` DISABLE KEYS */
INSERT INTO `audiosnimakkategorija` VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);
/*!40000 ALTER TABLE `audiosnimakkategorija` ENABLE KEYS */
UNLOCK TABLES;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `podsistem3` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE=utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `podsistem3`;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */

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
/*!40101 SET character_set_client = @saved_cs_client */

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */
INSERT INTO `korisnik` VALUES
(1, 'jovan.petrovic@gmail.com', 1995, 'Jovan', 1, 'muškarac', 'Petrović'),
(2, 'marija.jovanovic@yahoo.com', 1998, 'Marija', 2, 'žena', 'Jovanović'),
(3, 'nikola.stojanovic@outlook.com', 1990, 'Nikola', 3, 'muškarac', 'Stojanović'),
(4, 'ana.markovic@gmail.com', 1992, 'Ana', 1, 'žena', 'Marković'),
(5, 'dusan.ilic@gmail.com', 1988, 'Dušan', 2, 'muškarac', 'Ilić'),
(6, 'milica.nikolic@gmail.com', 1996, 'Milica', 3, 'žena', 'Nikolić'),
(7, 'vladimir.radovic@gmail.com', 1993, 'Vladimir', 1, 'muškarac', 'Radović'),
(8, 'jelena.simic@gmail.com', 1999, 'Jelena', 2, 'žena', 'Simić'),
(9, 'marko.milosevic@gmail.com', 1997, 'Marko', 3, 'muškarac', 'Milošević'),
(10, 'teodora.pavlovic@gmail.com', 2000, 'Teodora', 1, 'žena', 'Pavlović');
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */
UNLOCK TABLES;

--
-- Table structure for table `audiosnimak`
--

DROP TABLE IF EXISTS `audiosnimak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */

CREATE TABLE `audiosnimak` (
  `IdSnimka` int NOT NULL,
  `DatumPostavljanja` datetime NOT NULL,
  `Naziv` varchar(100) NOT NULL,
  `Trajanje` int NOT NULL,
  `vlasnikId` int NOT NULL,
  PRIMARY KEY (`IdSnimka`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */

--
-- Dumping data for table `audiosnimak`
--

LOCK TABLES `audiosnimak` WRITE;
/*!40000 ALTER TABLE `audiosnimak` DISABLE KEYS */
INSERT INTO `audiosnimak` VALUES
(1, '2025-08-01 20:00:00', 'ZvezdaniRok', 180, 1),
(2, '2025-08-02 21:30:00', 'PopBalada', 210, 2),
(3, '2025-08-03 19:45:00', 'RegeRitam', 200, 3),
(4, '2025-08-04 18:15:00', 'KlasikaVeče', 240, 4);
/*!40000 ALTER TABLE `audiosnimak` ENABLE KEYS */
UNLOCK TABLES;

--
-- Table structure for table `ocena`
--

DROP TABLE IF EXISTS `ocena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */

CREATE TABLE `ocena` (
  `DatumVreme` datetime NOT NULL,
  `Ocena` int NOT NULL,
  `AudioId` int NOT NULL,
  `KorisnikId` int NOT NULL,
  PRIMARY KEY (`AudioId`,`KorisnikId`),
  KEY `FK_OCENA_KorisnikId` (`KorisnikId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */

--
-- Table structure for table `omiljeni`
--

DROP TABLE IF EXISTS `omiljeni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */

CREATE TABLE `omiljeni` (
  `AudioId` int NOT NULL,
  `KorisnikId` int NOT NULL,
  PRIMARY KEY (`AudioId`,`KorisnikId`),
  KEY `FK_OMILJENI_KorisnikId` (`KorisnikId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */

--
-- Dumping data for table `omiljeni`
--

LOCK TABLES `omiljeni` WRITE;
/*!40000 ALTER TABLE `omiljeni` DISABLE KEYS */
INSERT INTO `omiljeni` VALUES
(1, 1),
(2, 2);
/*!40000 ALTER TABLE `omiljeni` ENABLE KEYS */
UNLOCK TABLES;

--
-- Table structure for table `paket`
--

DROP TABLE IF EXISTS `paket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */

CREATE TABLE `paket` (
  `IdPaketa` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(50) NOT NULL,
  `TrenutnaCena` double NOT NULL,
  PRIMARY KEY (`IdPaketa`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */

--
-- Dumping data for table `paket`
--

LOCK TABLES `paket` WRITE;
/*!40000 ALTER TABLE `paket` DISABLE KEYS */
INSERT INTO `paket` VALUES
(1, 'mts', 20000),
(2, 'vip', 100);
/*!40000 ALTER TABLE `paket` ENABLE KEYS */
UNLOCK TABLES;

--
-- Table structure for table `pretplata`
--

DROP TABLE IF EXISTS `pretplata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */

CREATE TABLE `pretplata` (
  `IdPretplate` int NOT NULL AUTO_INCREMENT,
  `Cena` double NOT NULL,
  `DatumPocetka` datetime NOT NULL,
  `KorisnikId` int NOT NULL,
  `PaketId` int NOT NULL,
  PRIMARY KEY (`IdPretplate`),
  KEY `FK_PRETPLATA_PaketId` (`PaketId`),
  KEY `FK_PRETPLATA_KorisnikId` (`KorisnikId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */

--
-- Dumping data for table `pretplata`
--

LOCK TABLES `pretplata` WRITE;
/*!40000 ALTER TABLE `pretplata` DISABLE KEYS */
INSERT INTO `pretplata` VALUES
(1, 20000, '2025-07-29 00:24:55', 1, 1);
/*!40000 ALTER TABLE `pretplata` ENABLE KEYS */
UNLOCK TABLES;

--
-- Table structure for table `slusanje`
--

DROP TABLE IF EXISTS `slusanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */

CREATE TABLE `slusanje` (
  `IdSlusanja` int NOT NULL AUTO_INCREMENT,
  `PocetakVreme` datetime NOT NULL,
  `SekundiOd` int NOT NULL,
  `SekundiTrajanje` int NOT NULL,
  `AudioId` int NOT NULL,
  `KorisnikId` int NOT NULL,
  PRIMARY KEY (`IdSlusanja`),
  KEY `FK_SLUSANJE_KorisnikId` (`KorisnikId`),
  KEY `FK_SLUSANJE_AudioId` (`AudioId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */

--
-- Dumping data for table `slusanje`
--

LOCK TABLES `slusanje` WRITE;
/*!40000 ALTER TABLE `slusanje` DISABLE KEYS */
INSERT INTO `slusanje` VALUES
(1, '2025-08-05 15:00:00', 5, 1000, 1, 1),
(2, '2025-08-05 18:00:00', 55, 222, 2, 2);
/*!40000 ALTER TABLE `slusanje` ENABLE KEYS */
UNLOCK TABLES;

--
-- Final session cleanup
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
  
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
  
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
  
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
  
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
  
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
  
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
  
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-05 22:03:00
CREATE TABLE IF NOT EXISTS `variables` (
  `id` integer NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `habitacion` varchar(30) NOT NULL,
  `sensor` varchar(30) NOT NULL,
  `temperatura` float NOT NULL,
  `humedad` float NOT NULL,
  PRIMARY KEY (ID)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
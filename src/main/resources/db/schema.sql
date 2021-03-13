DROP TABLE IF EXISTS `picture`;
DROP table IF EXISTS  `shop`;

CREATE TABLE `shop` (
  `id_shop` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL DEFAULT '',
  `capacity` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_shop`)
);



CREATE TABLE `picture` (
  `id_picture` int(11) NOT NULL,
  `id_shop` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `author` varchar(250) NOT NULL DEFAULT 'ANONYMOUS',
  `price` decimal(15,2) NOT NULL DEFAULT 0.00,
  `entry_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_shop`,`id_picture`),
  CONSTRAINT `picture_fk` FOREIGN KEY (`id_shop`) REFERENCES `shop` (`id_shop`)
);





DROP TABLE IF EXISTS `picture`;
DROP table IF EXISTS  `shop`;

CREATE TABLE `shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL DEFAULT '',
  `capacity` int(11) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
);

CREATE TABLE `picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `author` varchar(250) NOT NULL DEFAULT 'ANONYMOUS',
  `price` decimal(9,2) DEFAULT NULL,
  `entryDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `shop_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `picture_fk` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`)
);




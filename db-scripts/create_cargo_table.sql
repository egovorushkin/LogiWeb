CREATE TABLE `logiweb`.`cargo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `number` INT NOT NULL,
  `weight` INT NOT NULL DEFAULT 0,
  `status` VARCHAR(15) NULL,
  PRIMARY KEY (`id`));

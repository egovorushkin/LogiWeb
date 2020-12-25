CREATE TABLE `logiweb`.`trucks` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `registration_number` VARCHAR(7) NOT NULL,
  `team_size` INT NOT NULL,
  `capacity` INT NOT NULL,
  `status` VARCHAR(6) NOT NULL,
  `current_city` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `registration_number_UNIQUE` (`registration_number` ASC) VISIBLE);
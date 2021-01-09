CREATE TABLE `logiweb`.`drivers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` CHAR(80) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `personal_number` INT NOT NULL,
  `worked_hours_per_month` INT NULL,
  `current_status` VARCHAR(10) NULL,
  `current_city` INT NULL,
  `current_truck` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_drivers_trucks_idx` (`current_truck` ASC) VISIBLE,
  INDEX `fk_drivers_cities_idx` (`current_city` ASC) VISIBLE,
  CONSTRAINT `fk_drivers_trucks`
    FOREIGN KEY (`current_truck`)
    REFERENCES `logiweb`.`truck` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_drivers_cities`
    FOREIGN KEY (`current_city`)
    REFERENCES `logiweb`.`city` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
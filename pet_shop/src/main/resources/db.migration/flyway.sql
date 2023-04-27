
CREATE SCHEMA IF NOT EXISTS `pet_shop` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `pet_shop` ;

CREATE TABLE IF NOT EXISTS `pet_shop`.`categories` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `name` VARCHAR(45) NOT NULL,
   PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `pet_shop`.`discounts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` TEXT NULL DEFAULT NULL,
  `percent` DECIMAL(5,2) NOT NULL,
  `from_date` DATE NOT NULL,
  `to_date` DATE NOT NULL,
  `is_active` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `pet_shop`.`suppliers` (
      `id` INT NOT NULL AUTO_INCREMENT,
      `supplier_name` VARCHAR(45) NOT NULL,
      PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `pet_shop`.`subcategories` (
      `id` INT NOT NULL AUTO_INCREMENT,
      `name` VARCHAR(45) NOT NULL,
      `category_id` INT NOT NULL,
      PRIMARY KEY (`id`),
      INDEX `category_id` (`category_id` ASC) VISIBLE,
      CONSTRAINT `subcategories_ibfk_1`
          FOREIGN KEY (`category_id`)
              REFERENCES `pet_shop`.`categories` (`id`));

CREATE TABLE IF NOT EXISTS `pet_shop`.`products` (
     `id` INT NOT NULL AUTO_INCREMENT,
     `name` VARCHAR(45) NOT NULL,
     `description` TEXT NULL DEFAULT NULL,
     `supplier_id` INT NOT NULL,
     `subcategory_id` INT NOT NULL,
     `quantity` INT NOT NULL,
     `price` DECIMAL(10,2) NOT NULL,
     `discount_id` INT NULL DEFAULT '0',
     PRIMARY KEY (`id`),
     INDEX `supplier_id` (`supplier_id` ASC) VISIBLE,
     INDEX `subcategory_id` (`subcategory_id` ASC) VISIBLE,
     INDEX `discount_id` (`discount_id` ASC) VISIBLE,
     CONSTRAINT `products_ibfk_1`
         FOREIGN KEY (`supplier_id`)
             REFERENCES `pet_shop`.`suppliers` (`id`),
     CONSTRAINT `products_ibfk_2`
         FOREIGN KEY (`subcategory_id`)
             REFERENCES `pet_shop`.`subcategories` (`id`),
     CONSTRAINT `products_ibfk_3`
         FOREIGN KEY (`discount_id`)
             REFERENCES `pet_shop`.`discounts` (`id`));

CREATE TABLE IF NOT EXISTS `pet_shop`.`images` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `url` VARCHAR(255) NOT NULL,
   `product_id` INT NOT NULL,
   PRIMARY KEY (`id`),
   INDEX `product_id` (`product_id` ASC) VISIBLE,
   CONSTRAINT `images_ibfk_1`
       FOREIGN KEY (`product_id`)
           REFERENCES `pet_shop`.`products` (`id`));


CREATE TABLE IF NOT EXISTS `pet_shop`.`order_statuses` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `type` VARCHAR(45) NOT NULL,
   PRIMARY KEY (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `pet_shop`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(20) NOT NULL,
  `personal_discount` DECIMAL(5,2) NULL DEFAULT '0.00',
  `town` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_at` TIMESTAMP NULL DEFAULT NULL,
  `is_subscribed` TINYINT(1) NOT NULL DEFAULT '0',
  `is_admin` TINYINT(1) NOT NULL DEFAULT '0',
  `confirmation_token` VARCHAR(200) NULL DEFAULT NULL,
  `enable` TINYINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `pet_shop`.`payment_methods` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `type` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `pet_shop`.`orders` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `user_id` INT NOT NULL,
   `status_id` INT NOT NULL,
   `payment_method_id` INT NOT NULL,
   `address` VARCHAR(45) NOT NULL,
   `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `gross_value` DECIMAL(10,2) NOT NULL,
   `discount_amount` DECIMAL(10,2) NOT NULL,
   `net_value` DECIMAL(10,2) NOT NULL,
   `is_paid` TINYINT(1) NOT NULL DEFAULT '0',
   PRIMARY KEY (`id`),
   INDEX `user_id` (`user_id` ASC) VISIBLE,
   INDEX `status_id` (`status_id` ASC) VISIBLE,
   INDEX `payment_method_id` (`payment_method_id` ASC) VISIBLE,
   CONSTRAINT `orders_ibfk_1`
       FOREIGN KEY (`user_id`)
           REFERENCES `pet_shop`.`users` (`id`),
   CONSTRAINT `orders_ibfk_2`
       FOREIGN KEY (`status_id`)
           REFERENCES `pet_shop`.`order_statuses` (`id`),
   CONSTRAINT `orders_ibfk_3`
       FOREIGN KEY (`payment_method_id`)
           REFERENCES `pet_shop`.`payment_methods` (`id`));

CREATE TABLE IF NOT EXISTS `pet_shop`.`orders_have_products` (
     `order_id` INT NOT NULL,
     `product_id` INT NOT NULL,
     PRIMARY KEY (`order_id`, `product_id`),
     INDEX `product_id` (`product_id` ASC) VISIBLE,
     CONSTRAINT `orders_have_products_ibfk_1`
         FOREIGN KEY (`order_id`)
             REFERENCES `pet_shop`.`orders` (`id`),
     CONSTRAINT `orders_have_products_ibfk_2`
         FOREIGN KEY (`product_id`)
             REFERENCES `pet_shop`.`products` (`id`));


CREATE TABLE IF NOT EXISTS `pet_shop`.`payments` (
     `id` INT NOT NULL AUTO_INCREMENT,
     `user_id` INT NOT NULL,
     `order_id` INT NOT NULL,
     `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `amount` DECIMAL(10,2) NOT NULL,
     `transaction_id` VARCHAR(45) NOT NULL,
     `processed_at` TIMESTAMP NULL DEFAULT NULL,
     `status` VARCHAR(50) NOT NULL,
     PRIMARY KEY (`id`),
     INDEX `user_id` (`user_id` ASC) VISIBLE,
     INDEX `order_id` (`order_id` ASC) VISIBLE,
     CONSTRAINT `payments_ibfk_1`
         FOREIGN KEY (`user_id`)
             REFERENCES `pet_shop`.`users` (`id`),
     CONSTRAINT `payments_ibfk_2`
         FOREIGN KEY (`order_id`)
             REFERENCES `pet_shop`.`orders` (`id`));


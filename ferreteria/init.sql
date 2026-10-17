CREATE TABLE `articulos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombreArticulo` varchar(50) NOT NULL,
  `precio` decimal(6,2) NOT NULL,
  `existencia` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

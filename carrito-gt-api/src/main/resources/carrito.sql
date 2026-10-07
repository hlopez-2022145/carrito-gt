DROP DATABASE IF EXISTS cc;

CREATE DATABASE cc
CHARACTER SET utf8
COLLATE utf8_spanish_ci;

USE cc;

CREATE TABLE producto (
    idProducto INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) DEFAULT NULL,
    codigo VARCHAR(255) NOT NULL,
    imagen TEXT,
    precio DOUBLE(10,2) DEFAULT NULL,

    PRIMARY KEY (idProducto)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_spanish_ci;


CREATE TABLE shopping_cart (
    idShoppingCart INT NOT NULL AUTO_INCREMENT,
    idProducto INT DEFAULT NULL,
    codUsuario VARCHAR(128) DEFAULT NULL,
    cantidad INT DEFAULT NULL,

    PRIMARY KEY (idShoppingCart),
    KEY cc_profk_1 (idProducto),

    CONSTRAINT cc_profk_1
        FOREIGN KEY (idProducto)
        REFERENCES producto (idProducto)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_spanish_ci;


INSERT INTO producto
    (idProducto, nombre, codigo, imagen, precio)
VALUES
    (
        1,
        'FinePix Pro2 3D Camera',
        '3DcAM01',
        'images/product/camera.jpg',
        671.21
    ),
    (
        2,
        'EXP Portable Hard Drive',
        'USB02',
        'images/product/external-hard-drive.jpg',
        215.10
    ),
    (
        3,
        'Luxury Ultra thin Wrist Watch',
        'wristWear03',
        'images/product/watch.jpg',
        2121.86
    ),
    (
        4,
        'XP 1155 Intel Core Laptop',
        'LPN45',
        'images/product/laptop.jpg',
        10232.35
    );


ALTER TABLE producto AUTO_INCREMENT = 5;


SHOW TABLES;

SELECT * FROM producto;

SELECT * FROM shopping_cart;

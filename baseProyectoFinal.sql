
-- Crear base de datos
DROP DATABASE IF EXISTS ProyectoFinal;
CREATE DATABASE ProyectoFinal;
USE ProyectoFinal;

-- Tabla roles
CREATE TABLE roles (
    id_Rol BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla clientes con rol_id ya incluido
CREATE TABLE clientes (
    id_Cliente BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100) NOT NULL,
    edad INT NOT NULL CHECK(edad >= 18),
    sexo CHAR(1) NOT NULL CHECK (sexo IN ('M','F','O')),
    numero VARCHAR(12) UNIQUE NOT NULL,
    correo_electronico VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    rol_id BIGINT,
    FOREIGN KEY (rol_id) REFERENCES roles(id_Rol)
);

-- Tabla estado_pedido
CREATE TABLE estado_pedido (
    id_estado_pedido BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(200) NOT NULL
);

-- Tabla sucursal
CREATE TABLE sucursal (
    id_Sucursal BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    direccion TEXT NOT NULL,
    telefono VARCHAR(15) NOT NULL
);

-- Tabla usuarios
CREATE TABLE usuarios (
    id_Usuario BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    correo VARCHAR(255) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    id_Rol BIGINT NOT NULL,
    FOREIGN KEY (id_Rol) REFERENCES roles(id_Rol) ON DELETE CASCADE
);

-- Tabla estado_producto
CREATE TABLE estado_producto (
    id_Estado BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(100) NOT NULL
);

-- Tabla marca
CREATE TABLE marca (
    id_Marca BIGINT PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(100) NOT NULL
);

-- Tabla categoria
CREATE TABLE categoria (
    id_Categoria BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(100) NOT NULL
);

-- Tabla productos
CREATE TABLE productos (
    id_Producto BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_Categoria BIGINT NOT NULL,
    id_Marca BIGINT NOT NULL,
    id_Estado BIGINT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    modelo VARCHAR(100),
    precio DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_Categoria) REFERENCES categoria(id_Categoria) ON DELETE CASCADE,
    FOREIGN KEY (id_Marca) REFERENCES marca(id_Marca) ON DELETE CASCADE,
    FOREIGN KEY (id_Estado) REFERENCES estado_producto(id_Estado) ON DELETE CASCADE
);

-- Tabla fotos_producto
CREATE TABLE fotos_producto (
    id_Foto BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_Producto BIGINT NOT NULL,
    urlFoto VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_Producto) REFERENCES productos(id_Producto) ON DELETE CASCADE
);

-- Tabla inventario con campos renombrados
CREATE TABLE inventario (
    id_Inventario BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_Producto BIGINT NOT NULL,
    id_Sucursal BIGINT NOT NULL,
    stock_actual INT NOT NULL,
    stock_minimo INT NOT NULL,
    fecha_registro DATE NOT NULL,
    FOREIGN KEY (id_Producto) REFERENCES productos(id_Producto) ON DELETE CASCADE,
    FOREIGN KEY (id_Sucursal) REFERENCES sucursal(id_Sucursal) ON DELETE CASCADE
);

-- Tabla movimientos_stock
CREATE TABLE movimientos_stock (
    id_Movimiento BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_Producto BIGINT NOT NULL,
    id_Sucursal BIGINT NOT NULL,
    tipoMovimiento ENUM('entrada', 'salida') NOT NULL,
    cantidad INT NOT NULL,
    fechaMovimiento DATETIME DEFAULT CURRENT_TIMESTAMP,
    motivo VARCHAR(255),
    FOREIGN KEY (id_Producto) REFERENCES productos(id_Producto) ON DELETE CASCADE,
    FOREIGN KEY (id_Sucursal) REFERENCES sucursal(id_Sucursal) ON DELETE CASCADE
);

-- Tabla carrito con id_cliente integrado
CREATE TABLE carrito (
    id_carrito BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_carrito DOUBLE,
    id_cliente BIGINT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_Cliente)
);

-- Tabla item_carrito
CREATE TABLE item_carrito (
    id_item_carrito BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    cantidad INT NOT NULL,
    id_carrito BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    FOREIGN KEY (id_carrito) REFERENCES carrito(id_carrito) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_Producto)
);

-- Tabla pedidos con id_carrito integrado y nombre de columna corregido
CREATE TABLE pedidos (
    id_Pedido BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_Cliente BIGINT NOT NULL,
    id_estado_pedido BIGINT NOT NULL,
    id_carrito BIGINT NOT NULL,
    total_compra DECIMAL(10,2) NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_Cliente) REFERENCES clientes(id_Cliente) ON DELETE CASCADE,
    FOREIGN KEY (id_estado_pedido) REFERENCES estado_pedido(id_estado_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_carrito) REFERENCES carrito(id_carrito)
);

-- Tabla detalle_pedido
CREATE TABLE detalle_pedido (
    id_Detalle BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_Pedido BIGINT NOT NULL,
    id_Producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_Pedido) REFERENCES pedidos(id_Pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_Producto) REFERENCES productos(id_Producto) ON DELETE CASCADE
);

-- Tabla pagos
CREATE TABLE pagos (
    id_Pago BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_Pedido BIGINT NOT NULL,
    fecha_pago DATETIME NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_Pedido) REFERENCES pedidos(id_Pedido) ON DELETE CASCADE
);

-- Tabla envios
CREATE TABLE envios (
    id_Envio BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_Pedido BIGINT NOT NULL,
    direccion_Envio VARCHAR(255) NOT NULL,
    empresa_Recolectora VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_Pedido) REFERENCES pedidos(id_Pedido) ON DELETE CASCADE
);

-- Tabla facturas_pdf
CREATE TABLE facturas_pdf (
    id_factura BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_Pedido BIGINT NOT NULL,
    archivo_pdf LONGBLOB NOT NULL,
    fecha_guardado VARCHAR(20),
    FOREIGN KEY (id_Pedido) REFERENCES pedidos(id_Pedido) ON DELETE CASCADE
);
-- Insertar roles
INSERT INTO roles (nombre) VALUES 
('ADMIN'), 
('CLIENTE');

-- Insertar estado_pedido
INSERT INTO estado_pedido (descripcion) VALUES 
('Pendiente'),
('En camino'),
('Entregado');

-- Insertar estado_producto
INSERT INTO estado_producto (descripcion) VALUES 
('Disponible'),
('Agotado'),
('En espera');

-- Insertar categoria
INSERT INTO categoria (tipo) VALUES 
('Electrónica'),
('Ropa'),
('Hogar');

-- Insertar marca
INSERT INTO marca (marca) VALUES 
('Samsung'),
('Apple'),
('Sony');

-- Insertar productos
INSERT INTO productos (id_Categoria, id_Marca, id_Estado, nombre, descripcion, modelo, precio) VALUES
(1, 1, 2, 'Laptop Dell XPS', 'Laptop de alto rendimiento', 'XPS 15', 18999.99),
(2, 2, 2, 'Mouse Logitech', 'Mouse inalámbrico ergonómico', 'MX Master 3', 1499.50),
(2, 3, 2, 'Teclado Mecánico Razer', 'Teclado RGB para gaming', 'BlackWidow', 3299.00),
(3, 3, 1, 'Monitor Samsung', 'Monitor 24 pulgadas Full HD', 'S24F350', 2999.99),
(1, 2, 1, 'Disco SSD Kingston', 'SSD de 1TB NVMe', 'A2000', 1899.00);




-- Insertar sucursal
INSERT INTO sucursal (nombre, direccion, telefono) VALUES 
('Sucursal Centro', 'Avenida Principal 123, Ciudad X', '555-1234'),
('Sucursal Norte', 'Calle Secundaria 456, Ciudad Y', '555-5678'),
('Sucursal Sur', 'Plaza Comercial 789, Ciudad Z', '555-9012');




-- Insertar administrador
INSERT INTO clientes (nombre, apellido_paterno, apellido_materno, edad, sexo, numero, correo_electronico, contrasena, direccion, rol_id) VALUES 
('Administrador', 'Principal', 'Sistema', 35, 'M', '5559998888', 'admin@admin.com', '$2b$12$d/wkYASkjyGznSlkL8IuZuhlU6nXQRszyeXpA7B.2VkL8JZPVAZca', 'Oficina Central', 1);


INSERT INTO inventario (id_Producto, id_Sucursal, stock_actual, stock_minimo, fecha_registro) VALUES
(1, 1, 50, 5, '2025-05-18'),
(2, 1, 25, 5, '2025-05-18'),
(3, 1, 20, 5, '2025-05-18'),
(4, 1, 15, 5, '2025-05-18'),
(5, 1, 10, 2, '2025-05-18');



SELECT * FROM inventario;
SELECT * FROM clientes;
SELECT * FROM productos;

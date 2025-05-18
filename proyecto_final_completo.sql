
-- PROYECTO FINAL - SCRIPT ORDENADO

-- DROP & CREATE DATABASE
DROP DATABASE IF EXISTS ProyectoFinal;
CREATE DATABASE ProyectoFinal;
USE ProyectoFinal;

-- TABLAS BASE
CREATE TABLE roles (
    id_Rol BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL
);

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

CREATE TABLE estado_pedido (
    id_estado_pedido BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(200) NOT NULL
);

CREATE TABLE sucursal (
    id_Sucursal BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    direccion TEXT NOT NULL,
    telefono VARCHAR(15) NOT NULL
);

CREATE TABLE estado_producto (
    id_Estado BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(100) NOT NULL
);

CREATE TABLE marca (
    id_Marca BIGINT PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(100) NOT NULL
);

CREATE TABLE categoria (
    id_Categoria BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(100) NOT NULL
);

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

CREATE TABLE fotos_producto (
    id_Foto BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_Producto BIGINT NOT NULL,
    urlFoto VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_Producto) REFERENCES productos(id_Producto) ON DELETE CASCADE
);

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

CREATE TABLE carrito (
    id_carrito BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_carrito DOUBLE,
    id_cliente BIGINT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_Cliente)
);

CREATE TABLE item_carrito (
    id_item_carrito BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT ,
    cantidad INT NOT NULL,
    id_carrito BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    FOREIGN KEY (id_carrito) REFERENCES carrito(id_carrito) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_Producto)
);

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

CREATE TABLE detalle_pedido (
    id_Detalle BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_Pedido BIGINT NOT NULL,
    id_Producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_Pedido) REFERENCES pedidos(id_Pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_Producto) REFERENCES productos(id_Producto) ON DELETE CASCADE
);

CREATE TABLE pagos (
    id_Pago BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_Pedido BIGINT NOT NULL,
    fecha_pago DATETIME NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_Pedido) REFERENCES pedidos(id_Pedido) ON DELETE CASCADE
);

CREATE TABLE envios (
    id_Envio BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_Pedido BIGINT NOT NULL,
    direccion_Envio VARCHAR(255) NOT NULL,
    empresa_Recolectora VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_Pedido) REFERENCES pedidos(id_Pedido) ON DELETE CASCADE
);

CREATE TABLE facturas_pdf (
    id_factura BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_Pedido BIGINT NOT NULL,
    archivo_pdf LONGBLOB NOT NULL,
    fecha_guardado VARCHAR(20),
    FOREIGN KEY (id_Pedido) REFERENCES pedidos(id_Pedido) ON DELETE CASCADE
);

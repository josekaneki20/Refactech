
-- Eliminación y creación de la base de datos
DROP DATABASE IF EXISTS ProyectoFinal;
CREATE DATABASE ProyectoFinal;
USE ProyectoFinal;

-- Tabla de clientes
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

-- Tabla de roles
CREATE TABLE roles (
    id_Rol BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla de usuarios
CREATE TABLE usuarios (
    id_Usuario BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    correo VARCHAR(255) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    id_Rol BIGINT NOT NULL,
    FOREIGN KEY (id_Rol) REFERENCES roles(id_Rol) ON DELETE CASCADE
);

-- Tabla de estado del pedido
CREATE TABLE estado_pedido (
    id_estado_pedido BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(200) NOT NULL
);

-- Tabla de sucursales
CREATE TABLE sucursal (
    id_Sucursal BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    direccion TEXT NOT NULL,
    telefono VARCHAR(15) NOT NULL
);

-- Tabla de estado del producto
CREATE TABLE estado_producto (
    id_Estado BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(100) NOT NULL
);

-- Tabla de marcas
CREATE TABLE marca (
    id_Marca BIGINT PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(100) NOT NULL
);

-- Tabla de categorías
CREATE TABLE categoria (
    id_Categoria BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(100) NOT NULL
);

-- Tabla de productos
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

-- Tabla de fotos del producto
CREATE TABLE fotos_producto (
    id_Foto BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_Producto BIGINT NOT NULL,
    urlFoto VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_Producto) REFERENCES productos(id_Producto) ON DELETE CASCADE
);

-- Tabla de inventario
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

-- Tabla de movimientos de stock
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

-- Tabla de carrito
CREATE TABLE carrito (
    id_carrito BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_carrito DOUBLE,
    id_cliente BIGINT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_Cliente)
);

-- Tabla de ítems en el carrito
CREATE TABLE item_carrito (
    id_item_carrito BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    cantidad INT NOT NULL,
    id_carrito BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    FOREIGN KEY (id_carrito) REFERENCES carrito(id_carrito) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_Producto)
);

-- Tabla de pedidos
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

-- Tabla de detalle de pedido
CREATE TABLE detalle_pedido (
    id_Detalle BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_Pedido BIGINT NOT NULL,
    id_Producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_Pedido) REFERENCES pedidos(id_Pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_Producto) REFERENCES productos(id_Producto) ON DELETE CASCADE
);

-- Tabla de pagos
CREATE TABLE pagos (
    id_Pago BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_Pedido BIGINT NOT NULL,
    fecha_pago DATETIME NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_Pedido) REFERENCES pedidos(id_Pedido) ON DELETE CASCADE
);

-- Tabla de envíos
CREATE TABLE envios (
    id_Envio BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_Pedido BIGINT NOT NULL,
    direccion_Envio VARCHAR(255) NOT NULL,
    empresa_Recolectora VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_Pedido) REFERENCES pedidos(id_Pedido) ON DELETE CASCADE
);

-- Tabla de facturas PDF
CREATE TABLE facturas_pdf (
    id_factura BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_Pedido BIGINT NOT NULL,
    archivo_pdf LONGBLOB NOT NULL,
    fecha_guardado VARCHAR(20),
    FOREIGN KEY (id_Pedido) REFERENCES pedidos(id_Pedido) ON DELETE CASCADE
);


-- Insertar roles
INSERT INTO roles (nombre) VALUES ('ADMIN'), ('CLIENTE');

-- Insertar clientes (sin especificar rol inicialmente)
INSERT INTO Clientes (nombre, apellido_paterno, apellido_materno, edad, sexo, numero, correo_electronico, contrasena, direccion) 
VALUES 
('Juan', 'García', 'Pérez', 30, 'M', '5551234567', 'juan.perez@example.com', 'clave123', 'Calle 123, CDMX'),
('Ana', 'López', 'Martínez', 25, 'F', '5557654321', 'ana.lopez@example.com', 'segura456', 'Avenida Siempre Viva 742, GDL'),
('Carlos', 'Fernández', 'Ruiz', 40, 'M', '5559876543', 'carlos.ruiz@example.com', 'password789', 'Calle Reforma 345, MTY');

-- Insertar cliente con rol explícito
INSERT INTO clientes (
    nombre, apellido_paterno, apellido_materno, edad, sexo, numero, 
    correo_electronico, contrasena, direccion, rol_id
) VALUES (
    'Liam', 'Anderson', 'Walker', 28, 'M', '5559876543',
    'liam.anderson@example.com', 'liam123', '742 Evergreen Terrace', 2
);

-- Insertar cliente admin
INSERT INTO clientes (nombre, apellido_paterno, apellido_materno, edad, sexo, numero, correo_electronico, contrasena, direccion, rol_id)
VALUES ('Administrador', 'Principal', 'Sistema', 35, 'M', '5559998888', 'admin@admin.com', 'admin123', 'Oficina Central', 1);

-- Asignar rol por defecto a clientes sin rol
UPDATE clientes SET rol_id = 2 WHERE rol_id IS NULL;

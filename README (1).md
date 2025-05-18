# 🛒 RefacTech - Sistema de E-commerce

Este proyecto es una plataforma de comercio electrónico desarrollada en Java con Spring Boot, diseñada para manejar productos tecnológicos. Incluye autenticación JWT, gestión de clientes, trabajadores y control de inventario. El sistema ofrece flujos distintos para clientes y administradores.

---

## 🔄 Flujo General del Proyecto

### 👤 Cliente

1. **Inicio**
   - Ingresa a la página principal (`/`).
   - Puede visualizar las opciones de inicio de sesion,registrarse y ver el catalogo de productos

2. **Registro/Login**
   - Si no tiene cuenta, se registra mediante el botón de “Registrar”.
   - Si ya tiene cuenta, se autentica desde el login (`/auth/loginJwt`).
   - Al iniciar sesión correctamente, se redirige al catálogo de productos.

3. **Catálogo de Productos**
   - Visualiza los productos con su precio, stock y sucursal.
   - Agrega productos al carrito.

4. **Carrito de Compras**
   - Visualiza los productos seleccionados, puede eliminar o modificar cantidades.
   - Finaliza la compra y se genera un pedido.

5. **Formulario de Pago**
   - Ingresa los datos de tarjeta (no se almacenan).
   - Al confirmar pago:
     - Se genera una opcion para descargar PDF de la factura.
     - El carrito se vacía.
     - Se muestra un botón para volver al catálogo.

6. **Mis Pedidos**
   - Consulta todos sus pedidos y su estado.

---

### 🛠️ Trabajador / Administrador

1. **Login**
   - Accede desde el mismo formulario de login (se detecta rol automáticamente).

2. **Página de Inicio de Trabajador (`/trabajador/inicio`)**
   - Bienvenida personalizada.
   - Botones para:
     - Ver Inventario
     - Gestionar Pedidos
     - Ver catálogo de clientes

3. **Inventario (`/almacen`)**
   - Muestra todos los productos, sucursales, stock actual y mínimo.
   - Genera la opcion para descargar el PDF del inventario completo.

4. **Gestión de Pedidos (`/pedidos/admin`)**
   - Visualiza todos los pedidos de todos los clientes.
   - Puede cambiar el estado de los pedidos mediante un dropdown.

5. **Catálogo de Productos (`/productos/muestraCatalogo`)**
   - Permite crear, modificar y eliminar productos.
   - Al modificar, también se pueden editar los valores del stock.

6. **Catálogo de Clientes**
   - Visualiza la lista de todos los clientes registrados.
   - Puede modificarlos o eliminarlos.
   - Botón para volver al inicio del trabajador.

---

## 🔐 Seguridad

- Autenticación mediante JWT (almacenado en cookies).
- Diferentes roles (`CLIENTE`, `ADMIN`).
- Filtro de seguridad `JwtAuthenticationFilter` protege todas las rutas excepto las públicas.
- Rutas divididas por rol.
- Logout borra el JWT y redirige a `/`.

---

## ⚙️ Tecnologías

- Java 17
- Spring Boot
- Spring Security (JWT)
- Thymeleaf
- JPA (Hibernate)
- MySQL
- openPdf

---


Configura tu `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/proyecto_final
spring.datasource.username=root
spring.datasource.password=1234
```
nota-- en esta parte del configuration properties puse el ejemplo de la forma en como lo utilice yo,
para tu caso, puedes configurar la ruta y contraseña de tu abse de datos donde tu deseas.
---


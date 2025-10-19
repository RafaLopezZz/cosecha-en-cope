# 🌱 Cosecha en Cope - Plataforma Full-Stack

![Angular](https://img.shields.io/badge/Angular-19.2.0-red)
![TypeScript](https://img.shields.io/badge/TypeScript-5.5.2-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.2-green)
![Java](https://img.shields.io/badge/Java-17-orange)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3.7-purple)

**Cosecha en Cope** es una plataforma integral de comercio electrónico full-stack especializada en productos agrícolas locales. Implementa una arquitectura moderna cliente-servidor que facilita la conexión directa entre productores y consumidores a través de una interfaz web moderna, responsiva y segura, soportada por un backend robusto basado en Spring Boot.

## 📋 Tabla de Contenidos

- [🎯 Descripción del Proyecto](#-descripción-del-proyecto)
- [💻 Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [🏗️ Arquitectura de la Aplicación](#️-arquitectura-de-la-aplicación)
- [🚀 Requisitos del Sistema](#-requisitos-del-sistema)
- [📁 Estructura del Proyecto](#-estructura-del-proyecto)
- [🔐 Seguridad y Autenticación](#-seguridad-y-autenticación)
- [📡 Comunicación con APIs](#-comunicación-con-apis)
- [🎨 Interfaz de Usuario y Features](#-interfaz-de-usuario-y-features)
- [⚙️ Arquitectura del Backend](#️-arquitectura-del-backend)
- [🔧 Configuración de Entornos](#-configuración-de-entornos)
- [📞 Contacto](#-contacto)

---

## 🎯 Descripción del Proyecto

**Cosecha en Cope** es una solución integral de comercio electrónico diseñada específicamente para conectar productores agrícolas locales con consumidores finales. El proyecto aborda la necesidad de crear canales directos de distribución para productos agrícolas frescos, eliminando intermediarios y mejorando tanto la experiencia de compra del consumidor como la oportunidad de negocio para los productores.

### Objetivos Principales

- Facilitar la comercialización directa de productos agrícolas locales
- Proporcionar herramientas de gestión integral para productores
- Crear una experiencia de compra segura, intuitiva y accesible
- Implementar un sistema de autenticación y autorización robusto
- Optimizar el almacenamiento y gestión de recursos multimedia en la nube

### Funcionalidades Clave

- **Catálogo de Productos Dinámico**: Sistema completo de presentación de productos con filtros avanzados, búsqueda por categorías y detalles detallados de cada artículo
- **Carrito de Compras Inteligente**: Gestión del carrito con persistencia de datos y actualización en tiempo real de precios
- **Gestión de Pedidos**: Sistema de seguimiento de pedidos, historial de compras y estado de entregas
- **Panel Administrativo para Productores**: Dashboard completo con estadísticas de ventas, gestión de inventario, administración de productos y categorías
- **Autenticación Diferenciada**: Sistemas de login y registro separados para clientes y productores, con funcionalidades específicas para cada rol
- **Gestor de Perfiles**: Administración de datos personales, información empresarial y preferencias de usuario
- **Integración Cloud**: Carga y gestión de imágenes de productos directamente en AWS S3
- **Diseño Responsivo**: Interfaz completamente adaptada a dispositivos móviles, tablets y computadoras de escritorio

---

## 💻 Tecnologías Utilizadas

### Frontend

| Componente | Versión | Descripción |
|-----------|---------|------------|
| **Angular** | 19.2.0 | Framework web moderno de código abierto para aplicaciones de una sola página |
| **TypeScript** | 5.5.2 | Lenguaje tipado que se construye sobre JavaScript para mayor seguridad en desarrollo |
| **Bootstrap** | 5.3.7 | Framework CSS para diseño responsivo y componentes prediseñados |
| **RxJS** | - | Librería de programación reactiva para manejo de operaciones asincrónicas |
| **Angular Material** | - | Componentes UI precompilados siguiendo directrices Material Design |

### Backend

| Componente | Versión | Descripción |
|-----------|---------|------------|
| **Spring Boot** | 3.5.2 | Framework de aplicación Java para crear aplicaciones web robustas |
| **Java** | 21 | Lenguaje de programación con características modernas y mejoras de rendimiento |
| **Spring Security** | - | Framework para autenticación y autorización |
| **JWT (JSON Web Tokens)** | - | Estándar de token para autenticación segura entre cliente y servidor |

### Infraestructura

| Componente | Descripción |
|-----------|------------|
| **PostgreSQL** | Base de datos relacional para almacenamiento persistente |
| **JPA/Hibernate** | Mapeo objeto-relacional para interacción con la base de datos |
| **AWS S3** | Servicio de almacenamiento en la nube para imágenes y recursos multimedia |

---

## 🏗️ Arquitectura de la Aplicación

### Modelo Arquitectónico General

La aplicación implementa una arquitectura distribuida cliente-servidor con separación clara de responsabilidades:

- **Capa de Presentación (Frontend Angular)**: Proporciona una interfaz interactiva y responsiva para usuarios finales
- **Capa de API REST (Backend Spring Boot)**: Gestiona la lógica empresarial, validaciones y orquestación de datos
- **Capa de Datos (PostgreSQL)**: Almacenamiento persistente de toda la información del sistema
- **Almacenamiento en Nube (AWS S3)**: Gestión centralizada de archivos multimedia

### Patrón de Arquitectura Frontend

El frontend Angular implementa una **arquitectura modular por features** que proporciona los siguientes beneficios:

- **Escalabilidad**: Cada feature es independiente y puede crecer sin afectar otras partes
- **Mantenibilidad**: Código organizado y fácil de localizar
- **Reutilización**: Componentes y servicios compartidos en una capa dedicada
- **Separación de Responsabilidades**: Distinción clara entre presentación, lógica de negocio e integración

#### Estructura de Capas Frontend

- **Core Module**: Centraliza servicios globales, configuración, guards de navegación e interceptores HTTP. Los servicios aquí son singleton y se instancian una única vez en toda la aplicación
- **Shared Module**: Contiene componentes reutilizables, modelos de datos e interfaces TypeScript que se utilizan en múltiples features
- **Features Module**: Agrupa funcionalidades específicas del negocio en módulos independientes, cada uno con su propia estructura
- **Environments**: Gestiona configuraciones específicas por entorno (desarrollo, producción)

### Patrón de Arquitectura Backend (Spring Boot)

El backend Spring Boot implementa una **arquitectura en capas** altamente modular:

- **Controller Layer**: Maneja las peticiones HTTP y enruta hacia los servicios correspondientes
- **Service Layer**: Contiene la lógica empresarial principal del sistema
- **Repository Layer**: Abstrae el acceso a la base de datos mediante JPA/Hibernate
- **Model Layer**: Define las entidades y estructuras de datos
- **Security Layer**: Implementa autenticación JWT y control de acceso
- **Config Layer**: Proporciona configuración centralizada (CORS, seguridad, etc.)
- **Exception Layer**: Manejo global y consistente de excepciones

#### Componentes Principales del Backend

**Controllers**:
- `AuthController`: Gestiona autenticación, login y registro de usuarios
- `ArticuloController`: Operaciones CRUD de productos
- `CategoriaController`: Gestión de categorías de productos
- `ProductorController`: Administración de datos de productores
- `ClienteController`: Operaciones de clientes
- `PedidoController`: Gestión de pedidos y órdenes
- `CarritoController`: Operaciones del carrito de compras
- `UploadController`: Gestión de subida de archivos a AWS S3

**Services**:
- `ArticuloService`: Lógica de gestión de productos con validaciones
- `CategoriaService`: Operaciones sobre categorías
- `ProductorService`: Gestión integral de productores
- `ClienteService`: Gestión de datos de clientes
- `PedidoService`: Orquestación de pedidos y órdenes de venta
- `CarritoService`: Gestión del carrito de compras
- `S3FileStorageService`: Integración con AWS S3
- `UsuarioService`: Gestión de usuarios y autenticación
- `UserDetailsServiceImpl`: Implementación personalizada de Spring Security
- `SeoService`: Optimización para motores de búsqueda

**Repositories**:
- `ArticuloRepository`: Acceso a datos de productos
- `CategoriaRepository`: Acceso a datos de categorías
- `ProductorRepository`: Acceso a datos de productores
- `ClienteRepository`: Acceso a datos de clientes
- `PedidoRepository`: Acceso a datos de pedidos
- `UsuarioRepository`: Acceso a datos de usuarios
- `CarritoRepository`: Acceso a datos del carrito

**Security**:
- `JwtUtils`: Generación y validación de tokens JWT
- `AuthTokenFilter`: Filtro HTTP para validar tokens JWT
- `AuthEntryPointJwt`: Punto de entrada para errores de autenticación
- `UserDetailsImpl`: Implementación de UserDetails para Spring Security

**Exception Handling**:
- `GlobalExceptionHandler`: Manejo centralizado de excepciones con `@RestControllerAdvice`
- `ResourceNotFoundException`: Excepción para recursos no encontrados
- `InvalidPasswordException`: Excepción para contraseñas inválidas
- `StockInsuficienteException`: Excepción para stock insuficiente

### Flujo de Datos Full-Stack

El flujo típico de una petición en el sistema es:

1. **Frontend**: El usuario interactúa con un componente Angular
2. **Angular Service**: El componente utiliza un servicio para llamar a la API
3. **AuthInterceptor**: El interceptor añade el token JWT automáticamente
4. **Spring Controller**: La petición llega a un controlador Spring Boot
5. **Validación**: Se validan los datos y permisos del usuario
6. **Service Layer**: La lógica empresarial se ejecuta en el servicio
7. **Repository**: Se accede a la base de datos a través de JPA
8. **Database**: PostgreSQL almacena/recupera los datos
9. **Response**: La respuesta viaja de vuelta al frontend
10. **Component Update**: El componente Angular actualiza la UI

### Principios de Diseño Aplicados

- **Standalone Components**: Uso de componentes independientes sin NgModules, aprovechando la arquitectura moderna de Angular
- **Dependency Injection Moderno**: Sistema de inyección de dependencias con la función `inject()` para mayor flexibilidad
- **Programación Reactiva**: Uso extensivo de RxJS para manejo de flujos de datos asincronos
- **TypeScript First**: Tipado estricto en toda la aplicación para prevenir errores en tiempo de desarrollo
- **Barrel Exports**: Exportaciones centralizadas para mantener las importaciones limpias y organizadas
- **Clean Code**: Código limpio y mantenible siguiendo principios SOLID en ambas capas

---

## 🚀 Requisitos del Sistema

Para ejecutar y desarrollar la aplicación, se requieren los siguientes componentes:

- **Node.js**: Versión 18 o superior (incluye npm)
- **npm**: Versión 9 o superior (gestor de paquetes de Node.js)
- **Angular CLI**: Versión 19 o superior (herramienta de línea de comandos)
- **Backend**: Servidor Spring Boot ejecutándose en el puerto 8081
- **Navegador Moderno**: Chrome, Firefox, Safari o Edge con versiones recientes
- **Git**: Para control de versiones y gestión del repositorio

---

## 📁 Estructura del Proyecto

### Organización General del Código

La aplicación está estructurada siguiendo principios de modularidad y separación de responsabilidades:

```
src/app/
├── core/
│   ├── config/
│   │   └── Configuración centralizada de endpoints API y constantes globales
│   ├── guards/
│   │   └── Guardias de navegación para proteger rutas
│   ├── interceptors/
│   │   └── Interceptores HTTP para inyección de tokens y manejo centralizado de errores
│   └── services/
│       └── Servicios globales singleton para toda la aplicación
├── shared/
│   ├── components/
│   │   └── Componentes reutilizables agnósticos al dominio del negocio
│   └── models/
│       └── Interfaces y tipos TypeScript para comunicación API
├── features/
│   ├── auth/
│   │   └── Funcionalidades de autenticación y login
│   ├── home/
│   │   └── Landing page y navegación principal
│   ├── articulos/
│   │   └── Gestión del catálogo de productos
│   └── productor/
│       └── Panel administrativo para productores
└── environments/
    └── Configuraciones específicas por entorno
```

### Core Module - Servicios Disponibles

El módulo Core centraliza funcionalidades transversales:

- **AuthService**: Manejo de autenticación, login, registro y gestión de sesiones
- **ArticuloService**: Operaciones CRUD de productos
- **CategoriaService**: Gestión de categorías de productos
- **ProductorService**: Gestión de datos de productores y perfiles empresariales
- **ClienteService**: Operaciones relacionadas con clientes
- **PedidoService**: Gestión de pedidos y seguimiento
- **CarritoService**: Operaciones del carrito de compras
- **UploadService**: Gestión de subida de archivos a AWS S3
- **UserStoreService**: Almacén reactivo del estado del usuario autenticado

### Shared Module - Componentes Reutilizables

- **ImageUploadComponent**: Componente para la carga de imágenes a AWS S3 con previsualización

### Features Principales

#### **Feature: Auth** (`features/auth/`)
Maneja toda la autenticación del sistema incluyendo:
- Pantalla de login con validación de credenciales
- Sistema de registro para clientes y productores
- Manejo de errores de autenticación
- Recuperación de contraseña

#### **Feature: Home** (`features/home/`)
Proporciona la página de inicio e interfaz de navegación:
- Landing page con información principal
- Barra de navegación con menú responsivo
- Sección hero con call-to-action
- Secciones de características destacadas
- Carrusel de galería de productos
- Testimonios de usuarios
- Pie de página con información de contacto

#### **Feature: Articulos** (`features/articulos/`)
Gestión del catálogo de productos:
- Listado de productos con filtros y búsqueda
- Vista detallada de cada producto
- Gestión del carrito de compras
- Historial de pedidos

#### **Feature: Productor** (`features/productor/`)
Panel administrativo exclusivo para productores:
- Dashboard con estadísticas de ventas
- Gestión de perfil empresarial
- Administración de categorías de productos
- CRUD completo de productos
- Carga de imágenes para productos
- Seguimiento de inventario

### Convenciones de Nomenclatura

La aplicación sigue convenciones consistentes para mantener legibilidad:

| Tipo | Convención | Ejemplo |
|------|------------|---------|
| Componentes | kebab-case | `user-profile.component.ts` |
| Servicios | camelCase + .service | `auth.service.ts` |
| Guards | camelCase + .guard | `auth.guard.ts` |
| Modelos | camelCase + .models | `user.models.ts` |
| Interfaces | PascalCase | `User`, `Product` |
| Constantes | UPPER_SNAKE_CASE | `API_ENDPOINTS` |
| Variables privadas | # o _ | `#privateVariable` |

---

## 🔐 Seguridad y Autenticación

### Sistema de Autenticación con JWT

La aplicación implementa un sistema robusto de autenticación basado en JSON Web Tokens para garantizar la seguridad en la comunicación entre cliente y servidor:

#### Proceso de Autenticación

1. El usuario ingresa sus credenciales en la pantalla de login
2. Las credenciales se transmiten de forma segura al servidor Spring Boot
3. El servidor valida las credenciales contra la base de datos
4. Si son válidas, el servidor genera un token JWT que contiene información del usuario
5. El token se devuelve al cliente y se almacena en la sesión del navegador
6. En peticiones posteriores, el token se incluye automáticamente en los encabezados de autorización
7. El servidor valida el token antes de procesar cualquier solicitud

#### Tipos de Usuario

El sistema define tres tipos de usuario con permisos diferenciados:

- **CLIENTE**: Acceso a funcionalidades de compra, gestión de perfil personal, carrito y historial de pedidos
- **PRODUCTOR**: Acceso al panel de administración, gestión de productos, categorías e inventario
- **ADMIN**: Acceso total al sistema con capacidades de administración general

#### Almacenamiento de Estado

El estado de autenticación se gestiona de forma reactiva mediante un servicio dedicado que mantiene sincronizado el estado en toda la aplicación. Esto permite que los componentes se actualicen automáticamente cuando cambia el estado de autenticación.

### Protección de Rutas con Guards

Para garantizar el acceso autorizado a diferentes secciones de la aplicación:

#### AuthGuard

Este guard protege las rutas que requieren autenticación. Verifica que el usuario tenga una sesión activa. Si el usuario intenta acceder a una ruta protegida sin estar autenticado, es redirigido automáticamente a la página de login.

#### RoleGuard

Complementa el AuthGuard añadiendo validaciones basadas en el rol del usuario. Verifica que el usuario autenticado tenga el tipo requerido para acceder a una ruta específica. Por ejemplo, la ruta del dashboard de productores solo es accesible para usuarios con rol de productor.

### Interceptores HTTP para Seguridad

#### AuthInterceptor

Inyecta automáticamente el token JWT en todas las peticiones que requieren autenticación. Esto elimina la necesidad de añadir manualmente el token a cada llamada HTTP desde los servicios.

#### ErrorInterceptor

Maneja centralizadamente los errores HTTP devueltos por el servidor. Aplicación lógica específica según el tipo de error (por ejemplo, redirigir a login si el token ha expirado).

---

## 📡 Comunicación con APIs

### Configuración Centralizada de Endpoints

Todos los endpoints de la API REST están centralizados en un archivo de configuración global. Esta organización facilita el mantenimiento y permite cambiar URLs de endpoints en un único lugar sin modificar múltiples archivos.

Los endpoints se agrupan por dominio funcional:

- **Autenticación**: Login, registro de clientes, registro de productores
- **Productos**: Obtener catálogo, buscar, obtener detalles
- **Categorías**: Operaciones CRUD de categorías
- **Productores**: Gestión de perfiles y estadísticas
- **Clientes**: Operaciones específicas de clientes
- **Pedidos**: Gestión de historial y estado
- **Carrito**: Operaciones del carrito
- **Almacenamiento**: URLs firmadas para S3

### Patrones de Comunicación

Los servicios Angular implementan patrones consistentes para comunicarse con la API REST. Cada servicio encapsula las operaciones HTTP relacionadas con un dominio específico y proporciona métodos observables que los componentes pueden suscribirse. Esto mantiene la lógica de acceso a datos separada de la presentación.

---

## 🎨 Interfaz de Usuario y Features

### Componentes Standalone

La aplicación utiliza componentes standalone de Angular 19, que proporciona mayor flexibilidad y modularidad sin necesidad de NgModules.

### Landing Page

La página de inicio presenta:
- **Hero Section**: Presentación principal del proyecto con call-to-action destacado
- **Features Section**: Descripción de características clave del sistema
- **Gallery Carousel**: Carrusel interactivo de productos destacados
- **Testimonials Section**: Opiniones de usuarios satisfechos
- **Footer**: Información de contacto y enlaces útiles

### Sistema de Autenticación

- **Sign In**: Interfaz de login diferenciada para clientes y productores
- **Sign Up**: Formularios de registro con validaciones completas
- **Error Handling**: Manejo elegante y claro de errores de autenticación

### Panel de Productores

- **Dashboard**: Resumen visual de estadísticas de ventas y actividad
- **Perfil**: Formulario de gestión de datos empresariales
- **Categorías**: Interfaz CRUD para gestionar categorías de productos
- **Productos**: Gestión completa de productos con carga de imágenes

### E-commerce

- **Catálogo**: Listado de productos con filtros y búsqueda avanzada
- **Carrito**: Gestión del carrito con actualización en tiempo real
- **Checkout**: Proceso de compra seguro
- **Historial de Pedidos**: Seguimiento de compras anteriores

### Componentes Reutilizables

- **ImageUploadComponent**: Componente para carga de imágenes a AWS S3 con validación y previsualización

---

## ⚙️ Arquitectura del Backend

### Descripción General

El backend de **Cosecha en Cope** es una aplicación Spring Boot que proporciona una API REST robusta y segura. Gestiona toda la lógica empresarial, la persistencia de datos y la integración con servicios externos como AWS S3.

### Stack Tecnológico Backend

| Componente | Versión | Uso |
|-----------|---------|-----|
| Spring Boot | 3.5.2 | Framework principal para aplicación web |
| Java | 17 | Lenguaje de programación |
| Spring Data JPA | - | Acceso a datos con Hibernate |
| Spring Security | - | Autenticación y autorización |
| JWT (JJWT) | 0.11.5 | Tokens seguros para autenticación stateless |
| PostgreSQL | 15+ | Base de datos relacional |
| Lombok | - | Reducción de boilerplate code |
| AWS SDK S3 | 2.20.0 | Integración con almacenamiento en nube |
| SpringDoc OpenAPI | 2.8.8 | Documentación automática de API (Swagger) |
| Thymeleaf | - | Renderizado del lado del servidor |

### Entidades de Base de Datos

El backend gestiona las siguientes entidades principales:

- **Usuario**: Registro central de usuarios con campos: id, email, contraseña (hasheada), tipo (CLIENTE/PRODUCTOR/ADMIN), estado
- **Productor**: Extensión de Usuario con datos empresariales: razón social, NIF, dirección, teléfono, descripción
- **Cliente**: Extensión de Usuario con datos personales: nombre, apellidos, dirección de envío
- **Articulo**: Productos disponibles en la plataforma: nombre, descripción, precio, stock, imagen, categoría, productor
- **Categoria**: Clasificación de productos: nombre, descripción
- **Carrito**: Carrito de compras temporal: usuario, items, estado
- **DetalleCarrito**: Líneas individuales del carrito: artículo, cantidad, precio
- **Pedido**: Orden de compra finalizada: usuario, fecha, total, estado
- **DetallePedido**: Líneas individuales del pedido: artículo, cantidad, precio unitario
- **OrdenVentaProductor**: Órdenes de venta desde la perspectiva del productor: productor, estado, fecha
- **DetalleOvp**: Detalles de las órdenes de venta: artículos, cantidades

### Endpoints Principales

El backend expone los siguientes grupos de endpoints:

#### Autenticación
- `POST /auth/login`: Login para cualquier usuario
- `POST /auth/registro/clientes`: Registro de nuevos clientes
- `POST /auth/registro/productores`: Registro de nuevos productores
- `POST /auth/logout`: Cierre de sesión (invalidar token)

#### Productos (Artículos)
- `GET /articulos`: Listar todos los productos
- `GET /articulos/{id}`: Obtener detalle de un producto
- `POST /articulos`: Crear nuevo producto (solo productores)
- `PUT /articulos/{id}`: Actualizar producto (solo propietario)
- `DELETE /articulos/{id}`: Eliminar producto (solo propietario)

#### Categorías
- `GET /categorias`: Listar todas las categorías
- `GET /categorias/{id}`: Obtener categoría específica
- `GET /categorias/{id}/articulos`: Artículos de una categoría
- `POST /categorias`: Crear nueva categoría
- `PUT /categorias/{id}`: Actualizar categoría
- `DELETE /categorias/{id}`: Eliminar categoría

#### Carrito
- `POST /carrito/agregar`: Agregar artículo al carrito
- `GET /carrito`: Obtener contenido del carrito actual
- `DELETE /carrito/item/{id}`: Eliminar artículo del carrito
- `PUT /carrito/item/{id}`: Actualizar cantidad de artículo
- `DELETE /carrito`: Vaciar carrito completo

#### Pedidos
- `POST /pedidos`: Crear nuevo pedido
- `GET /pedidos`: Obtener pedidos del usuario
- `GET /pedidos/{id}`: Detalle de pedido específico
- `PUT /pedidos/{id}/estado`: Actualizar estado de pedido

#### Productores
- `GET /productores`: Listar todos los productores
- `GET /productores/{id}`: Obtener detalles de productor
- `GET /productores/usuario/{userId}`: Obtener productor por ID de usuario
- `PUT /productores/usuario/{userId}`: Actualizar datos de productor
- `GET /productores/{id}/estadisticas`: Estadísticas de ventas

#### Clientes
- `GET /clientes/{id}`: Obtener detalles de cliente
- `GET /clientes/usuario/{userId}`: Obtener cliente por ID de usuario
- `PUT /clientes/usuario/{userId}`: Actualizar datos de cliente

#### Upload/AWS S3
- `GET /upload/presigned-url`: Obtener URL firmada para subida
- `DELETE /upload/{fileKey}`: Eliminar archivo de S3

### Seguridad Backend

#### Autenticación JWT

El backend utiliza JWT (JSON Web Tokens) para una autenticación stateless:

1. El usuario envía credenciales al endpoint de login
2. El servidor valida credenciales y genera un token JWT firmado
3. El token contiene información del usuario (id, email, roles) y una fecha de expiración
4. El cliente almacena el token y lo envía en futuras peticiones
5. Un filtro HTTP (`AuthTokenFilter`) valida el token en cada petición protegida
6. Si el token es inválido o ha expirado, se rechaza la petición

#### Control de Acceso

- **Autenticación**: Verificación de identidad mediante usuario/contraseña
- **Autorización**: Control basado en roles (CLIENTE, PRODUCTOR, ADMIN)
- **Validación de Propiedad**: Verificar que el usuario puede modificar solo sus propios recursos
- **Manejo de Excepciones**: Respuestas HTTP consistentes (401 Unauthorized, 403 Forbidden)

#### Almacenamiento Seguro de Contraseñas

Las contraseñas se almacenan hasheadas utilizando algoritmos seguros (bcrypt o similar) proporcionados por Spring Security.

### Flujo de Datos

#### Petición HTTP típica

1. Cliente realiza petición HTTP con token JWT en header
2. `AuthTokenFilter` intercepta la petición
3. Se valida y extrae el token JWT
4. Se obtiene información del usuario desde el token
5. La petición llega al controlador correspondiente
6. El controlador valida parámetros y permisos
7. El servicio ejecuta la lógica empresarial
8. Se accede a la base de datos a través del repositorio
9. Los datos se transforman en DTOs
10. Se devuelve respuesta JSON al cliente

#### Gestión de Errores

El backend utiliza `@RestControllerAdvice` para manejo centralizado de excepciones:

- Excepciones de negocio se lanzan desde los servicios
- Se convierten en respuestas HTTP apropiadas
- Incluyen mensajes claros para el cliente
- Se registran en logs para debugging

### Integración con AWS S3

El servicio `S3FileStorageService` proporciona:

- **URLs Firmadas**: Genera URLs temporales con permisos limitados para subida de archivos
- **Subida Directa**: Los clientes suben directamente a S3 sin pasar por el servidor
- **Eliminación**: Elimina archivos cuando ya no se necesitan
- **Gestión de Recursos**: Organiza archivos en carpetas dentro del bucket

### Base de Datos PostgreSQL

La persistencia de datos se gestiona mediante:

- **JPA/Hibernate**: ORM que mapea objetos Java a tablas SQL
- **Migraciones**: Schema de base de datos con relaciones definidas
- **Consultas Nativas**: Queries personalizadas cuando es necesario
- **Transacciones**: Control de consistencia de datos

### Documentación de API

El backend genera documentación automática accesible en:

- **Swagger UI**: `http://localhost:8081/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8081/v3/api-docs`

Permite explorar y probar todos los endpoints directamente desde el navegador.

---

### Gestión de Entornos

La aplicación soporta múltiples entornos con configuraciones específicas:

#### Entorno de Desarrollo

Configuración para desarrollo local con acceso a servidor backend local y bucket S3 de desarrollo.

#### Entorno de Producción

Configuración para producción con acceso a API remota y almacenamiento S3 de producción.

Cada entorno tiene su propio archivo de configuración que especifica:
- URL base de la API
- Región de AWS
- Nombre del bucket S3
- Variables específicas del entorno

---

## 📞 Contacto

Para consultas, reportes de bugs o sugerencias sobre el proyecto:

- **Desarrollador**: Rafael López
- **GitHub**: [RafaLopezZz](https://github.com/RafaLopezZz)
- **Proyecto**: [Cosecha en Cope](https://github.com/RafaLopezZz/cosecha-en-cope)

---

**© 2025 Cosecha en Cope. Todos los derechos reservados.**

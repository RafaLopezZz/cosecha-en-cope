# Configuración Global de Endpoints - Cosecha en Cope

Este documento describe cómo utilizar la configuración global de endpoints centralizada en la aplicación Angular.

## Estructura de Archivos

```
src/app/core/config/
├── global.ts          # Configuración principal de endpoints
├── index.ts           # Barrel export
```

## Uso Básico

### Importación

```typescript
import { API_ENDPOINTS, HTTP_METHODS, buildAuthHeaders } from '@core/config';
```

### Endpoints Disponibles

Los endpoints están organizados por módulos correspondientes a los controladores del backend:

#### Autenticación
```typescript
API_ENDPOINTS.AUTH.LOGIN
API_ENDPOINTS.AUTH.LOGIN_PRODUCTORES
API_ENDPOINTS.AUTH.REGISTRO_CLIENTES
API_ENDPOINTS.AUTH.REGISTRO_PRODUCTORES
```

#### Artículos
```typescript
API_ENDPOINTS.ARTICULOS.GET_ALL
API_ENDPOINTS.ARTICULOS.GET_BY_ID(123)
API_ENDPOINTS.ARTICULOS.CREATE
API_ENDPOINTS.ARTICULOS.UPDATE(123)
API_ENDPOINTS.ARTICULOS.DELETE(123)
```

#### Categorías
```typescript
API_ENDPOINTS.CATEGORIAS.GET_ALL
API_ENDPOINTS.CATEGORIAS.GET_BY_ID(123)
API_ENDPOINTS.CATEGORIAS.GET_ARTICULOS_BY_CATEGORIA(123)
API_ENDPOINTS.CATEGORIAS.CREATE
API_ENDPOINTS.CATEGORIAS.UPDATE(123)
API_ENDPOINTS.CATEGORIAS.DELETE(123)
```

#### Carrito
```typescript
API_ENDPOINTS.CARRITO.ADD_ITEM
API_ENDPOINTS.CARRITO.GET_CART
API_ENDPOINTS.CARRITO.DECREMENT_ITEM(123)
API_ENDPOINTS.CARRITO.CLEAR_CART
```

#### Productores
```typescript
API_ENDPOINTS.PRODUCTORES.GET_ALL
API_ENDPOINTS.PRODUCTORES.GET_BY_USER_ID(123)
API_ENDPOINTS.PRODUCTORES.UPDATE_BY_USER_ID(123)
```

#### Clientes
```typescript
API_ENDPOINTS.CLIENTES.GET_ALL
API_ENDPOINTS.CLIENTES.GET_BY_USER_ID(123)
API_ENDPOINTS.CLIENTES.UPDATE_BY_USER_ID(123)
```

#### Pedidos
```typescript
API_ENDPOINTS.PEDIDOS.CREATE
API_ENDPOINTS.PEDIDOS.GET_BY_USER
```

#### Upload/AWS S3
```typescript
API_ENDPOINTS.UPLOAD.PRESIGNED_URL
API_ENDPOINTS.UPLOAD.DELETE_FILE('file-key')
```

## Uso en Servicios

### Ejemplo básico
```typescript
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_ENDPOINTS } from '@core/config';

@Injectable({ providedIn: 'root' })
export class MiServicio {
  private http = inject(HttpClient);

  getArticulos() {
    return this.http.get(API_ENDPOINTS.ARTICULOS.GET_ALL);
  }

  getArticuloPorId(id: number) {
    return this.http.get(API_ENDPOINTS.ARTICULOS.GET_BY_ID(id));
  }
}
```

### Uso con autenticación
```typescript
import { buildAuthHeaders } from '@core/config';

const token = sessionStorage.getItem('authToken');
const headers = buildAuthHeaders(token);

this.http.get(API_ENDPOINTS.USUARIOS.GET_ALL, { headers })
```

## Utilidades Incluidas

### Headers de Autorización
```typescript
const headers = buildAuthHeaders(token);
// Resultado: { 'Content-Type': 'application/json', 'Authorization': 'Bearer token' }
```

### URLs con Parámetros
```typescript
const url = buildUrlWithParams(API_ENDPOINTS.ARTICULOS.GET_ALL, {
  page: 0,
  size: 10,
  search: 'tomate'
});
```

### Validación de Autenticación
```typescript
const requiereAuth = requiresAuth(API_ENDPOINTS.CARRITO.GET_CART); // true
const noRequiereAuth = requiresAuth(API_ENDPOINTS.AUTH.LOGIN); // false
```

## Constantes Disponibles

### Métodos HTTP
```typescript
HTTP_METHODS.GET
HTTP_METHODS.POST
HTTP_METHODS.PUT
HTTP_METHODS.DELETE
HTTP_METHODS.PATCH
```

### Códigos de Estado
```typescript
HTTP_STATUS_CODES.OK // 200
HTTP_STATUS_CODES.CREATED // 201
HTTP_STATUS_CODES.BAD_REQUEST // 400
HTTP_STATUS_CODES.UNAUTHORIZED // 401
HTTP_STATUS_CODES.NOT_FOUND // 404
```

### Tipos de Usuario
```typescript
USER_TYPES.CLIENTE
USER_TYPES.PRODUCTOR
```

### Roles de Usuario
```typescript
USER_ROLES.ADMIN
USER_ROLES.SUPER_ADMIN
USER_ROLES.USER
```

### Métodos de Pago
```typescript
PAYMENT_METHODS.TARJETA
PAYMENT_METHODS.EFECTIVO
PAYMENT_METHODS.TRANSFERENCIA
```

### Estados de Pedido
```typescript
ORDER_STATUS.PENDIENTE
ORDER_STATUS.CONFIRMADO
ORDER_STATUS.EN_PREPARACION
ORDER_STATUS.ENVIADO
ORDER_STATUS.ENTREGADO
ORDER_STATUS.CANCELADO
```

## Interceptors Incluidos

### AuthInterceptor
Agrega automáticamente el token de autorización a las peticiones que lo requieran.

### ErrorInterceptor
Maneja centralizadamente los errores HTTP con mensajes personalizados.

## Configuración de Entornos

Los endpoints se construyen automáticamente usando la configuración del archivo `environment.ts`:

```typescript
// environment.ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8081/cosechaencope'
};
```

## Mantenimiento

Para agregar nuevos endpoints:

1. Añádelos al objeto `API_ENDPOINTS` en `global.ts`
2. Organízalos por módulo/controlador
3. Usa funciones para endpoints parametrizados
4. Actualiza la documentación

### Ejemplo de nuevo endpoint
```typescript
// En global.ts
NUEVO_MODULO: {
  BASE: `${API_BASE_URL}/nuevo-modulo`,
  GET_ALL: `${API_BASE_URL}/nuevo-modulo`,
  GET_BY_ID: (id: number) => `${API_BASE_URL}/nuevo-modulo/${id}`,
}
```

## Beneficios

- ✅ **Centralización**: Todos los endpoints en un solo lugar
- ✅ **Tipado**: TypeScript garantiza el uso correcto
- ✅ **Mantenibilidad**: Fácil actualización de URLs
- ✅ **Consistencia**: Evita URLs hardcodeadas dispersas
- ✅ **Reutilización**: Utilities comunes para toda la app
- ✅ **Configuración por entorno**: URLs diferentes para dev/prod
- ✅ **Interceptors automáticos**: Auth y manejo de errores
- ✅ **Documentación**: Todo está documentado y tipado
# 📋 Agents.md - Reglas de Desarrollo para Cosecha en Cope

## 🎯 **Información del Proyecto**

### **Descripción General**
- **Nombre**: Cosecha en Cope
- **Tipo**: Plataforma web para venta de productos agrícolas locales
- **Objetivo**: Conectar productores directamente con consumidores
- **Autor**: rafalopezzz (RafaLopezZz)
- **Repositorio**: cosecha-en-cope (Rama: Develop)

### **Stack Tecnológico**
- **Frontend**: Angular 19.2.0 + TypeScript 5.7.2
- **Backend**: Spring Boot 3.5.2 + Java 17
- **Base de Datos**: PostgreSQL
- **Autenticación**: JWT (JSON Web Tokens)
- **UI Framework**: Bootstrap 5.3.7
- **Iconos**: FontAwesome 7.0.0
- **Build Tool Backend**: Maven
- **Build Tool Frontend**: Angular CLI

---

## 🏗️ **Arquitectura del Proyecto**

### **Estructura Híbrida - SSR + SPA**
El proyecto utiliza una arquitectura híbrida que combina:
- **Server-Side Rendering (Thymeleaf)**: Para landing pages optimizadas para SEO
- **Single Page Application (Angular)**: Para funcionalidades interactivas y paneles de usuario

```
┌─────────────────────────────────────────────────────────────┐
│                    SPRING BOOT APPLICATION                   │
├─────────────────────────────────────────────────────────────┤
│  🏠 LANDING PAGES (SERVER-SIDE RENDERED)                     │
│  ├── / (home)                    → Thymeleaf Template       │
│  ├── /nosotros                   → Thymeleaf Template       │
│  ├── /contacto                   → Thymeleaf Template       │
│  └── /categorias (preview)       → Thymeleaf Template       │
├─────────────────────────────────────────────────────────────┤
│  🎯 ANGULAR SPA (CLIENT-SIDE)                               │
│  ├── /app/**                     → Angular Application      │
│  │   ├── /app/articulos          → Catálogo de productos    │
│  │   ├── /app/auth/*             → Autenticación           │
│  │   ├── /app/cliente/*          → Panel cliente           │
│  │   ├── /app/productor/*        → Panel productor         │
│  │   └── /app/carrito            → Carrito y pedidos       │
│  └── /api/**                     → REST API Endpoints      │
└─────────────────────────────────────────────────────────────┘
```

### **Estructura Frontend (Angular) - Actualizada**
```
src/app/
├── core/                           # Servicios centrales y configuración
│   ├── config/
│   │   └── global.ts              # Configuración centralizada de endpoints
│   ├── guards/                    # Guards de autenticación y roles
│   ├── interceptors/              # Interceptores HTTP
│   └── services/                  # Servicios centrales
├── features/                      # Módulos de funcionalidades
│   ├── auth/                      # Autenticación (ahora bajo /app/auth)
│   ├── articulos/                 # Gestión de artículos (ahora bajo /app/articulos)
│   ├── cliente/                   # Panel del cliente (ahora bajo /app/cliente)
│   └── productor/                 # Panel del productor (ahora bajo /app/productor)
├── shared/                        # Componentes y modelos compartidos
│   ├── components/                # Componentes reutilizables
│   └── models/                    # Interfaces TypeScript
└── environments/                  # Configuraciones de entorno
```

### **Estructura Backend (Spring Boot) - Expandida**
```
src/main/java/com/rlp/cosechaencope/
├── controller/                    # Controladores
│   ├── api/                       # REST API Controllers
│   │   ├── ArticuloController.java
│   │   ├── AuthController.java
│   │   └── ProductorController.java
│   └── web/                       # Web Controllers para SSR
│       ├── HomeController.java
│       └── SeoController.java
├── service/                       # Lógica de negocio
│   ├── ArticuloService.java
│   ├── AuthService.java
│   └── SeoService.java           # Nuevo: Servicio para optimización SEO
├── repository/                    # Acceso a datos (JPA)
├── model/                         # Entidades JPA
├── dto/                          # Data Transfer Objects
│   ├── request/                   # DTOs de entrada
│   └── response/                  # DTOs de salida
├── security/                      # Configuración de seguridad
├── exception/                     # Manejo de excepciones
└── config/                        # Configuraciones generales
    └── WebConfig.java            # Nuevo: Configuración para SSR + SPA
```

### **Estructura de Templates Thymeleaf**
```
src/main/resources/
├── templates/                     # Templates Thymeleaf para SSR
│   ├── layout/
│   │   ├── base.html             # Template base con SEO optimization
│   │   ├── header.html           # Header/Navbar común
│   │   └── footer.html           # Footer común
│   ├── pages/
│   │   ├── landing.html          # Página principal SSR
│   │   ├── about.html            # Página "Nosotros"
│   │   ├── contact.html          # Página de contacto
│   │   └── categories-preview.html # Preview de categorías
│   └── fragments/
│       ├── hero.html             # Hero section reutilizable
│       ├── features.html         # Features section
│       └── testimonials.html     # Testimonials section
└── static/                       # Recursos estáticos para SSR
    ├── css/
    │   └── main.css              # Estilos principales optimizados
    ├── js/
    │   └── main.js               # JavaScript para interactividad SSR
    ├── images/                   # Imágenes optimizadas
    └── manifest.json             # PWA manifest
```

---

## � **Optimización SEO y Rendimiento**

### **Estrategia SEO Híbrida**
1. **Landing Pages SSR**: Renderizado servidor para máxima visibilidad en buscadores
2. **Meta Tags Dinámicos**: Optimizados por página con Open Graph y Twitter Cards
3. **Structured Data**: JSON-LD para rich snippets en resultados de búsqueda
4. **Sitemap Automático**: Generado dinámicamente en `/sitemap.xml`
5. **Robots.txt**: Configurado para guiar crawlers efectivamente

### **Templates Thymeleaf SEO**
```html
<!-- Ejemplo de optimización SEO en templates -->
<head>
    <title th:text="${pageTitle}">Cosecha en Cope</title>
    <meta name="description" th:content="${pageDescription}">
    <link rel="canonical" th:href="${canonicalUrl}">
    
    <!-- Open Graph -->
    <meta property="og:title" th:content="${pageTitle}">
    <meta property="og:description" th:content="${pageDescription}">
    <meta property="og:image" th:content="${ogImage}">
    
    <!-- Structured Data -->
    <script type="application/ld+json" th:utext="${structuredData}"></script>
</head>
```

### **Controladores Web para SEO**
```java
@Controller
public class HomeController {
    
    @GetMapping("/")
    public String landing(Model model) {
        model.addAttribute("pageTitle", "Cosecha en Cope - Productos Agrícolas Frescos");
        model.addAttribute("pageDescription", "Conectamos productores locales...");
        model.addAttribute("canonicalUrl", "https://cosechaencope.com/");
        model.addAttribute("structuredData", seoService.getHomePageStructuredData());
        return "pages/landing";
    }
}
```

### **Performance Optimizations**
1. **Progressive Enhancement**: Funciona sin JavaScript
2. **Lazy Loading**: Imágenes y recursos no críticos
3. **Critical CSS**: Estilos críticos inline
4. **Preload/Prefetch**: Recursos importantes precargados
5. **CDN Ready**: Estructura preparada para CDN

---

## �🔐 **Reglas de Autenticación y Seguridad**

### **JWT Token Management**
1. **Campo ID**: Siempre usar `idUsuario` (NO `id`) en modelos Angular
2. **Storage**: Tokens se almacenan en `sessionStorage.getItem('authToken')`
3. **Header Format**: `Authorization: Bearer {token}`
4. **Interceptor**: Todos los requests usan `tokenInterceptor`

### **Autenticación por Tipo de Usuario**
1. **Navegación**: Usar rutas específicas `/cliente/` y `/productor/` para nuevas features
2. **Validación**: Siempre verificar `tipoUsuario` en respuestas de login
3. **Redirección Post-Login**: 
   - **Clientes**: `/articulos` (catálogo de productos)
   - **Productores**: `/productor/dashboard` (panel de control)
4. **Diseño Visual**: Mantener coherencia visual (verde=clientes, rosa/naranja=productores)
5. **Formularios**: Validaciones específicas según tipo de usuario
   - **Clientes**: Email, contraseña, nombre obligatorios
   - **Productores**: Email, contraseña, nombre, dirección y teléfono obligatorios

### **Rutas de Autenticación**
```typescript
// IMPORTANTE: Todas las rutas Angular ahora están bajo /app/**

// Página de selección de tipo de usuario
{ path: 'auth', component: AuthSelectorComponent }

// Rutas específicas por tipo
{ path: 'cliente/login', component: ClienteSignInComponent }
{ path: 'cliente/registro', component: ClienteSignUpComponent }
{ path: 'productor/login', component: ProductorSignInComponent }
{ path: 'productor/registro', component: ProductorSignUpComponent }

// Redirecciones automáticas
{ path: 'login', redirectTo: 'auth', pathMatch: 'full' }
{ path: 'registro', redirectTo: 'auth', pathMatch: 'full' }
```

### **Rutas Híbridas SSR + SPA**
```typescript
// BACKEND (Spring Boot) - Rutas SSR para SEO
GET /                          → landing.html (Thymeleaf)
GET /nosotros                  → about.html (Thymeleaf)
GET /contacto                  → contact.html (Thymeleaf)
GET /categorias                → categories-preview.html (Thymeleaf)
GET /productos/{categoria}     → products-preview.html (Thymeleaf)

// Redirecciones automáticas a Angular SPA
GET /articulos                 → redirect:/app/articulos
GET /auth                      → redirect:/app/auth
GET /cliente/**                → redirect:/app/cliente/
GET /productor/**              → redirect:/app/productor/

// Angular SPA (todas bajo /app prefix)
GET /app/**                    → forward:/app/index.html → Angular Router
```

### **Flujo de Navegación Híbrido**
```
┌─────────────────────────────────────────────────────────────┐
│                    FLUJO DE USUARIO                         │
├─────────────────────────────────────────────────────────────┤
│  1. Visitante → https://cosechaencope.com/                  │
│     └── Thymeleaf SSR (SEO optimizado)                     │
│                                                             │
│  2. Click "Ver Productos" → /app/articulos                 │
│     └── Angular SPA (interactividad completa)              │
│                                                             │
│  3. Login → /app/auth                                       │
│     └── Angular SPA (gestión de estado)                    │
│                                                             │
│  4. Panel Usuario → /app/cliente/* o /app/productor/*      │
│     └── Angular SPA (funcionalidades privadas)             │
└─────────────────────────────────────────────────────────────┘
```

### **Modelos de Autenticación**
```typescript
// Angular - auth.models.ts
interface JwtResponse {
  token: string;
  type?: string;
  idUsuario: number;        // ⚠️ CRÍTICO: Usar idUsuario, NO id
  email: string;
  tipoUsuario: 'CLIENTE' | 'PRODUCTOR';
  roles: string[];
}
```

```java
// Java - JwtResponse.java
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long idUsuario;     // ⚠️ CRÍTICO: Usar idUsuario, NO id
    private String email;
    private String tipoUsuario;
    private List<String> roles;
}
```

### **Flujo de Autenticación**
1. Login → `AuthService.loginProductor()` o `AuthService.login()`
2. Token guardado en `sessionStorage`
3. Usuario guardado en `UserStoreService`
4. Interceptor añade token a requests automáticamente
5. Guards protegen rutas según roles

---

## 📡 **Gestión de APIs y Endpoints**

### **Configuración Centralizada**
- **Archivo**: `core/config/global.ts`
- **Patrón**: Todos los endpoints centralizados en `API_ENDPOINTS`
- **Base URL**: `environment.apiUrl` (http://localhost:8081/cosechaencope)

### **Estructura de Endpoints**
```typescript
export const API_ENDPOINTS = {
  AUTH: {
    LOGIN: `${API_BASE_URL}/auth/login`,
    LOGIN_PRODUCTORES: `${API_BASE_URL}/auth/login/productores`,
    REGISTRO_CLIENTES: `${API_BASE_URL}/auth/registro/clientes`,
    REGISTRO_PRODUCTORES: `${API_BASE_URL}/auth/registro/productores`,
  },
  ARTICULOS: {
    BASE: `${API_BASE_URL}/articulos`,
    CREATE: `${API_BASE_URL}/articulos`,
    GET_BY_ID: (id: number) => `${API_BASE_URL}/articulos/${id}`,
    // ...
  },
  PRODUCTORES: {
    GET_BY_USER_ID: (idUsuario: number) => 
      `${API_BASE_URL}/usuarios/productores/${idUsuario}`,
    // ...
  }
};
```

### **Reglas de Servicios**
1. **Inyección**: Usar `inject()` en lugar de constructor injection
2. **Observables**: Todos los servicios retornan `Observable<T>`
3. **Error Handling**: Interceptor maneja errores HTTP automáticamente
4. **Endpoints**: Siempre usar `API_ENDPOINTS` desde `global.ts`

---

## 🎨 **Reglas de Componentes y UI**

### **Arquitectura de Componentes**
1. **Standalone Components**: Usar `standalone: true` en todos los componentes
2. **Imports**: Importar dependencias directamente en el componente
3. **Injection**: Usar `inject()` para servicios
4. **ReactiveFormsModule**: Para formularios reactivos

### **Ejemplo de Componente Standard**
```typescript
@Component({
  standalone: true,
  selector: 'app-ejemplo',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './ejemplo.component.html',
  styleUrls: ['./ejemplo.component.scss']
})
export class EjemploComponent implements OnInit {
  private fb = inject(FormBuilder);
  private userStore = inject(UserStoreService);
  private router = inject(Router);

  currentUser = this.userStore.snapshot();
  
  ngOnInit() {
    // Lógica de inicialización
  }
}
```

### **Validación de Formularios**
1. **FormBuilder**: Usar `fb.nonNullable.group()`
2. **Validators**: Importar de `@angular/forms`
3. **Error Display**: Mostrar errores con `touched` y `invalid`
4. **Submit**: Validar `form.invalid` antes de envío

### **Navegación y Routing**
1. **Guards**: Proteger rutas con `AuthGuard` y `RoleGuard`
2. **Lazy Loading**: Componentes se cargan lazy por defecto
3. **Navigation**: Usar `Router.navigateByUrl()` para navegación programática

---

## 🛠️ **Reglas de Desarrollo Backend**

### **Estructura de Controladores**
1. **CORS**: `@CrossOrigin(origins = "*")` en todos los controladores
2. **RequestMapping**: Usar path completo `/cosechaencope/{resource}`
3. **Swagger**: Documentar con `@Operation` y `@ApiResponses`
4. **Validation**: Usar `@Valid` para DTOs de entrada

### **Patrón de Controlador Standard**
```java
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cosechaencope/articulos")
@Tag(name = "Artículo", description = "Operaciones para gestión de artículos")
public class ArticuloController {
    
    @Autowired
    private ArticuloService articuloService;
    
    @PostMapping
    public ResponseEntity<ArticuloResponse> crearArticulo(
        @Valid @RequestBody ArticuloRequest request) {
        // Implementación
    }
}
```

### **DTOs y Modelos**
1. **Request/Response**: Separar DTOs de entrada y salida
2. **Validation**: Usar anotaciones Jakarta (`@NotBlank`, `@Min`, etc.)
3. **Lombok**: Usar `@Data` para getters/setters automáticos
4. **Schema**: Documentar con `@Schema` para Swagger

### **Servicios y Repositorios**
1. **@Service**: Lógica de negocio en servicios
2. **@Repository**: Extiende `JpaRepository<Entity, ID>`
3. **Transaccional**: Usar `@Transactional` cuando necesario
4. **Exception Handling**: Excepciones custom para errores de negocio

---

## 🗄️ **Reglas de Base de Datos**

### **Configuración PostgreSQL**
- **Puerto**: 5432 (default)
- **Host**: localhost
- **Database**: cosechaencope
- **Hibernate**: `ddl-auto=update`
- **Dialect**: PostgreSQLDialect

### **Naming Conventions**
1. **Tablas**: snake_case (e.g., `categoria`, `articulo`)
2. **Columnas**: snake_case (e.g., `id_categoria`, `imagen_url`)
3. **Entidades JPA**: PascalCase (e.g., `Articulo`, `Categoria`)
4. **Campos Java**: camelCase (e.g., `idCategoria`, `imagenUrl`)

### **Relaciones**
1. **Foreign Keys**: Usar `@JoinColumn` con nombres explícitos
2. **Cascade**: Configurar apropiadamente según lógica de negocio
3. **Fetch**: Lazy loading por defecto, Eager solo cuando necesario

---

## 🔧 **Reglas de Configuración y Entornos**

### **Environment Variables**
1. **Backend**: Usar `.env` para configuración sensible
2. **Frontend**: Environments separados (`environment.ts`, `environment.development.ts`)
3. **Database**: Configurar conexión via variables de entorno
4. **Ports**: Backend:8081, Frontend:4200

### **Logging y Debugging**
1. **Console Logs**: Usar para debugging, remover en producción
2. **Structured Logging**: Backend con logback, Frontend con console
3. **Error Tracking**: Interceptores manejan errores HTTP
4. **Debug Mode**: Activar logs detallados durante desarrollo

---

## 📝 **Reglas de Documentación**

### **Comentarios de Código**
1. **JavaDoc**: Documentar métodos públicos y clases
2. **TSDoc**: Documentar interfaces y funciones complejas
3. **README**: Mantener documentación actualizada
4. **API Docs**: Swagger para documentación de APIs

### **Naming Conventions**
1. **Variables**: camelCase (TypeScript/Java)
2. **Constants**: UPPER_SNAKE_CASE
3. **Files**: kebab-case para componentes, PascalCase para clases
4. **Components**: PascalCase + "Component" suffix

---

## 🚀 **Reglas de Build y Deploy**

### **Frontend Build**
```bash
# Development
npm start                    # ng serve en puerto 4200
npx ng build --configuration development

# Production
npm run build               # ng build optimizado
```

### **Backend Build**
```bash
# Development
./mvnw spring-boot:run      # Puerto 8081

# Production
./mvnw clean package        # Genera JAR ejecutable
```

### **Testing**
1. **Unit Tests**: Jest para Angular, JUnit para Spring Boot
2. **Integration Tests**: Testing de endpoints completos
3. **E2E Tests**: Cypress para pruebas end-to-end

---

## ⚠️ **Problemas Conocidos y Soluciones**

### **Error 401 - Unauthorized**
- **Causa**: Campo `id` vs `idUsuario` en JwtResponse
- **Solución**: SIEMPRE usar `idUsuario` en ambos frontend y backend

### **CORS Issues**
- **Causa**: Headers no configurados correctamente
- **Solución**: `@CrossOrigin(origins = "*")` en controladores

### **Token Expiration**
- **Causa**: JWT expira después de tiempo configurado
- **Solución**: Implementar refresh token o re-login automático

### **Compilación Angular**
- **Budget Errors**: Ajustar angular.json para límites de bundle size
- **TypeScript Errors**: Verificar tipos estrictos en tsconfig.json

---

## 📋 **Checklist de Nuevas Features**

### **Frontend (Angular)**
- [ ] Crear componente standalone
- [ ] Definir interfaces TypeScript en `shared/models`
- [ ] Crear servicio con `inject()` pattern
- [ ] Agregar endpoints a `global.ts`
- [ ] Implementar guards si requiere autenticación
- [ ] Agregar tests unitarios
- [ ] Documentar en README

### **Backend (Spring Boot)**
- [ ] Crear entidad JPA
- [ ] Definir Repository interface
- [ ] Implementar Service con lógica de negocio
- [ ] Crear Controller con endpoints
- [ ] Definir DTOs (Request/Response)
- [ ] Agregar validaciones
- [ ] Documentar con Swagger
- [ ] Agregar tests unitarios e integración

### **Database**
- [ ] Actualizar esquema si necesario
- [ ] Verificar constraints y foreign keys
- [ ] Actualizar datos de prueba si aplica

---

## 🎯 **Principios de Desarrollo**

1. **DRY (Don't Repeat Yourself)**: Centralizar configuración y lógica común
2. **SOLID**: Aplicar principios SOLID en backend
3. **Component-Based**: Arquitectura basada en componentes reutilizables
4. **API-First**: Diseñar APIs antes de implementar UI
5. **Security-First**: Autenticación y autorización en todas las capas
6. **Responsive Design**: UI adaptable a diferentes dispositivos
7. **Type Safety**: Usar TypeScript estrictamente tipado
8. **Error Handling**: Manejo robusto de errores en todas las capas

---

**Fecha de Creación**: 5 de octubre de 2025  
**Última Actualización**: 10 de octubre de 2025 - Migración SSR + SPA  
**Autor**: Análisis automático del proyecto Cosecha en Cope  
**Versión**: 2.0.0 - Arquitectura Híbrida
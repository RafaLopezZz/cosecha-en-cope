import { Routes } from '@angular/router';
import { SignInComponent } from './features/auth/sign-in/sign-in.component';
import { SignUpComponent } from './features/auth/sign-up/sign-up.component';
import { SignErrComponent } from './features/auth/sign-err/sign-err.component';
import { AuthSelectorComponent } from './features/auth/auth-selector/auth-selector.component';
import { ClienteSignInComponent } from './features/auth/cliente-sign-in/cliente-sign-in.component';
import { ClienteSignUpComponent } from './features/auth/cliente-sign-up/cliente-sign-up.component';
import { ProductorSignInComponent } from './features/auth/productor-sign-in/productor-sign-in.component';
import { ProductorSignUpComponent } from './features/auth/productor-sign-up/productor-sign-up.component';
import { ArticuloListComponent } from './features/articulos/articulo-list/articulo-list.component';
import { FeaturesSectionComponent } from './features/home/features-section/features-section.component';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  // NOTA: La ruta raíz '/' es manejada por Spring Boot (SSR con Thymeleaf)
  // Angular solo maneja rutas bajo /app/**

  // Página de selección de tipo de usuario
  {
    path: 'auth',
    component: AuthSelectorComponent,
  },

  // Rutas de autenticación específicas por tipo de usuario
  {
    path: 'cliente/login',
    component: ClienteSignInComponent,
  },
  {
    path: 'cliente/registro',
    component: ClienteSignUpComponent,
  },
  {
    path: 'productor/login',
    component: ProductorSignInComponent,
  },
  {
    path: 'productor/registro',
    component: ProductorSignUpComponent,
  },

  // Rutas públicas de contenido
  {
    path: 'articulos',
    component: ArticuloListComponent,
  },
  {
    path: 'categorias',
    component: FeaturesSectionComponent,
  },
  {
    path: 'categorias/:idCategoria',
    component: ArticuloListComponent,
  },

  // Rutas del panel de productor (protegidas por autenticación y tipo de usuario)
  {
    path: 'productor/dashboard',
    canActivate: [authGuard, roleGuard],
    data: { tipoUsuario: 'PRODUCTOR' },
    loadComponent: () =>
      import('./features/productor/dashboard/dashboard.component').then(
        (m) => m.DashboardProductorComponent
      ),
  },
  {
    path: 'productor/perfil',
    canActivate: [authGuard, roleGuard],
    data: { tipoUsuario: 'PRODUCTOR' },
    loadComponent: () =>
      import('./features/productor/perfil/perfil.component').then(
        (m) => m.PerfilProductorComponent
      ),
  },
  {
    path: 'productor/categorias',
    canActivate: [authGuard, roleGuard],
    data: { tipoUsuario: 'PRODUCTOR' },
    loadComponent: () =>
      import('./features/productor/categorias/categorias.component').then(
        (m) => m.CategoriasProductorComponent
      ),
  },
  {
    path: 'productor/articulos',
    canActivate: [authGuard, roleGuard],
    data: { tipoUsuario: 'PRODUCTOR' },
    loadComponent: () =>
      import('./features/productor/articulos/articulos.component').then(
        (m) => m.ArticulosProductorComponent
      ),
  },
  {
    path: 'productor/articulos/nuevo',
    canActivate: [authGuard, roleGuard],
    data: { tipoUsuario: 'PRODUCTOR' },
    loadComponent: () =>
      import('./features/productor/articulos/articulos.component').then(
        (m) => m.ArticulosProductorComponent
      ),
  },

  // Redirección por defecto del productor
  {
    path: 'productor',
    redirectTo: 'productor/dashboard',
    pathMatch: 'full',
  },

  
  // Rutas de autenticación genéricas (redireccionan a selector)
  // Deprecadas en favor de las específicas arriba 
  {
    path: 'login',
    redirectTo: 'auth',
    pathMatch: 'full',
  },
  {
    path: 'registro',
    redirectTo: 'auth',
    pathMatch: 'full',
  },
  {
    path: 'login/productores',
    component: SignInComponent,
    data: { productor: true },
  },
  {
    path: 'error',
    component: SignErrComponent,
  },
];

// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { SignInComponent } from './features/auth/sign-in/sign-in.component';
import { SignUpComponent } from './features/auth/sign-up/sign-up.component';
import { SignErrComponent } from './features/auth/sign-err/sign-err.component';
import { LandingComponent } from './features/home/landing/landing.component';
import { ArticuloListComponent } from './features/articulos/articulo-list/articulo-list.component';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'login', component: SignInComponent },
  { path: 'login/productores', component: SignInComponent, data: { productor: true } },
  { path: 'registro', component: SignUpComponent },
  { path: 'error', component: SignErrComponent },
  { path: 'articulos', component: ArticuloListComponent /*, canActivate: [authGuard]*/ },
  { path: 'categorias/:idCategoria', component: ArticuloListComponent },
  // Ejemplo zona productor protegida:
  // { path: 'productor', canActivate: [authGuard, roleGuard], data: { roles: ['PRODUCTOR'] }, loadComponent: () => import('./features/productor/dashboard.component').then(m => m.DashboardComponent) }
];


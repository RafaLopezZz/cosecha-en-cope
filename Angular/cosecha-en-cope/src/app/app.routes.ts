import { Routes } from '@angular/router';
import { ArticuloListComponent } from './features/articulos/articulo-list/articulo-list.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'articulos',
    pathMatch: 'full',
  },
  {
    path: 'articulos',
    component: ArticuloListComponent,
  },
];

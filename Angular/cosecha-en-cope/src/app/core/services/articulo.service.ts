import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ArticuloResponse {
  idArticulo: number;
  nombre: string;
  precio: number;
  descripcion: string;
  stock: number;
  imagenUrl: string;
  nombreCategoria: string;
  nombreProductor: string;
  idCategoria?: number;
  idProductor?: number;
}

const API = 'http://localhost:8081/cosechaencope';

@Injectable({ providedIn: 'root' })
export class ArticuloService {
  private http = inject(HttpClient);
  getArticulos(): Observable<ArticuloResponse[]> {
    return this.http.get<ArticuloResponse[]>(`${API}/articulos`);
  }
}


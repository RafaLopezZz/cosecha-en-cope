import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ArticuloResponse } from '../../shared/models/articulo.models';

const API = 'http://localhost:8081/cosechaencope';

@Injectable({ providedIn: 'root' })
export class ArticuloService {
  private http = inject(HttpClient);
  
  getArticulos(): Observable<ArticuloResponse[]> {
    return this.http.get<ArticuloResponse[]>(`${API}/articulos`);
  }

  getArticulosPorCategoria(idCategoria: number): Observable<ArticuloResponse[]> {
    return this.http.get<ArticuloResponse[]>(`${API}/categorias/${idCategoria}/articulos`);
  }
}


import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoriaResponse } from '../../shared/models/categoria.models';

const API = 'http://localhost:8081/cosechaencope';

@Injectable({
  providedIn: 'root'
})
export class CategoriasService {
  private http = inject(HttpClient);

  getCategorias(): Observable<CategoriaResponse[]> {
    return this.http.get<CategoriaResponse[]>(`${API}/categorias`);
  }

  obtenerCategoriaPorId(idCategoria: number): Observable<CategoriaResponse> {
    return this.http.get<CategoriaResponse>(`${API}/${idCategoria}`);
  }

  crearCategoria(categoria: any): Observable<CategoriaResponse> {
    return this.http.post<CategoriaResponse>(API, categoria);
  }

  actualizarCategoria(idCategoria: number, categoria: any): Observable<CategoriaResponse> {
    return this.http.put<CategoriaResponse>(`${API}/${idCategoria}`, categoria);
  }

  eliminarCategoria(idCategoria: number): Observable<string> {
    return this.http.delete<string>(`${API}/${idCategoria}`);
  }
}

import { Injectable, inject, signal } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ArticuloResponse } from '../../shared/models/articulo-response';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ArticuloService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8081/cosechaencope';
  
  private _articulos = signal<ArticuloResponse[]>([]);

  getArticulos(): Observable<ArticuloResponse[]> {
    return this.http.get<ArticuloResponse[]>(`${this.apiUrl}/articulos`);
  }
}

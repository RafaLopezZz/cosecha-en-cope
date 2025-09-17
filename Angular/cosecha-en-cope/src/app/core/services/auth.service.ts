import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest, JwtResponse, UsuarioRequest, UsuarioResponse } from '../../shared/models/auth.models';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { UserStoreService } from './user-store.service';

const API = 'http://localhost:8081/cosechaencope';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private store = inject(UserStoreService);
  private router = inject(Router);

  login(dto: LoginRequest) {
    return this.http.post<JwtResponse>(`${API}/auth/login`, dto).pipe(
      tap(res => {
        sessionStorage.setItem('authToken', res.token);
        this.store.set(res);
      })
    );
  }

  registerCliente(dto: UsuarioRequest) {
    return this.http.post<UsuarioResponse>(`${API}/auth/registro/clientes`, dto);
  }

  registerProductor(dto: UsuarioRequest) {
    return this.http.post<UsuarioResponse>(`${API}/auth/registro/productores`, dto);
  }

  logout() {
    sessionStorage.removeItem('authToken');
    this.store.clear();
    this.router.navigateByUrl('/login');
  }
}

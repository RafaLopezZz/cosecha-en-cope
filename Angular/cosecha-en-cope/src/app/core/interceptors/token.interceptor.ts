// src/app/core/interceptors/token.interceptor.ts
import { HttpErrorResponse, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { inject } from '@angular/core';
import { Router } from '@angular/router';

export const tokenInterceptor: HttpInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn) => {
  const router = inject(Router);
  const token = sessionStorage.getItem('authToken'); // mejor cookie HttpOnly si backend lo soporta
  const cloned = token ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } }) : req;

  return next(cloned).pipe(
    catchError((err: HttpErrorResponse) => {
      if (err.status === 401) {
        router.navigateByUrl('/login');
      } else if (err.status === 403) {
        router.navigate(['/error'], { queryParams: { code: 403, m: 'Acceso denegado' } });
      }
      return throwError(() => err);
    })
  );
};

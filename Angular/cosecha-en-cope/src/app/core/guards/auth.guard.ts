// src/app/core/guards/auth.guard.ts
import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = () => {
  const router = inject(Router);
  const token = sessionStorage.getItem('authToken');
  if (!token) {
    router.navigateByUrl('/login');
    return false;
  }
  return true;
};

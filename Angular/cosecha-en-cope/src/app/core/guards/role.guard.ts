import { CanActivateFn, ActivatedRouteSnapshot, Router } from '@angular/router';
import { inject } from '@angular/core';
import { UserStoreService } from '../services/user-store.service';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const router = inject(Router);
  const store = inject(UserStoreService);
  const rolesNeed = route.data['roles'] as string[] | undefined;
  const rolesHave = store.snapshot()?.roles ?? [];
  const ok = !rolesNeed || rolesNeed.some(r => rolesHave.includes(r));
  if (!ok) {
    router.navigate(['/error'], { queryParams: { code: 403, m: 'Sin permisos' } });
  }
  return ok;
};

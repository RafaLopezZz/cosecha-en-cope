import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, AbstractControl } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { finalize } from 'rxjs/operators';

function match(field: string, other: string) {
  return (group: AbstractControl) => {
    const a = group.get(field)?.value; const b = group.get(other)?.value;
    return a === b ? null : { mismatch: true };
  };
}

@Component({
  standalone: true,
  selector: 'app-sign-up',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss'],
})
export class SignUpComponent {
  private fb = inject(FormBuilder);
  private auth = inject(AuthService);
  private router = inject(Router);
  loading = false;

  form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    confirm: ['', [Validators.required]],
    nombre: [''],
    direccion: [''],
    telefono: [''],
    terms: [false, Validators.requiredTrue],
  }, { validators: match('password', 'confirm') });

  onSubmitCliente() {
    if (this.form.invalid) return;
    const { email, password, nombre, direccion, telefono } = this.form.getRawValue();
    this.loading = true;
    this.auth.registerCliente({ email, password, nombre, direccion, telefono })
      .pipe(finalize(() => this.loading = false))
      .subscribe({
        next: () => this.router.navigate(['/login'], { queryParams: { ok: 1 } }),
        error: (e) => this.router.navigate(['/error'], { queryParams: { code: e?.status ?? 0, m: 'Registro fallido' } })
      });
  }

  onSubmitProductor() {
    if (this.form.invalid) return;
    const { email, password, nombre, direccion, telefono } = this.form.getRawValue();
    this.loading = true;
    this.auth.registerProductor({ email, password, nombre, direccion, telefono })
      .pipe(finalize(() => this.loading = false))
      .subscribe({
        next: () => this.router.navigate(['/login'], { queryParams: { ok: 1 } }),
        error: (e) => this.router.navigate(['/error'], { queryParams: { code: e?.status ?? 0, m: 'Registro productor fallido' } })
      });
  }
}

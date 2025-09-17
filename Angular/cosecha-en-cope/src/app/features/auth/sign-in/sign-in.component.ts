// src/app/features/auth/sign-in/sign-in.component.ts
import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { finalize } from 'rxjs/operators';

@Component({
  standalone: true,
  selector: 'app-sign-in',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
})
export class SignInComponent {
  private fb = inject(FormBuilder);
  private auth = inject(AuthService);
  private router = inject(Router);
  loading = false;

  form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });

  onSubmit() {
    if (this.form.invalid) return;
    this.loading = true;
    this.auth.login(this.form.getRawValue())
      .pipe(finalize(() => this.loading = false))
      .subscribe({
        next: () => this.router.navigateByUrl('/articulos'),
        error: (e) => this.router.navigate(['/error'], { queryParams: { code: e?.status ?? 0, m: 'No se pudo iniciar sesión' } })
      });
  }
}


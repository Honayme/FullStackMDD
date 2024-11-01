import { FormBuilder, Validators, ValidationErrors, AbstractControl } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../interfaces/registerRequest.interface';
import { AuthSuccess } from '../../interfaces/authSuccess.interface';
import { Component } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  public onError = false;

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    name: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(8), this.passwordValidator]]
  });
  hide: any;

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private sessionService: SessionService
  ) {}

  // Validateur personnalisé pour le mot de passe
  private passwordValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.value || '';
    const hasUpperCase = /[A-Z]/.test(password);
    const hasLowerCase = /[a-z]/.test(password);
    const hasNumber = /[0-9]/.test(password);
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

    const isValid =
      hasUpperCase &&
      hasLowerCase &&
      hasNumber &&
      hasSpecialChar &&
      password.length >= 8;

    return isValid ? null : { passwordInvalid: true };
  }

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.authService.register(registerRequest).subscribe({
      next: (value: AuthSuccess) => this.router.navigate(['/login']),
      error: _ => (this.onError = true),
    });
  }
}

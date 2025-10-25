import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { auth } from '../../../services/auth';
import { Router } from '@angular/router';
import { LoginResponseDto } from '../../../entity/LoginResponseDto';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  loginForm: FormGroup;
  errorMessage: string = '';
  showPassword = false;

togglePasswordVisibility() {
  this.showPassword = !this.showPassword;
  const pwdInput = document.getElementById('password') as HTMLInputElement;
  pwdInput.type = this.showPassword ? 'text' : 'password';
}


  constructor(
    private fb: FormBuilder,
    private authService: auth,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  login() {
    if (this.loginForm.invalid) {
      return;
    }

    this.authService.login(this.loginForm.value).subscribe({
      next: (res: LoginResponseDto) => {
        this.authService.saveToken(res);
        // redirect based on role
        const role = this.authService.getUserRole();
        if (role === 'ROLE_ADMIN') {
          this.router.navigate(['/admin']);
        } else if (role === 'ROLE_MANAGER') {
          this.router.navigate(['/manager']);
        } else {
          this.router.navigate(['/customer']);
        }
      },
      error: err => {
        console.error(err);
        this.errorMessage = "Invalid email or password";
      }
    });
  }
}

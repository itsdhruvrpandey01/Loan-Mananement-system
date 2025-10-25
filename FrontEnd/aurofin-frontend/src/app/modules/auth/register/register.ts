import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { auth } from '../../../services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.html',
  styleUrl: './register.css'
})

export class Register {

  registerForm: FormGroup;
  errorMessage: string = '';
  showPassword = false;

togglePasswordVisibility() {
  this.showPassword = !this.showPassword;
  const pwdInput = document.getElementById('password') as HTMLInputElement;
  pwdInput.type = this.showPassword ? 'text' : 'password';
}

  constructor(private fb: FormBuilder, private authService: auth, private router: Router) {
    this.registerForm = this.fb.group({
      firstName: ['', Validators.required],
      middleName: [''],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      mobile: ['', Validators.required],
      gender: ['', Validators.required],
      address: this.fb.group({
        street: ['', Validators.required],
        area: ['', Validators.required],
        city: ['', Validators.required],
        pincode: ['', Validators.required]
      })
    });
  }

  register() {
    if (this.registerForm.valid) {
      this.authService.register(this.registerForm.value).subscribe({
        next: () => {
          alert('Registration successful! Please login.');
          this.router.navigate(['/login']);
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Registration failed. Try again.';
        }
      });
    }
  }
}

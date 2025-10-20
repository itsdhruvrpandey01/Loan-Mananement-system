import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterRequestDto } from '../entity/RegisterRequestDto';
import { LoginRequestDto } from '../entity/LoginRequestDto';
import { LoginResponseDto } from '../entity/LoginResponseDto';
import { user } from './user';

@Injectable({
  providedIn: 'root'
})
export class auth {

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  // Register new user
  register(registerDto: RegisterRequestDto): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/register`, registerDto);
  }

  // Login user
  login(loginDto: LoginRequestDto): Observable<LoginResponseDto> {
    return this.http.post<LoginResponseDto>(`${this.baseUrl}/login`, loginDto);
  }


  // Save token, role, email to localStorage
  saveToken(res: LoginResponseDto) {
    if (res.token) {
      localStorage.setItem('token', res.token);

      // Decode JWT payload
      const dataPayload = res.token.split('.')[1];
      const data = JSON.parse(atob(dataPayload));
      console.log('JWT Payload:', data);

      // Save role and email in localStorage
      const role = data.roles ? data.roles[0].authority : res.role;
      const email = data.sub ? data.sub : '';
      const userId = res.userId;
      
      console.log(userId)
      localStorage.setItem('role', role);
      localStorage.setItem('email', email);
      localStorage.setItem('userId',userId);
    }
  }

  // Get JWT token
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // Check if user is authenticated
  isAuthenticated(): boolean {
    return !!localStorage.getItem('role');
  }

  // Get logged-in user role
  getUserRole(): string | null {
    return localStorage.getItem('role');
  }

  // Logout
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('email');
  }


}

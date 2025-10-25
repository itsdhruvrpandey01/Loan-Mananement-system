import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserDetailsResponseDto } from '../entity/UserDetailsResponseDto';
import { UserDetailsUpdateDto } from '../entity/UserDetailsUpdateDto';
import { AddressDto } from '../entity/AddressDto';
import { AddressResponseDto } from '../entity/AddressResponseDto';

@Injectable({
  providedIn: 'root'
})
export class user {

  private baseUrl = 'http://localhost:8080/loan-app/user';

  constructor(private http: HttpClient) { }

  // Get user profile
  getUserProfile(userId: string): Observable<UserDetailsResponseDto> {
    return this.http.get<UserDetailsResponseDto>(`${this.baseUrl}/profile/${userId}`);
  }

  // Update user profile with optional file upload
  updateUserProfile(userId: string, userDetails: UserDetailsUpdateDto, file?: File): Observable<UserDetailsResponseDto> {
    const formData = new FormData();
    formData.append('userDetailsUpdateDto', new Blob([JSON.stringify(userDetails)], { type: 'application/json' }));
    if (file) {
      formData.append('file', file);
    }
    return this.http.put<UserDetailsResponseDto>(`${this.baseUrl}/profile/${userId}`, formData);
  }

  // Update user address
  updateUserAddress(userId: string, address: AddressDto): Observable<AddressResponseDto> {
    return this.http.put<AddressResponseDto>(`${this.baseUrl}/profile/address/${userId}`, address);
  }

  // Get user address
  getUserAddress(userId: string): Observable<AddressResponseDto> {
    return this.http.get<AddressResponseDto>(`${this.baseUrl}/profile/address/${userId}`);
  }

  getCustomerIdByUserId(userId: string): Observable<string> {
    return this.http.get<string>(`${this.baseUrl}/getCusomerID/${userId}`);
  }

  getManagerID(userID: string): Observable<string> {
    return this.http.get<string>(`${this.baseUrl}/managers/${userID}`);
  }
}

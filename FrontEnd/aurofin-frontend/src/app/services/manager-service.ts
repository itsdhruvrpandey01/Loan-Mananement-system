import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationResponse } from '../entity/ApplicationResponse';
import { EmployeeResponseDto } from '../entity/EmployeeResponseDto';
import { UpdatedEmployeeResponseDto } from '../entity/UpdatedEmployeeResponseDto';
import { UpdateEmployeeDetailsDto } from '../entity/UpdateEmployeeDetailsDto';
import { AddressDto } from '../entity/AddressDto';
import { AddressResponseDto } from '../entity/AddressResponseDto';
import { AppliedLoanApplications } from '../entity/AppliedLoanApplications';

@Injectable({
  providedIn: 'root'
})
export class ManagerService {

  private baseUrl = 'http://localhost:8080/loan-app/manager';

  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // 2. Approve Loan
  approveLoan(applicationId: string): Observable<ApplicationResponse> {
    return this.http.put<ApplicationResponse>(`${this.baseUrl}/loans/${applicationId}/approve`, null);
  }

  // 3. Reject Loan
  rejectLoan(loanId: string, reason?: string): Observable<ApplicationResponse> {
    let params = new HttpParams();
    if (reason) params = params.set('reason', reason);
    return this.http.put<ApplicationResponse>(`${this.baseUrl}/loans/${loanId}/reject`, null, { params });
  }

  // 4. View Manager Profile
  getManagerProfile(managerId: string): Observable<EmployeeResponseDto> {
    return this.http.get<EmployeeResponseDto>(`${this.baseUrl}/profile/${managerId}`);
  }

  updateEmployeeDetails(employeeId: string, details: UpdateEmployeeDetailsDto): Observable<UpdatedEmployeeResponseDto> {
    return this.http.post<UpdatedEmployeeResponseDto>(
      `${this.baseUrl}/${employeeId}/details`,
      details,
      { headers: this.getAuthHeaders() }
    );
  }

  updateAddress(employeeId: string, address: AddressDto): Observable<AddressResponseDto> {
    return this.http.post<AddressResponseDto>(
      `${this.baseUrl}/${employeeId}/address`,
      address,
      { headers: this.getAuthHeaders() }
    );
  }

  // Helper method to get employee ID from localStorage
  getEmployeeId(): string | null {
    return localStorage.getItem('eId');
  }


  // 5. Approve Customer KYC
  approveCustomerKyc(customerId: string): Observable<string> {
    return this.http.post(`${this.baseUrl}/customers/${customerId}/kyc/approve`, null, { responseType: 'text' });
  }

  // 6. Reject Customer KYC
  rejectCustomerKyc(customerId: string): Observable<string> {
    return this.http.post(`${this.baseUrl}/customers/${customerId}/kyc/reject`, null, { responseType: 'text' });
  }

  // Update Employee Details
  // updateEmployeeDetails(employeeId: string, employeeDetails: UpdateEmployeeDetailsDto): Observable<UpdatedEmployeeResponseDto> {
  //   return this.http.post<UpdatedEmployeeResponseDto>(`${this.baseUrl}/${employeeId}/details`, employeeDetails);
  // }

  // Update Employee Address
  updateEmployeeAddress(employeeId: string, address: AddressDto): Observable<AddressResponseDto> {
    return this.http.post<AddressResponseDto>(`${this.baseUrl}/${employeeId}/address`, address);
  }

  // Get Loans assigned to Manager
  getLoansAssignedToManager(managerId: string): Observable<AppliedLoanApplications[]> {
    return this.http.get<AppliedLoanApplications[]>(`${this.baseUrl}/${managerId}/loans/applications`);
  }

  // Get missing documents for an application
  getMissingDocuments(applicationId: string): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/applications/${applicationId}/missing-documents`);
  }


  // Add these methods to your ManagerService

  getEmployeeDetails(employeeId: string): Observable<UpdatedEmployeeResponseDto> {
    return this.http.get<UpdatedEmployeeResponseDto>(
      `${this.baseUrl}/manager/${employeeId}/details`,
      { headers: this.getAuthHeaders() }
    );
  }

  getEmployeeAddress(employeeId: string): Observable<AddressResponseDto> {
    return this.http.get<AddressResponseDto>(
      `${this.baseUrl}/manager/${employeeId}/address`,
      { headers: this.getAuthHeaders() }
    );
  }

  
}

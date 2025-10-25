import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RoleDto } from '../entity/RoleDto';
import { RoleResponseDto } from '../entity/RoleResponseDto';
import { LoanSchemeDto } from '../entity/LoanSchemeDto';
import { LoanResponseDto } from '../entity/LoanResponseDto';
import { EmployeeRequestDto } from '../entity/EmployeeRequestDto';
import { EmployeeResponseDto } from '../entity/EmployeeResponseDto';
import { LoanTypeDto } from '../entity/LoanTypeDto';
import { LoanTypeResponseDto } from '../entity/LoanTypeResponseDto';
import { CollateralTypeDto } from '../entity/CollateralTypeDto';
import { CollatoralTypeResponseDto } from '../entity/CollatoralTypeResponseDto';
import { PageResponseDto } from '../entity/PageResponseDto';
import { UpdateLoanSchemeDto } from '../entity/updateLoanScheme';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl = 'http://localhost:8080/loan-app/admin';

  constructor(private http: HttpClient) { }

  // Role endpoints
  addRole(roleDto: RoleDto): Observable<RoleResponseDto> {
    return this.http.post<RoleResponseDto>(`${this.baseUrl}/role`, roleDto);
  }

  getRoles(): Observable<RoleResponseDto[]> {
    return this.http.get<RoleResponseDto[]>(`${this.baseUrl}/role`);
  }

  // Loan scheme endpoints
  addLoanScheme(loanSchemeDto: LoanSchemeDto): Observable<LoanResponseDto> {
    return this.http.post<LoanResponseDto>(`${this.baseUrl}/loans`, loanSchemeDto);
  }

  getLoanById(loanSchemeId: number): Observable<LoanResponseDto> {
    return this.http.get<LoanResponseDto>(`${this.baseUrl}/loans/${loanSchemeId}`);
  }

  getLoanSchemes(loanType?: string): Observable<LoanResponseDto[]> {
    let params = new HttpParams();
    if (loanType) params = params.set('loanType', loanType);
    return this.http.get<LoanResponseDto[]>(`${this.baseUrl}/loans`, { params });
  }

  activateLoan(loanId: number): Observable<LoanResponseDto> {
    return this.http.put<LoanResponseDto>(`${this.baseUrl}/loans/${loanId}/active`, null);
  }

  deActivateLoan(loanId: number): Observable<LoanResponseDto> {
    return this.http.put<LoanResponseDto>(`${this.baseUrl}/loans/${loanId}/deactive`, null);
  }

  // Employee/Manager endpoints
  addEmployee(employeeRequestDto: EmployeeRequestDto): Observable<EmployeeResponseDto> {
    return this.http.post<EmployeeResponseDto>(`${this.baseUrl}/managers`, employeeRequestDto);
  }

  updateManagerCity(employeeId: string, city: string): Observable<EmployeeResponseDto> {
    return this.http.put<EmployeeResponseDto>(`${this.baseUrl}/manager/${employeeId}`, city);
  }

  // Loan type endpoints
  addLoanType(loanTypeDto: LoanTypeDto): Observable<LoanTypeResponseDto> {
    return this.http.post<LoanTypeResponseDto>(`${this.baseUrl}/loans/loantype`, loanTypeDto);
  }

  getAllLoanTypes(): Observable<LoanTypeResponseDto[]> {
    return this.http.get<LoanTypeResponseDto[]>(`${this.baseUrl}/loans/loantype`);
  }

  // Collateral endpoints
  addCollateral(collateralTypeDto: CollateralTypeDto): Observable<CollatoralTypeResponseDto> {
    return this.http.post<CollatoralTypeResponseDto>(`${this.baseUrl}/loans/collaterals`, collateralTypeDto);
  }

  getAllCollaterals(): Observable<CollatoralTypeResponseDto[]> {
    return this.http.get<CollatoralTypeResponseDto[]>(`${this.baseUrl}/loans/collaterals`);
  }

  updateLoanScheme(id: number, dto: UpdateLoanSchemeDto): Observable<LoanResponseDto> {
  return this.http.put<LoanResponseDto>(`${this.baseUrl}/loans/${id}`, dto);
}

getAllEmployees(page: number, size: number): Observable<PageResponseDto<EmployeeResponseDto>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<PageResponseDto<EmployeeResponseDto>>(`${this.baseUrl}/managers`, { params });
  }

}

import { Injectable } from '@angular/core';
import { AppliedLoanApplications } from '../entity/AppliedLoanApplications';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {
  private apiUrl = 'http://localhost:8080/loan-app/applications'; // ✅ base URL
  constructor(private http: HttpClient) { }

getLoanApplicationById(applicationId: string): Observable<AppliedLoanApplications> {
  return this.http.get<AppliedLoanApplications>(`${this.apiUrl}/${applicationId}`);
}
}

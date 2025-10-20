import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoanResponseDto } from '../entity/LoanResponseDto';

@Injectable({
  providedIn: 'root'
})
export class LoanScheme {

  private baseUrl = '/loan-app';

  constructor(private http: HttpClient) { }

  // Get loan by ID
  getLoanById(loanSchemeId: number): Observable<LoanResponseDto> {
    return this.http.get<LoanResponseDto>(`${this.baseUrl}/loans/${loanSchemeId}`);
  }

  // Get all loans or filter by loan type
  getLoanSchemes(loanType?: string): Observable<LoanResponseDto[]> {
    let params = new HttpParams();
    if (loanType) params = params.set('loanType', loanType);
    return this.http.get<LoanResponseDto[]>(`${this.baseUrl}/loans`, { params });
  }
}

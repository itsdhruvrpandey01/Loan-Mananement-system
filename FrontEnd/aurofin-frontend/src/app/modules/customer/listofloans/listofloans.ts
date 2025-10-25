import { Component, OnInit } from '@angular/core';
import { LoanSchemeDto } from '../../../entity/LoanSchemeDto';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoanResponseDto } from '../../../entity/LoanResponseDto';

@Component({
  selector: 'app-listofloans',
  standalone: false,
  templateUrl: './listofloans.html',
  styleUrl: './listofloans.css'
})
export class Listofloans implements OnInit {
  loanSchemes: LoanResponseDto[] = [];

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.loadLoanSchemes();
  }

  loadLoanSchemes() {
    this.http.get<LoanResponseDto[]>('http://localhost:8080/loan-app/loans').subscribe({
      next: (res) => this.loanSchemes = res,
      error: (err) => console.error('Error fetching loan schemes:', err)
    });
  }

  applyLoan(scheme: LoanResponseDto) {
    this.router.navigate(['/customer/applyloan'], {
      queryParams: { loanId: scheme.loanSchemeId }
    });
  }
}
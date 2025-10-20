import { Component, OnInit } from '@angular/core';
import { LoanSchemeDto } from '../../../entity/LoanSchemeDto';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-listofloans',
  standalone: false,
  templateUrl: './listofloans.html',
  styleUrl: './listofloans.css'
})
export class Listofloans implements OnInit {
  loanSchemes: LoanSchemeDto[] = [];

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.loadLoanSchemes();
  }

  loadLoanSchemes() {
    this.http.get<LoanSchemeDto[]>('http://localhost:8080/loan-app/loans').subscribe({
      next: (res) => this.loanSchemes = res,
      error: (err) => console.error('Error fetching loan schemes:', err)
    });
  }

  applyLoan(scheme: LoanSchemeDto) {
    this.router.navigate(['/customer/applyloan'], {
      queryParams: { loanId: scheme.loanTypeId }
    });
  }
}
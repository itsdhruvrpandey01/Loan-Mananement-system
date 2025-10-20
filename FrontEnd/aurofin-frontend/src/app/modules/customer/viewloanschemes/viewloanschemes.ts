import { Component, OnInit } from '@angular/core';
import { LoanSchemeDto } from '../../../entity/LoanSchemeDto';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-viewloanschemes',
  standalone: false,
  templateUrl: './viewloanschemes.html',
  styleUrl: './viewloanschemes.css'
})
export class Viewloanschemes implements OnInit {
  loanSchemes: LoanSchemeDto[] = [];
  selectedScheme: LoanSchemeDto | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadLoanSchemes();
  }

  loadLoanSchemes() {
    this.http.get<LoanSchemeDto[]>('http://localhost:8080/loan-app/loans')
      .subscribe({
        next: (res) => this.loanSchemes = res,
        error: (err) => console.error('Error fetching loan schemes:', err)
      });
  }

  viewDetails(scheme: LoanSchemeDto) {
    this.selectedScheme = scheme;
  }

  clearSelection() {
    this.selectedScheme = null;
  }
}
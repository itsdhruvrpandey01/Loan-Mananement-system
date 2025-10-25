import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadLoanSchemes();
  }

  loadLoanSchemes() {
    this.http.get<LoanSchemeDto[]>('http://localhost:8080/loan-app/loans')
      .subscribe({
        next: (res) => {
          this.loanSchemes = res;
          console.log('Loaded loan schemes:', res);
        },
        error: (err) => console.error('Error fetching loan schemes:', err)
      });
  }

  viewDetails(scheme: LoanSchemeDto) {
    this.selectedScheme = scheme;
  }

  clearSelection() {
    this.selectedScheme = null;
  }

  applyForThisLoan() {
    if (this.selectedScheme) {
      // Navigate to apply form with selected scheme data
      this.router.navigate(['/customer/apply-for-loan'], {
        state: { selectedScheme: this.selectedScheme }
      });
    }
  }
}
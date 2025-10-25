import { Component } from '@angular/core';
import { LoanSchemeDto } from '../../../entity/LoanSchemeDto';
import { AdminService } from '../../../services/admin-service';
import { Router } from '@angular/router';
import { LoanScheme } from '../../../services/loanscheme';
import { LoanResponseDto } from '../../../entity/LoanResponseDto';

@Component({
  selector: 'app-list-loans',
  standalone: false,
  templateUrl: './list-loans.html',
  styleUrl: './list-loans.css'
})
export class ListLoans {
loans: LoanResponseDto[] = [];

  constructor(private adminService: AdminService, private router: Router,private loanSchemeService:LoanScheme) {}

  ngOnInit(): void {
    this.fetchLoans();
  }

  fetchLoans(): void {
    this.adminService.getLoanSchemes().subscribe({
      next: (data) => (this.loans = data),
      error: (err) => console.error('Error fetching loans:', err)
    });
  }

  // Correct paths according to routing
createNewLoan(): void {
  this.router.navigate(['/admin/loan-scheme'], { queryParams: { mode: 'CREATE' } });
}

viewLoan(id: number): void {
  this.router.navigate(['/admin/loan-scheme', id], { queryParams: { mode: 'VIEW' } });
}

editLoan(id: number): void {
  this.router.navigate(['/admin/loan-scheme', id], { queryParams: { mode: 'UPDATE' } });
}


  deleteLoan(id: number): void {
    if (confirm('Are you sure you want to delete this loan scheme?')) {
      this.adminService.deActivateLoan(id).subscribe({
        next: () => {
          alert('Loan scheme deleted successfully!');
          this.fetchLoans();
        },
        error: (err) => {
          console.error('Error deleting loan scheme:', err);
          alert('Failed to delete loan scheme.');
        }
      });
    }
  }
}

import { Component, OnInit } from '@angular/core';
import { AppliedLoanApplications } from '../../../entity/AppliedLoanApplications';
import { ManagerService } from '../../../services/manager-service';

@Component({
  selector: 'app-requestedloans',
  standalone: false,
  templateUrl: './requestedloans.html',
  styleUrl: './requestedloans.css'
})
export class Requestedloans implements OnInit {

  requestedLoans: AppliedLoanApplications[] = [];
  managerId = localStorage.getItem('managerId') || '';
  loading: boolean = false;
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private managerService: ManagerService) { }

  ngOnInit(): void {
    this.fetchRequestedLoans();
  }

  fetchRequestedLoans(): void {
    this.loading = true;
    this.errorMessage = '';
    
    this.managerService.getLoansAssignedToManager(this.managerId)
      .subscribe({
        next: (response: AppliedLoanApplications[]) => {
          this.requestedLoans = response;
          this.loading = false;
        },
        error: (err) => {
          console.error('Error fetching requested loans:', err);
          this.errorMessage = 'Failed to load loan applications';
          this.loading = false;
        }
      });
  }

  approveLoan(applicationId: string): void {
    if (!confirm('Are you sure you want to approve this loan application?')) {
      return;
    }

    this.managerService.approveLoan(applicationId)
      .subscribe({
        next: (response) => {
          this.successMessage = 'Loan approved successfully!';
          this.fetchRequestedLoans();
          setTimeout(() => this.successMessage = '', 3000);
        },
        error: (err) => {
          console.error('Error approving loan:', err);
          this.errorMessage = 'Failed to approve loan';
          setTimeout(() => this.errorMessage = '', 3000);
        }
      });
  }

  rejectLoan(applicationId: string): void {
    const reason = prompt('Please enter reason for rejection:');
    
    if (reason === null) {
      return;
    }

    this.managerService.rejectLoan(applicationId, reason)
      .subscribe({
        next: (response) => {
          this.successMessage = 'Loan rejected successfully!';
          this.fetchRequestedLoans();
          setTimeout(() => this.successMessage = '', 3000);
        },
        error: (err) => {
          console.error('Error rejecting loan:', err);
          this.errorMessage = 'Failed to reject loan';
          setTimeout(() => this.errorMessage = '', 3000);
        }
      });
  }

  viewMissingDocuments(applicationId: string): void {
    this.managerService.getMissingDocuments(applicationId)
      .subscribe({
        next: (documents) => {
          if (documents.length === 0) {
            alert('All documents are submitted!');
          } else {
            alert('Missing Documents:\n' + documents.join('\n'));
          }
        },
        error: (err) => {
          console.error('Error fetching missing documents:', err);
          alert('Failed to fetch missing documents');
        }
      });
  }

  isPending(status: string): boolean {
    return status.toLowerCase() === 'pending';
  }
}
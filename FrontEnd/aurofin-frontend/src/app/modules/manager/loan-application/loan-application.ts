import { Component } from '@angular/core';
import { AppliedLoanApplications } from '../../../entity/AppliedLoanApplications';
import { ActivatedRoute } from '@angular/router';
import { ApplicationService } from '../../../services/application-service';

@Component({
  selector: 'app-loan-application',
  standalone: false,
  templateUrl: './loan-application.html',
  styleUrl: './loan-application.css'
})
export class LoanApplication {
applicationId!: string;
  loanApplication?: AppliedLoanApplications;
  loading = true;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private loanService: ApplicationService
  ) {}

  ngOnInit(): void {
    this.applicationId = this.route.snapshot.paramMap.get('id')!;
    this.loadApplicationDetails();
  }

  loadApplicationDetails() {
    this.loanService.getLoanApplicationById(this.applicationId).subscribe({
      next: (data) => {
        this.loanApplication = data;
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load application details.';
        this.loading = false;
      }
    });
  }
}

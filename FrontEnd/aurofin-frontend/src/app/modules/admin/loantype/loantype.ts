import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../../services/admin-service';
import { LoanTypeDto } from '../../../entity/LoanTypeDto';

@Component({
  selector: 'app-loantype',
  standalone: false,
  templateUrl: './loantype.html',
  styleUrl: './loantype.css'
})
export class Loantype {
loanTypeForm!: FormGroup;
  isSubmitting = false;
  submissionSuccess = false;
  submissionError = '';

  constructor(private fb: FormBuilder, private adminService: AdminService) {}

  ngOnInit(): void {
    this.loanTypeForm = this.fb.group({
      typeName: ['', Validators.required],
      description: ['']
    });
  }

  get typeName() {
    return this.loanTypeForm.get('typeName')!;
  }

  onSubmit(): void {
    if (this.loanTypeForm.invalid) {
      this.loanTypeForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    this.submissionSuccess = false;
    this.submissionError = '';

    const loanType: LoanTypeDto = this.loanTypeForm.value;

    this.adminService.addLoanType(loanType).subscribe({
      next: () => {
        this.submissionSuccess = true;
        this.loanTypeForm.reset();
        this.isSubmitting = false;
      },
      error: (err) => {
        console.error('Error adding loan type:', err);
        this.submissionError = 'Failed to add loan type. Please try again.';
        this.isSubmitting = false;
      }
    });
  }
}

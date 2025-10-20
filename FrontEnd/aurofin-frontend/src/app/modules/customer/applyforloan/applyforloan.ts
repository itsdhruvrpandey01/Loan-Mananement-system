import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApplicationDto } from '../../../entity/ApplicationDto';

@Component({
  selector: 'app-applyforloan',
  standalone: false,
  templateUrl: './applyforloan.html',
  styleUrl: './applyforloan.css'
})
export class Applyforloan  {
  applyLoanForm: FormGroup;
  userId = localStorage.getItem('userId') || '';

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.applyLoanForm = this.fb.group({
      loanAmount: ['', Validators.required],
      tenure: ['', Validators.required],
      loanSchemeId: ['', Validators.required],
      occupation: ['', Validators.required],
      monthlyIncome: ['', Validators.required],
      applicantAge: ['', Validators.required]
    });
  }

  applyForLoan() {
    if (this.applyLoanForm.valid) {
      const application: ApplicationDto = this.applyLoanForm.value;
      this.http.post(`http://localhost:8080/loan-app/apply/${this.userId}`, application)
        .subscribe({
          next: () => alert('Loan application submitted successfully!'),
          error: (err) => console.error('Error applying for loan:', err)
        });
    }
  }
}
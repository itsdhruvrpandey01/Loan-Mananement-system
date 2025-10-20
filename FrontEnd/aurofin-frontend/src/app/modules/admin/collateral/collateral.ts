import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../../services/admin-service';
import { CollateralTypeDto } from '../../../entity/CollateralTypeDto';

@Component({
  selector: 'app-collateral',
  standalone: false,
  templateUrl: './collateral.html',
  styleUrl: './collateral.css'
})
export class Collateral {
collateralForm!: FormGroup;
  isSubmitting = false;
  submissionSuccess = false;
  submissionError = '';

  constructor(private fb: FormBuilder, private adminService: AdminService) {}

  ngOnInit(): void {
    this.collateralForm = this.fb.group({
      typeName: ['', Validators.required],
      description: ['']
    });
  }

  get typeName() {
    return this.collateralForm.get('typeName')!;
  }

  onSubmit(): void {
    if (this.collateralForm.invalid) {
      this.collateralForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    this.submissionSuccess = false;
    this.submissionError = '';

    const collateral: CollateralTypeDto = this.collateralForm.value;

    this.adminService.addCollateral(collateral).subscribe({
      next: () => {
        this.submissionSuccess = true;
        this.collateralForm.reset();
        this.isSubmitting = false;
      },
      error: (err) => {
        console.error('Error adding collateral:', err);
        this.submissionError = 'Failed to add collateral. Please try again.';
        this.isSubmitting = false;
      }
    });
  }
}

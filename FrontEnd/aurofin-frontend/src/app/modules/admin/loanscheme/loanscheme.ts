import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoanTypeResponseDto } from '../../../entity/LoanTypeResponseDto';
import { CollatoralTypeResponseDto } from '../../../entity/CollatoralTypeResponseDto';
import { AdminService } from '../../../services/admin-service';
import { LoanSchemeDto } from '../../../entity/LoanSchemeDto';

@Component({
  selector: 'app-loanscheme',
  standalone: false,
  templateUrl: './loanscheme.html',
  styleUrl: './loanscheme.css'
})
export class Loanscheme {
loanForm!: FormGroup;
  loanTypes: LoanTypeResponseDto[] = [];
  collaterals: CollatoralTypeResponseDto[] = [];

  constructor(private fb: FormBuilder, private adminService: AdminService) {}

  ngOnInit(): void {
    this.buildForm();
    this.fetchLoanTypes();
    this.fetchCollaterals();
  }

  buildForm(): void {
    this.loanForm = this.fb.group({
      loanName: ['', Validators.required],
      loanTypeId: [null, Validators.required],
      minLoanAmount: [0, Validators.required],
      maxLoanAmount: [0, Validators.required],
      interestRate: [0, Validators.required],
      maxTenure: [0, Validators.required],
      minAge: [18, Validators.required],
      maxAge: [70, Validators.required],
      minIncome: [0, Validators.required],
      collateralRequired: [false],
      collateralRequirements: this.fb.array([]),
      otherConditions: [''],
      installmentDurationType: ['', Validators.required],
      processingFeeFlat: [0, Validators.required],
      earlyClosureCharge: [0, Validators.required],
      defaultPenaltyRate: [0, Validators.required]
    });
  }

  get collateralRequirements(): FormArray {
    return this.loanForm.get('collateralRequirements') as FormArray;
  }

  fetchLoanTypes(): void {
    this.adminService.getAllLoanTypes().subscribe({
      next: (types) => this.loanTypes = types,
      error: (err) => console.error('Failed to load loan types', err)
    });
  }

  fetchCollaterals(): void {
    this.adminService.getAllCollaterals().subscribe({
      next: (data) => {
        this.collaterals = data;
        // Add one form group per collateral
        if (this.loanForm.get('collateralRequired')?.value) {
          this.addCollateralFormGroups();
        }
      },
      error: (err) => console.error('Failed to load collaterals', err)
    });
  }

  onCollateralToggle(): void {
    const checked = this.loanForm.get('collateralRequired')?.value;
    this.collateralRequirements.clear();

    if (checked) {
      this.addCollateralFormGroups();
    }
  }

  addCollateralFormGroups(): void {
    for (let collateral of this.collaterals) {
      this.collateralRequirements.push(
        this.fb.group({
          collatoralId: [collateral.collateralTypeId, Validators.required],
          requiredDocuments: [''] // Will be converted to array on submit
        })
      );
    }
  }

  onSubmit(): void {
    if (this.loanForm.invalid) {
      this.loanForm.markAllAsTouched();
      return;
    }

    let formValue = this.loanForm.value;

    // Convert requiredDocuments (comma-separated string) to string[]
    formValue.collateralRequirements = formValue.collateralRequirements.map((item: any) => ({
      ...item,
      requiredDocuments: item.requiredDocuments
        ? item.requiredDocuments.split(',').map((doc: string) => doc.trim())
        : []
    }));

    const payload: LoanSchemeDto = formValue;
    console.log('Submitting Loan Scheme:', payload);

    this.adminService.addLoanScheme(payload).subscribe({
      next: () => {
        alert('Loan scheme created successfully!');
        this.loanForm.reset();
      },
      error: (err) => {
        console.error('Error submitting loan scheme:', err);
        alert('Failed to create loan scheme.');
      }
    });
  }
}

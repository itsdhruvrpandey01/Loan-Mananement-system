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
  availableCollaterals: CollatoralTypeResponseDto[] = [];

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
      next: (types) => (this.loanTypes = types),
      error: (err) => console.error('Failed to load loan types', err)
    });
  }

  fetchCollaterals(): void {
    this.adminService.getAllCollaterals().subscribe({
      next: (data) => {
        this.collaterals = data;
        this.availableCollaterals = [...data];
      },
      error: (err) => console.error('Failed to load collaterals', err)
    });
  }

  onCollateralToggle(): void {
    const checked = this.loanForm.get('collateralRequired')?.value;
    this.collateralRequirements.clear();

    if (checked) {
      this.availableCollaterals = [...this.collaterals];
      this.addCollateralBox(); // show one by default
    }
  }

  addCollateralBox(): void {
    if (this.availableCollaterals.length === 0) return;

    const group = this.fb.group({
      collatoralId: ['', Validators.required],
      requiredDocuments: ['']
    });

    this.collateralRequirements.push(group);
  }

  removeCollateralBox(index: number): void {
    this.collateralRequirements.removeAt(index);
    this.updateAvailableCollaterals();
  }

  updateAvailableCollaterals(): void {
    const selectedIds = this.collateralRequirements.controls
      .map((g) => g.get('collatoralId')?.value)
      .filter((v) => v);

    this.availableCollaterals = this.collaterals.filter(
      (c) => !selectedIds.includes(c.collateralTypeId)
    );
  }

  availableCollateralsForIndex(index: number): CollatoralTypeResponseDto[] {
    const selectedIds = this.collateralRequirements.controls
      .map((g, i) => (i === index ? null : g.get('collatoralId')?.value))
      .filter((v) => v);
    return this.collaterals.filter(
      (c) => !selectedIds.includes(c.collateralTypeId)
    );
  }

  onSubmit(): void {
    if (this.loanForm.invalid) {
      this.loanForm.markAllAsTouched();
      return;
    }

    let formValue = this.loanForm.value;

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

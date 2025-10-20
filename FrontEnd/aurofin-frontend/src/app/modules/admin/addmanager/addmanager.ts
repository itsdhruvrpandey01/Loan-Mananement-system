import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ManagerService } from '../../../services/manager-service';
import { EmployeeRequestDto } from '../../../entity/EmployeeRequestDto';
import { AdminService } from '../../../services/admin-service';

@Component({
  selector: 'app-addmanager',
  standalone: false,
  templateUrl: './addmanager.html',
  styleUrl: './addmanager.css'
})
export class Addmanager {
managerForm!: FormGroup;
  submissionSuccess = false;
  submissionError = '';

  constructor(private fb: FormBuilder, private adminservice: AdminService) {}

  ngOnInit(): void {
    this.managerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      city: ['', Validators.required],
      pincode: ['', [Validators.required, Validators.pattern('^[0-9]{6}$')]],
      designation: ['', Validators.required],
      salary: ['', [Validators.required, Validators.min(0)]],
    });
  }

  onSubmit(): void {
    if (this.managerForm.invalid) {
      this.managerForm.markAllAsTouched();
      return;
    }

    const newManager: EmployeeRequestDto = this.managerForm.value;

    this.adminservice.addEmployee(newManager).subscribe({
      next: (res) => {
        this.submissionSuccess = true;
        this.submissionError = '';
        this.managerForm.reset();
      },
      error: (err) => {
        console.error('Error adding manager:', err);
        this.submissionError = 'Failed to add manager. Please try again.';
        this.submissionSuccess = false;
      }
    });
  }
}

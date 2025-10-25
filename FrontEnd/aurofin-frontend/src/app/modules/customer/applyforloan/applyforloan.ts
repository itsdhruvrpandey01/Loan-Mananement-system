import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApplicationDto } from '../../../entity/ApplicationDto';
import { LoanSchemeDto } from '../../../entity/LoanSchemeDto';
import { LoanResponseDto } from '../../../entity/LoanResponseDto';

interface DocumentUpload {
  file: File | null;
  docType: string;
  fileName: string;
}

@Component({
  selector: 'app-applyforloan',
  standalone: false,
  templateUrl: './applyforloan.html',
  styleUrl: './applyforloan.css'
})
export class Applyforloan implements OnInit {
  applyLoanForm: FormGroup;
  userId = localStorage.getItem('userId') || '';
  customerId = localStorage.getItem('customerId') || '';
  selectedScheme: LoanResponseDto | null = null;
  


  // Document upload related
  requiredDocuments: string[] = [];
  documentUploads: DocumentUpload[] = [];

  // Application state
  applicationCreated = false;
  applicationId: string = '';
  isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    this.applyLoanForm = this.fb.group({
      loanAmount: ['', [Validators.required, Validators.min(1)]],
      tenure: ['', [Validators.required, Validators.min(1)]],
      loanSchemeId: [''],
      occupation: ['', Validators.required],
      monthlyIncome: ['', [Validators.required, Validators.min(0)]],
      applicantAge: ['', [Validators.required, Validators.min(18)]]
    });


    // IMPORTANT: Get selected scheme from navigation state in constructor
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras?.state) {
      this.selectedScheme = navigation.extras.state['selectedScheme'];
      console.log('✓ Scheme received in constructor:', this.selectedScheme);
    }

    // Also try to get from history state
    if (!this.selectedScheme && history.state.selectedScheme) {
      this.selectedScheme = history.state.selectedScheme;
      console.log('✓ Scheme received from history state:', this.selectedScheme);
    }
  }

  ngOnInit(): void {
    // Check if userId exists
    if (!this.userId) {
      console.error('⚠️ WARNING: userId not found in localStorage!');
      alert('User ID not found. Please login first.');
    } else {
      console.log('✓ User ID found:', this.userId);
    }

    if (this.selectedScheme) {
      console.log('✓ Selected scheme loaded:', this.selectedScheme);
      this.populateFormWithScheme();
      this.prepareRequiredDocuments();
    } else {
      console.log('⚠️ No scheme selected - user must enter loanSchemeId manually');
    }

  }

  loanSchemeId !: number;

  populateFormWithScheme() {
    if (this.selectedScheme) {
      this.applyLoanForm.patchValue({
        loanSchemeId: this.selectedScheme.loanSchemeId
      });
      console.log('✓ Loan Scheme ID set to:', this.selectedScheme.loanSchemeId);

      // Set validators based on scheme
      this.applyLoanForm.get('loanAmount')?.setValidators([
        Validators.required,
        Validators.min(this.selectedScheme.minLoanAmount),
        Validators.max(this.selectedScheme.maxLoanAmount)
      ]);

      this.applyLoanForm.get('tenure')?.setValidators([
        Validators.required,
        Validators.min(1),
        Validators.max(this.selectedScheme.maxTenure)
      ]);

      this.applyLoanForm.get('monthlyIncome')?.setValidators([
        Validators.required,
        Validators.min(this.selectedScheme.minIncome)
      ]);

      this.applyLoanForm.get('applicantAge')?.setValidators([
        Validators.required,
        Validators.min(this.selectedScheme.minAge),
        Validators.max(this.selectedScheme.maxAge)
      ]);

      // IMPORTANT: Update form validity after setting validators
      this.applyLoanForm.updateValueAndValidity();

      console.log('✓ Form validators updated');
      console.log('Form values after patch:', this.applyLoanForm.value);
      console.log('Form valid after patch:', this.applyLoanForm.valid);
    }
  }

  prepareRequiredDocuments() {
    this.requiredDocuments = [];

    if (this.selectedScheme?.collateralRequired &&
      this.selectedScheme.collateralRequirements) {

      // Collect all required documents from all collateral types
      this.selectedScheme.collateralRequirements.forEach(collateral => {
        if (collateral.requiredDocuments) {
          this.requiredDocuments.push(...collateral.requiredDocuments);
        }
      });
    }

    // Always require basic KYC documents
    const basicDocs = ['Aadhaar Card', 'PAN Card', 'Income Proof'];
    basicDocs.forEach(doc => {
      if (!this.requiredDocuments.includes(doc)) {
        this.requiredDocuments.unshift(doc);
      }
    });

    // Initialize document upload array
    this.documentUploads = this.requiredDocuments.map(docType => ({
      file: null,
      docType: docType,
      fileName: ''
    }));

    console.log('✓ Required documents prepared:', this.requiredDocuments);
  }

  onFileSelected(event: any, index: number) {
    const file = event.target.files[0];
    if (file) {
      // Optional: Add file size validation
      // const maxSize = 5 * 1024 * 1024; // 5MB
      // if (file.size > maxSize) {
      //   alert('File size must be less than 5MB');
      //   return;
      // }

      this.documentUploads[index].file = file;
      this.documentUploads[index].fileName = file.name;
      console.log(`✓ File selected for ${this.documentUploads[index].docType}:`, file.name);
    }
  }

  removeFile(index: number) {
    this.documentUploads[index].file = null;
    this.documentUploads[index].fileName = '';

    // Reset file input
    const fileInput = document.getElementById(`file-${index}`) as HTMLInputElement;
    if (fileInput) {
      fileInput.value = '';
    }
    console.log(`✓ File removed for ${this.documentUploads[index].docType}`);
  }

  allDocumentsUploaded(): boolean {
    return this.documentUploads.every(doc => doc.file !== null);
  }

  applyForLoan() {
    console.log('=== APPLY FOR LOAN CLICKED ===');
    console.log('Form Valid:', this.applyLoanForm.valid);
    console.log('Form Values:', this.applyLoanForm.value);
    console.log('Loan Scheme ID in form:', this.applyLoanForm.get('loanSchemeId')?.value);
    console.log('userId:', this.userId);

    // Check userId
    if (!this.userId) {
      alert('Error: User ID not found. Please login first.');
      console.error('❌ userId is missing!');
      return;
    }

    if (this.applyLoanForm.valid) {
      console.log('✓ Form is valid, proceeding with submission...');

      this.isSubmitting = true;
      const application: ApplicationDto = this.applyLoanForm.value;
      // application.loanSchemeId = this.selectedScheme?.loanTypeId ? Number(this.selectedScheme.loanTypeId) : -1;
      console.log('Application data to send:', application);

      const url = `http://localhost:8080/loan-app/customer/${this.customerId}/loans/application`;
      console.log('Posting to URL:', url);

      this.http.post<any>(url, application).subscribe({
        next: (response) => {
          console.log('✅ SUCCESS! Application created:', response);
          this.applicationCreated = true;

          // Try to get applicationId from different possible response structures
          if (response.applicationId) {
            this.applicationId = response.applicationId;
          } else if (response.id) {
            this.applicationId = response.id;
          } else if (typeof response === 'string') {
            this.applicationId = response;
          } else {
            console.warn('⚠️ Could not find applicationId in response:', response);
            this.applicationId = 'unknown';
          }

          console.log('Application ID:', this.applicationId);

          alert('Loan application submitted successfully! Please upload required documents.');
          this.isSubmitting = false;

          // If documents are ready, upload them
          if (this.allDocumentsUploaded()) {
            this.uploadDocuments();
          }
        },
        error: (err) => {
          console.error('❌ ERROR applying for loan:', err);
          console.error('Error status:', err.status);
          console.error('Error body:', err.error);

          let errorMessage = 'Unknown error occurred';

          if (err.status === 0) {
            errorMessage = 'Cannot connect to server. Please check if backend is running.';
          } else if (err.status === 401) {
            errorMessage = 'Unauthorized. Please login again.';
          } else if (err.status === 403) {
            errorMessage = 'Access forbidden. Check your permissions.';
          } else if (err.status === 404) {
            errorMessage = 'API endpoint not found. Check backend URL.';
          } else if (err.error?.message) {
            errorMessage = err.error.message;
          } else if (err.message) {
            errorMessage = err.message;
          }

          alert('Error submitting loan application: ' + errorMessage);
          this.isSubmitting = false;
        }
      });
    } else {
      console.log('❌ Form is INVALID');
      console.log('Form errors:', this.getFormValidationErrors());
      this.markFormGroupTouched(this.applyLoanForm);

      // Show which fields are invalid
      const invalidFields = this.getInvalidFields();
      if (invalidFields.length > 0) {
        alert('Please fix the following fields:\n' + invalidFields.join('\n'));
      } else {
        alert('Please fill all required fields correctly');
      }
    }
  }

  uploadDocuments() {
    if (!this.applicationId) {
      alert('Please submit the loan application first');
      console.error('❌ Cannot upload documents: No application ID');
      return;
    }

    if (!this.allDocumentsUploaded()) {
      alert('Please upload all required documents');
      console.error('❌ Cannot upload: Not all documents selected');
      return;
    }

    console.log('=== UPLOADING DOCUMENTS ===');
    console.log('Application ID:', this.applicationId);
    console.log('Documents to upload:', this.documentUploads.length);

    this.isSubmitting = true;
    const formData = new FormData();

    // Append all files
    this.documentUploads.forEach(doc => {
      if (doc.file) {
        formData.append('files', doc.file);
        console.log('Added file:', doc.file.name);
      }
    });

    // Append document types
    this.documentUploads.forEach(doc => {
      formData.append('docTypes', doc.docType);
      console.log('Added docType:', doc.docType);
    });

    const url = `http://localhost:8080/loan-app/customer/loans/application/${this.applicationId}`;
    console.log('Uploading to URL:', url);

    this.http.put(url, formData).subscribe({
      next: (response) => {
        console.log('✅ Documents uploaded successfully:', response);
        alert('Documents uploaded successfully! Your application is now complete.');
        this.isSubmitting = false;

        // Navigate to applied loans page
        this.router.navigate(['/customer/applied-loans']);
      },
      error: (err) => {
        console.error('❌ Error uploading documents:', err);
        console.error('Error status:', err.status);
        console.error('Error body:', err.error);

        let errorMessage = 'Unknown error occurred';

        if (err.status === 0) {
          errorMessage = 'Cannot connect to server. Please check if backend is running.';
        } else if (err.error?.message) {
          errorMessage = err.error.message;
        } else if (err.message) {
          errorMessage = err.message;
        }

        alert('Error uploading documents: ' + errorMessage);
        this.isSubmitting = false;
      }
    });
  }

  // Helper method to get validation errors
  private getFormValidationErrors(): any {
    const errors: any = {};
    Object.keys(this.applyLoanForm.controls).forEach(key => {
      const control = this.applyLoanForm.get(key);
      if (control && control.errors) {
        errors[key] = control.errors;
      }
    });
    return errors;
  }

  // Helper method to get list of invalid fields
  private getInvalidFields(): string[] {
    const invalidFields: string[] = [];
    Object.keys(this.applyLoanForm.controls).forEach(key => {
      const control = this.applyLoanForm.get(key);
      if (control && control.invalid) {
        const fieldName = this.getFieldDisplayName(key);
        const errors = control.errors;
        if (errors) {
          if (errors['required']) {
            invalidFields.push(`• ${fieldName} is required`);
          } else if (errors['min']) {
            invalidFields.push(`• ${fieldName} must be at least ${errors['min'].min}`);
          } else if (errors['max']) {
            invalidFields.push(`• ${fieldName} must not exceed ${errors['max'].max}`);
          }
        }
      }
    });
    return invalidFields;
  }

  // Helper to get user-friendly field names
  private getFieldDisplayName(fieldName: string): string {
    const displayNames: { [key: string]: string } = {
      'loanAmount': 'Loan Amount',
      'tenure': 'Tenure',
      'loanSchemeId': 'Loan Scheme ID',
      'occupation': 'Occupation',
      'monthlyIncome': 'Monthly Income',
      'applicantAge': 'Applicant Age'
    };
    return displayNames[fieldName] || fieldName;
  }

  private markFormGroupTouched(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.get(key);
      control?.markAsTouched();
    });
  }

  goBack() {
    this.router.navigate(['/customer/view-loan-schemes']);
  }
}
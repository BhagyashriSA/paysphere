import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { BeneficiaryService } from '../../services/beneficiary.service';
import { Beneficiary } from '../../models/beneficiary';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-beneficiary',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './add-beneficiary.component.html',
  styleUrl: './add-beneficiary.component.css'
})
export class AddBeneficiaryComponent {

  beneficiaryForm!: FormGroup;
  submitted = false;
  successMessage = '';
  errorMessage = '';

  accountTypes: string[] = [
    'Current',
    'Savings',
    'Salary',
    'Fixed Deposit'
  ];

  constructor(private fb: FormBuilder, 
    private beneficiaryService: BeneficiaryService,
    private router: Router) {
    this.initForm();
  }

  initForm(): void {
    this.beneficiaryForm = this.fb.group({
      beneficiaryName: ['', [Validators.required]],
      accountNumber: ['', [Validators.required, Validators.maxLength(10)]],
      ifscCode: ['', Validators.required],
      bankName: ['', Validators.required],
      branchName: ['', Validators.required],
      accountType: ['', Validators.required],
      nickName: ['', Validators.required]
    })
  }

  get f() {
    return this.beneficiaryForm.controls;
  }

  submit(): void {

    this.submitted = true;
    this.successMessage = '';
    this.errorMessage = '';

    if (this.beneficiaryForm.invalid) {
      return;
    }

    const beneficiaryData: Beneficiary = this.beneficiaryForm.value;

    this.beneficiaryService.addBeneficiary(beneficiaryData).subscribe({
      next: (response) => {
        console.log('Beneficiary added:', response);

        this.successMessage = 'Beneficiary added successfully';

        this.beneficiaryForm.reset();
        this.submitted = false;
        this.router.navigate(['/view-beneficiary']);
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Failed to add beneficiary';
      }
    });
  }

}



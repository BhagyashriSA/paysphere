import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AccountService } from '../../services/account.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Console } from 'node:console';
import { Customer } from '../../models/customer';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-create-account',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, FormsModule],   // ✅ correct
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css'] // ✅ fixed
})
export class CreateAccountComponent implements OnInit {

  accountForm!: FormGroup;   // ✅ FIXED (VERY IMPORTANT)

  accountTypes = ['SAVINGS', 'CURRENT'];
  statuses = ['ACTIVE', 'BLOCKED', 'CLOSED'];
  branches: any[] = [];
  searchText: string = '';
  selectedCustomer: any = null;
  accountHolderName: string = '';
  selectedCif: string = '';
  customers: Customer[] = [];
  states: string[] = [];
  selectedState = '';
  selectedBranchId = '';
  selectedCity = '';
  cities: string[] = [];
  branch: any[] = [];





  constructor(private fb: FormBuilder,
    private http: HttpClient,
    private accountService: AccountService,
    private router: Router) { }



  ngOnInit(): void {
    this.accountForm = this.fb.group({
      userId: ['', Validators.required],
      accountHolderName: ['', [Validators.required, Validators.minLength(3)]],
      accountNumber: [{ value: '', disabled: true }],
      accountType: ['', Validators.required],
      customerId: ['', Validators.required],
      balance: [0, [Validators.required, Validators.min(0)]],
      currency: ['INR', Validators.required],
      branchId: ['', Validators.required],
      state: ['', Validators.required],
      city: ['', Validators.required],
      ifscCode: [{ value: '', disabled: true }]

    });
    this.loadAccountNumber();
    this.loadBranches();
    this.loadStates();
  }

  //  LOAD AccountNumber (separate method)
  loadAccountNumber() {
    this.accountService.getAccountNumber().subscribe(res => {
      this.accountForm.patchValue({
        accountNumber: res
      });
    });
  }

  //  LOAD BRANCHES (separate method)
  loadBranches(): void {
    this.accountService.getBranches().subscribe({
      next: (data: any[]) => {
        this.branches = data;
        console.log("Branches:", data);
      },
      error: (err: any) => {
        console.error("Error fetching branches", err);
      }
    });
  }

  //  LOAD states (separate method)
  loadStates(): void {
    this.accountService.getStates().subscribe(res => {
      this.states = res;
    });
  }

  submi1t() {
    console.log("Submit button clicked");
  }

  submit() {
    console.log("Submit button clicked");

    if (this.accountForm.valid) {
      console.log(this.accountForm.getRawValue());
      return;
    }

    console.log("Submit1  button clicked");

    // const payload = {
    //   accountHolderName: this.accountForm.value.accountHolderName,
    //   customerId: this.selectedCif,
    //   accountNumber: this.accountForm.value.accountNumber,
    //   accountType: this.accountForm.value.accountType,
    //   balance: this.accountForm.value.balance,
    //   currency: this.accountForm.value.currency,
    //   branchId: this.selectedBranchId,
    //   ifscCode: this.accountForm.value.ifscCode
    // };

    console.log("Submi2 button clicked");
    const form = this.accountForm.getRawValue();
    const branch = this.accountForm.value.branchId;

    const payload = {
      ...form,
      branchId: branch.branchId,
      branchCode: branch.branchCode,
    };
    console.log("Payload:", payload);

    this.accountService.createAccount(payload).subscribe({

      next: (res: any) => {
        console.log("Account Created:", res);

        alert("Account created successfully");

       this.router.navigate(['/accounts/fetch-account']);

        this.accountForm.reset();

        this.selectedCif = '';
        this.customers = [];
      },

      error: (err) => {
        console.log(err);

        alert("Failed to create account");
      }
    });
  }


  // submit() {
  //   if (this.accountForm.invalid) {
  //     this.accountForm.markAllAsTouched();
  //     return;
  //   }

  //   const payload = {
  //     ...this.accountForm.value,
  //     customer: { customerId: this.accountForm.value.customerId },
  //     branch: { branchId: this.accountForm.value.branchId }
  //   };

  //   this.http.post('http://localhost:8080/api/accounts', payload)
  //     .subscribe({
  //       next: () => {
  //         alert('Account created successfully');
  //         this.accountForm.reset();   // ✅ works now
  //       },
  //       error: (err) => {
  //         console.error(err);
  //         alert('Error creating account');
  //       }
  //     });
  // }

  searchCustomerByName(value: string) {
    console.log("Typing:", value);

    if (value.length < 2) {
      this.customers = [];
      return;
    }

    this.accountService.searchCustomer(value).subscribe((res: any) => {
      console.log("API:", res);

      // 🔥 adjust based on your backend
      // this.customers = res.content || res;
      this.customers = res.data;

    });
  }

  selectCustomer(c: Customer) {
    this.selectedCustomer = c;
    this.accountHolderName = c.fullName;
    this.selectedCif = c.customerId;
    this.customers = [];

    this.accountForm.patchValue({
      accountHolderName: c.fullName,
      customerId: c.customerId
    });

  }

  onStateChange(event: any) {

    const selectedState = event.target.value;

    console.log("Selected State:", selectedState);

    this.accountService.getCities(selectedState).subscribe({
      next: (data) => {

        console.log("Cities:", data);

        this.cities = data;
      }
    });
  }

  onCityChange(event: any) {

    const selectedCity = event.target.value;

    console.log("Selected City:", selectedCity);

    this.accountService.getBranch(selectedCity).subscribe({
      next: (data) => {

        console.log("Cities:", data);

        this.branch = data;
      }
    });
  }

  onCityBranch(event: any) {

    const selectedBranchId = event.target.value;

    console.log("Selected branch: in genrate ifsc method", selectedBranchId);

    const state = this.accountForm.get('state')?.value;

    const city = this.accountForm.get('city')?.value;


    console.log("Selected State:", state);

    console.log("Selected City:", city);



    if (state && city && selectedBranchId) {

      const stateCode = state.substring(0, 2).toUpperCase();

      const cityCode = city.substring(0, 2).toUpperCase();

      const branch = this.accountForm.value.branchId;

      const branchCode = branch?.branchCode;

      const ifsc =
        'PSH' + stateCode + cityCode + branchCode;

      this.accountForm.patchValue({
        ifscCode: ifsc
      });

    }
  }


}
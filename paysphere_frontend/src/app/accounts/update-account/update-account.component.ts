import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup
} from '@angular/forms';
import { AccountService } from '../../services/account.service';
import { HttpClient } from '@angular/common/http';
import { Customer } from '../../models/customer';
import { Router, ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-update-account',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './update-account.component.html',
  styleUrl: './update-account.component.css'
})
export class UpdateAccountComponent {

  accountForm!: FormGroup;

  constructor(private fb: FormBuilder,
    private http: HttpClient,
    private accountService: AccountService,
    private route: ActivatedRoute,
    private router: Router) { }

  accountId!: number;
  branch: any[] = [];
  branches: any[] = [];
  ities: string[] = [];
  cities: string[] = [];
  states: string[] = [];
  selectedCif: string = '';
  customers: Customer[] = [];
  accountHolderName: string = '';
  selectedCustomer: any = null;
  currency: string = '';
  ifscCode: string = '';
  customerId: string = '';




  ngOnInit(): void {

    this.accountForm = this.fb.group({

      accountHolderName: [''],
      accountNumber: [''],
      accountType: [''],
      state: [''],
      city: [''],
      branchId: [''],
      ifscCode: [''],
      balance: [''],
      currency: [''],
      customerId: ['']


    });

    this.loadBranches();
    this.loadStates();
    this.getAccountList

    this.accountId = Number(
      this.route.snapshot.paramMap.get('id')
    );

    console.log("Received Id:", this.accountId);

    this.getAccountById(this.accountId);

  }


  submit(): void {

    console.log("Form Submitted");

    // if (this.accountForm.valid) {
    //   console.log(this.accountForm.getRawValue());
    //   return;
    // }

    const form = this.accountForm.getRawValue();
    const branch = this.accountForm.value.branchId;


    const payload = {
      ...form,
      branchId: branch.branchId,
      branchCode: branch.branchCode,
      branchName: branch.branchName,
      state: branch.state,
      city: branch.city,
    };

    console.log("Payload:", payload);


    this.accountService.updateAccount(this.accountId, payload).subscribe({
      next: (res: any) => {
        console.log("Account Updated:", res);

        alert("Account updated successfully");

        this.getAccountList();
        this.accountForm.reset();
        this.customerId = '';
        this.customers = [];
      },

      error: (err) => {
        console.log(err);
        alert("Failed to update account");
      }
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

//  LOAD states (separate method)
loadStates(): void {
  this.accountService.getStates().subscribe(res => {
    this.states = res;
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

getAccountById(accountId: number) {
  console.log("Account Id " + accountId);

  this.accountService.getAccountById(accountId)
    .subscribe((res: any) => {

      console.log(res);

      this.accountForm.patchValue({

        accountHolderName: res.accountHolderName,
        accountNumber: res.accountNumber,
        accountType: res.accountType,
        customerId: res.customerId,
        balance: res.balance,
        ifscCode: res.ifscCode,
        currency: res.currency,
        state: res.state,
        city: res.city,
        branchName: res.branchName
      });

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

getAccountList(){
  this.router.navigate(['/accounts/fetch-account']);
}


}

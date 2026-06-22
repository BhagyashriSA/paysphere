import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Account } from '../account';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-fetch-account',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './fetch-account.component.html',
  styleUrls: ['./fetch-account.component.css']
})
export class FetchAccountComponent implements OnInit {

  account: Account[] = [];
  selectedAccount: Account | null = null;
  showAccountModal = false;
  branches: any[] = []; 
// branchFilter: any;  // ✔ keep this

  constructor(
    private accountService: AccountService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadAccount();
    this.loadBranches();
  }

  // 🔹 FILTERS
  accountNoFilter: string = '';
  accountTypeFilter: string = '';
  statusFilter: string = '';
  branchFilter: string = '';

  // 🔹 PAGINATION
  page = 0;
  size = 5;
  totalElements = 0;
  totalPages = 0;
  totalPagesArray: number[] = [];

  // ✅ LOAD DATA
  loadAccount(): void {
    this.accountService.getAccountByFilter(
      this.accountNoFilter,
      this.accountTypeFilter,
      this.statusFilter,
      this.branchFilter,
      this.page,
      this.size
    ).subscribe((res: any) => {
     console.log({
  accountNo: this.accountNoFilter,
  accountType: this.accountTypeFilter,
  status: this.statusFilter,
  branch: this.branchFilter
});
      this.account = res?.content ?? [];
      this.totalElements = res?.totalElements ?? 0;
      this.totalPages = res?.totalPages ?? 0;

      this.totalPagesArray = Array.from(
        { length: this.totalPages },
        (_, i) => i
      );
    });
  }

  // ✅ LOAD BRANCHES (separate method)
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



  // 🔍 SEARCH
  search(): void {
    this.page = 0;
    this.loadAccount();
  }

  // ❌ CLEAR FIXED
  clear(): void {
    this.accountNoFilter = '';
    this.accountTypeFilter = '';
    this.statusFilter = '';
    this.branchFilter = '';
    this.page = 0;
    this.loadAccount();
  }

  // 🔹 PAGINATION
  previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadAccount();
    }
  }

  nextPage(): void {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadAccount();
    }
  }

  goToPage(index: number): void {
    this.page = index;
    this.loadAccount();
  }

  applyFilter(): void {
    this.page = 0;
    this.loadAccount();
  }

  // 👁 VIEW
  viewAccount(id: number): void {
    console.log("View account:", id);

    const foundAccount = this.account.find(a => a.accountId === id);

    if (foundAccount) {
      this.selectedAccount = foundAccount;
      this.showAccountModal = true;
    }
  }

  closeAccountModal(): void {
    this.showAccountModal = false;
  }

  // ✏ EDIT
  editAccount(id: number): void {
    console.log("Clicked ID:", id);
    this.router.navigate(['/accounts/update-account', id]);
  }

  // 🗑 DELETE
  deleteAccount(id: number): void {
    this.accountService.deleteAccount(id).subscribe({
      next: (res: any) => {
        console.log('Deleted successfully', res);
        alert("Account deleted successfully.");
        this.loadAccount(); 
      },
      error: (err: any) => {
        console.error('Error deleting account', err);
      }
    });
  }
}
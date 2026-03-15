import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Transactions } from '../transactions';
import { TransactionsService } from '../transactions.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-transactions-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './transactions-list.component.html',
  styleUrl: './transactions-list.component.css'
})
export class TransactionsListComponent implements OnInit {

    transactions: Transactions[] = [];

     constructor(private transactionservice: TransactionsService, private router:Router) {}

       ngOnInit(): void {
          this.getTransactions();
            this.loadTransactions();
  }

    private getTransactions(): void {
    this.transactionservice.getTransactionsList().subscribe((data: Transactions[]) => {
      this.transactions = data;
    });
  }

  
  // 🔹 FILTERS
  transactionIdFilter: string = '';
  transactionTypeFilter: string = '';
  channelFilter: string = '';

  // 🔹 PAGINATION
  page = 0;
  size = 5;
  totalElements = 0;
  totalPages = 0;
  totalPagesArray: number[] = [];

  loadTransactions(): void {
    this.transactionservice.getTransactionsByFilter(
      this.transactionIdFilter,
      this.transactionTypeFilter,
      this.channelFilter,
      this.page,
      this.size
    ).subscribe(res => {
      console.log('API RESPONSE:', res);

      this.transactions = res?.content ?? [];
      this.totalElements = res?.totalElements ?? 0;
      this.totalPages = res?.totalPages ?? 0;

      this.totalPagesArray = Array.from(
        { length: this.totalPages },
        (_, i) => i
      );
    });
  }

  search(): void {
  this.page = 0;
  this.loadTransactions();
}

clear(): void {
  this.transactionIdFilter = '';
  this.transactionTypeFilter = '';
  this.channelFilter = '';
  this.page = 0;
  this.loadTransactions();
}

  previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadTransactions();
    }
  }

    nextPage(): void {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadTransactions();
    }
  }

    goToPage(index: number): void {
    this.page = index;
    this.loadTransactions();
  }

  applyFilter() {
  this.page = 0;
  this.loadTransactions();
}



}

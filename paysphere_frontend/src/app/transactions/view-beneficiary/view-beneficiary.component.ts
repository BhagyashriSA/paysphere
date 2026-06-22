
import { Beneficiary } from '../../models/beneficiary';
import { BeneficiaryService } from '../../services/beneficiary.service';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'app-view-beneficiary',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './view-beneficiary.component.html',
  styleUrl: './view-beneficiary.component.css'
})
export class ViewBeneficiaryComponent {

  beneficiaries: Beneficiary[] = [];
  // filteredBeneficiaries: Beneficiary[] = [];

  beneficiaryNameFilter = '';
  accountNumberFilter = '';
  bankNameFilter = '';

  currentPage = 0;
  totalPages = 0;
  totalElements = 0;
  itemsPerPage = 5;

  constructor(private beneficiaryService: BeneficiaryService) { }

  ngOnInit(): void {
    this.loadBeneficiaries();
  }

  loadBeneficiaries(): void {
    this.beneficiaryService
      .getAllBeneficiaries(
        this.currentPage,
        this.itemsPerPage,
        this.beneficiaryNameFilter,
        this.accountNumberFilter,
        this.bankNameFilter
      )
      .subscribe({
        next: (data) => {
          console.log(JSON.stringify(data, null, 2));
          console.log('Response:', data);
          console.log('Content:', data.content);


          this.beneficiaries = data.content;
          this.currentPage = data.number;
          this.totalPages = data.totalPages;
          this.totalElements = data.totalElements;
        },
        error: (err) => {
          console.error('Error loading beneficiaries', err);
        }
      });
  }


  // applyFilters(): void {
  //   this.filteredBeneficiaries = this.beneficiaries.filter((beneficiary) => {

  //     const beneficiaryNameMatch = beneficiary.beneficiaryName
  //       .toLowerCase()
  //       .includes(this.beneficiaryNameFilter.toLowerCase());

  //     const accountNumberMatch = beneficiary.accountNumber
  //       .toLowerCase()
  //       .includes(this.accountNumberFilter.toLowerCase());

  //     const bankNameMatch = beneficiary.bankName
  //       .toLowerCase()
  //       .includes(this.bankNameFilter.toLowerCase());

  //     return (
  //       beneficiaryNameMatch &&
  //       accountNumberMatch &&
  //       bankNameMatch
  //     );
  //   });

  //   this.currentPage = 0;
  // }


  applyFilters(): void {
    this.currentPage = 0;
    this.loadBeneficiaries();
  }



  clearFilters(): void {
    this.beneficiaryNameFilter = '';
    this.accountNumberFilter = '';
    this.bankNameFilter = '';

    this.currentPage = 0;
    this.loadBeneficiaries();
  }


  // get paginatedBeneficiaries(): Beneficiary[] {
  //   return this.beneficiaries;
  // }

  // const startIndex = this.currentPage * this.itemsPerPage;

  //   return this.filteredBeneficiaries.slice(
  //   startIndex,
  //   startIndex + this.itemsPerPage
  // );
  // }


  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadBeneficiaries();
    }
  }



  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadBeneficiaries();
    }
  }


}




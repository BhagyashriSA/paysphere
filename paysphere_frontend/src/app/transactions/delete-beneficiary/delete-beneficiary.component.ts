import { Component } from '@angular/core';
import { BeneficiaryService } from '../../services/beneficiary.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-delete-beneficiary',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './delete-beneficiary.component.html',
  styleUrl: './delete-beneficiary.component.css'
})
export class DeleteBeneficiaryComponent {

  beneficiaryId!: number;

  constructor(private beneficiaryService: BeneficiaryService,
    private router: Router) { }

  deleteBeneficiary(): void {

    if (this.beneficiaryId == null) {
      alert("Please enter beneficiary ID");
      return;
    }

    this.beneficiaryService.deleteBeneficiary(this.beneficiaryId).subscribe({

      next: (response) => {
        alert(response);
        this.router.navigate(['/view-beneficiary']);
      },
      error: (err) => {
        console.log(err);
        alert("Beneficiary not found");
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/beneficiaries']);
  }



}

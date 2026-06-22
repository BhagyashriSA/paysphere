import { Component } from '@angular/core';
import { BeneficiaryService } from '../../services/beneficiary.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-activate-beneficiary',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './activate-beneficiary.component.html',
  styleUrl: './activate-beneficiary.component.css'
})
export class ActivateBeneficiaryComponent {

  beneficiaryId!:number;

  constructor(private beneficiaryService: BeneficiaryService,
              private router: Router) {}

 activateBeneficiary(): void {

    if(this.beneficiaryId==null) {
      alert("Please enter beneficiary ID");
      return;
    }

    this.beneficiaryService.activateBeneficiary(this.beneficiaryId).subscribe( { 

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

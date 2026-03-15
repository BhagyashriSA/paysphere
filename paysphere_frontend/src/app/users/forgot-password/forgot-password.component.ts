import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css'
})
export class ForgotPasswordComponent {

  loading = false;
   email: string = '';

  constructor(private authService: AuthService) {}

  submit() {

    if (!this.email) {
      alert("Please enter email");
      return;
    }
  alert(this.email);
   console.log("Email before API call:", this.email);

     const payload = {
    email: this.email
  };

  console.log("Payload:", payload);

this.authService.forgotPassword(payload).subscribe({
  next: (res: any) => {
    console.log("Full Response:", this.email);
    this.loading = false;
    alert("Reset password link sent to email");
  },
  error: (err) => {
    console.log("Error:", err);
     this.loading = false;
    alert("Failed to send reset email");
  }
});

  }


}

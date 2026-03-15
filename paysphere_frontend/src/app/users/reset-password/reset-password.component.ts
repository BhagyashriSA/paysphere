import { Component } from '@angular/core';
import { ActivatedRoute, Router} from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.css'
})
export class ResetPasswordComponent {


token: string = '';
password: string = '';
confirmPassword: string = '';

constructor(private route:ActivatedRoute,
          private service:AuthService,
          private authService: AuthService,
         private router: Router){}

ngOnInit() {
  this.token = this.route.snapshot.queryParamMap.get('token') || '';
  console.log("Token from URL:", this.token);
}

 submit() {

    if(this.password !== this.confirmPassword){
    alert("Passwords do not match");
    return;
  }


    const payload = {
    token: this.token,
    password: this.password
  };

   console.log("Payload:", payload);

   this.authService.resetPassword(payload).subscribe({
    next: (res) => {
      console.log("Success:", res);
      alert("Password reset successfully!");
      this.getlogin();
    },
    error: (err) => {
      console.error("Error:", err);
        if (err.error && err.error.message) {
      alert(err.error.message);
    } else {
      alert("Failed to reset password. Please try again.");
    }
    }
  });

  }

getlogin() {
  this.router.navigate(['/login']);
}
}

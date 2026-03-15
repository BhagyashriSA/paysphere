import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer/footer.component';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth.service';


@Component({
  selector: 'app-auth-layout',
  standalone: true,
  imports: [ReactiveFormsModule, HeaderComponent, FooterComponent, CommonModule, FormsModule, RouterModule],
  templateUrl: './auth-layout.component.html',
  styleUrl: './auth-layout.component.css'
})
export class AuthLayoutComponent {

  private authService = inject(AuthService);
  
  loginForm: FormGroup;
  errorMessage: string = '';
  loading: boolean = false;

  // isLoggedIn = true; // must be true to show
  // username = 'John';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }
    onSubmit() {
    if (this.loginForm.invalid) return;

    this.loading = true;
    this.errorMessage = '';

    const { username, password } = this.loginForm.value;

    this.http.post<any>('http://localhost:8080/auth/login', { username, password })
      .subscribe({
        next: (res) => {
          console.log('Login success:', res);
          // localStorage.setItem('token', res.token); // save JWT
          this.authService.setToken(res.token);
          this.authService.setUsername(username);

          // alert(res.token);
          this.router.navigate(['/transactions']); // redirect after login
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = err.error?.message || 'Login failed. Check credentials.';
          this.loading = false;
        }
      });
  }
  
   logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }



  // onSubmit() {
  //    alert('Login clicked');
  //    this.goToEmployeeList();
  // }

  //   goToEmployeeList(){
  //   this.route.navigate(['/employees']);
  // }


}

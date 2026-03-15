import { Component, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
  encapsulation: ViewEncapsulation.None   // 🔥 important
})
export class HeaderComponent {
    username: string = '';
  isLoggedIn = false;
   showUsers = false;

  constructor(
     private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isAuthenticated();
    this.username = this.authService.getUsername();
  }

  toggleUsers() {
  this.showUsers = !this.showUsers;
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  
  openUsers() {
    this.showUsers = true;
  }

  closeUsers() {
    this.showUsers = false;
  }

}

import { Injectable } from '@angular/core';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = "http://localhost:8080/auth";
  
  constructor(private http: HttpClient) {} 

   private platformId = inject(PLATFORM_ID);

  //  isAuthenticated(): boolean {
  //   return !!localStorage.getItem('token');
  // }

  forgotPassword(data: any) {
    return this.http.post(`${this.baseUrl}/forgot-password`, data);
  }

   resetPassword(data:any){
    return this.http.post(`${this.baseUrl}/reset-password`, data);
 }

  isAuthenticated(): boolean {
    if (isPlatformBrowser(this.platformId)) {
      return !!localStorage.getItem('token');
    }
    return false;
  }

  getUsername(): string {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('username') || '';
    }
    return '';
  }

  setToken(token: string): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('token', token);
    }
  }

  setUsername(username: string): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('username', username);
    }
  }

  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('token');
      localStorage.removeItem('username');
    }
  }
}

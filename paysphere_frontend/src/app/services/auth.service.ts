import { Injectable } from '@angular/core';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = "http://localhost:8080/auth";
  private platformId = inject(PLATFORM_ID);
  constructor(private http: HttpClient) {} 
  
  getAccessToken() {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('accessToken');
    }
    return null;
  }

     getRefreshToken() {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('refreshToken');
    }
    return null;
  }

   refreshToken() {
    return this.http.post<any>(`${this.baseUrl}/refresh`, {
      refreshToken: this.getRefreshToken()
    });
  }


   saveTokens(data: any) {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('accessToken', data.accessToken);
      localStorage.setItem('refreshToken', data.refreshToken);
    }
  }

    isAuthenticated(): boolean {
    if (isPlatformBrowser(this.platformId)) {
      return !!localStorage.getItem('accessToken');
    }
    return false;
  }

    forgotPassword(data: any) {
    return this.http.post(`${this.baseUrl}/forgot-password`, data);
  }

   resetPassword(data:any){
    return this.http.post(`${this.baseUrl}/reset-password`, data);
 }

    logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
    }
  }


  //  isAuthenticated(): boolean {
  //   return !!localStorage.getItem('token');
  // }

  // isAuthenticated(): boolean {
  //   if (isPlatformBrowser(this.platformId)) {
  //     return !!localStorage.getItem('token');
  //   }
  //   return false;
  // }

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

  //  logout(): void {
  //   if (isPlatformBrowser(this.platformId)) {
  //     localStorage.removeItem('accessToken');
  //     localStorage.removeItem('refreshToken');
  //   }
  // }
}

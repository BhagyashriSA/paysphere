import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../users/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

   createUser(formData: FormData): Observable<any> {
    return this.http.post(`${this.baseUrl}/create`, formData);
  }

   getUserList(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}`);
  }

   updateUser(id: number, userData: any) {
    return this.http.put(`${this.baseUrl}/${id}`, userData);
  }

  getUserById(id: number) {
   return this.http.get(`${this.baseUrl}/${id}`);
 }

   
  deleteUserById(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }


 getUserByFilter(
  username: string,
  role: string,
  status: string,
  page: number,
  size: number
): Observable<any> {

    let params = new HttpParams()
    .set('page', page.toString())
    .set('size', size.toString());

  if (username && username.trim() !== '') {
    params = params.set('username', username.trim());
  }

    if (role && role.trim() !== '') {
    params = params.set('role', role.trim());
  }

if (status && status.trim() !== '') {
    params = params.set('status', status.trim());
  }

    console.log('REQUEST PARAMS:', params.toString());

  return this.http.get<any>(this.baseUrl, { params });
}

 
}

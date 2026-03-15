import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from './employee';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

 private baseUrl = 'http://localhost:8080/api/employees';

  constructor(private http: HttpClient) {}

  getEmployeeList(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.baseUrl);
  }

  // createEmployee(employee:Employee):Observable<Object>{
  //   return this.http.post(`${this.baseUrl}`, employee);
  // }

   createEmployee(employee:Employee):Observable<Object>{
    return this.http.post(this.baseUrl, employee);
  }

  getEmployeeById(id:number):Observable<Employee>{
     return this.http.get<Employee>(`${this.baseUrl}/${id}`);
  }

  updateEmployee(id:number, employee:Employee):Observable<Employee>{
    return this.http.put<Employee>(`${this.baseUrl}/${id}`, employee);
  }

  deleteEmployeeById(id:number):Observable<Object>{
    return this.http.delete<Employee>(`${this.baseUrl}/${id}`);
  }


  }

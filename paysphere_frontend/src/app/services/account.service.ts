import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Account } from '../accounts/account';


@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private baseUrl = 'http://localhost:8080/api/accounts';
 private branchBaseUrl = 'http://localhost:8080/api/branches';

  constructor(private http: HttpClient) { }

  // ✅ Get All Accounts (Simple)
  getAccountList(): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.baseUrl}`);
  }

  // ✅ Get All Branch (Simple)
  getBranches() {
    return this.http.get<Account[]>(`${this.branchBaseUrl}`);
  }

  // ✅ Get All state (Simple)
  getStates() {
     return this.http.get<string[]>(
    `${this.branchBaseUrl}/state`
  );
  }

   // ✅ Get All cities (Simple)
  getCities(state: string) {
   return this.http.get<string[]>(
    `${this.branchBaseUrl}/cities/${state}`
  );
}

  // ✅ Get All branch (Simple)
  getBranch(state: string) {
   return this.http.get<string[]>(
    `${this.branchBaseUrl}/branch/${state}`
  );
}

getAccById(accountId: number) {
  return this.http.get<any>(
    `${this.baseUrl}/${accountId}`
  );
}



  getAccountNumber(): Observable<string> {
    return this.http.get(`${this.baseUrl}/generate-acc-num`, { responseType: 'text' });
  }

  searchCustomer(name: string) {
    return this.http.get(`http://localhost:8080/api/customers/${name}`);
  }


  // ✅ Get Accounts with Filters + Pagination
  getAccountByFilter(
    accountNumber: string,
    accountType: string,
    status: string,
    branchId: any,
    page: number,
    size: number
  ): Observable<any> {

    let params: any = {
      page,
      size
    };

    if (accountNumber) params.accountNumber = accountNumber;
    if (accountType) params.accountType = accountType;
    if (status) params.status = status;
    if (branchId) params.branchId = branchId;

    return this.http.get(`${this.baseUrl}`, { params });
  }


  //  Get Account by ID
  getAccountById(id: number): Observable<Account> {
    return this.http.get<Account>(`${this.baseUrl}/${id}`);
  }

  //  Create Account
  createAccount(data: any): Observable<Account> {
    return this.http.post<Account>(`${this.baseUrl}`, data);
  }

  //  Update Account
  updateAccount(id: number, account: Account): Observable<Account> {
    return this.http.put<Account>(`${this.baseUrl}/${id}`, account);
  }

  //  Delete Account
  deleteAccount(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
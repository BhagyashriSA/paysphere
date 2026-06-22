import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Beneficiary } from '../models/beneficiary';
import { Observable } from 'rxjs';
import { PageResponse } from '../models/page-response';

@Injectable({
  providedIn: 'root'
})
export class BeneficiaryService {

  private baseUrl = 'http://localhost:8080/api/beneficiaries';

  constructor(private http: HttpClient) { }


  // add beneficiary
  addBeneficiary(data: Beneficiary): Observable<Beneficiary> {
    return this.http.post<Beneficiary>(`${this.baseUrl}`, data);
  }

  // get all beneficiaries
  // getAllBeneficiaries(): Observable<PageResponse<Beneficiary>> {
  //   return this.http.get<PageResponse<Beneficiary>>(this.baseUrl);
  // }

  getAllBeneficiaries(
    page: number,
    size: number,
    beneficiaryName: string,
    accountNumber: string,
    bankName: string
  ): Observable<PageResponse<Beneficiary>> {

    return this.http.get<PageResponse<Beneficiary>>(
      `${this.baseUrl}?page=${page}&size=${size}&beneficiaryName=${beneficiaryName}&accountNumber=${accountNumber}&bankName=${bankName}`
    );
  }

  // delete beneficiary
  deleteBeneficiary(iD: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${iD}`, { responseType: `text` });
  }

  // activate beneficiary
  activateBeneficiary(iD: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${iD}`, { responseType: `text` });
  }


}

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transactions } from './transactions';

@Injectable({
  providedIn: 'root'
})
export class TransactionsService {

  private baseUrl = 'http://localhost:8080/api/transactions';

   constructor(private http: HttpClient) {}

     getTransactionsList(): Observable<Transactions[]> {
    return this.http.get<Transactions[]>(this.baseUrl);
  }


  getTransactionsByFilter(
  transactionId: string,
  transactionType: string,
  channel: string,
  page: number,
  size: number
): Observable<any> {

  let params = new HttpParams()
    .set('page', page.toString())
    .set('size', size.toString());

  if (transactionId && transactionId.trim() !== '') {
    params = params.set('transactionId', transactionId.trim());
  }

  if (transactionType && transactionType.trim() !== '') {
    params = params.set('transactionType', transactionType.trim());
  }

  if (channel && channel.trim() !== '') {
    params = params.set('channel', channel.trim());
  }

  console.log('REQUEST PARAMS:', params.toString());

  return this.http.get<any>(this.baseUrl, { params });
}


}

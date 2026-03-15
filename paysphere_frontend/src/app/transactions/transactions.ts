export class Transactions {

  transactionId: string | undefined;
  transactionType: 'CREDIT' | 'DEBIT' | undefined;
  channel: 'UPI' | 'IMPS' | 'NEFT' | 'CARD' | undefined;
  amount: number | undefined;
  status: 'SUCCESS' | 'PENDING' | 'FAILED' | undefined;
  transactionDateTime: string | undefined; // ISO string from backend

}

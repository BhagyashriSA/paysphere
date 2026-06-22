import { AccountStatus } from "../models/account-status.enum";
import { AccountType } from "../models/account-type.enum";
import { Branch } from "../models/branch";
import { Customer } from "../models/customer";

export class Account {
  accountId!: number;
  userId!: number;
  accountHolderName!: string;
  accountNumber!: string;
  accountType!: AccountType;
  customer!: Customer;
  balance!: number;
  currency!: string;
  status!: AccountStatus;
  branchName!: Branch;
  createdAt!: string;   // ISO date string
  updatedAt!: string;
}
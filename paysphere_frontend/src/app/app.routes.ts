import { RouterModule, Routes } from '@angular/router';
import { ListEmployeeComponent } from './list-employee/list-employee.component';
import { NgModule } from '@angular/core';
import { CreateEmployeeComponent } from './create-employee/create-employee.component';
import { UpdateEmployeeComponent } from './update-employee/update-employee.component';
import { EmployeeDetailsComponent } from './employee-details/employee-details.component';
import { AuthLayoutComponent } from './auth-layout/auth-layout.component';
import { MainLayoutComponent } from './main-layout/main-layout.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer/footer.component';
import { TransactionsListComponent } from './transactions/transactions-list/transactions-list.component';
import { CreateUserComponent } from './users/create-user/create-user.component';
import { UserListComponent } from './users/user-list/user-list.component';
import { UpdateUserComponent } from './users/update-user/update-user.component';
import { ForgotPasswordComponent } from './users/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './users/reset-password/reset-password.component';


export const routes: Routes = [

  // 🔹 Default → Login
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  // 🔹 Auth pages (NO main layout)
  { path: 'login', component: AuthLayoutComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'reset-password', component: ResetPasswordComponent },

  // 🔹 Pages WITH main layout (after login)
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      { path: 'employees', component: ListEmployeeComponent },
      { path: 'create-employee', component: CreateEmployeeComponent },
      { path: 'update-employee/:id', component: UpdateEmployeeComponent },
      { path: 'employee-details/:id', component: EmployeeDetailsComponent },
      { path: 'transactions', component: TransactionsListComponent },
      { path: 'users/create', component: CreateUserComponent },
      { path: 'users/list', component: UserListComponent },
      { path: 'edit-user/:id', component: UpdateUserComponent }
    ]
  },

  // 🔹 Wildcard
  { path: '**', redirectTo: 'login' }
];

//   { path: 'login', component: AuthLayoutComponent },
//     //  { path: 'header', component: HeaderComponent },
//     //   { path: 'footer', component: FooterComponent },

//    // Pages WITH layout
//   { 
//     path: '',
//     component: MainLayoutComponent,
//     children: [
//     { path: 'transactions', component: TransactionsListComponent },
//   { path: 'employees', component: ListEmployeeComponent },
//   { path: 'create-employee', component: CreateEmployeeComponent },
//     { path: '', redirectTo: 'employees', pathMatch: 'full' },
//      { path: 'update-employee/:id', component: UpdateEmployeeComponent },
//      { path: 'employee-details/:id', component: EmployeeDetailsComponent }
//         // { path: 'transactions', component: TransactionsListComponent }
//     ]
//   },

//       { path: '**', redirectTo: 'login' }
// ];


// @NgModule({
//   imports: [RouterModule.forRoot(routes),
//     exports[RouterModule]
// })
// ]
// export class AppRoutingModule { }
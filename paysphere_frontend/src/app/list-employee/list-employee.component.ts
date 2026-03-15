import { Component } from '@angular/core';
import { Employee } from '../employee';
import { NgFor } from '@angular/common';
import { EmployeeService } from '../employee.service';
import { Route } from '@angular/router';
//  import { Router } from 'express';
import { Router } from '@angular/router';


@Component({
  selector: 'app-list-employee',
  standalone: true,
  imports: [NgFor],
  templateUrl: './list-employee.component.html',
  styleUrl: './list-employee.component.css'
})
export class ListEmployeeComponent {

      employees: Employee[] = [];

  constructor(private employeeService: EmployeeService, private router:Router) {}

  ngOnInit(): void {
    this.getEmployee();
  }

  private getEmployee(): void {
    this.employeeService.getEmployeeList().subscribe((data: Employee[]) => {
      this.employees = data;
    });
  }

  updateEmployee(id:number){
    this.router.navigate(['/update-employee', id]);
  }

deleteEmployee(id: number) {
  this.employeeService.deleteEmployeeById(id).subscribe({
    next: (data) => {
      console.log('Employee deleted successfully', data);
      alert("Employee Deleted Successfully");
      this.getEmployee(); // refresh list
    },
    error: err => {
      console.error('ERROR DETAILS:', err);
      alert(err.error?.message || 'Backend error – check console');
        this.getEmployee(); // refresh list
    }
  });
}

viewEmployee(id:number) {
  this.router.navigate(['/employee-details', id]);
}



}



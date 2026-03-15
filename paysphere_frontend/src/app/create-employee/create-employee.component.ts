import { Component } from '@angular/core';
import { Employee } from '../employee';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EmployeeService } from '../employee.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-employee',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './create-employee.component.html',
  styleUrls: ['./create-employee.component.css']
})
export class CreateEmployeeComponent {
  
  employee:Employee=new Employee();
  
  constructor(private employeeService:EmployeeService, private route:Router){}

  onSubmit(){
     alert('Submit works!');
    this.insertEmployee();
    console.log(this.employee)
  }

  // insertEmployee(){
  //   this.employeeService.createEmployee(this.employee).subscribe(data=>{
  //     console.log(data);
  //           this.goToEmployeeList();
  //   })
  // }

insertEmployee() {
  this.employeeService.createEmployee(this.employee).subscribe({
    next: data => {
      console.log('SUCCESS:', data);
      alert("Data inserted Successfully");
      this.goToEmployeeList();
    },
    error: err => {
      console.error('ERROR DETAILS:', err);
      alert(err.error?.message || 'Backend error – check console');
    }
  });
}


  goToEmployeeList(){
    this.route.navigate(['/employees']);
  }

}



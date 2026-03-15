import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-employee-details',
  standalone: true,
  imports: [],
  templateUrl: './employee-details.component.html',
  styleUrl: './employee-details.component.css'
})
export class EmployeeDetailsComponent {

  id:number=0;
  employee:Employee=new Employee();
  constructor(private route:ActivatedRoute, private employeeService:EmployeeService){}

ngOnInit(): void {
  this.id = this.route.snapshot.params['id'];

  this.employeeService.getEmployeeById(this.id).subscribe({
    next: (data) => {
      this.employee = data;
    }
  });
}

}

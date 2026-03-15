import { Component } from '@angular/core';
// import { RouterOutlet } from '@angular/router';
// import { ListEmployeeComponent } from './list-employee/list-employee.component';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { HeaderComponent } from './header/header.component';



@Component({
  selector: 'app-root',
  standalone: true,
  // imports: [RouterOutlet,ListEmployeeComponent],
  imports: [RouterOutlet, RouterLink, RouterLinkActive, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'CPH';

}


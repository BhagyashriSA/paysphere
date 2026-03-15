import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnInit {

  user: User[] = [];

 selectedUser: User | null = null;
showUserModal = false;

  constructor(private userservice: UserService, private router:Router) {}

   ngOnInit(): void {
    //  this.getUsers();
     this.loadUsers();
  }

   private getUsers(): void {
    this.userservice.getUserList().subscribe((data: User[]) => {
      this.user = data;
    });
  }

    // 🔹 FILTERS
  usernameFilter: string = '';
  roleFilter: string = '';
  statusFilter: string = '';

  // 🔹 PAGINATION
  page = 0;
  size = 5;
  totalElements = 0;
  totalPages = 0;
  totalPagesArray: number[] = [];


    loadUsers(): void {
    this.userservice.getUserByFilter(
      this.usernameFilter,
      this.roleFilter,
      this.statusFilter,
      this.page,
      this.size
    ).subscribe(res => {
      console.log('API RESPONSE:', res);

      this.user = res?.content ?? [];
      this.totalElements = res?.totalElements ?? 0;
      this.totalPages = res?.totalPages ?? 0;

      this.totalPagesArray = Array.from(
        { length: this.totalPages },
        (_, i) => i
      );
    });
  }

 search(): void {
  this.page = 0;
  this.loadUsers();
}

clear(): void {
  this.usernameFilter = '';
  this.roleFilter = '';
  this.statusFilter = '';
  this.page = 0;
  this.loadUsers();
}

  previousPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadUsers();
    }
  }

     nextPage(): void {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadUsers();
    }
  }

  goToPage(index: number): void {
    this.page = index;
    this.loadUsers();
  }

  applyFilter() {
  this.page = 0;
  this.loadUsers();
}

viewUser(id: number) {
  console.log("View user:", id);

  const foundUser = this.user.find(u => u.id === id);
    console.log("Selected User:", foundUser);  

  if (foundUser) {
    this.selectedUser = foundUser;
    this.showUserModal = true;
  }


}

closeUserModal(){
  this.showUserModal = false;
}

editUser(id: number) { 
   this.router.navigate(['/edit-user', id]);
}

deleteUser(id: number) {
 console.log("before Delete user:", id);

 if (confirm(`Are you sure you want to delete user with ID ${id}?`)) {

    this.userservice.deleteUserById(id).subscribe({
      next: (res) => {
        console.log("User deleted:", id);
        alert("User deleted successfully");

        // reload user list
        this.loadUsers();
      },
      error: (err) => {
        console.error("Delete failed", err);
        alert("Failed to delete user");
      }
    });

  }
}




}

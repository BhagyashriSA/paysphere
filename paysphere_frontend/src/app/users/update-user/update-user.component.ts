import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-update-user',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './update-user.component.html',
  styleUrl: './update-user.component.css'
})
export class UpdateUserComponent {

  
   id!: number;
   userForm!: FormGroup;     // 🔑 declare only
   hidePassword = true;
   imageFile!: File;
   imagePreview: any = null;
  
  
    constructor(
      private fb: FormBuilder,
      private router: Router,
      private route: ActivatedRoute,
      private userService: UserService
    ) {
      // ✅ initialize AFTER fb exists
      this.userForm = this.fb.group({
        username: ['', Validators.required],
        fullName: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', Validators.required],
        role: ['', Validators.required],
        status: ['', Validators.required]
      });
    }
  onImageUpload(event: any) {
    const file = event.target.files[0];
  
    console.log("SELECTED FILE =", file);
  
    if (!file) return;
  
    this.imageFile = file;
  
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    };
    reader.readAsDataURL(file);
  }

  ngOnInit() {
      this.userForm = this.fb.group({
    username: [''],
    fullName: [''],
    email: [''],
    role: [''],
    status: ['']
  });
  this.id = this.route.snapshot.params['id'];
   console.log("Received ID:", this.id);
   this.getUserById();
  }
    
   submit() {
  
    console.log("SUBMIT CLICKED");
    console.log("IMAGE FILE =", this.imageFile);
  
    const formData = new FormData();
  
    // convert form to JSON blob
    formData.append(
      "user",
      new Blob([JSON.stringify(this.userForm.value)], {
        type: "application/json"
      })
    );
  
    console.log(this.imageFile);
  
    // append image
    if (this.imageFile) {
      alert("image uploaded");
      formData.append("photo", this.imageFile);
    }
  
    
   this.userService.updateUser(this.id, formData).subscribe({
  next: (res: any) => {
    alert("User updated successfully");
    this.getUserList();
  },
  error: (err: HttpErrorResponse) => {
    console.error(err);
    alert(err.error.message || "Something went wrong");
    }
   });
  }
  
    getUserList(){
      this.router.navigate(['/users/list']);
    }

 getUserById() {

  this.userService.getUserById(this.id).subscribe({

    next: (res: any) => {

      console.log("User Data:", res);

      this.userForm.patchValue({
        username: res.username,
        fullName: res.fullName,
        email: res.email,
        password: res.password,
        role: res.role,
        status: res.status
      });

      this.imagePreview = res.imageUrl; // if image exists

    },

    error: (err: any) => {
      console.error(err);
    }

  });

 }
  
  
    cancel() {
      this.userForm.reset();
      this.imagePreview = null;
    }

}

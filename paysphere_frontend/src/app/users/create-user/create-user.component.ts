import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { User } from '../user';

@Component({
  selector: 'app-create-user',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create-user.component.html',
  styleUrl: './create-user.component.css'
})
export class CreateUserComponent {


  userForm!: FormGroup;     // 🔑 declare only
  hidePassword = true;
imageFile!: File;
imagePreview: any = null;


  constructor(
    private fb: FormBuilder,
    private router: Router,
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

  
  this.userService.createUser(formData).subscribe({
  next: () => {
    alert("User Created Successfully");
      this.getUserList();
  },
  error: (err) => {
    alert("Error creating user");
  }
  });
}

  getUserList(){
    this.router.navigate(['/users/list']);
  }


  cancel() {
    this.userForm.reset();
    this.imagePreview = null;
  }

}

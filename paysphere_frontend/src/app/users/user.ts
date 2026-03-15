export class User {
      
  id: number = 0;
  username?: string;
  password?: string;
  fullName?: string;
  email?: string;

  status?: 'ACTIVE' | 'INACTIVE';
  role?: 'ADMIN' | 'USER' | 'HR';

  photoPath?: string;

  createdAt?: string; // ISO string from backend
  updatedAt?: string; // ISO string from backend
}
export class Employee {
    
   // id:number;
   // name:String="";
   // email:string="";
   // department:string="";
   // salary:number=0;

  id!: number;   // <-- non-null assertion
  name: string = '';
  email: string = '';
  department: string = '';
  salary: number = 0;

}

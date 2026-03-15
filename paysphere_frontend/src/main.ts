// import { bootstrapApplication } from '@angular/platform-browser';
// import { appConfig } from './app/app.config';
// import { AppComponent } from './app/app.component';
// import { provideHttpClient } from '@angular/common/http';


// // bootstrapApplication(AppComponent, appConfig)
// //   .catch((err) => console.error(err));

// bootstrapApplication(AppComponent, {
//   providers: [provideHttpClient()]
// });



import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withInterceptors  } from '@angular/common/http';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { FormsModule } from '@angular/forms';
import { importProvidersFrom } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { jwtInterceptor } from './app/auth/jwt.interceptor';


// bootstrapApplication(AppComponent, {
//   providers: [
//   provideHttpClient(),
//     provideRouter(routes),
//       importProvidersFrom(FormsModule),
//       ReactiveFormsModule
//   ]
// }).catch(err => console.error(err));

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(
      withInterceptors([jwtInterceptor])   // ✅ CORRECT PLACE
    ),
    provideRouter(routes),
    importProvidersFrom(
      FormsModule,
      ReactiveFormsModule
    )
  ]
}).catch(err => console.error(err));
import { HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { catchError, switchMap, throwError } from "rxjs";

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {

  const http = inject(HttpClient);

  // ❗ Skip login & refresh API
  if (req.url.includes('/auth/login') || req.url.includes('/auth/refresh')) {
    return next(req);
  }

  const accessToken = localStorage.getItem('accessToken');
    // console.log("Access token " +accessToken );
    // alert("Access Token " + accessToken);

  // Attach access token
  let authReq = req;
  if (accessToken) {
    authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${accessToken}`
      }
    });
  }

  return next(authReq).pipe(

    catchError((error) => {

      // 🔥 If token expired
      if (error.status === 401 || error.status === 403) {

        const refreshToken = localStorage.getItem('refreshToken');
            // console.log("refresh token " +refreshToken );
            // alert("refresh Token " + refreshToken);

      

        if (!refreshToken) {
          return throwError(() => error);
        }

        // 🔄 Call refresh API
        return http.post<any>('http://localhost:8080/auth/refresh', {
          refreshToken: refreshToken
        }).pipe(

          switchMap((res) => {

            // ✅ Save new tokens
            localStorage.setItem('accessToken', res.accessToken);
            localStorage.setItem('refreshToken', res.refreshToken);

            // 🔁 Retry original request with new token
            const newReq = req.clone({
              setHeaders: {
                Authorization: `Bearer ${res.accessToken}`
              }
            });

            return next(newReq);
          }),

          catchError(err => {
            // ❌ Refresh token also failed → logout
            localStorage.clear();
            return throwError(() => err);
          })
        );
      }

      return throwError(() => error);
    })
  );
};


// export const jwtInterceptor: HttpInterceptorFn = (req, next) => {

//   // ❗ Skip login API
//   if (req.url.includes('/auth/login')) {
//     return next(req);
//   }

//   const token = localStorage.getItem('token');

//   if (token) {
//     req = req.clone({
//       setHeaders: {
//         Authorization: `Bearer ${token}`
//       }
//     });
//   }

//   return next(req);
// };
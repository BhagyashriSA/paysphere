import { HttpInterceptorFn } from "@angular/common/http";


export const jwtInterceptor: HttpInterceptorFn = (req, next) => {

  // ❗ Skip login API
  if (req.url.includes('/auth/login')) {
    return next(req);
  }

  const token = localStorage.getItem('token');

  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(req);
};
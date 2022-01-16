import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { AuthStore } from '../_services';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthStore
        , private auth: AuthStore) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add auth header with jwt if user is logged in and request is to api url
        const user = this.authenticationService.userValue;

        const isLoggedIn = !!user && this.auth.accessToken;
        const isApiUrl = request.url.startsWith(environment.apiUrl);

        if (isLoggedIn && isApiUrl)
         {
            console.log(this.auth.idToken)
            request = request.clone({
                setHeaders: {
                    // Authorization: `Bearer ${this.auth.idToken}`
                    Authorization: `Bearer ${this.auth.accessToken}`
                }
            });
        }

        return next.handle(request);
    }
}

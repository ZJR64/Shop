import { Component } from '@angular/core';
import { Router, Event, NavigationStart, NavigationEnd, NavigationError} from '@angular/router';
import { LoginService } from './services/login.service';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Coffee/Tea E-store';
  currentUser = localStorage.getItem('currentUser');
  currentRoute: string;

  constructor(private router: Router, private loginService: LoginService) {
    this.currentRoute = "";
    window.addEventListener('storage', function(e) {  
      location.reload();
      
    });
    this.router.events.subscribe((event: Event) => {

        if (event instanceof NavigationEnd) {
            this.currentRoute = event.url;
              console.log(event);
        }


    });
  }

  public get isLoggedIn$(): Observable<boolean> {
    return this.loginService.isLoggedIn$;
  }

  goHome(): void {
    this.router.navigateByUrl('/home');
  }

  goLogout(): void{
    this.router.navigateByUrl('/logout');
  }


}

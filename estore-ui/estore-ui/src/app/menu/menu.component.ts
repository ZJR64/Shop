import { Component } from '@angular/core';
import { Router, Event, NavigationEnd } from '@angular/router';
import { LoginService } from '../services/login.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent {
  currentUser = localStorage.getItem('currentUser');

  constructor(private router: Router, private loginService: LoginService) {}

  goHome(): void {
    this.router.navigateByUrl('/home');
  }

  goLogout(): void{
    this.router.navigateByUrl('/logout');
  }
}

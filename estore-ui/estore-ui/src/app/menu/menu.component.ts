import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent {
  currentUser = localStorage.getItem('currentUser');

  constructor(private router: Router) {}

  goHome(): void {
    this.router.navigateByUrl('/home');
  }

  goLogout(): void{
    this.router.navigateByUrl('/logout');
  }
}

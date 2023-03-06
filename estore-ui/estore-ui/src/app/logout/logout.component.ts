import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit{
  message!: String;

  constructor(
    private router: Router,
    private loginService: LoginService,
  ) {}

  ngOnInit(): void {
    this.message = "Are you sure you want to log out?";
  }

  logout(): void {
    localStorage.removeItem('currentUser');
    this.loginService.setLoggedIn(false);
    this.router.navigateByUrl('/login')

  }

  back(): void {
    this.router.navigateByUrl('/home')
  }

}

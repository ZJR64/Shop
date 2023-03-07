import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

import { User } from '../user';
import { UserService } from '../services/user.service';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  message!: String;
  user!: User;

  constructor(
    private router: Router,
    private userService: UserService,
    private loginService: LoginService,
  ) {}

  ngOnInit(): void {
    this.message = "";
    if (localStorage.getItem('currentUser') != null) {

    }
  }

  verify(email: string, password: string): void {
    email = email.trim();
    password = password.trim();
    if (!email) { return; }
    if (!password) { return; }

    this.userService.getUserFromEmail(email).subscribe(
    (user) => {
      if (user) {
        if (user.password.trim() == password) {
          localStorage.setItem('currentUser', user.email);
          this.loginService.setLoggedIn(true);
          this.router.navigateByUrl('/home')
          return;
        }
        this.message = "Wrong Password";
        return;
      }
      this.message = "Unkown Email";
    });
  }

  register(): void {
    this.router.navigateByUrl('/register')
  }

}

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../object-interfaces/user';
import { UserService } from '../services/user.service';

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
  ) {}

  ngOnInit(): void {
    this.message = "";
  }

  verify(email: string, password: string): void {
    email = email.trim();
    password = password.trim();
    if (!email) {
      this.message = "Email Cannot Be Empty";
      return;
    }
    if (!password) {
      this.message = "Password Cannot Be Empty";
      return;
    }

    this.userService.getUserFromEmail(email).subscribe(
    (user) => {
      if (user) {
        if (user.password.trim() == password) {
          localStorage.setItem('currentUser', user.email);
          if (user.admin) {
            this.router.navigateByUrl('/admin')
          }
          else {
            this.router.navigateByUrl('/home')
          }
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

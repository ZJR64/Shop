import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { User } from '../user';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  users: User[] = [];

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getUsers();
    if (localStorage.getItem('currentUser') != null) {

    }
  }

  getUsers(): void {
    this.userService.getUsers()
      .subscribe(users => this.users = users);
  }

  verify(email: string, password: string): void {
    email = email.trim();
    password = password.trim();
    if (!email) { return; }
    if (!password) { return; }

    this.users.forEach(element => {
      if (element.email == email) {
        if (element.password == password) {
          localStorage.setItem('currentUser', element.email);
          return;
        }
      }
    });
  }

}

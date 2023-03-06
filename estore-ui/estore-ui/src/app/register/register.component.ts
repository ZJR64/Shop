import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

import { User } from '../user';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{
  users: User[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
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

  createNewUser(email: string, name: string, password: string): void {
    email = email.trim();
    name = name.trim();
    password = password.trim();
    if (!email) { return; }
    if (!name) {return; }
    if (!password) { return; }

    this.users.forEach(element => {
      if (element.email == email) {
        return;
      }
    });
    
    this.userService.addUser({email, name, password} as User);
    this.back();
  }

  back(): void {
    this.router.navigateByUrl('/login')
  }

}

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit{
  message!: String;

  constructor(
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.message = "Are you sure you want to log out?";
  }

  logout(): void {
    localStorage.removeItem('currentUser');
    this.router.navigateByUrl('/login')
  }

}

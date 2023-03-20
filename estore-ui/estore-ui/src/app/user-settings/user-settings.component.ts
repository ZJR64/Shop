import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

import { UserService } from '../services/user.service';
import { User } from '../object-interfaces/user';

@Component({
  selector: 'app-users',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css']
})
export class UserSettingsComponent implements OnInit{
  user!: User;

  constructor(
    private router: Router,
    private userService: UserService,
    private location: Location
    ) {

    this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe(
      (user) => {
        this.user = user;
      });
  }

  ngOnInit() {
    console.log("opened details")
  }

  newPay(): void {
    this.user.payInfo.push('')
  }

  deletePay(pay: string): void {
    const index = this.user.payInfo.indexOf(pay, 0);
    this.user.payInfo.splice(index, 1);
  }

  trackByFn(index: number, item: any): any {
    return index;
  }

  saveUser(): void {
    // Check that all payment info strings are non-empty
    if (this.user.payInfo.some((pay) => pay.trim() === '')) {
      alert('Please enter valid payment information');
      return;
    }

    // Check that each payment info string is in the correct format
    // You can use a regular expression to validate the format
    const regex = /[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}/; // Example regex for credit card numbers
    if (this.user.payInfo.some((pay) => !regex.test(pay))) {
      alert('Please enter payment information in the correct format: xxxx-xxxx-xxxx-xxxx');
      return;
    }

    this.userService.updateUser(this.user)
    .subscribe(() => {
      console.log('User saved');
      this.location.back();
    });
  }

  deleteUser(): void {
    this.router.navigateByUrl(`delete`);
  }

}

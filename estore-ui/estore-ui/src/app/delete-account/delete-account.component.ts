import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { User } from '../object-interfaces/user';

@Component({
  selector: 'app-delete-account',
  templateUrl: './delete-account.component.html',
  styleUrls: ['./delete-account.component.css']
})
export class DeleteAccountComponent {
  message!: String;
  user!: User;

  constructor(
    private router: Router,
    private userService: UserService,
  ) {
    this.userService.getUserFromEmail(localStorage.getItem('currentUser')!).subscribe(
      (user) => {
        this.user = user;
      });
  }

  ngOnInit(): void {
    this.message = "Are you sure you want to delete your account?";
  }

  delete(): void {
    this.userService.deleteUser(this.user.id)
    .subscribe(() => {
        console.log(`Deleted user with id=${this.user.id}`);
        localStorage.removeItem('currentUser');
        this.router.navigateByUrl(`login`);
    });
  }
}

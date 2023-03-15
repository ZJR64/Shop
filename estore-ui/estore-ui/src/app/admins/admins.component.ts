import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { User } from '../user';

@Component({
  selector: 'app-admins',
  templateUrl: './admins.component.html',
  styleUrls: ['./admins.component.css']
})
export class AdminsComponent {
  users!: User[];
  adminView!: boolean;
  searchTerm?: string;
  sortOrder!: string;
  sortReverse!: boolean;

  constructor(
    private userService: UserService,
    private router: Router,
    ) { }

  ngOnInit(): void {
    this.adminView = true;
    this.sortReverse = false;
    this.sortOrder = '';
    this.getUsers();
  }

  getUsers(): void {
    if (this.adminView) {
      this.getAdmins();
    }
    else {
      this.getNonAdmins();
    }
  }

  getAdmins(): void {
    this.userService.getUsers()
      .subscribe((users: User[]) =>
      this.users = users.filter(user => user.admin)
      );
  }

  getNonAdmins(): void {
    this.userService.getUsers()
      .subscribe((users: User[]) =>
      this.users = users.filter(user => !user.admin)
      );
  }

  onSearch() {
    if (this.searchTerm) {
      this.userService.searchUsers(this.searchTerm).subscribe((data: any[]) => {
        this.users = data;
      });
    } else {
      this.getUsers();
    }
  }

  switch():void {
    this.adminView = !this.adminView;
    this.getUsers();
  }

  changeAdmin(user: User): void {
    if (localStorage.getItem('currentUser') == user.email) {
      return;
    }

    user.admin = !user.admin;
    this.userService.updateUser(user)
    .subscribe(() => {
      console.log('Ingredient saved');
      this.getUsers();
    });
  }

  sort(column: string) {
    if (this.sortOrder === column) {
      this.sortReverse = !this.sortReverse;
    } else {
      this.sortOrder = column;
      this.sortReverse = false;
    }
    this.users.sort((a, b) => {
      if (this.sortReverse) {
        [a, b] = [b, a];
      }
      if (a[column] < b[column]) {
        return -1;
      }
      if (a[column] > b[column]) {
        return 1;
      }
      return 0;
    });
  }
}

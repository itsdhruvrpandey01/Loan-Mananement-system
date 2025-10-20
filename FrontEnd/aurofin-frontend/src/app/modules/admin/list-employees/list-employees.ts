import { Component } from '@angular/core';
import { EmployeeResponseDto } from '../../../entity/EmployeeResponseDto';
import { AdminService } from '../../../services/admin-service';

@Component({
  selector: 'app-list-employees',
  standalone: false,
  templateUrl: './list-employees.html',
  styleUrl: './list-employees.css'
})
export class ListEmployees {
employees: EmployeeResponseDto[] = [];
  page = 0;
  size = 2;

  totalPages = 0;
  first = true;
  last = false;

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.adminService.getAllEmployees(this.page, this.size).subscribe({
      next: (res) => {
        this.employees = res.contents;
        this.totalPages = res.totalPage;
        this.first = res.isFirst;
        this.last = res.isLast;
      },
      error: (err) => {
        console.error('Error fetching employees:', err);
      }
    });
  }

  nextPage(): void {
    if (!this.last) {
      this.page++;
      this.loadEmployees();
    }
  }

  prevPage(): void {
    if (!this.first && this.page > 0) {
      this.page--;
      this.loadEmployees();
    }
  }
}
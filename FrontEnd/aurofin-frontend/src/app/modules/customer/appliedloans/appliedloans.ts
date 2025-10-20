import { Component, OnInit } from '@angular/core';
import { customer } from '../../../services/customer';
import { ApplicationResponse } from '../../../entity/ApplicationResponse';
import { PageResponseDto } from '../../../entity/PageResponseDto';

@Component({
  selector: 'app-appliedloans',
  standalone: false,
  templateUrl: './appliedloans.html',
  styleUrl: './appliedloans.css'
})
export class Appliedloans implements OnInit {

  appliedLoans: ApplicationResponse[] = [];
  customerId = localStorage.getItem('customerId') || '';
  
  // Pagination properties
  currentPage: number = 0;
  pageSize: number = 10;
  totalElements: number = 0;
  totalPages: number = 0;
  isFirst: boolean = true;
  isLast: boolean = false;

  // Optional: Filter by status
  selectedStatus?: string;

  constructor(private customer: customer) { }

  ngOnInit(): void {
    this.fetchAppliedLoans();
  }

  fetchAppliedLoans(page: number = 0, size: number = 10, status?: string): void {
    this.customer.getAppliedLoans(this.customerId, page, size, status)
      .subscribe({
        next: (response: PageResponseDto<ApplicationResponse>) => {
          this.appliedLoans = response.contents;
          this.currentPage = page;
          this.pageSize = response.size;
          this.totalElements = response.totalElements;
          this.totalPages = response.totalPage;
          this.isFirst = response.isFirst;
          this.isLast = response.isLast;
        },
        error: (err) => {
          console.error('Error fetching applied loans:', err);
        }
      });
  }

  // Pagination methods
  goToNextPage(): void {
    if (!this.isLast) {
      this.fetchAppliedLoans(this.currentPage + 1, this.pageSize, this.selectedStatus);
    }
  }

  goToPreviousPage(): void {
    if (!this.isFirst) {
      this.fetchAppliedLoans(this.currentPage - 1, this.pageSize, this.selectedStatus);
    }
  }

  goToPage(page: number): void {
    this.fetchAppliedLoans(page, this.pageSize, this.selectedStatus);
  }

  // Filter by status
  filterByStatus(status: string): void {
    this.selectedStatus = status;
    this.fetchAppliedLoans(0, this.pageSize, status);
  }

  clearFilter(): void {
    this.selectedStatus = undefined;
    this.fetchAppliedLoans(0, this.pageSize);
  }
}
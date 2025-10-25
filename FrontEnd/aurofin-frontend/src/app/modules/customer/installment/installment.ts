import { Component } from '@angular/core';
import { InstallmentDto } from '../../../entity/InstallmentDto';
import { PaymentService } from '../../../services/payment-service';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { customer } from '../../../services/customer';
import { PageResponseDto } from '../../../entity/PageResponseDto';

@Component({
  selector: 'app-installment',
  standalone: false,
  templateUrl: './installment.html',
  styleUrl: './installment.css'
})
export class Installment {
  installments: InstallmentDto[] = [];
  loading = false;
  message = '';
  customerId = '';
  page = 0;
  size = 2;

  constructor(
    private paymentService: PaymentService,
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router,
    private customerService: customer
  ) { }

  ngOnInit(): void {
    this.customerId = localStorage.getItem('customerId') || '';
    this.loadInstallments();
  }

  loadInstallments() {
    // Fetch dummy installments (replace with actual backend)
    console.log(this.customerId);
    this.customerService.getUnpaidInstallments(this.customerId, this.page, this.size).subscribe({
      next: (res: PageResponseDto<InstallmentDto>) => {
        console.log(res);
        this.installments = res.contents;
      },
      error: (err) => console.error(err),
    });
  }

  async payInstallment(installment: InstallmentDto) {
    this.loading = true;
    this.paymentService.createCheckoutSession(installment.installmentId).subscribe({
      next: (session) => {
        this.loading = false;
        this.paymentService.redirectToStripe(session.url); // use session.url
      },
      error: (err) => {
        this.loading = false;
        console.error(err);
        this.message = 'Error creating Stripe session.';
      }
    });
  }
}

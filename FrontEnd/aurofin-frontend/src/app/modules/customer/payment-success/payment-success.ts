import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from '../../../services/payment-service';

@Component({
  selector: 'app-payment-success',
  standalone: false,
  templateUrl: './payment-success.html',
  styleUrl: './payment-success.css'
})
export class PaymentSuccess {
message = '';

  constructor(
    private route: ActivatedRoute,
    private paymentService: PaymentService
  ) {}

  ngOnInit(): void {
    const installmentId = this.route.snapshot.paramMap.get('installmentId');
    const sessionId = this.route.snapshot.queryParamMap.get('session_id');

    if (installmentId && sessionId) {
      this.paymentService.handleSuccess(installmentId, sessionId).subscribe({
        next: () => (this.message = 'Payment successful! Installment updated.'),
        error: () => (this.message = 'Error updating payment.'),
      });
    } else {
      this.message = 'Missing session or installment information.';
    }
  }
}

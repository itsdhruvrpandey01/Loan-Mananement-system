import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { loadStripe, Stripe } from '@stripe/stripe-js';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PaymentService {
  private baseUrl = 'http://localhost:8080/api/payments';

  constructor(private http: HttpClient) {}

  // Create Stripe Checkout session
  createCheckoutSession(installmentId: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/create-checkout-session/${installmentId}`, {});
  }

  redirectToStripe(url: string) {
    if (url) {
      window.location.href = url;  // simple browser redirect
    } else {
      console.error('Stripe URL is missing');
    }
  }

  handleSuccess(installmentId: string, sessionId: string): Observable<any> {
  return this.http.post(`${this.baseUrl}/success/${installmentId}?session_id=${sessionId}`, {});
}
}

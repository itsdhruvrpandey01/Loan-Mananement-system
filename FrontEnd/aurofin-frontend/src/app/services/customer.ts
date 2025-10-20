import { Injectable } from '@angular/core';
import { ApplicationResponse } from '../entity/ApplicationResponse';
import { PageResponseDto } from '../entity/PageResponseDto';
import { ApplicationDto } from '../entity/ApplicationDto';
import { Observable } from 'rxjs';
import { DocumentResponseDto } from '../entity/DocumentResponseDto';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { DocumentUploadDto } from '../entity/DocumentUploadDto';
import { NotificationDto } from '../entity/NotificationDto';
import { FeedbackResponseDto } from '../entity/FeedbackResponseDto';
import { FeedbackRequestDto } from '../entity/FeedbackRequestDto';
import { InstallmentDto } from '../entity/InstallmentDto';

@Injectable({
  providedIn: 'root'
})
export class customer {
  private baseUrl = 'http://localhost:8080/loan-app/customer';

  constructor(private http: HttpClient) {}

  // Helper method to get auth headers
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken'); // Adjust based on your auth implementation
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  // =============== INSTALLMENT APIs ===============

  getUnpaidInstallments(customerId: string, page: number, size: number): Observable<PageResponseDto<InstallmentDto>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<PageResponseDto<InstallmentDto>>(
      `${this.baseUrl}/${customerId}/loans/application/installments`,
      { headers: this.getHeaders(), params }
    );
  }

  // =============== APPLICATION APIs ===============

  getAppliedLoans(customerId: string, page: number, size: number, status?: string): Observable<PageResponseDto<ApplicationResponse>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    if (status) {
      params = params.set('status', status);
    }

    return this.http.get<PageResponseDto<ApplicationResponse>>(
      `${this.baseUrl}/${customerId}/loans/application`,
      { headers: this.getHeaders(), params }
    );
  }

  applyLoan(customerId: string, applicationDto: ApplicationDto): Observable<ApplicationResponse> {
    return this.http.post<ApplicationResponse>(
      `${this.baseUrl}/${customerId}/loans/application`,
      applicationDto,
      { headers: this.getHeaders() }
    );
  }

  uploadRequiredDocuments(applicationId: string, files: File[], docTypes: string[]): Observable<DocumentResponseDto> {
    const formData = new FormData();
    
    files.forEach(file => {
      formData.append('files', file);
    });
    
    docTypes.forEach(type => {
      formData.append('docTypes', type);
    });

    const token = localStorage.getItem('authToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.put<DocumentResponseDto>(
      `${this.baseUrl}/loans/application/${applicationId}`,
      formData,
      { headers }
    );
  }

  // =============== DOCUMENT APIs ===============

  uploadDocument(documentUploadDto: DocumentUploadDto, customerId: string): Observable<DocumentResponseDto> {
    const params = new HttpParams().set('customerId', customerId);
    
    return this.http.post<DocumentResponseDto>(
      `${this.baseUrl}/documents`,
      documentUploadDto,
      { headers: this.getHeaders(), params }
    );
  }

  getDocuments(customerId: string): Observable<DocumentResponseDto[]> {
    const params = new HttpParams().set('customerId', customerId);
    
    return this.http.get<DocumentResponseDto[]>(
      `${this.baseUrl}/documents`,
      { headers: this.getHeaders(), params }
    );
  }

  getDocumentById(docId: string): Observable<DocumentResponseDto> {
    return this.http.get<DocumentResponseDto>(
      `${this.baseUrl}/documents/${docId}`,
      { headers: this.getHeaders() }
    );
  }

  deleteDocument(docId: string): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(
      `${this.baseUrl}/documents/${docId}`,
      { headers: this.getHeaders() }
    );
  }

  // =============== NOTIFICATION APIs ===============

  getAllNotifications(userId: string): Observable<NotificationDto[]> {
    const params = new HttpParams().set('userId', userId);
    
    return this.http.get<NotificationDto[]>(
      `${this.baseUrl}/notifications`,
      { headers: this.getHeaders(), params }
    );
  }

  getUnreadNotifications(userId: string): Observable<NotificationDto[]> {
    const params = new HttpParams().set('userId', userId);
    
    return this.http.get<NotificationDto[]>(
      `${this.baseUrl}/notifications/unread`,
      { headers: this.getHeaders(), params }
    );
  }

  getUnreadCount(userId: string): Observable<{ unreadCount: number }> {
    const params = new HttpParams().set('userId', userId);
    
    return this.http.get<{ unreadCount: number }>(
      `${this.baseUrl}/notifications/unread/count`,
      { headers: this.getHeaders(), params }
    );
  }

  markNotificationAsRead(notificationId: string): Observable<NotificationDto> {
    return this.http.put<NotificationDto>(
      `${this.baseUrl}/notifications/${notificationId}/read`,
      {},
      { headers: this.getHeaders() }
    );
  }

  markAllNotificationsAsRead(userId: string): Observable<{ message: string }> {
    const params = new HttpParams().set('userId', userId);
    
    return this.http.put<{ message: string }>(
      `${this.baseUrl}/notifications/read-all`,
      {},
      { headers: this.getHeaders(), params }
    );
  }

  // =============== FEEDBACK APIs ===============

  submitFeedback(feedbackRequestDto: FeedbackRequestDto, userId: string): Observable<FeedbackResponseDto> {
    const params = new HttpParams().set('userId', userId);
    
    return this.http.post<FeedbackResponseDto>(
      `${this.baseUrl}/feedback`,
      feedbackRequestDto,
      { headers: this.getHeaders(), params }
    );
  }

  getMyFeedbacks(userId: string): Observable<FeedbackResponseDto[]> {
    const params = new HttpParams().set('userId', userId);
    
    return this.http.get<FeedbackResponseDto[]>(
      `${this.baseUrl}/feedback`,
      { headers: this.getHeaders(), params }
    );
  }

  getFeedbackById(feedbackId: string): Observable<FeedbackResponseDto> {
    return this.http.get<FeedbackResponseDto>(
      `${this.baseUrl}/feedback/${feedbackId}`,
      { headers: this.getHeaders() }
    );
  }
}
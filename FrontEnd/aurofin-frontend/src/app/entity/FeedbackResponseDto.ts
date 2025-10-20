export interface FeedbackResponseDto {
  feedbackId: string; // UUID
  subject: string;
  message: string;
  status: string;
  response?: string; // Admin/Manager response
  createdAt: string;
  resolvedAt?: string;
}

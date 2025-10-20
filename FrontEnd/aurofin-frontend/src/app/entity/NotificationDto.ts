export interface NotificationDto {
  notificationId: string; // UUID
  message: string;
  type: string;
  isRead: boolean;
  createdAt: string; // ISO date string
}

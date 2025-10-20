export interface DocumentResponseDto {
  docId: string; // UUID
  docName: string;
  documentType: string;
  docURL: string;
  docUploadedAt: string; // ISO date string
  customerId: string; // UUID
  applicationId?: string; // UUID, can be null
}

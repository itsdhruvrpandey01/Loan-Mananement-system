import { InstallmentStatus } from './installment-status.enum.js';

export interface InstallmentDto {
  installmentId: string; // UUID
  instAmt: number;
  instStartDate: string; // ISO date string
  instEndDate: string; // ISO date string
  installmentStatus: InstallmentStatus;
  paidDate?: string; // ISO date string
  fineAmt?: number;
}

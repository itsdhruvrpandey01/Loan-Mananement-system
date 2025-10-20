import { LoanResponseDto } from './LoanResponseDto';
import { InstallmentDto } from './InstallmentDto';

export interface ApplicationResponse {
  status: string;
  rejectionReason?: string;
  requestedAmount: number;
  requestedTenure: number;
  totalLoanAmount: number;
  createdAt: string; // ISO date string
  loanResponseDto: LoanResponseDto;
  installmentDtos: InstallmentDto[];
  managerName: string;
  applicantAge: number;
}

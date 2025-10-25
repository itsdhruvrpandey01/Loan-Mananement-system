import { DocumentResponseDto } from './DocumentResponseDto';
import { LoanResponseDto } from './LoanResponseDto';

export interface AppliedLoanApplications {
  applicationId: string; // UUID
  status: string;
  requestedAmount: number;
  requestedTenure: number;
  totalLoanAmount: number;
  createdAt: string;
  updatedAt: string;
  documentResponseDto: DocumentResponseDto[];
  loanResponse: LoanResponseDto;
  customerEmail:string;
	customerName:string;
	customerMobileNumber:string;
}

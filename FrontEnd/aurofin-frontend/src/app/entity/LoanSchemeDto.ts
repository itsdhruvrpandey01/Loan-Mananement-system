import { LoanCollateralRequestDto } from './LoanCollateralRequestDto';

export interface LoanSchemeDto {
  loanName: string;
  loanTypeId: number;
  minLoanAmount: number;
  maxLoanAmount: number;
  interestRate: number;
  maxTenure: number;
  minAge: number;
  maxAge: number;
  minIncome: number;
  collateralRequired: boolean;
  collateralRequirements?: LoanCollateralRequestDto[];
  otherConditions?: string;
  installmentDurationType: string;
  processingFeeFlat: number;
  earlyClosureCharge: number;
  defaultPenaltyRate: number;
}

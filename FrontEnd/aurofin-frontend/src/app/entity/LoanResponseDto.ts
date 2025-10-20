import { CollateralRequirementDto } from './CollateralRequirementDto';

export interface LoanResponseDto {
  loanSchemeId: number;
  loanName: string;
  loanType: string;
  minLoanAmount: number;
  maxLoanAmount: number;
  interestRate: number;
  maxTenure: number;
  minAge: number;
  maxAge: number;
  minIncome: number;
  collateralRequired: boolean;
  otherConditions?: string;
  installmentDurationType?: string;
  collateralRequirements?: CollateralRequirementDto[];
  active: boolean;
  createdAt: string;
  updatedAt: string;
  processingFeeFlat: number;
  earlyClosureCharge: number;
  defaultPenaltyRate: number;
}

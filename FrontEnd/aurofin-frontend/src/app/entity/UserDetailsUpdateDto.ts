export interface UserDetailsUpdateDto {
  firstName: string;
  middleName?: string;
  lastName: string;
  mobile: string;
  gender: string;
  createdAt: string; // ISO date string
}

import { AddressDto } from './AddressDto';

export interface RegisterRequestDto {
  firstName: string;
  middleName?: string;
  lastName: string;
  mobile: string;
  email: string;
  password: string;
  gender: string;
  address: AddressDto;
}

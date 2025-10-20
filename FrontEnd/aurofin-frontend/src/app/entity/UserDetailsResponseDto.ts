import { ProfilePictureResponseDto } from './ProfilePictureResponseDto';

export interface UserDetailsResponseDto {
  userId: string; // UUID
  firstName: string;
  middleName?: string;
  lastName: string;
  mobile: string;
  email: string;
  isActive: boolean;
  gender: string;
  createdAt: string; // ISO date string
  profilePictureResponseDto?: ProfilePictureResponseDto | null;
}

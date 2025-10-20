export interface PageResponseDto<T> {
  contents: T[];
  totalElements: number;
  totalPage: number;
  size: number;
  isFirst: boolean;
  isLast: boolean;
}

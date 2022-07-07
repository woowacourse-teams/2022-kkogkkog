import { KkogKkogType } from '@/types/domain';

export interface KkogKkogRequest {
  senderId: number;
  receiverId: number;
  backgroundColor: string;
  modifier: string;
  message: string;
  couponType: KkogKkogType;
}

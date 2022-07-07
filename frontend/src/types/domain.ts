export type KkogKkogType = '커피' | '식사' | '술';

export interface KkogKkog {
  id: number;
  senderName: string;
  receiverName: string;
  backgroundColor: string;
  modifier: string;
  couponType: KkogKkogType;
  message: string;
}

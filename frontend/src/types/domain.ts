export type KkogKkogType = '커피' | '식사' | '술';

export interface KkogKkog {
  id: number;
  senderName: string;
  receiverName: string;
  backgroundColor: string;
  modifier: string;
  couponType: KkogKkogType;
  message: string;
  thumbnail: string;
}

export const KKOGKKOG_THUMBNAIL = {
  커피: '/assets/images/coffee.png',
  술: '/assets/images/beer.png',
  식사: '/assets/images/meal.png',
} as const;

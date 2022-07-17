export type KkogKkogType = 'COFFEE' | '' | 'DRINK';

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

export interface User {
  id: number;
  email: string;
  nickname: string;
}

export const KKOGKKOG_THUMBNAIL = {
  커피: '/assets/images/coffee.png',
  술: '/assets/images/beer.png',
  식사: '/assets/images/meal.png',
} as const;

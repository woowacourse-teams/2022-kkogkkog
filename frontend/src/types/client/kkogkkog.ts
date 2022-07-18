import theme from '@/styles/theme';
import { User } from '@/types/client/user';

export const kkogkkogType = [
  { imageUrl: '/assets/images/coffee.png', koreanType: '커피', engType: 'COFFEE' } as const,
  { imageUrl: '/assets/images/beer.png', koreanType: '술', engType: 'DRINK' } as const,
] as const;

export const kkogkkogColors = [
  theme.colors.white_100,
  theme.colors.primary_100,
  theme.colors.primary_200,
  theme.colors.primary_300,
  theme.colors.primary_400,
  theme.colors.primary_500,
] as const;

export const kkogkkogModifiers = ['재미있게', '활기차게', '한턱쏘는'] as const;

export const THUMBNAIL = {
  COFFEE: '/assets/images/coffee.png',
  DRINK: '/assets/images/beer.png',
} as const;

export interface KkogKkog {
  id: number;
  sender: User;
  receiver: User;
  backgroundColor: KKOGKKOG_COLORS;
  modifier: KKOGKKOG_MODIFIERS;
  couponType: KKOGKKOG_ENG_TYPE;
  message: string;
  // thumbnail: string;
}

export type KkogKkogType = 'COFFEE' | 'DRINK';

export type KKOGKKOG_ENG_TYPE = typeof kkogkkogType[number]['engType'];
export type KKOGKKOG_KOREAN_TYPE = typeof kkogkkogType[number]['koreanType'];

export type KKOGKKOG_COLORS = typeof kkogkkogColors[number];

export type KKOGKKOG_MODIFIERS = typeof kkogkkogModifiers[number];

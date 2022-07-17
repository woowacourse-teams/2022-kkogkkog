import theme from '@/styles/theme';

export type KkogKkogType = 'COFFEE' | 'DRINK';

export interface KkogKkog {
  id: number;
  senderName: string;
  receiverName: string;
  backgroundColor: KKOGKKOG_COLORS;
  modifier: KKOGKKOG_MODIFIERS;
  couponType: KKOGKKOG_ENG_TYPE;
  message: string;
  // thumbnail: string;
}

export interface User {
  id: number;
  email: string;
  nickname: string;
}

export const kkogkkog_type = [
  { imageUrl: '/assets/images/coffee.png', koreanType: '커피', engType: 'COFFEE' } as const,
  { imageUrl: '/assets/images/beer.png', koreanType: '술', engType: 'DRINK' } as const,
] as const;

export type KKOGKKOG_ENG_TYPE = typeof kkogkkog_type[number]['engType'];
export type KKOGKKOG_KOREAN_TYPE = typeof kkogkkog_type[number]['koreanType'];

export const kkogkkog_colors = [
  theme.colors.white_100,
  theme.colors.primary_100,
  theme.colors.primary_200,
  theme.colors.primary_300,
  theme.colors.primary_400,
  theme.colors.primary_500,
] as const;

export type KKOGKKOG_COLORS = typeof kkogkkog_colors[number];

export const kkogkkog_modifiers = ['재미있게', '활기차게', '한턱쏘는'] as const;

export type KKOGKKOG_MODIFIERS = typeof kkogkkog_modifiers[number];

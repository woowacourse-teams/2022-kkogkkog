import theme from '@/styles/theme';
import { User } from '@/types/client/user';

export const kkogkkogType = [
  { imageUrl: '/assets/images/coffee.png', koreanType: '커피', engType: 'COFFEE' } as const,
  { imageUrl: '/assets/images/beer.png', koreanType: '술', engType: 'DRINK' } as const,
  { imageUrl: '/assets/images/meal.png', koreanType: '식사', engType: 'MEAL' } as const,
] as const;

export const couponEvent = ['REQUEST', 'CANCEL', 'DECLINE', 'ACCEPT', 'FINISH'] as const;
export const couponStatus = ['REQUESTED', 'READY', 'ACCEPTED', 'FINISHED'] as const;

export const kkogkkogColors = [
  theme.colors.white_100,
  theme.colors.primary_100,
  theme.colors.primary_200,
  theme.colors.primary_300,
  theme.colors.primary_400,
  theme.colors.primary_500,
] as const;

export const kkogkkogModifiers = ['재미있게', '활기차게', '한턱쏘는'] as const;

export const THUMBNAIL: { [x: string]: string } = {
  COFFEE: '/assets/images/coffee.png',
  DRINK: '/assets/images/beer.png',
  MEAL: '/assets/images/meal.png',
} as const;

export const KKOGKKOG_TYPE_MAPPER = kkogkkogType.reduce((prev, current) => {
  const { engType, koreanType } = current;

  return {
    ...prev,
    [engType]: koreanType,
    [koreanType]: engType,
  };
}, {} as any);

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

export type KKOGKKOG_ENG_TYPE = typeof kkogkkogType[number]['engType'];
export type KKOGKKOG_KOREAN_TYPE = typeof kkogkkogType[number]['koreanType'];

export type KKOGKKOG_COLORS = typeof kkogkkogColors[number];

export type KKOGKKOG_MODIFIERS = typeof kkogkkogModifiers[number];

export type COUPON_EVENT = typeof couponEvent[number];
export type COUPON_STATUS = typeof couponStatus[number];

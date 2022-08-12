import theme from '@/styles/theme';

import { CommonHistory } from './common';

export const couponTypeCollection = [
  { koreanType: '커피', engType: 'COFFEE' } as const,
  { koreanType: '술', engType: 'DRINK' } as const,
  { koreanType: '식사', engType: 'MEAL' } as const,
] as const;

export const couponListType = ['received', 'sent'] as const;

export const couponEvent = ['INIT', 'REQUEST', 'CANCEL', 'DECLINE', 'ACCEPT', 'FINISH'] as const;
export const couponStatus = ['REQUESTED', 'READY', 'ACCEPTED', 'FINISHED'] as const;

export const couponColors = [
  theme.colors.white_100,
  theme.colors.primary_100,
  theme.colors.primary_200,
  theme.colors.primary_300,
  theme.colors.primary_400,
  theme.colors.primary_500,
] as const;

export const couponModifiers = ['고마워요', '축하해요', '미안해요', '화이팅!'] as const;

export const THUMBNAIL: { [x: string]: string } = {
  COFFEE: '/assets/images/coffee.png',
  DRINK: '/assets/images/beer.png',
  MEAL: '/assets/images/meal.png',
} as const;

export type COUPON_ENG_TYPE = typeof couponTypeCollection[number]['engType'];
export type COUPON_KOREAN_TYPE = typeof couponTypeCollection[number]['koreanType'];

export type COUPON_LIST_TYPE = typeof couponListType[number];

export type COUPON_COLORS = typeof couponColors[number];

export type COUPON_MODIFIERS = typeof couponModifiers[number];

export type COUPON_EVENT = typeof couponEvent[number];
export type COUPON_STATUS = typeof couponStatus[number];

export interface CouponHistory extends CommonHistory {
  message: string;
}

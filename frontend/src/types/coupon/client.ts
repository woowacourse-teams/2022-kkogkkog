import beerImage from '@/assets/images/beer.png';
import coffeeImage from '@/assets/images/coffee.png';
import mealImage from '@/assets/images/meal.png';
import { Member } from '@/types/user/client';
import { YYYYMMDDhhmmss } from '@/types/utils';

export const couponTypeCollection = [
  { koreanType: '커피', engType: 'COFFEE' } as const,
  { koreanType: '술', engType: 'DRINK' } as const,
  { koreanType: '식사', engType: 'MEAL' } as const,
] as const;

export const couponListType = ['received', 'sent'] as const;

export const couponEvent = [
  'RECEIVE',
  'INIT',
  'REQUEST',
  'CANCEL',
  'DECLINE',
  'ACCEPT',
  'FINISH',
] as const;

export const couponStatus = {
  REQUESTED: 'REQUESTED',
  READY: 'READY',
  ACCEPTED: 'ACCEPTED',
  FINISHED: 'FINISHED',
} as const;

export const couponHashtags = [
  '고마워요',
  '축하해요',
  '미안해요',
  '화이팅!',
  '함께 자라기',
  '즐코!',
  '사랑해요',
  '잘했어요!',
] as const;

export const THUMBNAIL = {
  COFFEE: { src: coffeeImage, alt: '커피 쿠폰' },
  DRINK: { src: beerImage, alt: '술 쿠폰' },
  MEAL: { src: mealImage, alt: '식사 쿠폰' },
} as const;

export type COUPON_ENG_TYPE = typeof couponTypeCollection[number]['engType'];
export type COUPON_KOREAN_TYPE = typeof couponTypeCollection[number]['koreanType'];

export type COUPON_LIST_TYPE = typeof couponListType[number];

export type COUPON_HASHTAGS = typeof couponHashtags[number];

export type COUPON_EVENT = typeof couponEvent[number];

export type COUPON_STATUS = typeof couponStatus[keyof typeof couponStatus];
export type COUPON_STATUS_WITH_MEETINGDATE = Exclude<COUPON_STATUS, 'READY'>;

export type COUPON_MEETING_DATE = YYYYMMDDhhmmss;

export type CouponCase<T extends COUPON_STATUS> = {
  id: number;
  sender: Member;
  receiver: Member;
  couponTag: string;
  couponMessage: string;
  couponType: COUPON_ENG_TYPE;
  couponStatus: T;
  meetingDate: T extends COUPON_STATUS_WITH_MEETINGDATE ? COUPON_MEETING_DATE : null;
  createdTime: YYYYMMDDhhmmss;
};

export type Coupon =
  | CouponCase<'ACCEPTED'>
  | CouponCase<'FINISHED'>
  | CouponCase<'READY'>
  | CouponCase<'REQUESTED'>;

export interface CouponHistory {
  id: number;
  nickname: string;
  imageUrl: string;
  couponType: COUPON_ENG_TYPE;
  couponEvent: COUPON_EVENT;
  meetingMessage: string;
  meetingDate: COUPON_MEETING_DATE;
  createdTime: YYYYMMDDhhmmss;
}

export interface Reservation {
  meetingDate: COUPON_MEETING_DATE;
  coupons: Coupon[];
}

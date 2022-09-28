import { YYYYMMDDhhmmss } from '@/types/utils';

import { UserResponse } from '../user/remote';

export const couponTypeCollection = [
  { koreanType: '커피', engType: 'COFFEE' } as const,
  { koreanType: '술', engType: 'DRINK' } as const,
  { koreanType: '식사', engType: 'MEAL' } as const,
] as const;

export const couponListType = ['received', 'sent'] as const;

export const couponEvent = ['INIT', 'REQUEST', 'CANCEL', 'DECLINE', 'ACCEPT', 'FINISH'] as const;
export const couponStatus = ['REQUESTED', 'READY', 'ACCEPTED', 'FINISHED'] as const;

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

export const THUMBNAIL: { [x: string]: string } = {
  COFFEE: '/assets/images/coffee.png',
  DRINK: '/assets/images/beer.png',
  MEAL: '/assets/images/meal.png',
} as const;

export type COUPON_ENG_TYPE = typeof couponTypeCollection[number]['engType'];
export type COUPON_KOREAN_TYPE = typeof couponTypeCollection[number]['koreanType'];

export type COUPON_LIST_TYPE = typeof couponListType[number];

export type COUPON_HASHTAGS = typeof couponHashtags[number];

export type COUPON_EVENT = typeof couponEvent[number];
export type COUPON_STATUS = typeof couponStatus[number];

export type COUPON_MEMBER_TYPE = 'SENT' | 'RECEIVED';

export interface Coupon {
  id: number;
  sender: Pick<UserResponse, 'id' | 'nickname' | 'imageUrl'>;
  receiver: Pick<UserResponse, 'id' | 'nickname' | 'imageUrl'>;
  couponTag: string;
  couponMessage: string;
  couponType: COUPON_ENG_TYPE;
  couponStatus: COUPON_STATUS;
  meetingDate: YYYYMMDDhhmmss;
  createdTime: YYYYMMDDhhmmss;
}

export interface CouponHistory {
  id: number;
  nickname: string;
  imageUrl: string;
  couponType: COUPON_ENG_TYPE;
  couponEvent: COUPON_EVENT;
  meetingMessage: string;
  meetingDate: YYYYMMDDhhmmss;
  createdTime: YYYYMMDDhhmmss;
}

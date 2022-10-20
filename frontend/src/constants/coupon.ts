/** Typing은 어려워 */

import { COUPON_ENG_TYPE, COUPON_EVENT, COUPON_KOREAN_TYPE } from '@/types/coupon/client';

export const couponTypeTextMapper: Record<COUPON_ENG_TYPE, COUPON_KOREAN_TYPE> = {
  COFFEE: '커피',
  DRINK: '술',
  MEAL: '식사',
};

export const couponEventTextMapper: Record<COUPON_EVENT, string> = {
  RECEIVE: '쿠폰을 받았어요.',
  INIT: '쿠폰을 보냈어요.',
  REQUEST: '쿠폰 사용을 요청했어요.',
  CANCEL: '쿠폰 사용을 취소했어요.',
  DECLINE: '쿠폰 사용을 거절했어요.',
  ACCEPT: '쿠폰 사용을 승인했어요.',
  FINISH: '쿠폰 사용을 완료했어요.',
};

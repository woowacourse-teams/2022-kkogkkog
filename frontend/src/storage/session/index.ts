import { FilterOption } from '@/@pages/coupon-list';

const SESSION_KEY = {
  couponListStatus: 'couponListStatus',
  prevUrl: 'prevUrl',
};

class SessionStorage<T extends string> {
  constructor(public key: string) {
    this.key = key;
  }

  get = (): T | null => {
    return sessionStorage.getItem(this.key) as T | null;
  };

  set = (value: T) => {
    sessionStorage.setItem(this.key, value);
  };
}

export const { get: getCouponListStatus, set: setCouponListStatus } =
  new SessionStorage<FilterOption>(SESSION_KEY.couponListStatus);

export const { get: getPrevUrl, set: setPrevUrl } = new SessionStorage(SESSION_KEY.prevUrl);

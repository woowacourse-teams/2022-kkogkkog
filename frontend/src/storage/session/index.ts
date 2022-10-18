import { FilterOption } from '@/@pages/coupon-list';
import { UnregisteredFilterOption } from '@/@pages/unregistered-coupon-list';

const SESSION_KEY = {
  filterOptions: 'filterOptions',
  unregisteredFilterOptions: 'unregisteredFilterOptions',
  prevUrl: 'prevUrl',
  unregisteredCouponCode: 'unregisteredCouponCode',
};

class SessionStorage<T extends string> {
  constructor(public key: string) {
    this.key = key;
  }

  get(): T | null {
    return sessionStorage.getItem(this.key) as T | null;
  }

  set(value: T) {
    sessionStorage.setItem(this.key, value);
  }

  remove() {
    sessionStorage.removeItem(this.key);
  }
}

export const filterOptionsSessionStorage = new SessionStorage<FilterOption>(
  SESSION_KEY.filterOptions
);

export const unregisteredFilterOptionsSessionStorage = new SessionStorage<UnregisteredFilterOption>(
  SESSION_KEY.unregisteredFilterOptions
);

export const prevUrlSessionStorage = new SessionStorage(SESSION_KEY.prevUrl);

export const unregisteredCouponCodeStorage = new SessionStorage(SESSION_KEY.unregisteredCouponCode);

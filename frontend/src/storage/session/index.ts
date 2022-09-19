import { FilterOption } from '@/@pages/coupon-list';

const SESSION_KEY = {
  filterOptions: 'filterOptions',
  prevUrl: 'prevUrl',
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
}

export const filterOptionsSessionStorage = new SessionStorage<FilterOption>(
  SESSION_KEY.filterOptions
);

export const prevUrlSessionStorage = new SessionStorage(SESSION_KEY.prevUrl);

const SESSION_KEY = {
  couponListStatus: 'couponListStatus',
  prevUrl: 'prevUrl',
};

class SessionStorage {
  constructor(public key: string) {
    this.key = key;
  }

  get = () => {
    return sessionStorage.getItem(this.key) ?? '';
  };

  set = (value: string) => {
    sessionStorage.setItem(this.key, value);
  };
}

export const { get: getCouponListStatus, set: setCouponListStatus } = new SessionStorage(
  SESSION_KEY.couponListStatus
);

export const { get: getPrevUrl, set: setPrevUrl } = new SessionStorage(SESSION_KEY.prevUrl);

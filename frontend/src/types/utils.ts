export const hasKey = <T>(state: unknown, key: string): state is { [key: string]: T } => {
  if (typeof state !== 'object') {
    return false;
  }

  if (state === null) {
    return false;
  }

  return key in state;
};

export type MakeOptional<T, K extends keyof T> = Omit<T, K> & {
  [P in K]?: T[P];
};

export type YYYYMMDD = `${string}-${string}-${string}`;
export type YYYYMMDD_KR = `${string}년 ${string}월 ${string}일`;
export type YYYYMMDDhhmmss = `${YYYYMMDD}T${string}:${string}:${string}`;
export type MMDD_KR = `${string}월 ${string}일`;

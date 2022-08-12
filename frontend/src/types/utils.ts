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

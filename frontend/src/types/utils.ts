export const hasKey = <T>(state: unknown, key: string): state is { [key: string]: T } => {
  if (typeof state !== 'object') {
    return false;
  }

  if (state === null) {
    return false;
  }

  return key in state;
};

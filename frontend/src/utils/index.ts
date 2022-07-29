export const getToday = () => {
  const now = new Date();

  const year = now.getFullYear();
  const month = addZero(now.getMonth() + 1);
  const date = addZero(now.getDate());

  return year + '-' + month + '-' + date;
};

export const addZero = (num: number): string => {
  return Math.floor(num / 10) === 0 ? `0${num}` : String(num);
};

export const extractDate = (date: string | undefined) => {
  if (!date) {
    return null;
  }

  const [year, month, day] = date.split('-');

  return `${month}월 ${day}일`;
};

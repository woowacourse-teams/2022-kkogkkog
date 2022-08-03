export const getToday = () => {
  const now = new Date();

  const year = now.getFullYear();
  const month = addZero(now.getMonth() + 1);
  const date = addZero(now.getDate());

  return `${year}-${month}-${date}`;
};

export const addZero = (num: number): string => {
  return Math.floor(num / 10) === 0 ? `0${num}` : String(num);
};

export const extractDate = (date: string | undefined, includeYear = false) => {
  if (!date) {
    return null;
  }

  const [year, month, day] = date.split('-');

  if (includeYear) {
    return year && month && day && `${year}년 ${month}월 ${day}일`;
  }

  return month && day && `${month}월 ${day}일`;
};

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

export const generateDateText = (date: string | undefined, includeYear = false) => {
  if (!date) {
    return null;
  }

  const [year, month, day] = date.split('-');

  const dateText = includeYear
    ? year && month && day && `${year}년 ${Number(month)}월 ${Number(day)}일`
    : month && day && `${Number(month)}월 ${Number(day)}일`;

  return dateText;
};

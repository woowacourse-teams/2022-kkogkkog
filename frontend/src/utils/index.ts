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

// 2022-08-15 형식의 텍스트가 들어옴.
// @TODO: string 타입 구체적으로 좁히기
export const isBeforeToday = (date: string) => {
  const today = getToday();

  const [todayYear, todayMonth, todayDay] = today.split('-');
  const [year, month, day] = date.split('-');

  if (todayYear > year) {
    return true;
  } else if (todayMonth > month) {
    return true;
  } else if (todayDay > day) {
    return true;
  }

  return false;
};

const week = ['일', '월', '화', '수', '목', '금', '토'];

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
    return '-';
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
  const today = new Date();

  const todayYear = today.getFullYear();
  const todayMonth = today.getMonth() + 1;
  const todayDay = today.getDate();

  const dateObj = new Date(date);

  const year = dateObj.getFullYear();
  const month = dateObj.getMonth() + 1;
  const day = dateObj.getDate();

  if (todayYear > year) {
    return true;
  } else if (todayMonth > month) {
    return true;
  } else if (todayDay > day) {
    return true;
  }

  return false;
};

export const generateDDay = (date: string) => {
  if (!date) {
    return 999;
  }

  const propDateInstance = new Date(date);

  const todayDateInstance = new Date();

  const dDay =
    Math.ceil((propDateInstance.getTime() - todayDateInstance.getTime()) / (1000 * 60 * 60 * 24)) -
    1;

  return dDay > 999 ? 999 : dDay;
};

export const computeDay = (date: string) => {
  if (!date) {
    return '-';
  }

  const propDate = new Date(date);

  const day = propDate.getDay();

  return week[day];
};

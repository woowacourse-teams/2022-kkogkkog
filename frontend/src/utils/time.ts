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

  const propDateInstance = new Date(Number(year), Number(month) - 1, Number(day.slice(0, 2)));

  const propYear = propDateInstance.getFullYear();
  const propMonth = propDateInstance.getMonth() + 1;
  const propDay = propDateInstance.getDate();

  const dateText = includeYear
    ? propYear &&
      propMonth &&
      propDay &&
      `${propYear}년 ${Number(propMonth)}월 ${Number(propDay)}일`
    : propMonth && propDay && `${Number(propMonth)}월 ${Number(propDay)}일`;

  return dateText;
};

// 2022-08-15 형식의 텍스트가 들어옴.
// @TODO: string 타입 구체적으로 좁히기
export const isBeforeToday = (date: string) => {
  const today = new Date();

  const todayYear = today.getFullYear();
  const todayMonth = today.getMonth() + 1;
  const todayDay = today.getDate();

  const [year, month, day] = date.split('-');
  const dateObj = new Date(Number(year), Number(month) - 1, Number(day.slice(0, 2)));

  const propYear = dateObj.getFullYear();
  const propMonth = dateObj.getMonth() + 1;
  const propDay = dateObj.getDate();

  if (todayYear > propYear) {
    return true;
  } else if (todayMonth > propMonth) {
    return true;
  } else if (todayDay > propDay) {
    return true;
  }

  return false;
};

export const generateDDay = (date: string) => {
  if (!date) {
    return 999;
  }

  const [year, month, day] = date.split('-').map(str => Number(str));

  const propDateInstance = new Date(year, month - 1, day);

  const todayDateInstance = new Date();

  const dDay = Math.ceil(
    (propDateInstance.getTime() - todayDateInstance.getTime()) / (1000 * 60 * 60 * 24)
  );

  return dDay > 999 ? 999 : dDay;
};

export const computeDay = (date: string) => {
  if (!date) {
    return '-';
  }

  const [year, month, day] = date.split('-').map(str => Number(str));

  const propDate = new Date(year, month - 1, day);

  return week[propDate.getDay()];
};

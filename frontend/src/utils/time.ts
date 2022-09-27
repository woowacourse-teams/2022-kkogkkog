import { MMDD_KR, YYYYMMDD, YYYYMMDD_KR, YYYYMMDDhhmmss } from './../types/utils';

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

  const [year, month, day] = date.split(/[- ]/);

  const propDateInstance = new Date(Number(year), Number(month) - 1, Number(day));

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

  const [year, month, day] = date.split(/[- ]/);
  const dateObj = new Date(Number(year), Number(month) - 1, Number(day));

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

  const [year, month, day] = date.split(/[- ]/);

  const propDateInstance = new Date(Number(year), Number(month) - 1, Number(day));

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

  const [year, month, day] = date.split(/[- ]/);

  const propDate = new Date(Number(year), Number(month) - 1, Number(day));

  return week[propDate.getDay()];
};

export const sortByTime = (targetDateA: string, targetDateB: string) => {
  const [yearA, monthA, dayA] = targetDateA.split(/[- ]/);
  const [yearB, monthB, dayB] = targetDateB.split(/[- ]/);

  return Number(`${yearA}${monthA}${dayA}`) - Number(`${yearB}${monthB}${dayB}`);
};

/** NEWEST */

export const getTodayDate = (): YYYYMMDD => {
  const now = new Date();

  const year = now.getFullYear();
  const month = addZero(now.getMonth() + 1);
  const date = addZero(now.getDate());

  return `${year}-${month}-${date}`;
};

export const generateDateKR = (
  date: YYYYMMDDhhmmss,
  includeYear = false
): YYYYMMDD_KR | MMDD_KR | '' => {
  if (!date) {
    return '';
  }

  const [year, month, day] = date.split(/[- ]/);

  const propDateInstance = new Date(Number(year), Number(month) - 1, Number(day));

  const propYear = propDateInstance.getFullYear();
  const propMonth = propDateInstance.getMonth() + 1;
  const propDay = propDateInstance.getDate();

  if (includeYear) {
    const includeYearDateKR: YYYYMMDD_KR = `${propYear}년 ${propMonth}월 ${propDay}일`;

    return includeYearDateKR;
  }

  const excludeYearDateKR: MMDD_KR = `${propMonth}월 ${propDay}일`;

  return excludeYearDateKR;
};

export const generateDday = (date: YYYYMMDDhhmmss): number => {
  if (!date) {
    // 에러를 쓰로우 해버릴까?
    return 999;
  }

  const [year, month, day] = date.split(/[- ]/);

  const propDateInstance = new Date(Number(year), Number(month) - 1, Number(day));

  const todayDateInstance = new Date();

  const dDay = Math.ceil(
    (propDateInstance.getTime() - todayDateInstance.getTime()) / (1000 * 60 * 60 * 24)
  );

  return dDay > 999 ? 999 : dDay;
};

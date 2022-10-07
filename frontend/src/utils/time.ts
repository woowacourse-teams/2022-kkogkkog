import { MMDD_KR, YYYYMMDD, YYYYMMDD_KR, YYYYMMDDhhmmss } from '@/types/utils';

import { addZero } from './time';

const week = ['일', '월', '화', '수', '목', '금', '토'] as const;

type Week = typeof week[number];

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

  const [year, month, day] = date.split(/[-T]/);

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

export const isBeforeToday = (date: YYYYMMDD | YYYYMMDDhhmmss): boolean => {
  const today = new Date();

  const todayYear = today.getFullYear();
  const todayMonth = today.getMonth() + 1;
  const todayDay = today.getDate();

  const [year, month, day] = date.split(/[-T]/);
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

export const generateDday = (date: YYYYMMDDhhmmss): number => {
  if (!date) {
    return 999;
  }

  const [year, month, day] = date.split(/[-T]/);

  const propDateInstance = new Date(Number(year), Number(month) - 1, Number(day));

  const todayDateInstance = new Date();

  const dDay = Math.ceil(
    (propDateInstance.getTime() - todayDateInstance.getTime()) / (1000 * 60 * 60 * 24)
  );

  return dDay > 999 ? 999 : dDay;
};

export const computeDay = (date: YYYYMMDD | YYYYMMDDhhmmss): Week | '' => {
  if (!date) {
    return '';
  }

  const [year, month, day] = date.split(/[-T]/);

  const propDate = new Date(Number(year), Number(month) - 1, Number(day));

  return week[propDate.getDay()];
};

export const sortByTime = (targetDateA: string, targetDateB: string): number => {
  const [yearA, monthA, dayA] = targetDateA.split(/[-T]/);
  const [yearB, monthB, dayB] = targetDateB.split(/[-T]/);

  return Number(`${yearA}${monthA}${dayA}`) - Number(`${yearB}${monthB}${dayB}`);
};

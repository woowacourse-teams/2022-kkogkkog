import { MMDD_KR, YYYYMMDD, YYYYMMDD_KR, YYYYMMDDhhmmss } from '@/types/utils';

const week = ['일', '월', '화', '수', '목', '금', '토'] as const;

type Week = typeof week[number];

export const addZero = (num: number): string => {
  return Math.floor(num / 10) === 0 ? `0${num}` : String(num);
};

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

  return includeYear ? `${propYear}년 ${propMonth}월 ${propDay}일` : `${propMonth}월 ${propDay}일`;
};

export const isBeforeToday = (date: YYYYMMDD | YYYYMMDDhhmmss): boolean => {
  const todayDate = new Date();

  const [year, month, day] = date.split(/[-T]/);
  const propDate = new Date(Number(year), Number(month) - 1, Number(day));

  return todayDate.getTime() > propDate.getTime();
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

export const computeExpiredTime = (startTime: YYYYMMDDhhmmss, expirationPeriodMS: number) => {
  const startTimeMS = new Date(startTime).getTime();

  const expiredTimeMS = startTimeMS + expirationPeriodMS;

  return expiredTimeMS;
};

export const computeDayHourMinSecByMS = (ms: number) => {
  const seconds = ms / 1000;

  const day = Math.floor(seconds / 86400);
  const hour = Math.floor((seconds % 86400) / 3600);
  const min = Math.floor(((seconds % 86400) % 3600) / 60);
  const sec = Math.floor(seconds % 60);

  return { day, hour, min, sec };
};

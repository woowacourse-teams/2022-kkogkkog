import { expectType } from 'tsd';

import { MakeOptional, MMDD_KR, YYYYMMDDhhmmss } from '@/types/utils';
import { generateDateKR, generateDday, getTodayDate } from '@/utils/time';

import { YYYYMMDD, YYYYMMDD_KR } from './../types/utils';

// eslint-disable-next-line jest/expect-expect
test('MakeOptional 유틸 타입은 인수로 받는 프로퍼티들을 옵셔널로 만든다.', () => {
  type abOptional = MakeOptional<{ a: 1; b: 2; c: 3 }, 'a' | 'b'>;

  const data: abOptional = { c: 3 };

  expectType<{ a?: number; b?: number; c: number }>(data);
});

// eslint-disable-next-line jest/expect-expect
test('generateTodatDate는 YYYYMMDD 타입의 값을 반환한다.', () => {
  const date = getTodayDate();

  expectType<YYYYMMDD>(date);
});

// eslint-disable-next-line jest/expect-expect
test('generateDateKR은 YYYYMMDDhhmmss와 false 타입의 인자 값을 받아 MMDD_KR을 반환한다.', () => {
  const createdAt: YYYYMMDDhhmmss = '2022-01-01 08:00:00';
  const includeYear = false;

  const date = generateDateKR(createdAt, includeYear);

  expectType<MMDD_KR>(date);
});

// eslint-disable-next-line jest/expect-expect
test('generateDateKR은 YYYYMMDDhhmmss와 true 타입의 인자 값을 받아 YYYYMMDD_KR을 반환한다.', () => {
  const createdAt: YYYYMMDDhhmmss = '2022-01-01 08:00:00';
  const includeYear = true;

  const date = generateDateKR(createdAt, includeYear);

  expectType<YYYYMMDD_KR>(date);
});

// eslint-disable-next-line jest/expect-expect
test('generateDateKR은 undefined와 boolean 타입의 인자 값을 받아 빈 문자열을 반환한다.', () => {
  const includeYear = false;

  const date = generateDateKR(undefined, includeYear);

  expectType<''>(date);
});

// eslint-disable-next-line jest/expect-expect
test('generateDday은 YYYYMMDDhhmmss의 인자 값을 받아 number 타입의 값을 반환한다.', () => {
  const createdAt: YYYYMMDDhhmmss = '2022-01-01 08:00:00';

  const dday = generateDday(createdAt);

  expectType<number>(dday);
});

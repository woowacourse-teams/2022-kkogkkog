import { YYYYMMDDhhmmss } from '@/types/utils';
import { computeDay, generateDateKR, generateDday, isBeforeToday } from '@/utils/time';

import { addZero } from './../../utils/time';

const realDate = global.Date;

beforeAll(() => {
  global.Date = jest.fn((...prop) =>
    prop.length === 0 ? new realDate(2022, 0, 1) : new realDate(prop[0], prop[1], prop[2])
  );
});

afterAll(() => {
  global.Date = realDate;
});

describe('addZero 함수 테스트', () => {
  it('addZero는 10보다 작은 숫자가 들어오는 경우 0을 붙여 반환한다.', () => {
    const input = 8;
    const expectedValue = '08';

    const output = addZero(input);

    expect(output).toBe(expectedValue);
  });

  it('addZero는 10보다 큰 숫자가 들어오는 경우 그대로 반환한다.', () => {
    const input = 12;
    const expectedValue = '12';

    const output = addZero(input);

    expect(output).toBe(expectedValue);
  });
});

describe('generateDateText 테스트', () => {
  it('generateDateText 함수는 includeYear가 true인 경우 **년 **월 **일의 형식의 값을 반환한다.', () => {
    const input = '2022-08-09T11:20:30';
    const expectedValue = '2022년 8월 9일';

    const output = generateDateKR(input, true);

    expect(output).toBe(expectedValue);
  });

  it('generateDateText 함수는 includeYear가 false인 경우 **월 **일의 형식의 값을 반환한다.', () => {
    const input = '2022-08-09T11:20:30';
    const expectedValue = '8월 9일';

    const output = generateDateKR(input, false);

    expect(output).toBe(expectedValue);
  });

  it('generateDateText 함수는 인자 date가 없는 경우 빈 문자열을 반환한다.', () => {
    const input: unknown = undefined;
    const expectedValue = '';

    const output = generateDateKR(input as YYYYMMDDhhmmss, false);

    expect(output).toBe(expectedValue);
  });
});

describe('isBeforeToday 테스트', () => {
  it('isBeforeToday는 오늘 날짜와 비교하여 이전 날짜가 인자로 들어가면 true를 반환한다.', () => {
    const input = '1997-01-26';
    const expectedValue = true;

    const output = isBeforeToday(input);

    expect(output).toBe(expectedValue);
  });

  it('isBeforeToday는 오늘 날짜와 비교하여 이후 날짜가 인자로 들어가면 false를 반환한다.', () => {
    const input = '2333-01-26';
    const expectedValue = false;

    const output = isBeforeToday(input);

    expect(output).toBe(expectedValue);
  });
});

describe('generateDday 함수 테스트', () => {
  it('generateDday는 현재 날짜로 부터의 D-Day를 반환한다.', () => {
    const input = '2022-01-05T00:11:22';
    const expectedValue = 4;

    const output = generateDday(input);

    expect(output).toBe(expectedValue);
  });

  it('generateDday는 D-Day가 99일 이상 남은 경우 99를 반환한다.', () => {
    const input = '2022-05-05T00:11:22';
    const expectedValue = 99;

    const output = generateDday(input);

    expect(output).toBe(expectedValue);
  });
});

describe('computeDay 함수 테스트', () => {
  it('computeDay는 요일을 반환한다.', () => {
    const input = '2022-01-01T00:11:22';
    const expectedValue = '토';

    const output = computeDay(input);

    expect(output).toBe(expectedValue);
  });
});

import { expectType } from 'tsd';

import { MakeOptional } from '@/types/utils';

// eslint-disable-next-line jest/expect-expect
test('MakeOptional 유틸 타입은 인수로 받는 프로퍼티들을 옵셔널로 만든다.', () => {
  type abOptional = MakeOptional<{ a: 1; b: 2; c: 3 }, 'a' | 'b'>;

  const data: abOptional = { c: 3 };

  expectType<{ a?: number; b?: number; c: number }>(data);
});

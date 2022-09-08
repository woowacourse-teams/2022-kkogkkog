import { screen } from '@testing-library/react';

import UserHistoryPage from '@/@pages/history';

import { testUser } from '../setupTests';
import { render } from './test-utils';

describe('<UserHistoryPage />', () => {
  it('알림을 확인할 수 있다.', async () => {
    render(<UserHistoryPage />);

    expect(await screen.findByText(testUser.nickname)).toBeInTheDocument();
  });
  // 히스토리를 확인할 수 있다.
  // 안 읽은 알림은 주황색 배경을 가진다.

  // 이것들은 테스트가 가능할까? + mock 보충 필요
  // 하나 읽고 돌아오면 하나만 읽음 처리 된다.
});

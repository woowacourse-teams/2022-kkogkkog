import { screen } from '@testing-library/react';

import UserHistoryPage from '@/@pages/history';
import { testUser } from '@/setupTests';
import theme from '@/styles/theme';
import { render } from '@/tests/utils';
import { rgb2hex } from '@/utils/rgba';

describe('<UserHistoryPage />', () => {
  it('알림이 온 개수만큼 알림 히스토리를 확인할 수 있다.', async () => {
    render(<UserHistoryPage />);

    expect(await screen.findAllByRole('listitem')).toHaveLength(testUser.histories.length);
  });

  it('읽지 않은 알림은 주황색 배경을 가진다.', async () => {
    render(<UserHistoryPage />);

    const unReadCount = testUser.histories.filter(history => !history.isRead).length;
    const unReadHistories = (await screen.findAllByRole('listitem')).filter(
      li => `${theme.colors.primary_100}20` === rgb2hex(getComputedStyle(li).backgroundColor)
    );

    expect(unReadHistories).toHaveLength(unReadCount);
  });
});

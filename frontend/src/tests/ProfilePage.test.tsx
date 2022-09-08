import { screen } from '@testing-library/react';

import ProfilePage from '@/@pages/profile';

import { testUser } from '../setupTests';
import { render } from './test-utils';

describe('<ProfilePage />', () => {
  it('유저 정보를 확인할 수 있다.', async () => {
    localStorage.setItem('user-token', testUser.email);

    render(<ProfilePage />);

    expect(await screen.findByText(testUser.nickname)).toBeInTheDocument();
  });
});

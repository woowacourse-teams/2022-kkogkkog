import NotFoundPage from '@/@pages/404';
import { render } from '@/tests/utils';

describe('<NotFOundPage />', () => {
  it('이미지가 렌더링 된다.', () => {
    const { getByText } = render(<NotFoundPage />);
    const heading2 = getByText('찾을 수 없는 페이지에요.');

    expect(heading2).toBeInTheDocument();
  });
});

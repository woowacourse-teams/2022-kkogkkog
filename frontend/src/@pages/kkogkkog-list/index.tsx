import styled from '@emotion/styled';
import { useQuery } from 'react-query';
import { Link } from 'react-router-dom';

import PageTemplate from '@/@components/@shared/PageTemplate';
import KkogKkogList from '@/@components/kkogkkog/KkogKkogList';
import { getKkogkkog } from '@/apis/kkogkkog';
import { PATH } from '@/Router';
import { KkogKkog } from '@/types/domain';

const KkogkkogListPage = () => {
  const {
    data: { data: kkogkkogList },
    isLoading,
    error,
  } = useQuery<{ data: KkogKkog[] }>('kkogkkogList', getKkogkkog);

  return (
    <PageTemplate title='꼭꼭 리스트'>
      <StyledRoot>
        <Link to={PATH.KKOGKKOG_CREATE}>생성해볼래?</Link>
        <KkogKkogList kkogkkogList={kkogkkogList} />
      </StyledRoot>
    </PageTemplate>
  );
};

export default KkogkkogListPage;

export const StyledRoot = styled.div`
  padding: 20px;

  border-radius: 4px;
`;

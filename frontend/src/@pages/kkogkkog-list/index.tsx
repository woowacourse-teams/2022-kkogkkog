import styled from '@emotion/styled';
import { useQuery } from 'react-query';

import PageTemplate from '@/@components/@shared/PageTemplate';
import KkogKkogList from '@/@components/kkogkkog/KkogKkogList';
import { getKkogkkog } from '@/apis/kkogkkog';
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
        <KkogKkogList kkogkkogList={kkogkkogList} />
      </StyledRoot>
    </PageTemplate>
  );
};

export default KkogkkogListPage;

export const StyledRoot = styled.div`
  min-height: 100vh;

  background-color: ${({ theme }) => theme.colors.white_100};

  padding: 20px;

  border-radius: 4px;
`;

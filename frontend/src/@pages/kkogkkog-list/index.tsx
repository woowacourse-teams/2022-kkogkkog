import styled from '@emotion/styled';
import { useQuery } from 'react-query';
import { Link } from 'react-router-dom';

import PageTemplate from '@/@components/@shared/PageTemplate';
import KkogKkogItem from '@/@components/kkogkkog/KkogKkogItem';
import KkogKkogList from '@/@components/kkogkkog/KkogKkogList';
import { getKkogkkog } from '@/apis/kkogkkog';
import { PATH } from '@/Router';
import { KkogKkog } from '@/types/domain';

const KkogkkogListPage = () => {
  const { data } = useQuery<{ data: KkogKkog[] }>('kkogkkogList', getKkogkkog);

  const { data: kkogkkogList } = data;

  return (
    <PageTemplate title='꼭꼭 모아보기'>
      <StyledRoot>
        <Link to={PATH.KKOGKKOG_CREATE}>
          <KkogKkogItem.LinkButton />
        </Link>
        <KkogKkogList kkogkkogList={kkogkkogList} />
      </StyledRoot>
    </PageTemplate>
  );
};

KkogkkogListPage.Skeleton = function Skeleton() {
  return (
    <PageTemplate title='꼭꼭 리스트'>
      <StyledRoot>
        <Link to={PATH.KKOGKKOG_CREATE}>
          <KkogKkogItem.LinkButton />
        </Link>
        <KkogKkogItem.Skeleton />
        <KkogKkogItem.Skeleton />
        <KkogKkogItem.Skeleton />
        <KkogKkogItem.Skeleton />
        <KkogKkogItem.Skeleton />
        <KkogKkogItem.Skeleton />
      </StyledRoot>
    </PageTemplate>
  );
};

export default KkogkkogListPage;

export const StyledRoot = styled.div`
  padding: 20px;

  border-radius: 4px;

  & > div {
    margin-top: 20px;
  }
`;

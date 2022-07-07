import styled from '@emotion/styled';
import { Suspense } from 'react';
import { useQuery } from 'react-query';
import { Link } from 'react-router-dom';

import PageTemplate from '@/@components/@shared/PageTemplate';
import KkogKkogItem from '@/@components/kkogkkog/KkogKkogItem';
import KkogKkogList from '@/@components/kkogkkog/KkogKkogList';
import { getKkogkkog } from '@/apis/kkogkkog';
import { PATH } from '@/Router';
import { KkogKkog } from '@/types/domain';

const Loading = () => {
  return (
    <PageTemplate title='꼭꼭 리스트'>
      <StyledRoot>
        <Link to={PATH.KKOGKKOG_CREATE}>
          <KkogKkogItem.CreateLinkButton />
        </Link>
        <KkogKkogItem.Skeleton />
        <KkogKkogItem.Skeleton />
        <KkogKkogItem.Skeleton />
      </StyledRoot>
    </PageTemplate>
  );
};

const KkogkkogListPage = () => {
  const { data } = useQuery<{ data: KkogKkog[] }>('kkogkkogList', getKkogkkog);

  const { data: kkogkkogList } = data;

  return (
    <Suspense fallback={<Loading />}>
      <PageTemplate title='꼭꼭 리스트'>
        <StyledRoot>
          <Link to={PATH.KKOGKKOG_CREATE}>
            <KkogKkogItem.CreateLinkButton />
          </Link>
          <KkogKkogList kkogkkogList={kkogkkogList} />
        </StyledRoot>
      </PageTemplate>
    </Suspense>
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

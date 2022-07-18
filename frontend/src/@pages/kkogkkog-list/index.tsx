import styled from '@emotion/styled';
import { useQuery } from 'react-query';
import { Link } from 'react-router-dom';

import PageTemplate from '@/@components/@shared/PageTemplate';
import KkogKkogItem from '@/@components/kkogkkog/KkogKkogItem';
import KkogKkogList from '@/@components/kkogkkog/KkogKkogList';
import { getKkogkkogList } from '@/apis/kkogkkog';
import { PATH } from '@/Router';
import { KkogKkogListResponse } from '@/types/remote/response';

const KkogkkogListPage = () => {
  const { data } = useQuery<{ data: KkogKkogListResponse }>('kkogkkogList', getKkogkkogList);

  const kkogkkogList = data?.data;

  return (
    <PageTemplate title='꼭꼭 모아보기'>
      <Styled.Root>
        <Link to={PATH.KKOGKKOG_CREATE}>
          <KkogKkogItem.LinkButton />
        </Link>
        <div>received</div>
        <KkogKkogList kkogkkogList={kkogkkogList?.received} />
        <div>sent</div>
        <KkogKkogList kkogkkogList={kkogkkogList?.sent} />
      </Styled.Root>
    </PageTemplate>
  );
};

KkogkkogListPage.Skeleton = function Skeleton() {
  return (
    <PageTemplate title='꼭꼭 모아보기'>
      <Styled.Root>
        <Link to={PATH.KKOGKKOG_CREATE}>
          <KkogKkogItem.LinkButton />
        </Link>
        <KkogKkogItem.Skeleton />
        <KkogKkogItem.Skeleton />
        <KkogKkogItem.Skeleton />
        <KkogKkogItem.Skeleton />
        <KkogKkogItem.Skeleton />
        <KkogKkogItem.Skeleton />
      </Styled.Root>
    </PageTemplate>
  );
};

export default KkogkkogListPage;

export const Styled = {
  Root: styled.div`
    padding: 20px;

    border-radius: 4px;

    & > div {
      margin-top: 20px;
    }
  `,
};

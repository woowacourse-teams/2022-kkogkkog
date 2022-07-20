import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link, useLocation } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import PageTemplate from '@/@components/@shared/PageTemplate';
import KkogKkogItem from '@/@components/kkogkkog/KkogKkogItem';
import KkogKkogList from '@/@components/kkogkkog/KkogKkogList';
import { useStatus } from '@/@hooks/@common/useStatus';
import { useKkogKkogList } from '@/@hooks/kkogkkog/useKkogKkogList';
import useMe from '@/@hooks/user/useMe';
import { PATH } from '@/Router';

const LandingPage = () => {
  const { me, isFetched } = useMe();

  if (!isFetched) {
    return <></>;
  }

  return me ? <AuthorizedLanding /> : <UnAuthorizedLanding />;
};

export default LandingPage;

LandingPage.Skeleton = function Skeleton() {
  return (
    <PageTemplate title='꼭꼭' hasHeader={false}>
      <Styled.Root>
        <Styled.LinkContainer>
          <Link to={PATH.KKOGKKOG_CREATE}>
            <KkogKkogItem.LinkButton />
          </Link>
        </Styled.LinkContainer>
        <Styled.ListContainer>
          <Styled.ListHeaderContainer>
            <Styled.ListHeaderItem>받은 쿠폰</Styled.ListHeaderItem>
            <Styled.ListHeaderItem>보낸 쿠폰</Styled.ListHeaderItem>
          </Styled.ListHeaderContainer>
          <KkogKkogItem.Skeleton />
          <KkogKkogItem.Skeleton />
          <KkogKkogItem.Skeleton />
          <KkogKkogItem.Skeleton />
          <KkogKkogItem.Skeleton />
          <KkogKkogItem.Skeleton />
        </Styled.ListContainer>
      </Styled.Root>
    </PageTemplate>
  );
};

const UnAuthorizedLanding = () => {
  return (
    <PageTemplate title='꼭꼭'>
      <Styled.UnAuthorizedRoot>
        <Button
          css={css`
            padding: 15px;
          `}
        >
          꼭꼭 시작하기
        </Button>
      </Styled.UnAuthorizedRoot>
    </PageTemplate>
  );
};

type STATUS_TYPE = 'received' | 'sent';

const AuthorizedLanding = () => {
  const { state } = useLocation() as { state: { status: STATUS_TYPE } };

  const { kkogkkogList } = useKkogKkogList();

  const { status, changeStatus } = useStatus<STATUS_TYPE>(state?.status ?? 'received');

  return (
    <PageTemplate title='꼭꼭'>
      <Styled.Root>
        <Styled.LinkContainer>
          <Link to={PATH.KKOGKKOG_CREATE}>
            <KkogKkogItem.LinkButton />
          </Link>
        </Styled.LinkContainer>
        <Styled.ListContainer>
          <Styled.ListHeaderContainer>
            <Styled.ListHeaderItem
              isSelected={status === 'received'}
              onClick={() => changeStatus('received')}
            >
              받은 쿠폰
            </Styled.ListHeaderItem>
            <Styled.ListHeaderItem
              isSelected={status === 'sent'}
              onClick={() => changeStatus('sent')}
            >
              보낸 쿠폰
            </Styled.ListHeaderItem>
          </Styled.ListHeaderContainer>
          {status === 'received' && <KkogKkogList kkogkkogList={kkogkkogList?.received} />}
          {status === 'sent' && <KkogKkogList kkogkkogList={kkogkkogList?.sent} />}
        </Styled.ListContainer>
      </Styled.Root>
    </PageTemplate>
  );
};

export const Styled = {
  UnAuthorizedRoot: styled.div`
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;

    padding: 20px;
  `,
  Root: styled.div`
    border-radius: 4px;
  `,
  LinkContainer: styled.div`
    padding: 20px;
  `,
  ListContainer: styled.div`
    & > div {
      margin-top: 20px;
    }
  `,
  ListHeaderContainer: styled.div`
    display: flex;

    padding: 0 20px;

    & > div {
      margin-right: 30px;
    }

    ${({ theme }) => css`
      border-bottom: 1px solid ${theme.colors.light_grey_100};
    `}
  `,
  ListHeaderItem: styled.div<{ isSelected?: boolean }>`
    padding-bottom: 8px;

    font-size: 14px;

    cursor: pointer;

    ${({ theme, isSelected }) => css`
      color: ${theme.colors.primary_400};

      border-bottom: ${isSelected ? `1.5px solid ${theme.colors.primary_400}` : 'none'};
    `}
  `,
};

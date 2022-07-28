import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { useMemo } from 'react';
import { Link, useLocation } from 'react-router-dom';

import Icon from '@/@components/@shared/Icon';
import ListFilter from '@/@components/@shared/ListFilter';
import PageTemplate from '@/@components/@shared/PageTemplate';
import BigKkogKkogItem from '@/@components/kkogkkog/KkogKkogItem/big';
import VerticalKkogKkogList from '@/@components/kkogkkog/KkogKkogList/vertical';
import KkogKkogModal from '@/@components/kkogkkog/KkogKkogModal';
import { useStatus } from '@/@hooks/@common/useStatus';
import { useKkogKkogList } from '@/@hooks/kkogkkog/useKkogKkogList';
import useKkogKkogModal from '@/@hooks/kkogkkog/useKkogKkogModal';
import { PATH } from '@/Router';
import theme from '@/styles/theme';
import { COUPON_LIST_TYPE } from '@/types/client/kkogkkog';
import { KkogKKogResponse } from '@/types/remote/response';

const filterOption = ['전체', '열린 약속', '잡은 약속', '지난 약속'] as const;

export type FilterOption = typeof filterOption[number];

const KkogkkogListPage = () => {
  const { kkogkkogList } = useKkogKkogList();

  const couponListType: COUPON_LIST_TYPE =
    useLocation().pathname === PATH.SENT_KKOGKKOG_LIST ? 'sent' : 'received';

  const { status, changeStatus } = useStatus<FilterOption>('전체');

  const { currentKkogKkog, openKkogKkogModal, closeKkogKkogModal } = useKkogKkogModal();

  const parsedKkogKkogList = useMemo(
    () =>
      kkogkkogList &&
      kkogkkogList[couponListType].reduce(
        (prev, kkogkkog) => {
          const key = kkogkkog.couponStatus;

          return { ...prev, [key]: [...prev[key], kkogkkog] };
        },
        {
          REQUESTED: [],
          READY: [],
          ACCEPTED: [],
          FINISHED: [],
        } as any
      ),
    [kkogkkogList, couponListType]
  );

  const onClickFilterButton = (status: FilterOption) => {
    changeStatus(status);
  };

  const onClickCouponItem = (kkogkkog: KkogKKogResponse) => {
    openKkogKkogModal(kkogkkog);
  };

  return (
    <PageTemplate title='꼭꼭 모아보기'>
      <Styled.Root>
        <ListFilter<FilterOption>
          status={status}
          options={filterOption}
          onClickFilterButton={onClickFilterButton}
        />
        {status === '전체' && (
          <Styled.Container>
            <VerticalKkogKkogList
              kkogkkogList={parsedKkogKkogList['REQUESTED']}
              CouponItem={BigKkogKkogItem}
              onClickCouponItem={onClickCouponItem}
            />
          </Styled.Container>
        )}

        {status === '열린 약속' && (
          <Styled.Container>
            <VerticalKkogKkogList
              kkogkkogList={[...parsedKkogKkogList['READY'], ...parsedKkogKkogList['REQUESTED']]}
              CouponItem={BigKkogKkogItem}
              onClickCouponItem={onClickCouponItem}
            />
          </Styled.Container>
        )}

        {status === '잡은 약속' && (
          <Styled.Container>
            <VerticalKkogKkogList
              kkogkkogList={parsedKkogKkogList['ACCEPTED']}
              CouponItem={BigKkogKkogItem}
              onClickCouponItem={onClickCouponItem}
            />
          </Styled.Container>
        )}

        {status === '지난 약속' && (
          <Styled.Container>
            <VerticalKkogKkogList
              kkogkkogList={parsedKkogKkogList['FINISHED']}
              CouponItem={BigKkogKkogItem}
              onClickCouponItem={onClickCouponItem}
            />
          </Styled.Container>
        )}

        {currentKkogKkog && (
          <KkogKkogModal kkogkkog={currentKkogKkog} closeModal={closeKkogKkogModal} />
        )}
        <Link
          to={PATH.KKOGKKOG_CREATE}
          css={css`
            position: fixed;
            bottom: 12px;
            right: 12px;
          `}
        >
          <Icon iconName='plus' size='37' color={theme.colors.primary_400} />
        </Link>
      </Styled.Root>
    </PageTemplate>
  );
};

KkogkkogListPage.Skeleton = function Skeleton() {
  return (
    <PageTemplate title='꼭꼭 모아보기'>
      <Styled.Root>
        <VerticalKkogKkogList.Skeleton CouponItemSkeleton={BigKkogKkogItem.Skeleton} />
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
  Container: styled.div`
    padding-bottom: 20px;
    margin-bottom: 10px;
  `,
};

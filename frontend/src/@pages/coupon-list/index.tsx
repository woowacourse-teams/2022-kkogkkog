import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link, useLocation } from 'react-router-dom';

import Icon from '@/@components/@shared/Icon';
import ListFilter from '@/@components/@shared/ListFilter';
import PageTemplate from '@/@components/@shared/PageTemplate';
import BigCouponItem from '@/@components/coupon/CouponItem/big';
import SmallCouponItem from '@/@components/coupon/CouponItem/small';
import HorizontalCouponList from '@/@components/coupon/CouponList/horizontal';
import VerticalCouponList from '@/@components/coupon/CouponList/vertical';
import CouponModal from '@/@components/coupon/CouponModal';
import { useStatus } from '@/@hooks/@common/useStatus';
import { useFetchCouponList } from '@/@hooks/@queries/coupon';
import useCouponModal from '@/@hooks/coupon/useCouponModal';
import { PATH } from '@/Router';
import theme from '@/styles/theme';
import { COUPON_LIST_TYPE } from '@/types/client/coupon';
import { CouponResponse } from '@/types/remote/response';

const filterOption = ['전체', '열린 약속', '잡은 약속', '지난 약속'] as const;

export type FilterOption = typeof filterOption[number];

const CouponListPage = () => {
  const couponListType: COUPON_LIST_TYPE =
    useLocation().pathname === PATH.SENT_COUPON_LIST ? 'sent' : 'received';

  const { parsedSentCouponList, parsedReceivedCouponList } = useFetchCouponList();

  const { status, changeStatus } = useStatus<FilterOption>('전체');

  const { currentCoupon, openCouponModal, closeCouponModal } = useCouponModal();

  const onClickFilterButton = (status: FilterOption) => {
    changeStatus(status);
  };

  const onClickCouponItem = (coupon: CouponResponse) => {
    openCouponModal(coupon);
  };

  const currentParsedCouponList =
    couponListType === 'sent' ? parsedSentCouponList : parsedReceivedCouponList;

  return (
    <PageTemplate title={couponListType === 'sent' ? '보낸 쿠폰' : '받은 쿠폰'}>
      <Styled.Root>
        <Styled.ListFilterContainer>
          <ListFilter<FilterOption>
            status={status}
            options={filterOption}
            onClickFilterButton={onClickFilterButton}
          />
        </Styled.ListFilterContainer>
        {currentParsedCouponList && (
          <>
            {status === '전체' && (
              <Styled.Container>
                <section>
                  <h2>열린 약속</h2>
                  <HorizontalCouponList
                    couponList={[
                      ...currentParsedCouponList['REQUESTED'],
                      ...currentParsedCouponList['READY'],
                    ]}
                    CouponItem={SmallCouponItem}
                    onClickCouponItem={onClickCouponItem}
                  />
                </section>
                <section>
                  <h2>잡은 약속</h2>
                  <HorizontalCouponList
                    couponList={currentParsedCouponList['ACCEPTED']}
                    CouponItem={SmallCouponItem}
                    onClickCouponItem={onClickCouponItem}
                  />
                </section>
                <section>
                  <h2>지난 약속</h2>
                  <HorizontalCouponList
                    couponList={currentParsedCouponList['FINISHED']}
                    CouponItem={SmallCouponItem}
                    onClickCouponItem={onClickCouponItem}
                  />
                </section>
              </Styled.Container>
            )}

            {status === '열린 약속' && (
              <Styled.Container>
                <VerticalCouponList
                  couponList={[
                    ...currentParsedCouponList['REQUESTED'],
                    ...currentParsedCouponList['READY'],
                  ]}
                  CouponItem={BigCouponItem}
                  onClickCouponItem={onClickCouponItem}
                />
              </Styled.Container>
            )}

            {status === '잡은 약속' && (
              <Styled.Container>
                <VerticalCouponList
                  couponList={currentParsedCouponList['ACCEPTED']}
                  CouponItem={BigCouponItem}
                  onClickCouponItem={onClickCouponItem}
                />
              </Styled.Container>
            )}

            {status === '지난 약속' && (
              <Styled.Container>
                <VerticalCouponList
                  couponList={currentParsedCouponList['FINISHED']}
                  CouponItem={BigCouponItem}
                  onClickCouponItem={onClickCouponItem}
                />
              </Styled.Container>
            )}
          </>
        )}

        {currentCoupon && <CouponModal coupon={currentCoupon} closeModal={closeCouponModal} />}
        <Styled.LinkInner>
          <Link to={PATH.COUPON_CREATE}>
            <Icon iconName='plus' size='37' color={theme.colors.primary_400} />
          </Link>
        </Styled.LinkInner>
      </Styled.Root>
    </PageTemplate>
  );
};

CouponListPage.Skeleton = function Skeleton() {
  return (
    <PageTemplate title='쿠폰 모아보기'>
      <Styled.Root>
        <VerticalCouponList.Skeleton CouponItemSkeleton={BigCouponItem.Skeleton} />
      </Styled.Root>
    </PageTemplate>
  );
};

export default CouponListPage;

export const Styled = {
  Root: styled.div`
    border-radius: 4px;

    & > div {
      margin-top: 20px;
    }
  `,
  ListFilterContainer: styled.div`
    padding: 0 20px;
  `,
  Container: styled.div`
    padding-bottom: 20px;
    margin-bottom: 10px;

    & > section {
      padding: 20px;
    }

    & > section:nth-of-type(2n) {
      background-color: #ffbb9415;
    }

    ${({ theme }) => css`
      & > section > h2 {
        color: ${theme.colors.grey_400};
        font-size: 18px;
        font-weight: 600;
      }
    `}
  `,
  LinkInner: styled.div`
    position: sticky;
    bottom: 0;
    right: 0;

    display: flex;
    justify-content: flex-end;

    & > a {
      position: relative;
      right: 16px;
      bottom: 16px;
    }
  `,
};

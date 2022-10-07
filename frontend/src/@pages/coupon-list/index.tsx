import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { Link, useLocation, useNavigate } from 'react-router-dom';

import Icon from '@/@components/@shared/Icon';
import ListFilter from '@/@components/@shared/ListFilter';
import PageTemplate from '@/@components/@shared/PageTemplate';
import BigCouponItem from '@/@components/coupon/CouponItem/big';
import VerticalCouponList from '@/@components/coupon/CouponList/vertical';
import AcceptedCouponListSection from '@/@components/coupon/CouponListSection/AcceptedCouponListSection';
import AllCouponListSection from '@/@components/coupon/CouponListSection/AllCouponListSection';
import FinishedCouponListSection from '@/@components/coupon/CouponListSection/FinishedCouponListSection';
import WaitingCouponListSection from '@/@components/coupon/CouponListSection/WaitingCouponListSection';
import { useStatus } from '@/@hooks/@common/useStatus';
import { DYNAMIC_PATH, PATH } from '@/Router';
import { filterOptionsSessionStorage } from '@/storage/session';
import theme from '@/styles/theme';
import { Coupon, COUPON_LIST_TYPE } from '@/types/coupon/client';

const filterOption = ['전체', '대기', '확정', '완료'] as const;

export type FilterOption = typeof filterOption[number];

const CouponListPage = () => {
  const navigate = useNavigate();

  const couponListType: COUPON_LIST_TYPE =
    useLocation().pathname === PATH.SENT_COUPON_LIST ? 'sent' : 'received';

  // 항상 전체가 기본값으로 들어가야 하는가?
  const { status, changeStatus } = useStatus<FilterOption>(
    filterOptionsSessionStorage.get() ?? '전체'
  );

  const onClickFilterButton = (status: FilterOption) => {
    changeStatus(status);
    filterOptionsSessionStorage.set(status);
  };

  const onClickCouponItem = (coupon: Coupon) => {
    navigate(DYNAMIC_PATH.COUPON_DETAIL(coupon.id));
  };

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
        <Styled.Container>
          {status === '전체' && (
            <AllCouponListSection
              couponListType={couponListType}
              onClickCouponItem={onClickCouponItem}
            />
          )}
          {status === '대기' && (
            <WaitingCouponListSection
              couponListType={couponListType}
              onClickCouponItem={onClickCouponItem}
            />
          )}
          {status === '확정' && (
            <AcceptedCouponListSection
              couponListType={couponListType}
              onClickCouponItem={onClickCouponItem}
            />
          )}
          {status === '완료' && (
            <FinishedCouponListSection
              couponListType={couponListType}
              onClickCouponItem={onClickCouponItem}
            />
          )}
        </Styled.Container>
        <Styled.LinkInner>
          <Link to={PATH.COUPON_CREATE}>
            <Icon iconName='plus' size='37' color={theme.colors.primary_400} />
          </Link>
        </Styled.LinkInner>
      </Styled.Root>
    </PageTemplate>
  );
};

export const CouponListPageFallback = () => {
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
    padding: 20px 0;
  `,
  ListFilterContainer: styled.div`
    padding: 0 20px;
  `,
  Container: styled.div`
    margin-bottom: 10px;
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
  VerticalListContainer: styled.div`
    padding: 20px 10px;
  `,
  HorizonListContainer: styled.div`
    & > section {
      padding: 30px 20px 10px;
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
};

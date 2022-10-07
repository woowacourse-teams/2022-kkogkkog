import { Suspense } from 'react';
import { useNavigate } from 'react-router-dom';

import ListFilter from '@/@components/@shared/ListFilter';
import PageTemplate from '@/@components/@shared/PageTemplate';
import VerticalCouponList from '@/@components/coupon/CouponList/vertical';
import ExpiredCouponListSection from '@/@components/unregistered-coupon/ExpiredCouponListSection';
import RegisteredCouponListSection from '@/@components/unregistered-coupon/RegisteredCouponListSection';
import UnregisteredCouponItem from '@/@components/unregistered-coupon/UnregisteredCouponItem';
import UnregisteredCouponListSection from '@/@components/unregistered-coupon/UnregisteredCouponListSection';
import { useStatus } from '@/@hooks/@common/useStatus';
import { unregisteredFilterOptionsSessionStorage } from '@/storage/session';

import * as Styled from './style';

const unregisteredFilterOption = ['미등록', '등록됨', '만료'] as const;

export type UnregisteredFilterOption = typeof unregisteredFilterOption[number];

const UnregisteredCouponList = () => {
  const navigate = useNavigate();

  const { status, changeStatus } = useStatus<UnregisteredFilterOption>(
    unregisteredFilterOptionsSessionStorage.get() ?? '미등록'
  );

  const onClickFilterButton = (status: UnregisteredFilterOption) => {
    changeStatus(status);
    unregisteredFilterOptionsSessionStorage.set(status);
  };

  const onClickUnregisteredCouponItem = () => {
    // 미등록 쿠폰 으로 라우팅
  };

  const onClickRegisteredCouponItem = () => {
    // 쿠폰 상세 페이지로 라우팅
  };

  const onClickExpiredCouponItem = () => {
    // 반응하지 않음.
  };

  return (
    <PageTemplate title='미등록 쿠폰'>
      <Styled.Root>
        <Styled.ListFilterContainer>
          <ListFilter<UnregisteredFilterOption>
            status={status}
            options={unregisteredFilterOption}
            onClickFilterButton={onClickFilterButton}
          />
        </Styled.ListFilterContainer>
        <Suspense fallback={<CouponListPageFallback />}>
          <Styled.Container>
            {status === '미등록' && (
              <UnregisteredCouponListSection onClickCouponItem={onClickUnregisteredCouponItem} />
            )}
            {status === '등록됨' && (
              <RegisteredCouponListSection onClickCouponItem={onClickRegisteredCouponItem} />
            )}
            {status === '만료' && (
              <ExpiredCouponListSection onClickCouponItem={onClickExpiredCouponItem} />
            )}
          </Styled.Container>
        </Suspense>
      </Styled.Root>
    </PageTemplate>
  );
};

export default UnregisteredCouponList;

export const CouponListPageFallback = () => {
  return (
    <Styled.Root>
      <VerticalCouponList.Skeleton CouponItemSkeleton={UnregisteredCouponItem.Skeleton} />
    </Styled.Root>
  );
};

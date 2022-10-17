import { Suspense } from 'react';
import { useNavigate } from 'react-router-dom';

import ListFilter from '@/@components/@shared/ListFilter';
import PageTemplate from '@/@components/@shared/PageTemplate';
import VerticalCouponList from '@/@components/coupon/CouponList/vertical';
import UnregisteredCouponItem from '@/@components/unregistered-coupon/UnregisteredCouponItem';
import ExpiredCouponListSection from '@/@components/unregistered-coupon/UnregisteredCouponListSection/ExpiredCouponListSection';
import RegisteredCouponListSection from '@/@components/unregistered-coupon/UnregisteredCouponListSection/RegisteredCouponListSection';
import UnregisteredCouponListSection from '@/@components/unregistered-coupon/UnregisteredCouponListSection/UnregisteredCouponListSection';
import { useStatus } from '@/@hooks/@common/useStatus';
import { DYNAMIC_PATH } from '@/Router';
import { unregisteredFilterOptionsSessionStorage } from '@/storage/session';
import { UnregisteredCouponResponse } from '@/types/unregistered-coupon/remote';

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

  const onClickUnregisteredCouponItem = ({ id }: UnregisteredCouponResponse) => {
    navigate(DYNAMIC_PATH.UNREGISTERED_COUPON_DETAIL(id));
  };

  const onClickRegisteredCouponItem = ({ couponId }: UnregisteredCouponResponse) => {
    if (couponId === null) {
      return;
    }

    navigate(DYNAMIC_PATH.COUPON_DECLINE(couponId));
  };

  const onClickExpiredCouponItem = () => {
    // @TODO: 만료 쿠폰은 어떤 동작을 해야할까
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

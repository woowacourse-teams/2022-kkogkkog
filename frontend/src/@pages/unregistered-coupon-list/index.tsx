import { Suspense } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import Icon from '@/@components/@shared/Icon';
import ListFilter from '@/@components/@shared/ListFilter';
import PageTemplate from '@/@components/@shared/PageTemplate';
import VerticalCouponList from '@/@components/coupon/CouponList/vertical';
import UnregisteredCouponItem from '@/@components/unregistered-coupon/UnregisteredCouponItem';
import ExpiredCouponListSection from '@/@components/unregistered-coupon/UnregisteredCouponListSection/ExpiredCouponListSection';
import RegisteredCouponListSection from '@/@components/unregistered-coupon/UnregisteredCouponListSection/RegisteredCouponListSection';
import UnregisteredCouponListSection from '@/@components/unregistered-coupon/UnregisteredCouponListSection/UnregisteredCouponListSection';
import { useStatus } from '@/@hooks/@common/useStatus';
import { DYNAMIC_PATH, PATH } from '@/Router';
import { unregisteredFilterOptionsSessionStorage } from '@/storage/session';
import theme from '@/styles/theme';
import { UnregisteredCoupon } from '@/types/unregistered-coupon/client';

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

  const onClickUnregisteredCouponItem = ({ id }: UnregisteredCoupon) => {
    navigate(DYNAMIC_PATH.UNREGISTERED_COUPON_DETAIL(id));
  };

  const onClickRegisteredCouponItem = ({ couponId }: UnregisteredCoupon) => {
    if (couponId === null) {
      return;
    }

    navigate(DYNAMIC_PATH.COUPON_DETAIL(couponId));
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
        <Styled.LinkInner>
          <Link to={PATH.UNREGISTERED_COUPON_CREATE} css={Styled.ExtendedLink}>
            <Icon iconName='plus' size='37' color={theme.colors.primary_400} />
          </Link>
        </Styled.LinkInner>
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

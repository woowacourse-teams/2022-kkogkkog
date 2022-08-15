import { FunctionComponent } from 'react';

import { CouponResponse } from '@/types/remote/response';

import { BigCouponItemProps } from '../CouponItem/big';
import * as Styled from './vertical.style';

interface VerticalCouponListProps {
  couponList?: CouponResponse[];
  CouponItem: FunctionComponent<BigCouponItemProps>;
  onClickCouponItem?: (coupon: CouponResponse) => void;
}

const VerticalCouponList = (props: VerticalCouponListProps) => {
  const { couponList, onClickCouponItem, CouponItem } = props;

  if (couponList?.length === 0) {
    return (
      <Styled.Root>
        <Styled.TextContainer>해당 쿠폰이 없습니다.</Styled.TextContainer>
      </Styled.Root>
    );
  }

  return (
    <Styled.Root>
      {couponList?.map(coupon => (
        <CouponItem key={coupon.couponId} onClick={() => onClickCouponItem?.(coupon)} {...coupon} />
      ))}
    </Styled.Root>
  );
};

export default VerticalCouponList;

interface VerticalCouponListSkeletonProps {
  CouponItemSkeleton: FunctionComponent;
}

VerticalCouponList.Skeleton = function Skeleton(props: VerticalCouponListSkeletonProps) {
  const { CouponItemSkeleton } = props;

  return (
    <Styled.Root>
      <CouponItemSkeleton />
      <CouponItemSkeleton />
      <CouponItemSkeleton />
      <CouponItemSkeleton />
    </Styled.Root>
  );
};

import React from 'react';

import { SmallCouponItemProps } from '@/@components/coupon/CouponItem/small';
import { CouponResponse } from '@/types/remote/response';

import * as Styled from './horizontal.style';

interface HorizontalCouponListProps {
  couponList?: CouponResponse[];
  CouponItem: React.FunctionComponent<SmallCouponItemProps>;
  onClickCouponItem?: (coupon: CouponResponse) => void;
}

const HorizontalCouponList = (props: HorizontalCouponListProps) => {
  const { couponList, CouponItem, onClickCouponItem } = props;

  if (couponList?.length === 0) {
    return (
      <Styled.SlideRoot>
        <Styled.TextContainer>
          <div>
            <img
              src='/assets/images/skeleton_coupon_small.png'
              alt='쿠폰'
              width={80}
              height={97.94}
            />
          </div>
          <h2>아직 쿠폰이 존재하지 않아요.</h2>
          <h3>쿠폰을 생성해보세요!</h3>
        </Styled.TextContainer>
      </Styled.SlideRoot>
    );
  }

  return (
    <Styled.SlideRoot>
      {couponList?.map(coupon => (
        <CouponItem key={coupon.couponId} onClick={() => onClickCouponItem?.(coupon)} {...coupon} />
      ))}
    </Styled.SlideRoot>
  );
};

export default HorizontalCouponList;

interface SkeletonHorizontalCouponListProps {
  CouponItemSkeleton: React.FunctionComponent;
}

HorizontalCouponList.Skeleton = function Skeleton(props: SkeletonHorizontalCouponListProps) {
  const { CouponItemSkeleton } = props;

  return (
    <Styled.SlideRoot>
      <CouponItemSkeleton />
      <CouponItemSkeleton />
      <CouponItemSkeleton />
      <CouponItemSkeleton />
    </Styled.SlideRoot>
  );
};

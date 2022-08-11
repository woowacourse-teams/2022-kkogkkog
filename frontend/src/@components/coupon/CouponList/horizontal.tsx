import React from 'react';
import { useNavigate } from 'react-router-dom';

import { SmallCouponItemProps } from '@/@components/coupon/CouponItem/small';
import { CouponResponse } from '@/types/remote/response';

import * as Styled from './horizontal.style';

interface HorizontalCouponListProps {
  couponList?: CouponResponse[];
  CouponItem: React.FunctionComponent<SmallCouponItemProps>;
}

const HorizontalCouponList = (props: HorizontalCouponListProps) => {
  const { couponList, CouponItem } = props;
  const navigate = useNavigate();

  if (couponList?.length === 0) {
    return (
      <Styled.SlideRoot>
        <Styled.TextContainer>해당 쿠폰이 없습니다.</Styled.TextContainer>
      </Styled.SlideRoot>
    );
  }

  return (
    <Styled.SlideRoot>
      {couponList?.map(coupon => (
        <CouponItem
          key={coupon.id}
          onClick={() => {
            navigate(`/coupon-list/${coupon.id}`);
          }}
          {...coupon}
        />
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

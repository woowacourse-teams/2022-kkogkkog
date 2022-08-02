import React from 'react';

import { SmallCouponItemProps } from '@/@components/coupon/CouponItem/small';
import { CouponResponse } from '@/types/remote/response';

import * as Styled from './horizontal.style';

interface HorizontalCouponListProps {
  couponList?: CouponResponse[];
  onClickCouponItem?: (coupon: CouponResponse) => void;
  CouponItem: React.FunctionComponent<SmallCouponItemProps>;
}

const HorizontalCouponList = (props: HorizontalCouponListProps) => {
  const { couponList, onClickCouponItem, CouponItem } = props;

  if (couponList?.length === 0) {
    return (
      <Styled.SlideRoot>
        <Styled.TextContainer fontSize='40px'>😱</Styled.TextContainer>
        <Styled.TextContainer>해당 꼭꼭이 존재하지 않아요 ㅠㅠ</Styled.TextContainer>
      </Styled.SlideRoot>
    );
  }

  return (
    <Styled.SlideRoot>
      {couponList?.map(coupon => (
        <CouponItem key={coupon.id} onClick={() => onClickCouponItem?.(coupon)} {...coupon} />
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

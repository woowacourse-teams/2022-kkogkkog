import { MouseEventHandler } from 'react';

import Placeholder from '@/@components/@shared/Placeholder';
import CouponStatus from '@/@components/coupon/CouponStatus';
import { THUMBNAIL } from '@/types/client/coupon';
import { CouponResponse } from '@/types/remote/response';

import * as Styled from './small.style';

export interface SmallCouponItemProps extends CouponResponse {
  className?: string;
  onClick?: MouseEventHandler<HTMLDivElement>;
}

const SmallCouponItem = (props: SmallCouponItemProps) => {
  const { className, onClick, ...coupon } = props;

  const { memberType, nickname, thumbnail, couponStatus, meetingDate } = {
    ...coupon,
    thumbnail: THUMBNAIL[coupon.couponType],
  };

  const isSent = memberType === 'SENT';

  return (
    <Styled.Root hasCursor={!!onClick} onClick={onClick}>
      <CouponStatus status={couponStatus} meetingDate={meetingDate} isSent={isSent} />

      <img src={thumbnail} alt='쿠폰' width='50px' />

      <Styled.TextContainer>
        <Styled.Preposition>{isSent ? 'To.' : 'From.'} </Styled.Preposition>
        {nickname}
      </Styled.TextContainer>
    </Styled.Root>
  );
};

export default SmallCouponItem;

SmallCouponItem.Skeleton = function Skeleton() {
  return <Placeholder width='100px' height='200px' />;
};

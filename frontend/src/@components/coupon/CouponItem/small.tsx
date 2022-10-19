import { MouseEventHandler } from 'react';

import Placeholder from '@/@components/@shared/Placeholder';
import CouponStatus from '@/@components/coupon/CouponStatus';
import useCouponPartner from '@/@hooks/ui/coupon/useCouponPartner';
import { Coupon, THUMBNAIL } from '@/types/coupon/client';

import * as Styled from './small.style';

export interface SmallCouponItemProps extends Coupon {
  className?: string;
  onClick?: MouseEventHandler<HTMLDivElement>;
}

const SmallCouponItem = (props: SmallCouponItemProps) => {
  const { className, onClick, ...coupon } = props;

  const { thumbnail, couponStatus, meetingDate } = {
    ...coupon,
    thumbnail: THUMBNAIL[coupon.couponType],
  };

  const { isSent, member } = useCouponPartner(coupon);

  return (
    <Styled.Root as={onClick && 'button'} onClick={onClick}>
      <CouponStatus status={couponStatus} meetingDate={meetingDate} isSent={isSent} />

      <img src={thumbnail.src} alt={thumbnail.alt} width={50} height={50} />

      <Styled.TextContainer>
        <Styled.Preposition>{isSent ? 'To.' : 'From.'} </Styled.Preposition>
        {member.nickname}
      </Styled.TextContainer>
    </Styled.Root>
  );
};

export default SmallCouponItem;

SmallCouponItem.Skeleton = function Skeleton() {
  return <Placeholder width='140px' height='132px' />;
};

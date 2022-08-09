import { MouseEventHandler } from 'react';

import Placeholder from '@/@components/@shared/Placeholder';
import CouponStatus from '@/@components/coupon/CouponStatus';
import { useFetchMe } from '@/@hooks/@queries/user';
import { THUMBNAIL } from '@/types/client/coupon';
import { CouponResponse } from '@/types/remote/response';

import * as Styled from './small.style';

export interface SmallCouponItemProps extends CouponResponse {
  className?: string;
  onClick?: MouseEventHandler<HTMLDivElement>;
}

const SmallCouponItem = (props: SmallCouponItemProps) => {
  const { className, onClick, ...coupon } = props;

  const { sender, receiver, thumbnail, couponStatus } = {
    ...coupon,
    thumbnail: THUMBNAIL[coupon.couponType],
  };

  const { me } = useFetchMe();

  const isSent = me?.id === sender.id;

  return (
    <Styled.Root hasCursor={!!onClick} onClick={onClick}>
      <CouponStatus status={couponStatus} isSent={isSent} />

      <img src={thumbnail} alt='쿠폰' width='50px' />

      {sender.id === me?.id ? (
        <Styled.TextContainer>
          <Styled.Preposition>To. </Styled.Preposition>
          {receiver.nickname}
        </Styled.TextContainer>
      ) : (
        <Styled.TextContainer>
          <Styled.Preposition>From. </Styled.Preposition>
          {sender.nickname}
        </Styled.TextContainer>
      )}
    </Styled.Root>
  );
};

export default SmallCouponItem;

SmallCouponItem.Skeleton = function Skeleton() {
  return <Placeholder width='100px' height='200px' />;
};

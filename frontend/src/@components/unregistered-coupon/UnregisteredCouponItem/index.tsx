import { MouseEventHandler } from 'react';

import UnregisteredCouponStatus from '@/@components/unregistered-coupon/UnregisteredCouponStatus';
import { THUMBNAIL } from '@/types/coupon/client';
import { UnregisteredCoupon } from '@/types/unregistered-coupon/client';

import * as Styled from './style';

export interface UnregisteredCouponItemProps extends UnregisteredCoupon {
  className?: string;
  onClick?: MouseEventHandler<HTMLDivElement>;
}

const UnregisteredCouponItem = (props: UnregisteredCouponItemProps) => {
  const { className, onClick, ...coupon } = props;

  const { receiver, couponTag, unregisteredCouponStatus, couponMessage, thumbnail } = {
    ...coupon,
    thumbnail: THUMBNAIL[coupon.couponType],
  };

  return (
    <Styled.Root className={className} hasCursor={!!onClick} onClick={onClick}>
      <Styled.CouponPropertyContainer>
        <UnregisteredCouponStatus status={unregisteredCouponStatus} />

        <Styled.ImageInner>
          <img src={thumbnail} alt='쿠폰' width={44} height={44} />
        </Styled.ImageInner>
      </Styled.CouponPropertyContainer>
      <Styled.TextContainer>
        <Styled.Top>
          <Styled.Member>
            <Styled.English>To</Styled.English> {receiver?.nickname ?? '?'}
          </Styled.Member>
        </Styled.Top>
        <Styled.Message>{couponMessage}</Styled.Message>
        <Styled.Hashtag>#{couponTag}</Styled.Hashtag>
      </Styled.TextContainer>
    </Styled.Root>
  );
};

export default UnregisteredCouponItem;

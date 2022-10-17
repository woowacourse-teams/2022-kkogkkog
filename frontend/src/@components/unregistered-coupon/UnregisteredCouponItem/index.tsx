import { MouseEvent, MouseEventHandler } from 'react';

import Icon from '@/@components/@shared/Icon';
import Placeholder from '@/@components/@shared/Placeholder';
import UnregisteredCouponStatus from '@/@components/unregistered-coupon/UnregisteredCouponStatus';
import { useToast } from '@/@hooks/@common/useToast';
import { DYNAMIC_PATH } from '@/Router';
import { THUMBNAIL } from '@/types/coupon/client';
import { UnregisteredCouponResponse } from '@/types/unregistered-coupon/remote';
import clipboardCopy from '@/utils/clipboardCopy';

import * as Styled from './style';

export interface UnregisteredCouponItemProps extends UnregisteredCouponResponse {
  className?: string;
  onClick?: MouseEventHandler<HTMLDivElement>;
}

const UnregisteredCouponItem = (props: UnregisteredCouponItemProps) => {
  const { className, onClick, ...coupon } = props;
  const { displayMessage } = useToast();

  const { receiver, couponTag, unregisteredCouponStatus, couponMessage, thumbnail, couponCode } = {
    ...coupon,
    thumbnail: THUMBNAIL[coupon.couponType],
  };

  const copyUrl = (e: MouseEvent<HTMLButtonElement>) => {
    try {
      clipboardCopy(
        `${window.location.origin}${DYNAMIC_PATH.UNREGISTERED_COUPON_REGISTER(couponCode)}`
      );
      displayMessage('링크가 복사되었어요!', false);
    } catch (e) {
      displayMessage('링크 복사에 실패했어요', true);
    }
  };

  return (
    <Styled.Root>
      <Styled.Coupon className={className} hasCursor={!!onClick} onClick={onClick}>
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
      </Styled.Coupon>

      {unregisteredCouponStatus === 'ISSUED' && (
        <Styled.CopyButton type='button' onClick={copyUrl}>
          <Icon iconName='copy' />
        </Styled.CopyButton>
      )}
    </Styled.Root>
  );
};

export default UnregisteredCouponItem;

UnregisteredCouponItem.Skeleton = function Skeleton() {
  return <Placeholder aspectRatio='3/1' />;
};

UnregisteredCouponItem.Preview = function UnregisteredCouponItem(
  props: UnregisteredCouponItemProps
) {
  const { ...coupon } = props;

  const { receiver, couponTag, couponMessage, thumbnail } = {
    ...coupon,
    thumbnail: THUMBNAIL[coupon.couponType],
  };

  return (
    <Styled.Root>
      <Styled.Coupon hasCursor={false}>
        <Styled.CouponPropertyContainer>
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
      </Styled.Coupon>
    </Styled.Root>
  );
};

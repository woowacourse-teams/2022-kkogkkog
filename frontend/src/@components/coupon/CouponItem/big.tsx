import { MouseEventHandler } from 'react';

import Placeholder from '@/@components/@shared/Placeholder';
import CouponStatus from '@/@components/coupon/CouponStatus';
import { THUMBNAIL } from '@/types/client/coupon';
import { CouponResponse } from '@/types/remote/response';
import { MakeOptional } from '@/types/utils';

import * as Styled from './big.style';

export interface BigCouponItemProps
  extends MakeOptional<CouponResponse, 'couponId' | 'reservationId' | 'message' | 'imageUrl'> {
  className?: string;
  onClick?: MouseEventHandler<HTMLDivElement>;
}

const BigCouponItem = (props: BigCouponItemProps) => {
  const { className, onClick, ...coupon } = props;

  const { memberType, nickname, hashtag, couponStatus, description, meetingDate, thumbnail } = {
    ...coupon,
    thumbnail: THUMBNAIL[coupon.couponType],
  };

  const isSent = memberType === 'SENT';

  return (
    <Styled.Root className={className} hasCursor={!!onClick} onClick={onClick}>
      <Styled.CouponPropertyContainer>
        <CouponStatus status={couponStatus} meetingDate={meetingDate} isSent={isSent} />

        <Styled.ImageInner>
          <img src={thumbnail} alt='쿠폰' />
        </Styled.ImageInner>
      </Styled.CouponPropertyContainer>
      <Styled.TextContainer>
        <Styled.Top>
          <Styled.Member>
            <Styled.English>{isSent ? 'To.' : 'From.'}</Styled.English> {nickname}
          </Styled.Member>
        </Styled.Top>
        <Styled.Message>{description}</Styled.Message>
        <Styled.Hashtag>#{hashtag}</Styled.Hashtag>
      </Styled.TextContainer>
    </Styled.Root>
  );
};

type BigCouponItemPreviewProps = Omit<
  CouponResponse,
  'couponId' | 'memberId' | 'reservationId' | 'couponStatus' | 'message'
> & {
  className?: string;
};

/* UI에서 보이지 않는 id, ,sender, couponStatus, onClick를 제외한 props만 받는 프로토타입 컴포넌트 */
BigCouponItem.Preview = function Preview(props: BigCouponItemPreviewProps) {
  const { className, nickname, hashtag, thumbnail, description } = {
    ...props,
    thumbnail: THUMBNAIL[props.couponType],
  };

  return (
    <Styled.Root className={className} hasCursor={false}>
      <Styled.ImageContainer>
        <Styled.ImageInner>
          <img src={thumbnail} alt='쿠폰' />
        </Styled.ImageInner>
      </Styled.ImageContainer>
      <Styled.TextContainer>
        <Styled.Member>
          <Styled.English>To.</Styled.English> {nickname}
        </Styled.Member>
        <Styled.Message>{description}</Styled.Message>
        <Styled.Hashtag>#{hashtag}</Styled.Hashtag>
      </Styled.TextContainer>
    </Styled.Root>
  );
};

BigCouponItem.Skeleton = function Skeleton() {
  return <Placeholder aspectRatio='3/1' />;
};

export default BigCouponItem;

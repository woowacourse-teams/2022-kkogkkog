import { MouseEventHandler } from 'react';

import Placeholder from '@/@components/@shared/Placeholder';
import CouponStatus from '@/@components/coupon/CouponStatus';
import useCouponPartner from '@/@hooks/ui/coupon/useCouponPartner';
import { Coupon, THUMBNAIL } from '@/types/coupon/client';

import * as Styled from './big.style';

export interface BigCouponItemProps extends Coupon {
  className?: string;
  onClick?: MouseEventHandler<HTMLDivElement>;
}

const BigCouponItem = (props: BigCouponItemProps) => {
  const { className, onClick, ...coupon } = props;

  const { couponTag, couponStatus, couponMessage, meetingDate, thumbnail } = {
    ...coupon,
    thumbnail: THUMBNAIL[coupon.couponType],
  };

  const { isSent, member } = useCouponPartner(coupon);

  const rootElementTag = onClick && 'button';

  return (
    <Styled.Root as={rootElementTag} className={className} onClick={onClick}>
      <Styled.CouponPropertyContainer>
        <CouponStatus status={couponStatus} meetingDate={meetingDate} isSent={isSent} />

        <Styled.ImageInner>
          <img src={thumbnail.src} alt={thumbnail.alt} width={44} height={44} />
        </Styled.ImageInner>
      </Styled.CouponPropertyContainer>
      <Styled.TextContainer>
        <Styled.Top>
          <Styled.Member>
            <Styled.English>{isSent ? 'To.' : 'From.'}</Styled.English> {member.nickname}
          </Styled.Member>
        </Styled.Top>
        <Styled.Message>{couponMessage}</Styled.Message>
        <Styled.Hashtag aria-label={`hashtag ${couponTag}`}>#{couponTag}</Styled.Hashtag>
      </Styled.TextContainer>
    </Styled.Root>
  );
};

interface BigCouponItemPreviewProps
  extends Pick<Coupon, 'receiver' | 'couponTag' | 'couponMessage' | 'couponType'> {
  className?: string;
}

/* UI에서 보이지 않는 id, ,sender, couponStatus, onClick를 제외한 props만 받는 프로토타입 컴포넌트 */
BigCouponItem.Preview = function Preview(props: BigCouponItemPreviewProps) {
  const { className, receiver, couponTag, thumbnail, couponMessage } = {
    ...props,
    thumbnail: THUMBNAIL[props.couponType],
  };

  return (
    <Styled.Root className={className}>
      <Styled.ImageContainer>
        <Styled.ImageInner>
          <img src={thumbnail.src} alt='쿠폰' width={44} height={44} />
        </Styled.ImageInner>
      </Styled.ImageContainer>
      <Styled.TextContainer>
        <Styled.Member>
          <Styled.English>To.</Styled.English> {receiver.nickname}
        </Styled.Member>
        <Styled.Message>{couponMessage}</Styled.Message>
        <Styled.Hashtag>#{couponTag}</Styled.Hashtag>
      </Styled.TextContainer>
    </Styled.Root>
  );
};

BigCouponItem.Skeleton = function Skeleton() {
  return <Placeholder width='100%' maxWidth='340px' height='120px' />;
};

export default BigCouponItem;

import { MouseEventHandler } from 'react';

import Placeholder from '@/@components/@shared/Placeholder';
import CouponStatus from '@/@components/coupon/CouponStatus';
import { useFetchMe } from '@/@hooks/@queries/user';
import { THUMBNAIL } from '@/types/client/coupon';
import { CouponResponse } from '@/types/remote/response';
import { generateDateText } from '@/utils';

import * as Styled from './big.style';

export type BigCouponItemProps = CouponResponse & {
  className?: string;
  onClick?: MouseEventHandler<HTMLDivElement>;
};

type BigCouponItemPreviewProps = Omit<CouponResponse, 'id' | 'couponStatus' | 'sender'> & {
  className?: string;
};

const BigCouponItem = (props: BigCouponItemProps) => {
  const { className, onClick, ...coupon } = props;

  const {
    sender,
    receiver,
    backgroundColor,
    modifier,
    couponStatus,
    message,
    meetingDate,
    thumbnail,
  } = {
    ...coupon,
    thumbnail: THUMBNAIL[coupon.couponType],
  };

  const { me } = useFetchMe();

  const meetingDateText = generateDateText(meetingDate);

  return (
    <Styled.Root className={className} hasCursor={!!onClick} onClick={onClick}>
      <Styled.CouponPropertyContainer>
        <CouponStatus status={couponStatus} />

        <Styled.ImageInner backgroundColor={backgroundColor}>
          <img src={thumbnail} alt='쿠폰' />
        </Styled.ImageInner>
      </Styled.CouponPropertyContainer>
      <Styled.TextContainer>
        <Styled.Top>
          {sender.id === me?.id ? (
            <Styled.Member>
              <Styled.English>To.</Styled.English> {receiver.nickname}
            </Styled.Member>
          ) : (
            <Styled.Member>
              <Styled.English>From.</Styled.English> {sender.nickname}
            </Styled.Member>
          )}
          {meetingDate && (
            <Styled.MeetingDate couponStatus={couponStatus}>
              {meetingDateText} 약속 {couponStatus === 'REQUESTED' && '신청됨'}
            </Styled.MeetingDate>
          )}
        </Styled.Top>
        <Styled.Message>{message}</Styled.Message>
        <Styled.Modifier>#{modifier}</Styled.Modifier>
      </Styled.TextContainer>
    </Styled.Root>
  );
};

/* UI에서 보이지 않는 id, ,sender, couponStatus, onClick를 제외한 props만 받는 프로토타입 컴포넌트 */
BigCouponItem.Preview = function Preview(props: BigCouponItemPreviewProps) {
  const { className, receiver, backgroundColor, modifier, thumbnail, message } = {
    ...props,
    thumbnail: THUMBNAIL[props.couponType],
  };

  return (
    <Styled.Root className={className} hasCursor={false}>
      <Styled.ImageContainer>
        <Styled.ImageInner backgroundColor={backgroundColor}>
          <img src={thumbnail} alt='쿠폰' />
        </Styled.ImageInner>
      </Styled.ImageContainer>
      <Styled.TextContainer>
        <Styled.Member>
          <Styled.English>To.</Styled.English> {receiver.nickname}
        </Styled.Member>
        <Styled.Message>{message}</Styled.Message>
        <Styled.Modifier>#{modifier}</Styled.Modifier>
      </Styled.TextContainer>
    </Styled.Root>
  );
};

BigCouponItem.Skeleton = function Skeleton() {
  return <Placeholder aspectRatio='3/1' />;
};

export default BigCouponItem;

import { MouseEventHandler } from 'react';

import Placeholder from '@/@components/@shared/Placeholder';
import CouponStatus from '@/@components/kkogkkog/CouponStatus';
import { useMe } from '@/@hooks/@queries/user';
import { THUMBNAIL } from '@/types/client/kkogkkog';
import { KkogKKogResponse } from '@/types/remote/response';

import * as Styled from './small.style';

export type SmallCouponItemProps = KkogKKogResponse & {
  className?: string;
  onClick?: MouseEventHandler<HTMLDivElement>;
};

const SmallCouponItem = (props: SmallCouponItemProps) => {
  const { className, onClick, ...kkogkkog } = props;

  const { sender, receiver, thumbnail, couponStatus } = {
    ...kkogkkog,
    thumbnail: THUMBNAIL[kkogkkog.couponType],
  };

  const { data: me } = useMe();

  return (
    <Styled.Root hasCursor={!!onClick} onClick={onClick}>
      <CouponStatus status={couponStatus} />

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
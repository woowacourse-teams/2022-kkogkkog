import { MouseEventHandler } from 'react';

import Placeholder from '@/@components/@shared/Placeholder';
import useMe from '@/@hooks/user/useMe';
import { THUMBNAIL } from '@/types/client/kkogkkog';
import { KkogKKogResponse } from '@/types/remote/response';

import * as Styled from './small.style';

export type SmallCouponItemProps = KkogKKogResponse & {
  className?: string;
  onClick?: MouseEventHandler<HTMLDivElement>;
};

const statusMapper = {
  REQUESTED: '요청중',
  FINISHED: '완료됨',
  READY: '대기중',
  ACCEPTED: '승인됨',
};

const SmallCouponItem = (props: SmallCouponItemProps) => {
  const { className, onClick, ...kkogkkog } = props;

  const { sender, receiver, thumbnail, couponStatus } = {
    ...kkogkkog,
    thumbnail: THUMBNAIL[kkogkkog.couponType],
  };

  const { me } = useMe();

  return (
    <Styled.Root hasCursor={!!onClick}>
      <Styled.StatusContainer couponStatus={couponStatus}>
        {statusMapper[couponStatus]}
      </Styled.StatusContainer>

      <img src={thumbnail} alt='쿠폰' />

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

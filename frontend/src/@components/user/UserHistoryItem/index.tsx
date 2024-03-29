import { MouseEventHandler } from 'react';

import { couponEventTextMapper, couponTypeTextMapper } from '@/constants/coupon';
import { UserCouponHistory } from '@/types/user/client';
import { generateDateKR } from '@/utils/time';

import * as Styled from './style';

interface UserHistoryItemProps {
  history: UserCouponHistory;
  onClick: MouseEventHandler<HTMLLIElement>;
}

const UserHistoryItem = (props: UserHistoryItemProps) => {
  const { history, onClick } = props;
  const { createdTime, couponEvent, couponType, isRead, imageUrl, nickname } = history;

  const createdTimeText = generateDateKR(createdTime, true);

  return (
    <Styled.Root isRead={isRead} onClick={onClick}>
      <Styled.ProfileImageContainer>
        <img src={imageUrl} alt='프로필' width={50} height={50} />
      </Styled.ProfileImageContainer>
      <Styled.InformationContainer>
        <Styled.Contents>
          {nickname}님이 {couponTypeTextMapper[couponType]}&nbsp;
          {couponEventTextMapper[couponEvent]}
        </Styled.Contents>
        <Styled.Date>{createdTimeText}</Styled.Date>
      </Styled.InformationContainer>
    </Styled.Root>
  );
};

export default UserHistoryItem;

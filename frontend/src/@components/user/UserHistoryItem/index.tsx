import { MouseEventHandler } from 'react';

import { couponEventTextMapper, couponTypeTextMapper } from '@/constants/coupon';
import { UserHistory } from '@/types/client/user';
import { generateDateText } from '@/utils/time';

import * as Styled from './style';

interface UserHistoryItemProps {
  history: UserHistory;
  onClick: MouseEventHandler<HTMLDivElement>;
}

const UserHistoryItem = (props: UserHistoryItemProps) => {
  const { history, onClick } = props;
  const { imageUrl, createdTime, nickname, couponEvent, couponType, isRead } = history;

  const createdTimeText = generateDateText(createdTime, true);

  return (
    <Styled.Root isRead={isRead} onClick={onClick}>
      <Styled.ProfileImageContainer>
        <img src={imageUrl} alt='프로필 이미지' />
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

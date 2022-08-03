import { MouseEventHandler } from 'react';

import { UserHistory } from '@/types/client/user';
import { extractDate } from '@/utils';

import * as Styled from './style';

interface UserHistoryItemProps {
  history: UserHistory;
  onClick: MouseEventHandler<HTMLDivElement>;
}

const couponTypeTextMapper = {
  COFFEE: '커피',
  DRINK: '맥주',
  MEAL: '식사',
};

const couponEventTextMapper = {
  INIT: '쿠폰을 보냈어요.',
  REQUEST: '쿠폰 사용을 요청했어요.',
  CANCEL: '쿠폰 사용을 취소했어요.',
  DECLINE: '쿠폰 사용을 거절했어요.',
  ACCEPT: '쿠폰 사용을 승낙했어요.',
  FINISH: '쿠폰 사용을 완료했어요.',
};

const UserHistoryItem = (props: UserHistoryItemProps) => {
  const { history, onClick } = props;
  const { imageUrl, meetingTime, nickname, couponEvent, couponType, isRead } = history;

  const meetingDateText = extractDate(meetingTime, true);

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
        <Styled.Date>{meetingDateText}</Styled.Date>
      </Styled.InformationContainer>
    </Styled.Root>
  );
};

export default UserHistoryItem;

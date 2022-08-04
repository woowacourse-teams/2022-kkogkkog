import { MouseEventHandler } from 'react';

import { COUPON_ENG_TYPE, COUPON_EVENT, COUPON_KOREAN_TYPE } from '@/types/client/coupon';
import { UserHistory } from '@/types/client/user';
import { generateDateText } from '@/utils';

import * as Styled from './style';

interface UserHistoryItemProps {
  history: UserHistory;
  onClick: MouseEventHandler<HTMLDivElement>;
}

/** Typing은 어려워 */
const couponTypeTextMapper: Record<COUPON_ENG_TYPE, COUPON_KOREAN_TYPE> = {
  COFFEE: '커피',
  DRINK: '술',
  MEAL: '식사',
};

const couponEventTextMapper: Record<COUPON_EVENT, string> = {
  INIT: '쿠폰을 보냈어요.',
  REQUEST: '쿠폰 사용을 요청했어요.',
  CANCEL: '쿠폰 사용을 취소했어요.',
  DECLINE: '쿠폰 사용을 거절했어요.',
  ACCEPT: '쿠폰 사용을 승인했어요.',
  FINISH: '쿠폰 사용을 완료했어요.',
};

const UserHistoryItem = (props: UserHistoryItemProps) => {
  const { history, onClick } = props;
  const { imageUrl, createdAt, nickname, couponEvent, couponType, isRead } = history;

  const createdAtText = generateDateText(createdAt, true);

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
        <Styled.Date>{createdAtText}</Styled.Date>
      </Styled.InformationContainer>
    </Styled.Root>
  );
};

export default UserHistoryItem;

import { couponEventTextMapper, couponTypeTextMapper } from '@/constants/coupon';
import { CouponHistory } from '@/types/coupon/client';
import { generateDateKR } from '@/utils/time';

import * as Styled from './style';

interface CouponHistoryItemProps {
  history: CouponHistory;
}

const CouponHistoryItem = (props: CouponHistoryItemProps) => {
  const { history } = props;
  const { imageUrl, createdTime, nickname, couponEvent, couponType, meetingMessage, meetingDate } =
    history;

  const createdTimeText = generateDateKR(createdTime, true);

  const meetingDateText = generateDateKR(meetingDate, true);

  const hasMeetingDateText = couponEvent === 'REQUEST';

  return (
    <Styled.Root>
      <Styled.CouponInfoContainer>
        <Styled.ProfileImageContainer>
          <img src={imageUrl} alt='프로필' width={50} height={50} />
        </Styled.ProfileImageContainer>
        <Styled.InformationContainer>
          <Styled.Contents>
            {nickname}님이 {hasMeetingDateText ? `${meetingDateText}로 ` : ''}
            {couponTypeTextMapper[couponType]}&nbsp;
            {couponEventTextMapper[couponEvent]}
          </Styled.Contents>
          <Styled.Date>{createdTimeText}</Styled.Date>
        </Styled.InformationContainer>
      </Styled.CouponInfoContainer>
      {meetingMessage && <Styled.Message>{meetingMessage}</Styled.Message>}
    </Styled.Root>
  );
};

export default CouponHistoryItem;

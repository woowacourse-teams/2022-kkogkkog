import { couponEventTextMapper, couponTypeTextMapper } from '@/constants/coupon';
import { CouponHistory } from '@/types/client/coupon';
import { generateDateText } from '@/utils/time';

import * as Styled from './style';

interface CouponHistoryItemProps {
  history: CouponHistory;
}

const CouponHistoryItem = (props: CouponHistoryItemProps) => {
  const { history } = props;
  const { imageUrl, createdTime, nickname, couponEvent, couponType, message, meetingDate } =
    history;

  const createdTimeText = generateDateText(createdTime, true);

  const meetingDateText = generateDateText(meetingDate, true);

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
      {message && <Styled.Message>{message}</Styled.Message>}
    </Styled.Root>
  );
};

export default CouponHistoryItem;

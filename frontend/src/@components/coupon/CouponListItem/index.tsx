import { couponEventTextMapper, couponTypeTextMapper } from '@/constants/coupon';
import { CouponHistory } from '@/types/client/coupon';
import { generateDateText } from '@/utils';

import * as Styled from './style';

interface UserHistoryItemProps {
  history: CouponHistory;
}

const UserHistoryItem = (props: UserHistoryItemProps) => {
  const { history } = props;
  const { imageUrl, createdTime, nickname, couponEvent, couponType } = history;

  const createdTimeText = generateDateText(createdTime, true);

  return (
    <Styled.Root>
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

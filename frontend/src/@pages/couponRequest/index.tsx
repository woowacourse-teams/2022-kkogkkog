import { css } from '@emotion/react';
import { useLocation, useNavigate } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import useInput from '@/@hooks/@common/useInput';
import useChangeCouponStatus from '@/@hooks/coupon/useChangeCouponStatus';
import { couponTypeTextMapper } from '@/constants/coupon';
import { PATH } from '@/Router';
import theme from '@/styles/theme';
import { CouponDetailResponse } from '@/types/remote/response';
import { getToday } from '@/utils';

import * as Styled from './style';

const isOverMaxLength = (value: string, maxLength: number): boolean => {
  return value.length >= maxLength;
};

const CouponRequestPage = () => {
  const navigate = useNavigate();
  const { coupon, isSent, couponId } = useLocation().state as {
    coupon: CouponDetailResponse;
    isSent: boolean;
    couponId: number;
  };

  const [meetingDate, onChangeMeetingDate] = useInput('');
  const [message, onChangeMessage] = useInput('', [(value: string) => isOverMaxLength(value, 200)]);

  const { requestCoupon } = useChangeCouponStatus(couponId);

  const { sender, receiver, couponType, couponStatus, couponHistories } = coupon;

  const onClickRequestButton = () => {
    requestCoupon(
      { meetingDate },
      {
        onSuccessCallback() {
          navigate(PATH.LANDING);
        },
      }
    );
  };

  return (
    <PageTemplate title='쿠폰' hasHeader={false}>
      <Styled.Root>
        <Styled.Top>
          <Position position='absolute' top='20px' left='20px'>
            <Icon
              iconName='arrow'
              size='20'
              color={theme.colors.primary_400}
              onClick={() => navigate(-1)}
            />
          </Position>
          <Styled.ProfileImage src={isSent ? receiver.imageUrl : sender.imageUrl} alt='' />
          <Styled.SummaryMessage>
            {isSent ? `${receiver.nickname}님에게 ` : `${sender.nickname}님이 `}보낸&nbsp;
            {couponTypeTextMapper[couponType]} 쿠폰
          </Styled.SummaryMessage>
        </Styled.Top>
        <Styled.Main>
          <Styled.SectionTitle>쿠폰을 사용하시겠어요?</Styled.SectionTitle>
          <Styled.Description>만나고 싶은 날짜를 지정해주세요.</Styled.Description>
          <Styled.DateInput
            type='date'
            value={meetingDate}
            min={getToday()}
            onChange={onChangeMeetingDate}
            required
          />
          <Styled.Description>메시지를 작성해주세요.</Styled.Description>
          <Position position='relative'>
            <Styled.MessageTextarea
              placeholder='시간, 장소 등 원하는 메시지를 보내보세요!'
              value={message}
              onChange={onChangeMessage}
            />
            <Position position='absolute' bottom='12px' right='12px'>
              <span>{message.length} / 200</span>
            </Position>
          </Position>
          <Position
            position='fixed'
            bottom='0'
            right='0'
            css={theme => css`
              width: 100%;
              display: flex;

              & > button + button {
                border-left: 1px solid ${theme.colors.grey_100};
              }
            `}
          >
            <Button onClick={onClickRequestButton} css={Styled.ExtendedButton}>
              사용 요청
            </Button>
          </Position>
        </Styled.Main>
      </Styled.Root>
    </PageTemplate>
  );
};

export default CouponRequestPage;

import { useNavigate, useParams } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import useInput from '@/@hooks/@common/useInput';
import { usePreventReload } from '@/@hooks/@common/usePreventReload';
import { useToast } from '@/@hooks/@common/useToast';
import { useFetchCoupon } from '@/@hooks/@queries/coupon';
import { useFetchMe } from '@/@hooks/@queries/user';
import useChangeCouponStatus from '@/@hooks/coupon/useChangeCouponStatus';
import NotFoundPage from '@/@pages/404';
import { couponTypeTextMapper } from '@/constants/coupon';
import { PATH } from '@/Router';
import theme from '@/styles/theme';
import { getToday, isBeforeToday } from '@/utils/time';
import { isOverMaxLength } from '@/utils/validations';

import * as Styled from './style';

const CouponRequestPage = () => {
  usePreventReload();

  const navigate = useNavigate();
  const { couponId } = useParams();

  const [meetingDate, onChangeMeetingDate] = useInput('', [isBeforeToday]);
  const [message, onChangeMessage] = useInput('', [(value: string) => isOverMaxLength(value, 200)]);

  const { me } = useFetchMe();
  const { coupon } = useFetchCoupon(Number(couponId));

  const { requestCoupon } = useChangeCouponStatus({
    id: Number(couponId),
    reservationId: coupon?.reservationId ?? null,
  });

  const { displayMessage } = useToast();

  if (!coupon) {
    return <NotFoundPage />;
  }

  const {
    senderId,
    senderNickname,
    senderImageUrl,
    receiverNickname,
    receiverImageUrl,
    couponType,
  } = coupon;

  const isSent = me?.id === senderId;

  const onClickRequestButton = () => {
    if (!meetingDate) {
      displayMessage('날짜를 지정해주세요.', true);

      return;
    }

    if (window.confirm('쿠폰 사용을 요청하시겠어요?')) {
      requestCoupon(
        { meetingDate, message },
        {
          onSuccessCallback() {
            if (isSent) {
              navigate(PATH.SENT_COUPON_LIST, { replace: true });
            } else {
              navigate(PATH.RECEIVED_COUPON_LIST, { replace: true });
            }
          },
        }
      );
    }
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
          <Styled.ProfileImage src={isSent ? receiverImageUrl : senderImageUrl} alt='' />
          <Styled.SummaryMessage>
            <strong>{isSent ? `${receiverNickname}님에게 ` : `${senderNickname}님이 `}보낸</strong>
            &nbsp;
            {couponTypeTextMapper[couponType]} 쿠폰
          </Styled.SummaryMessage>
        </Styled.Top>
        <Styled.Main>
          <Styled.SectionTitle>쿠폰을 사용하시겠어요? </Styled.SectionTitle>
          <Styled.Description>만남 날짜를 지정해보세요. (필수)</Styled.Description>
          <Styled.DateInput
            type='date'
            value={meetingDate}
            min={getToday()}
            data-placeholder='날짜 선택'
            onChange={onChangeMeetingDate}
            required
          />
          <Styled.Description>메시지를 작성해보세요. (선택)</Styled.Description>

          <Styled.TextareaContainer>
            <Styled.MessageTextareaContainer>
              <Styled.MessageTextarea
                id='message-textarea'
                placeholder='시간, 장소 등 원하는 메시지를 보내보세요!'
                value={message}
                onChange={onChangeMessage}
              />
              <Styled.MessageLength>{message.length} / 200</Styled.MessageLength>
            </Styled.MessageTextareaContainer>
          </Styled.TextareaContainer>

          <Position position='fixed' bottom='0' right='0' css={Styled.ExtendedPosition}>
            <Button onClick={onClickRequestButton} css={Styled.ExtendedButton}>
              약속 요청하기
            </Button>
          </Position>
        </Styled.Main>
      </Styled.Root>
    </PageTemplate>
  );
};

export default CouponRequestPage;

import { useNavigate, useParams } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import useInput from '@/@hooks/@common/useInput';
import { usePreventReload } from '@/@hooks/@common/usePreventReload';
import { useFetchCoupon } from '@/@hooks/@queries/coupon';
import { useFetchMe } from '@/@hooks/@queries/user';
import useChangeCouponStatus from '@/@hooks/coupon/useChangeCouponStatus';
import NotFoundPage from '@/@pages/404';
import { couponTypeTextMapper } from '@/constants/coupon';
import { PATH } from '@/Router';
import theme from '@/styles/theme';
import { generateDateText } from '@/utils/time';
import { isOverMaxLength } from '@/utils/validations';

import * as Styled from '../request/style';

const CouponAcceptPage = () => {
  usePreventReload();

  const navigate = useNavigate();
  const { couponId } = useParams();

  const [message, onChangeMessage] = useInput('', [(value: string) => isOverMaxLength(value, 200)]);

  const { me } = useFetchMe();
  const { coupon } = useFetchCoupon(Number(couponId));

  const { acceptCoupon } = useChangeCouponStatus({
    id: Number(couponId),
    reservationId: coupon?.reservationId ?? null,
  });

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
    meetingDate,
  } = coupon;

  const isSent = me?.id === senderId;

  const onClickAcceptButton = () => {
    if (window.confirm('쿠폰 사용 요청을 승인하시겠어요?')) {
      acceptCoupon({
        onSuccessCallback() {
          navigate(PATH.LANDING);
        },
      });
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
          <Styled.SectionTitle>
            {generateDateText(meetingDate)}로 약속을 확정하시겠어요?
          </Styled.SectionTitle>

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
            <Button onClick={onClickAcceptButton} css={Styled.ExtendedButton}>
              사용 승인
            </Button>
          </Position>
        </Styled.Main>
      </Styled.Root>
    </PageTemplate>
  );
};

export default CouponAcceptPage;
